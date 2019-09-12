package com.ourrealm.boys.YZ.Controllers.MiniProjectControllers;

import com.alibaba.fastjson.JSONObject;
import com.ourrealm.boys.YZ.BaseConfig.*;
import com.ourrealm.boys.YZ.Models.MiniUser;
import com.ourrealm.boys.YZ.Services.MiniTokenService;
import com.ourrealm.boys.YZ.Services.MiniUserService;
import com.ourrealm.boys.YZ.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping(value = "/Yzwx/BaseController")
public class MiniBaseController extends AppController {


    @Autowired
    MiniUserService miniUserService;

    @Autowired
    MiniTokenService miniTokenService;

    /**
     * 小程序授权登录，保存用户信息
     */
    @RequestMapping("/login_by_weixin")
    @ResponseBody
    @Transactional
    public Object loginByWeixin(){

       try {
           //通过AppController中的获取request请求中数据方法得到用户的基本信息和code
           JSONObject jsonParam=this.getJsonRequest();

           //创建全部用户对象信息
           FullUserInfo fullUserInfo=null;

           //声明code,调接口凭证
           String code="";

           //判断request参数中是否有code,blank判断字符串非空，且无空白符
           if (!StringUtils.isBlank(jsonParam.getString("code"))){
               code=jsonParam.getString("code");//非空
           }

           //判断request参数中用户信息是否为null
           if(null!=jsonParam.getString("detail")){
               //调用alibaba的json对象封装到实体类中的方法，将参数封装到fullUserInfo对象中
               fullUserInfo=jsonParam.getObject("detail",FullUserInfo.class);
           }

           //判断是否获取到用户信息
           if (null==fullUserInfo){
               return toResponsFail("登录失败，无用户信息");
           }

           //声明返回结果集合
           Map<String,Object> resultMap=new HashMap<>();

           //声明小程序参数中全部用户信息中的用户基本信息对象
           UserInfo userInfo=fullUserInfo.getUserInfo();

           //调用微信code2Session接口获取openid等隐私信息
           //1.首先拼接请求路径携带相应参数
           String requestUrl=ApiUtils.getWebAccess(code);
           //2.通过工具类中的发送请求方法，获取接口返回参数
           JSONObject sessionData=HttpUtils.httpsRequest(requestUrl,"GET",null);
           //3.判断发送网络请求获取到的返回对象
           if (null==sessionData||StringUtils.isBlank(sessionData.getString("openid"))){
               return toResponsFail("登录失败，未能获取到openid");
           }

           //以上走通后，获取到用户的最新最全正确信息,开始存储到数据库中
           Date nowTime=new Date();
           MiniUser miniUser=miniUserService.findByOpenid(sessionData.getString("openid"));
           if (miniUser==null){
               //新用户，数据库中没有，创建保存
               miniUser=new MiniUser();
               miniUser.setUserOpenid(sessionData.getString("openid"));//用户openid
               miniUser.setUserNickname(userInfo.getNickName());//用户昵称
               miniUser.setUserSex(userInfo.getGender().toString());//性别，0未知，1男，2女
               miniUser.setUserImg(userInfo.getAvatarUrl());//头像路径
               miniUser.setUserAdress(userInfo.getProvince()+userInfo.getCity());//省+市
               miniUserService.save(miniUser);//保存用户到数据库

           }else{
               //数据库中已经存在此用户
               miniUser.setUserNickname(userInfo.getNickName());//用户昵称
               miniUser.setUserSex(userInfo.getGender().toString());//性别，0未知，1男，2女
               miniUser.setUserImg(userInfo.getAvatarUrl());//头像路径
               miniUser.setUserAdress(userInfo.getProvince()+userInfo.getCity());//省+市
               miniUserService.update(miniUser);//更新信息
           }
           //生成更新用户登录标识符
           Map<String, Object> tokenMap = miniTokenService.createToken(miniUser.getId());
           String token = (String) tokenMap.get("token");

           //最后验证用户信息和登录标识符信息
           if (userInfo==null||StringUtils.isBlank(token)){
               return toResponsFail("登录失败，token为空");
           }

           resultMap.put("token",token);
           resultMap.put("userInfo",userInfo);
           resultMap.put("userId",miniUser.getId());
           return toResponsSuccess(resultMap);
       }catch (Exception e){
           e.printStackTrace();
           return toResponsFail("代码出错");
       }

    }


    /**
     * 小程序获取手机号
     */


    /**
     * 小程序调微信支付
     */


    /**
     * 小程序微信支付回调函数
     */

    /**
     * 多种小程序普通接口S，传递各种参数以及接受各种参数
     */
}
