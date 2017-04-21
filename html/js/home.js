/**
 * Created by teeyoung on 2017/4/22.
 */

function toUpdateBus() {
    $('#myModal').modal('show');
}

function refresh() {
    $("#closeBtn").click(function () {
        $('#myModal').modal('hide');

        var inputBusId = $('#inputBusId').val();

        if(inputBusId == undefined || inputBusId == null || inputBusId == "") {
            alert("请输入文章ID");
            return;
        }

        window.location.href = "update.html?id=" + inputBusId;
    });
}

refresh();