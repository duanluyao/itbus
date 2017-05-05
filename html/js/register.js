/**
 * Created by teeyoung on 2017/5/5.
 */
function register() {
    var email = $("#inputEmail").val();
    var password1 = $("#inputPassword1").val();
    var password2 = $("#inputPassword2").val();
    var inputInvitationCode = $("#inputInvitationCode").val();

    if (password1 != password2) {
        alert("两次输入的密码不相同");
        return;
    }

    $.ajax({
        type: 'post',
        url: "user/register",
        data: {
            email: email,
            password: password1,
            invitationCode: inputInvitationCode
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