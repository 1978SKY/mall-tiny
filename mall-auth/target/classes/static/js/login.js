var register = document.querySelector('#rBtn');
window.onload = function () {
    register.onclick = function () {
        location.href = "http://localhost:8000/api/auth/register"
    }
}

function submitForm() {
    let user = {
        username: '',
        password: ''
    };
    user.username = document.getElementById('username').value;
    user.password = document.getElementById('password').value;
    if (user.username === "")
        alert("请输入用户名");
    if (user.password === "")
        alert("请输入密码");

    $.ajax({
        type: 'POST',
        datatype: 'json',
        url: 'http://localhost:8000/api/auth/login/doLogin',
        contentType: "application/json",
        data: JSON.stringify(user),
        success: function (data) {
            alert("success1:" + data);
            window.location.href = "http://localhost:88/api/product/index";
        },
        error: function () {
            alert("服务器异常!");
        }
    });
}