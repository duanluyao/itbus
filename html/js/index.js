/**
 * Created by teeyoung on 2017/4/15.
 */

/**
 * 查询首页推荐的分类列表
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
                    $("#topBusLineList").append("<a href='javascript:void(0)' onclick=\"javascirpt:freshBusListByLineId(" + item.id + ", 1)\" class=\"list-group-item\">" + item.lineName + "</a>");
                } else {
                    $("#topBusLineList").append("<a href='javascript:void(0)' onclick=\"javascirpt:freshBusListByLineId(" + item.id + ", 1)\" class=\"list-group-item\">" + item.lineName + "</a>");
                }
            });
        },
        error: function () {
            return;
        }
    });
}

/**
 * 查询首页推荐的文章列表
 */
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
                    + " &nbsp;&nbsp; 赞:"
                    + item.upCount + " &nbsp;&nbsp; 踩:"
                    + item.downCount + " </p> <p><a class=\"btn btn-default\" href=\"/detail.html?id="
                    + item.id
                    + "\" role=\"button\">阅读文章</a></p> </div>");
            });
        },
        error: function () {
            return;
        }
    });
}

/**
 * 根据分类查询文章
 * @param lineId
 */
function freshBusListByLineId(lineId, pageId) {

    $("#pageNavigation").addClass("hidden");

    $.ajax({
        type: 'get',
        url: "bus/list",
        data: {
            lineId: lineId,
            pageId: pageId
        },
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
                    + " &nbsp;&nbsp; 赞:"
                    + item.upCount + " &nbsp;&nbsp; 踩:"
                    + item.downCount + " </p> <p><a target=\"_blank\" class=\"btn btn-default\" href=\"/detail.html?id="
                    + item.id
                    + "\" role=\"button\">阅读文章</a></p> </div>");
            });
        },
        error: function () {
            return;
        }
    });

    $.ajax({
        type: 'get',
        url: "bus/count",
        data: {
            lineId: lineId,
            pageId: pageId
        },
        cache: false,
        dataType: 'json',
        success: function (data) {
            var pageIdList = data.pageIdList;
            var currentPageId = data.currentPageId;
            $("#pageIdList").html("");

            if (pageIdList.length < 7) {
                jQuery.each(pageIdList, function (i, item) {
                    if (item == 0) {

                    } else {
                        $("#pageIdList").append("<li><a href='#' onclick=\"javascirpt:freshBusListByLineId("
                            + lineId + ", "
                            + item
                            + ")\">"
                            + item
                            + "</a></li>");
                    }
                });
            } else {
                jQuery.each(pageIdList, function (i, item) {
                    if (currentPageId == item) {
                        $("#pageIdList").append("<li class='active'><a href='#' onclick=\"javascirpt:freshBusListByLineId("
                            + lineId + ", "
                            + item
                            + ")\">"
                            + item
                            + "</a></li>");
                    } else if (i == 0 && currentPageId != 1) {
                        $("#pageIdList").append("<li> <a href='#' aria-label='Previous' onclick=\"javascirpt:freshBusListByLineId("
                            + lineId + ", "
                            + item
                            + ")\"> <span aria-hidden='true'>&laquo;</span> </a> </li>");
                    } else if (i == pageIdList.length - 1 && currentPageId != item) {
                        $("#pageIdList").append("<li> <a href='#' aria-label='Next' onclick=\"javascirpt:freshBusListByLineId("
                            + lineId + ", "
                            + item
                            + ")\"> <span aria-hidden='true'>&raquo;</span> </a> </li>");
                    } else {
                        $("#pageIdList").append("<li><a href='#' onclick=\"javascirpt:freshBusListByLineId("
                            + lineId + ", "
                            + item
                            + ")\">"
                            + item
                            + "</a></li>");
                    }
                });
            }


            $("#pageNavigation").removeClass("hidden");
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
        var pageId = getUrlParam('pageId');
        if (pageId == undefined || pageId == null) {
            pageId = 1;
        }
        freshBusListByLineId(parseInt(id), pageId);
    } else {
        freshTopBusList();
    }


}


refresh();