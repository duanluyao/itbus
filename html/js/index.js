/**
 * Created by teeyoung on 2017/4/15.
 */

function freshTopBusLineList() {
    $.ajax({
        type: 'get',
        url: "line/index",
        cache: false,
        dataType: 'json',
        success: function (data) {
            jQuery.each(data, function (i, item) {
                console.log(JSON.stringify(item));
                if (i == 0) {
                    // $("#topBusLineList").append("<a href=\"#\" class=\"list-group-item active\">" + item.lineName + "</a>");
                    $("#topBusLineList").append("<a href='javascript:void(0)' onclick=\"javascirpt:freshBusListByLineId(" + item.id + ")\" class=\"list-group-item\">" + item.lineName + "</a>");
                } else {
                    $("#topBusLineList").append("<a href='javascript:void(0)' onclick=\"javascirpt:freshBusListByLineId(" + item.id + ")\" class=\"list-group-item\">" + item.lineName + "</a>");
                }
            });
        },
        error: function () {
            return;
        }
    });
}


function freshTopBusList() {
    $.ajax({
        type: 'get',
        url: "bus/index",
        cache: false,
        dataType: 'json',
        success: function (data) {
            jQuery.each(data, function (i, item) {
                var date = new Date();
                date.setTime(item.updateTime);
                $("#busList").append("<div class=\"col-md-12 col-sm-12 col-xs-12 col-lg-6\"> <h4>"
                    + item.busName
                    + "</h4> <p>" + date.toLocaleDateString()
                    + " &nbsp;&nbsp; <span class=\"glyphicon glyphicon-thumbs-up\" aria-hidden=\"true\"></span>:"
                    + item.upCount + " &nbsp;&nbsp; <span class=\"glyphicon glyphicon-thumbs-down\" aria-hidden=\"true\"></span>:"
                    + item.downCount + " </p> <p><a target=\"_blank\" class=\"btn btn-default\" href=\"/detail.html?id="
                    + item.id
                    + "\" role=\"button\">阅读文章</a></p> </div>");
            });
        },
        error: function () {
            return;
        }
    });
}

function freshBusListByLineId(lineId) {
    $.ajax({
        type: 'get',
        url: "bus/list",
        data: {lineId: lineId},
        cache: false,
        dataType: 'json',
        success: function (data) {
            $("#busList").html("");
            jQuery.each(data, function (i, item) {
                var date = new Date();
                date.setTime(item.updateTime);
                $("#busList").append("<div class=\"col-md-12 col-sm-12 col-xs-12 col-lg-6\"> <h4>"
                    + item.busName
                    + "</h4> <p>" + date.toLocaleDateString()
                    + " &nbsp;&nbsp; <span class=\"glyphicon glyphicon-thumbs-up\" aria-hidden=\"true\"></span>:"
                    + item.upCount + " &nbsp;&nbsp; <span class=\"glyphicon glyphicon-thumbs-down\" aria-hidden=\"true\"></span>:"
                    + item.downCount + " </p> <p><a target=\"_blank\" class=\"btn btn-default\" href=\"/detail.html?id="
                    + item.id
                    + "\" role=\"button\">阅读文章</a></p> </div>");
            });
        },
        error: function () {
            return;
        }
    });
}

function refresh() {
    freshTopBusLineList();

    var id = getUrlParam('id');
    if (id != undefined && id != null) {
        freshBusListByLineId(id);
    } else {
        freshTopBusList();
    }




}


refresh();