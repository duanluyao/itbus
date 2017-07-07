/**
 * Created by teeyoung on 2017/5/5.
 */
function modifyPassword() {
    var inputPassword2 = $("#inputPassword2").val();
    var inputPassword = $("#inputPassword").val();

    if (inputPassword2 != inputPassword) {
        alert("两次输入的密码不相同");
        return;
    }
    $.ajax({
        type: 'post',
        url: "user/modify/password",
        data: {
            password: inputPassword
        },
        cache: false,
        dataType: 'json',
        success: function (data) {
            alert(data);
            window.location.href = "index.html";
        },
        error: function (data) {
           alert(data.responseText);
            return;
        }
    });
}