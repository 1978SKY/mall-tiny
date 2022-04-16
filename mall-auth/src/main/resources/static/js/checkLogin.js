// 判断用户是否登录--判断cookie中会否存在username的值
$(function(){
  // 获取cookie的username，如果cookie中的username有值，则表示用户已经登录，否则未登录
  var username = getCookie('username');
  var login = document.querySelector('.loginname');
  if(username){
    // 用户已经登录，在顶部导航栏，显示用户的信息，及显示退出按钮
    var str = `<a id="loginbtn">你好，${username} 尊贵的VIP</a><a class="Htext-red" id="reBtn">退出</a>`;
    login.innerHTML = str;
    
    
    // 退出功能,点击退出
    var logout = document.querySelector('#reBtn');    
    logout.onclick = function(){
      // 使用layer的confirm弹出层，展示 确定 和 取消 的选项
      layer.confirm('你确定要退出吗？',{ btn:['确定','取消'] },
        // 第一个 确定 按钮点击触发的函数
        function(){
          // 删除cookie，退出登录状态
          delCookie('username');
          login.innerHTML = `<a id="loginbtn" href="./login.html">你好，请登录</a><a class="Htext-red" id="reBtn" href="./register.html">免费注册</a>`;
          layer.msg('退出成功',{icon:1,time:500})
        },
        // 第二个 取消 按钮 点击触发的函数
        function(){
          layer.msg('已取消',{icon:1,time:500})
          return false;
        }
      )
    }
  }
})