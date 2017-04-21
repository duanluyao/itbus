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

        },
        error: function () {
            return;
        }
    });
}

var editor;

function refresh() {
    // editor = editormd("editormd", {
    //     path: "/lib/editormd/lib/",// Autoload modules mode, codemirror, marked... dependents libs path
    //     imageUpload: false,
    //     readOnly: true,
    //     styleActiveLine: false
    // });

    var busId = getUrlParam('id');
    if (busId == undefined || busId == null)
        busId = 1;
    freshDetail(busId);
}

refresh();