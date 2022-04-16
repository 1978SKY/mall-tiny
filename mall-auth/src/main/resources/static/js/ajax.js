/**
 * @description: ajax 函数 
 * @param {}  options 
 *  {url,type,data,async,dataType,function(){}}
 * @return {*}
 */
function ajax(options){
  // 判断url
  if(!options.url){
    // url必须传入
    // 手动抛出错误
    throw new Error("url必须传入");
  }
  // 定义一默认值
  const defInfo = {
    data:{},
    type:'get',
    async:true,
    dataType:'string',
    success:function(res){}
  }

  // 通过循环将传入的option替换到defIno中的值
  for(var key in options){
    defInfo[key] = options[key];
  }
  // 将data对象转换
  var str = '';
  if(defInfo.data){
    // 将defInfo.data循环遍历 拼接到str
    for(var key in defInfo.data){
      str += `${key}=${defInfo.data[key]}&`;      
    }
    str = str.slice(0,-1);
  }

  // 创建ajax对象
  let xhr = new XMLHttpRequest();
  // 此处只考虑 post 和get请求
  if(defInfo.type.toUpperCase() === "GET"){
    defInfo.url += "?"+str;
    xhr.open("get",defInfo.url,defInfo.async);
    xhr.send();
  }else{
    xhr.open("post",defInfo.url,defInfo.async);
    // 设置请求头
    xhr.setRequestHeader("Content-type","application/json; charset=utf-8");
    xhr.send(str);
  }

  // 接收响应
  xhr.onreadystatechange = function(){
    // 判断http和ajax状态码
    if(xhr.readyState === 4 && /^2\d{2}$/.test(xhr.status)){
      // 判断dataType
      var res = xhr.responseText;
      if(defInfo.dataType.toUpperCase() === "JSON"){
        res = JSON.parse(res);
      }
      // 调用成功函数，将结果传回
      defInfo.success(res);
    }
  }
}

// promise封装ajax
function pAjax(options){
  return new Promise(function(reslove,reject){
    ajax({
      url:options.url,
      type:options.type || "GET",
      data:options.data || {},
      async:options.async || true,
      dataType:options.dataType || "text",
      success:function(res){
        reslove(res)
      }
    })
  })
}