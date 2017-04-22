/**
 * Created by teeyoung on 2017/4/22.
 */
function freshTopBusLineList() {
    $.ajax({
        type: 'get',
        url: "line/all",
        cache: false,
        dataType: 'json',
        success: function (data) {
            $("#busLineList").html("");

            jQuery.each(data, function (i, item) {

                $("#busLineList").append("<a onclick='goToBusList("
                    + item.id
                    + ")' href=\"#\" class=\"btn btn-default col-md-3 col-sm-6 col-xs-12 col-lg-2\" style=\"margin-bottom: 6px\">"
                    + item.lineName
                    + "</a>"
                );

                // $("#privateBusLineList").append("<a href=\"#\" class=\"btn btn-default col-md-3 col-sm-6 col-xs-12 col-lg-2\" style=\"margin-bottom: 6px\">"
                //     + item.lineName
                //     + "</a>"
                // );

            });
        },
        error: function () {
            return;
        }
    });
}

function goToBusList(lineId) {
    if (lineId != undefined && lineId != null) {
        window.location.href = "index.html?id=" + lineId;
    }
}

function refresh() {
    freshTopBusLineList();

}


refresh();