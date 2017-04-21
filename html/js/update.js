/**
 * Created by teeyoung on 2017/4/17.
 */
function freshBusLineList() {
    $.ajax({
        type: 'get',
        url: "line/all",
        cache: false,
        dataType: 'json',
        success: function (data) {
            jQuery.each(data, function (i, item) {
                $("#busLines").append("<label class=\"radio-inline\">"
                    + "<input type=\"radio\" name=\"busLine\" id=\"busLine" + i + "\" value=\"" + item.id + "\"> " + item.lineName + ""
                    + "</label>");
            });
        },
        error: function () {
            return;
        }
    });
}

function save(busId, ticket, busLineId, busName, busContent) {
    $.ajax({
        type: 'post',
        url: "bus/update",
        data: {
            busId: busId,
            ticket: ticket,
            busLineId: busLineId,
            busName: busName,
            busContent: busContent
        },
        cache: false,
        dataType: 'json',
        success: function (data) {
            console.log(JSON.stringify(data));
            $("#id").text(data.id);
            $("#ticket").text(data.busTicket);
            $('#myModal').modal('show');
        },
        error: function () {
            console.log("error");
            return;
        }
    });
}

//  获取文章
function freshDetail(busId) {
    $.ajax({
        type: 'get',
        url: "bus/detail",
        data: {id: busId},
        cache: false,
        dataType: 'json',
        success: function (data) {
            $("[name='editormd-markdown-doc']").val(data.busContent);
            $("#busName").val(data.busName);
        },
        error: function () {
            return;
        }
    });
}


function refresh() {
    freshBusLineList();

    var busId = getUrlParam('id');
    if (busId == undefined || busId == null)
        return;

    freshDetail(busId);


    $("#closeBtn").click(function () {
        $('#myModal').modal('hide');
        window.location.href = "detail.html?id=" + $("#id").text();
    });

    $("#saveBtn").click(function () {
        var busLineId = $('input:radio:checked').val();
        var busName = $("#busName").val();
        var busContent = $("[name='editormd-markdown-doc']").val();
        var inputTicket = $("#inputTicket").val();

        if(busLineId == undefined || busLineId == null) {
            alert("请选择分类");
            return;
        }

        if(busName == undefined || busName == null || busName == "") {
            alert("标题不能为空");
            return;
        }

        if(busContent == undefined || busContent == null || busContent == "[TOC]") {
            alert("内容不能为空");
            return;
        }

        if(inputTicket == undefined || inputTicket == null || inputTicket == "") {
            alert("Ticket不能为空");
            return;
        }

        save(busId, inputTicket, busLineId, busName, busContent);
    });
}


refresh();