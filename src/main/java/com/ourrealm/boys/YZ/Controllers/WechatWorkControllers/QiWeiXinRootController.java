package com.ourrealm.boys.YZ.Controllers.WechatWorkControllers;

import com.alibaba.fastjson.JSONObject;
import com.ourrealm.boys.YZ.BaseConfig.AppController;
import com.ourrealm.boys.YZ.BaseConfig.WeixinConfig;
import com.ourrealm.boys.YZ.BaseConfig.memberInfo;
import com.ourrealm.boys.YZ.Models.WxToken;
import com.ourrealm.boys.YZ.Services.WxTokenService;
import com.ourrealm.boys.YZ.utils.QiYeWeixinHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.Date;

import static com.ourrealm.boys.YZ.utils.CommonUtil.httpsRequest;

@Controller
@RequestMapping("qiYeWxRoot")
public class QiWeiXinRootController extends AppController {

    @Value("${qiye.APPID}")
    private String APPID;

    @Value("${qiye.SECRET}")
    private String SECRET;

    @Value("${qiye.NOTIFY_URL_INFO}")
    private String NOTIFY_URL_INFO;

    @Autowired
    private WxTokenService wxTokenService;//保存凭证调接口用

    /**
     * 企业微信请求接口
     */
    @RequestMapping("QiYeLogin")
    public void QiYeLogin(HttpServletResponse response) throws IOException {
        String url="https://open.weixin.qq.com/connect/oauth2/authorize?appid=" + APPID+
                "&redirect_uri=" + URLEncoder.encode(NOTIFY_URL_INFO)+
                "&response_type=code" +
                "&scope=snsapi_userinfo" +
                "&state=YYBH#wechat_redirect";
        response.sendRedirect(url);
    }

    /**
     * 企业微信授权回调接口
     */
    @RequestMapping("toLogin1")
    public String toLogin1(HttpSession session, HttpServletResponse response, HttpServletRequest request, Model model){
        try {
            //获取请求过来的code
            String code=request.getParameter("code");//请求登录oauth2接口获取到的验证code，后续请求access_token必须的参数
            String state=request.getParameter("state");//请求标识，算是一个标记，前面自定义的参数值，可用作区分

            //111在这可以做一个access_token的查询，如果数据库中已经存储了access-token，就可以省略下一步获取Wxoauth2token对象，一个项目的access_token 是唯一的，当然也是有时效性的

            //根据企业微信的appid查询数据库中，该项目针对此企业微信是否有权限access_token，并判断是否过期
            WxToken wxToken=wxTokenService.getWxTokenByAppId(APPID,"3");

            if (wxToken==null){ //还没有授权存储过access_token，需要调接口获取凭证，并保存到持久层
                wxToken=wxTokenService.getNewTokenAndSaveOrUpdate(wxToken,APPID,SECRET);
                wxTokenService.addNewToken(wxToken);//保存数据库
            }else {//已经授权，存过access_token，还需要检测是否过期
                Date expire = wxToken.getExpire();//数据库中access_token的过期时间
                if (expire.before(new Date())){
                    //过期,重新获取存储一次（此处用调接口的底层方法进行实现一次）
                    JSONObject jsonObject=httpsRequest(WeixinConfig.GET_ACCESS_TOKEN,"GET",null);
                    if ("0".equals(jsonObject.getString("errcode"))){
                        //调接口成功
                        String token=jsonObject.getString("access_token");
                        Long date_=System.currentTimeMillis()+7200*1000;
                        wxToken.setToken(token);
                        wxToken.setExpire(new Date(date_));
                        wxTokenService.updateWxToken(wxToken);
                    }
                }//不走if既代表没过期……
            }

            //以下便是，根据数据库中存储的access_token查询用户信息
            String access_token_now=wxToken.getToken();
            if (access_token_now==null||"".equals(access_token_now)){
                System.out.println("access_token出现错误，请检测……");
                return "error";
            }
            //通过授权凭证和接口请求标识code获取用户的userId，后续查询详细个人信息需要,值得注意的是，是否是内部成员的区分，看方法详细
            memberInfo memberInfo=QiYeWeixinHelper.getMemberInfo(access_token_now,code);

            if (memberInfo!=null){
                //已经获取到用的用户信息，里面会包含userid或者openid
                if (memberInfo.getUserId()!=null){
                    //说明是内部成员
                    String requestUrl=WeixinConfig.GET_INFO_USERID;
                    requestUrl= requestUrl.replace("ACCESS_TOKEN",access_token_now);
                    requestUrl=requestUrl.replace("USERID",memberInfo.getUserId());

                    //通过access_token和具体的userid查询用户的详细信息
                    JSONObject jsonObject = httpsRequest(requestUrl, "GET", null);

                    String errcode=jsonObject.getString("errcode");//操作码
                    String errmsg=jsonObject.getString("errmsg");//操作信息

                    if(!"0".equals(errcode)&&!"ok".equals(errmsg)){
                        //授权登录获取通讯录用户信息失败,跳转到index页面显示授权失败(暂定）
                        model.addAttribute("flag","fail");
                        model.addAttribute("info","授权登录异常,请联系客服人员");
                        return "error";
                    }

                    //授权获取用户信息成功
                    String name=(String) jsonObject.get("name");//姓名
                    String sex= (String) jsonObject.get("gender");//性别1男，2女，0未定义
                    String email= (String) jsonObject.get("email");//邮箱
                    String mobile= (String) jsonObject.get("mobile");//手机号
                    String position= (String) jsonObject.get("position");//职务信息
                    String avatar= (String) jsonObject.get("avatar");//头像
                    String alias= (String) jsonObject.get("alias");//别名

                    //用户唯一标识符,根据userId查询用户授权表
                    String userId=memberInfo.getUserId();

                    /**
                     * 项目需要的业务逻辑，自行填写
                     */

                    //把微信用户信息保存到session
//                    request.getSession(true).setAttribute("weixinUserInfo",user);

                }
            }

            return "ceshi1";
        }catch (Exception e){
            e.printStackTrace();
            return "error";//跳转到测试页面
        }
    }

}
