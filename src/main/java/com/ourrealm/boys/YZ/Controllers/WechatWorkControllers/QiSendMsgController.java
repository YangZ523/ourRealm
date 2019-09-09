package com.ourrealm.boys.YZ.Controllers.WechatWorkControllers;

import com.alibaba.fastjson.JSONObject;
import com.ourrealm.boys.YZ.BaseConfig.AppController;
import com.ourrealm.boys.YZ.Models.WxToken;
import com.ourrealm.boys.YZ.Services.WxTokenService;
import com.ourrealm.boys.YZ.utils.CommonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Date;
import java.util.Map;

@Controller
@RequestMapping(value = "qiYeWxRootSendMsg")
public class QiSendMsgController extends AppController {

    @Value("${qiye.APPID}")
    private String APPID;

    @Value("${qiye.SECRET}")
    private String SECRET;

    @Value("${qiye.AGENTID}")
    private String AGENTID;

    @Value("${qiye.TUI_MSG_URL}")
    private String TUI_MSG_URL;

    @Autowired
    private WxTokenService wxTokenService;

    /**
     * 企业应用，企业应用，企业应用推送消息给该企业的用户
     * @param userId 接收消息的企业微信用户的userid，唯一标识，如果发送给多人，可用"userId|userId|userId"进行隔开
     * @param contextMsg 推送的消息内容
     */
    @RequestMapping(value = "sendMsg")
    public Map<String,Object> sendMsgToUser(String contextMsg,String userId){
    try {
        //第一步，获取调接口的凭证，access_token
        WxToken wxToken=wxTokenService.getWxTokenByAppId(APPID,"3");
        if (wxToken==null){
            wxToken=wxTokenService.getNewTokenAndSaveOrUpdate(wxToken,APPID,SECRET);
        }else {
            if (wxToken.getExpire().before(new Date())){
                //需要更新access_token
                wxToken=wxTokenService.getNewTokenAndSaveOrUpdate(wxToken,APPID,SECRET);
            }
            //既存在又没过期，则……
        }

        //推送消息
        JSONObject jsonObject=new JSONObject();//填装参数，详细看文档
        jsonObject.put("touser",userId);
        jsonObject.put("msgtype","text");
        jsonObject.put("agentid",AGENTID);//应用的编号
        JSONObject content=new JSONObject();
        content.put("content",contextMsg);
        jsonObject.put("text",content);
        jsonObject.put("safe",0);//保密否
        jsonObject.put("enable_id_trans",0);
        String tuiStr=jsonObject.toJSONString();
        JSONObject jsonObjectTui = CommonUtil.httpsRequest(TUI_MSG_URL + wxToken.getToken(), "POST", tuiStr);//调接口
        System.out.println(jsonObjectTui);
        System.out.println(jsonObjectTui);
        System.out.println(jsonObjectTui);

        if ("0".equals(jsonObjectTui.getString("errcode"))&&"ok".equals(jsonObjectTui.getString("errmsg"))){
            return writeResultRep("接口调用成功，推送成功");
        }else {
            return writeResultFailure("接口调用成功，推送失败");
        }
    }catch (Exception e){
        e.printStackTrace();
        return writeResultFailure("接口调用失败");
    }
    }


}
