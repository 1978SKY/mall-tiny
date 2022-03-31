/**
 * 封装js操作cookie 的党方法 
 * 
 */

// 封装设置cookie的方法
/**
 * @description: 设置cookie
 * @param {name}  cookie的名字
 * @param {value} cookie的值
 * @param {expires} cookie的有效时间
 * @return {string} 设置cookie成功
 */
function setCookie(name,value,expires){
  let time = new Date();  
  time.setTime(time.getTime()-8*60*60*1000 + expires*1000)
  document.cookie = `${name}=${value};expires=${time}`
}

// 封装读取cookie的方法
/**
 * @description: 读取cookie的方法
 * @param {key} 读取的cookie的名字
 * @return {string}  返回对应key的cookie值
 */
function getCookie(key){

  // age=18; hobby=code; like=ctrl; love=life
  // 将字符串转为数组，通过炸裂的方式
  let arr =  document.cookie.split(";")
  // ["age=18", " hobby=code", " like=ctrl", " love=life"]
  // 通过循环遍历每一个元素，然后进行比较
  var str = "";
  for(let i = 0;i<arr.length;i++){
      // console.log(arr[i].indexOf(key))
      if(arr[i].indexOf(key) != -1){
        str = arr[i];
      }
  }
  // 如果str还是空字符串，这在cookie对应的key找不到
  if(!str){
    return '';
  }
  // 通过炸裂的方式, = 则后一部分就是我们要获取的值
  // console.log(str.split("="));
  return str.split("=")[1];
}

// 封装删除cookie
/**
 * @description: 删除cookie中key的值
 * @param {*} key 需要删除的键名
 * @return {*}
 */
function delCookie(key){
  // 调用设置cookie将 对应的cookie设置失效
  setCookie(key,1,-1);
}