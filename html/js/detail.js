/**
 * Created by teeyoung on 2017/4/16.
 */

function freshDetail(busId) {
    $.ajax({
        type: 'get',
        url: "bus/detail",
        data: {id: busId},
        cache: false,
        dataType: 'json',
        success: function (data) {
            var date = new Date();
            date.setTime(data.updateTime);
            $("#title").text(data.busName);
            $("#content").text(data.busContent);
        },
        error: function () {
            return;
        }
    });
}

function refresh() {
    var busId = getUrlParam('id');
    if (busId == undefined || busId == null)
        busId = 1;
    freshDetail(busId);
}

refresh();