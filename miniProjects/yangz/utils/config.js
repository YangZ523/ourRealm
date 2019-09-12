//服务器域名
const API_BASE_URL = 'http://localhost:8080/yangz';

//外漏的静态后台请求路径
module.exports={
  AuthLoginByWeixin: API_BASE_URL + '/wx/BaseController/login_by_weixin', //微信登录，保存用户等信息
  GetUserPhoneInfo: API_BASE_URL + '/wx/BaseController/getUserPhoneInfo',//获取用户手机号信息

}