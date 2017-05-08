/**
 * Created by teeyoung on 2017/5/5.
 */
function login() {
    var email = $("#inputEmail").val();
    var password = $("#inputPassword").val();
    $.ajax({
        type: 'post',
        url: "user/login",
        data: {
            email: email,
            password: password
        },
        cache: false,
        dataType: 'json',
        success: function (data) {
            window.location.href = "index.html";
        },
        error: function (data) {
           alert(data.responseText);
            return;
        }
    });
}

function resetPassword() {
    var email = $("#inputEmail").val();

    if(email == undefined || email == null) {
        alert("请填写邮箱");
        return;
    }

    $.ajax({
        type: 'post',
        url: "user/resetpassword",
        data: {
            email: email
        },
        cache: false,
        dataType: 'json',
        success: function (data) {
            alert("请查看邮件");
        },
        error: function (data) {
            alert(data.responseText);
            return;
        }
    });
}