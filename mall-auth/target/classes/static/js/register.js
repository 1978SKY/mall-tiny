var btn = document.querySelector(".head-btn");

btn.onclick = function () {

    // 注册请求pAjax，弹出加载遮罩层
    var index = layer.load(1, {
        shade: [0.5, '#666'] //0.1透明度的白色背景
    });
    btn.disabled = true;
    // 发送ajax
    var username = document.querySelector("[name='username']").value;
    var password = document.querySelector("[name='password']").value;
    var tel = document.querySelector("[name='tel']").value;
    pAjax({
        url: './php/register.php',
        dataType: 'json',
        data: {
            username,
            password,
            tel
        },
        type: "post"
    }).then(res => {
        // 接收到注册的请求响应，关闭遮罩层
        layer.close(index)
        // 结构赋值 接受返回的响应数据
        var {
            meta: {
                status,
                msg
            }
        } = res;
        var msgIndex = layer.msg(msg);
        if (status === 0) {
            // 注册成功，两秒后关闭提示成功注册的弹出层，并跳转登录页面
            setTimeout(() => {
                layer.close(msgIndex)
                location.href = 'login.html'
            }, 2000)
        } else {
            btn.disabled = false;
            return false;
        }
    })
};

let loginBtn = document.querySelector('#loginBtn');
window.onload = function () {
    loginBtn.onclick = function () {
        location.replace('./login.html')
    }
}
$(".head-btn").click(function () {
    //验证手机号
    var regtel = /^1[3-9]\d{9}$/;
    if ($(this).is("#phonenum")) {
        if ($(this).val() === "") {
            $('.errorphone').html("手机号必填").css('color', 'red');
        } else if (!regtel.test(parseInt($(this).val()))) {
            $('.errorphone').html("请输入正确的手机号").css('color', 'red');
        } else {
            $(this).parent().next().html(" 输入正确").css('color', 'green')
        }
    }
    // 验证用户名
    if ($(this).is("#username")) {
        var reguser = /^[a-zA-Z][a-zA-Z0-9]{2,7}$/;
        if ($(this).val() === "") {
            $('.erroruser').html("用户名必填").css('color', 'red');
        } else if (!reguser.test($(this).val())) {
            $('.erroruser').html("请输入规范的用户名").css('color', 'red');
        } else {
            $(this).parent().next().html(" 输入正确").css('color', 'green')
        }
    }

    if ($(this).is("#paw")) {
        var regpaw = /^[a-zA-Z0-9]{6,12}$/;
        if ($(this).val() === "") {
            $('.errorpaw').html("密码必填").css('color', 'red');
        } else if (!regpaw.test(parseInt($(this).val()))) {
            $('.errorpaw').html("请输入规范的密码").css('color', 'red');
        } else {
            $(this).parent().next().html(" 输入正确").css('color', 'green')
        }
    }

    if ($(this).is("#pawre")) {
        if ($(this).val() != $('#paw').val()) {
            $('.errorpawre').html("密码不一致").css('color', 'red')
        } else {
            $(this).parent().next().html(" 输入正确").css('color', 'green')
        }
    }
});