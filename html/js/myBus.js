/**
 * Created by teeyoung on 2017/4/15.
 */

/**
 * 查询首页推荐的文章列表
 */
function freshMyBusList() {
    $.ajax({
        type: 'get',
        url: "bus/mine",
        cache: false,
        dataType: 'json',
        success: function (data) {
            jQuery.each(data, function (i, item) {
                var date = new Date();
                date.setTime(item.createTime);
                $("#busList").append("<div class=\"col-md-12 col-sm-12 col-xs-12 col-lg-12\"> <h4>"
                    + item.busName
                    + "</h4> " + date.toLocaleDateString()
                    + " &nbsp;&nbsp; 赞:"
                    + item.upCount + " &nbsp;&nbsp; 踩:"
                    + item.downCount + " &nbsp;&nbsp;  <a class=\"btn btn-default\" href=\"/detail.html?id="
                    + item.id
                    + "\" role=\"button\">阅读文章</a>"
                    + " &nbsp;&nbsp;<a class=\"btn btn-default\" href=\"/modifyBus.html?id="
                    + item.id
                    + "\" role=\"button\">修改文章</a> </div><br/>");
            });
        },
        error: function () {
            return;
        }
    });
}


function refresh() {
    freshMyBusList();
}


refresh();