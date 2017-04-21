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
            $("[name='editormd-markdown-doc']").text(data.busContent);

            var testEditormdView = editormd.markdownToHTML("test-editormd-view", {
                markdown        : data.busContent ,//+ "\r\n" + $("#append-test").text(),
                //htmlDecode      : true,       // 开启 HTML 标签解析，为了安全性，默认不开启
                htmlDecode      : "style,script,iframe",  // you can filter tags decode
                //toc             : false,
                tocm            : true,    // Using [TOCM]
                //tocContainer    : "#custom-toc-container", // 自定义 ToC 容器层
                //gfm             : false,
                //tocDropdown     : true,
                // markdownSourceCode : true, // 是否保留 Markdown 源码，即是否删除保存源码的 Textarea 标签
                emoji           : true,
                taskList        : true,
                tex             : true,  // 默认不解析
                flowChart       : true,  // 默认不解析
                sequenceDiagram : true  // 默认不解析
            });
        },
        error: function () {
            return;
        }
    });
}

var editor;

function refresh() {
    var busId = getUrlParam('id');
    if (busId == undefined || busId == null)
        busId = 1;
    freshDetail(busId);
}

refresh();