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

function save(busLineId, busName, busContent) {
    $.ajax({
        type: 'post',
        url: "bus/save",
        data: {
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


function refresh() {
    freshBusLineList();

    $("#saveBtn").click(function () {
        var busLineId = $('input:radio:checked').val();
        var busName = $("#busName").val();
        var busContent = $("[name='editormd-markdown-doc']").val();

        save(busLineId, busName, busContent);
    })
}


refresh();