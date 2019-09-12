const formatTime = date => {
  const year = date.getFullYear()
  const month = date.getMonth() + 1
  const day = date.getDate()
  const hour = date.getHours()
  const minute = date.getMinutes()
  const second = date.getSeconds()

  return [year, month, day].map(formatNumber).join('/') + ' ' + [hour, minute, second].map(formatNumber).join(':')
}

const formatNumber = n => {
  n = n.toString()
  return n[1] ? n : '0' + n
}

/**
 * 方法封装之——wx.login获取code数据
 */
function login(){
  return new Promise(function (resolve,reject){
    wx.login({
      //小程序端Api获取code成功
      success:function(res){
        if(res.code){
          //将返回值存入Promise的resolve属性中
          resolve(res);
        }else{
          //将返回值存入Promise的reject属性中
          reject(res);
        }
      },
      //小程序端获取code登录调用失败
      fail:function(res){
        reject(res)
      }
    });
  });
}

/**
 * 封装方法之二——request,请求后台，携带参数
 */
function request(url,data={},method="POST",header="applocation/x-www-form-urlencoded"){
  console.log("util.js封装方法request被调用"+url);
  
  wx.showLoading({
    title: '加载中……',
  });

  return new Promise(function (resolve, reject){
    wx.request({
      url: url,
      data: data,
      header: {},
      method: method,
      header: {
        'Content-Type': header,
        'X-Nideshop-Token': wx.getStorageSync('token'),
        'openId':wx.getStorageSync('openId')
      },
      success: function(res) {
        wx.hideLoading();//隐藏上面的加载等待提示
        console.log(res)
        if(res.statusCode==200){
          //返回的res对象中statusCode是请求默认的，为200则代表请求并返回成功
          if(res.data.errno==123){
            //这里的errno编码是自定义的了
          }else{
            //成功获取到返回值
            resolve(res.data);
          }
        }else{
          //不为200代表请求成功，但是没返回成功，可以这样理解，哈哈，反正是不成功
          reject(res.errMsg);
        }
      },
      fail: function(res) {
        console.log("接口调用失败：" + res);
        reject("请求接口调用失败");
      }
    });
  });

}


/**
 * 封装方法之三——展示错误提示
 */
function showErrorToase(msg){
  wx.showToast({
    icon:"none",
    title: msg,
  })
}

module.exports = {
  formatTime: formatTime,
  login,
  request,
}
