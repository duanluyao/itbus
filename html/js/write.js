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

function save(busLineId, busName, busContent, email) {
    $.ajax({
        type: 'post',
        url: "bus/save",
        data: {
            busLineId: busLineId,
            busName: busName,
            busContent: busContent,
            email: email
        },
        cache: false,
        dataType: 'json',
        success: function (data) {
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


function refresh() {
    freshBusLineList();

    $("#closeBtn").click(function () {
        $('#myModal').modal('hide');
        window.location.href = "detail.html?id=" + $("#id").text();
    });

    $("#saveBtn").click(function () {
        var busLineId = $('input:radio:checked').val();
        var busName = $("#busName").val();
        var busContent = $("[name='editormd-markdown-doc']").val();
        var email = $("#email").val();

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

        save(busLineId, busName, busContent, email);
    });
}


refresh();