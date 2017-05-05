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