
const util=require('util.js');
const config=require('config.js');


/**
 * 调用后台的微信授权登录方法，获取token以及openid等相关信息
 */
function loginByWeixin(detail){
  
  let code=null;//请求access_token的凭证

  return new Promise(
    
    function (resolve,reject){
      return util.login().then((res)=>{

        /**
         * 写法解析：此处，先是调用了util的login方法，如果成功，将会从login的返回对象，既promise对象的resolve属性中获取到返回值
         * 同时，直接进行着之后的步骤，也就是赋值code给这里的code,而且是成功才会执行的方法
         */

        //调用了util中的login方法，获取到返回的Promise对象，成功后，得到传递过来的参数res，
        code=res.code;
        return detail;
        //此处的detail返回给了后续的成功继续执行方法then处
      }).then((detail)=>{
        //这里也是，以上的方法，成功了才会继续执行的方法
        //通过util的工具调用封装的request方法，相当于是ajax方法
        util.request(config.AuthLoginByWeixin, { code: code, detail: detail}).then(res=>{
          //请求 成功才会执行以下步骤
          if(res.errno===0){
            console.log("调用后台方法成功，存缓存……")
            //将返回的用户对象和token存入缓存中
            wx.setStorageSync('userInfo', res.data.userInfo);
            wx.setStorageSync('token', res.data.token);
            resolve(res);//将返回参数进行向下传递
          }else{
            //调用工具js中的展示错误方法
            util.showErrorToase(res.errmsg);//展示错误信息
            reject(res);
          }
        }).catch((err)=>{
          console.log("调用后台方法出错……")
          reject(err);
        });
      }).catch((err)=>{
        console.log("util.login()调用then后的方法出错……")
        reject(err);
      });
  }

  );

}

module.exports = {
  loginByWeixin: loginByWeixin,
}