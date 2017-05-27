/**
 * Created by teeyoung on 2017/4/16.
 */
//获取url中的参数
function getUrlParam(name) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)"); //构造一个含有目标参数的正则表达式对象
    var r = window.location.search.substr(1).match(reg);  //匹配目标参数
    if (r != null) return unescape(r[2]);
    return null; //返回参数值
}

function getUrl() {
    return window.location.href;
}

function getCookie(name) {
    var cookieList = document.cookie.split(";");

    for (var i = 0; i < cookieList.length; ++i) {
        var item = cookieList[i];
        var cookiePair = item.split("=");

        if (name.trim() == cookiePair[0].trim()) {
            return cookiePair[1];
        }
    }
}

function checkLoginFromCookie() {
    var email = getCookie('LOGIN_EMAIL');
    if (email != null && email != undefined) {
        $("#nickname").removeClass("hidden");
        $("#nickname").text(email);
        $("#loginBtn").addClass("hidden");
        $("#userinfo").removeClass("hidden");
        return true;
    } else {
        $("#nickname").addClass("hidden");
        $("#nickname").text(email);
        $("#loginBtn").removeClass("hidden");
        $("#userinfo").removeClass("hidden");
        return false;
    }
}

function logout() {
    $.ajax({
        type: 'post',
        url: "user/logout",
        cache: false,
        success: function (data) {
            window.location.href = "index.html";
            commonRefresh();
        },
        error: function (XMLHttpRequest, textStatus, errorThrown) {
            console.log("logout error");
            console.log(textStatus);
            console.log(errorThrown);
            commonRefresh();
        }
    });
}

function commonRefresh() {
    checkLoginFromCookie();

    console.log("========================================================================================================================================================");
    console.log("本人所在的美团点评由于业务扩张，所以现在诚邀请各位大神来共同进步发展。美团点评旗下的美团，大众点评，猫眼电影等都可以内推，地点包括上海，北京。邮件请发送至yangzheng03@meituan.com。如果岗位咨询也可以发邮件咨询，希望能和各位共事~");
    console.log("========================================================================================================================================================");
}

commonRefresh();