package com.ourrealm.boys.YZ.BaseConfig;

import com.mysql.cj.util.StringUtils;
import com.ourrealm.boys.YZ.Models.User;
import com.ourrealm.boys.YZ.Models.WxToken;
import com.ourrealm.boys.YZ.Services.UserService;
import com.ourrealm.boys.YZ.Services.WxTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequestMapping("/YzBase")
@Controller
public class BaseController extends AppController {

    @Autowired
    private UserService userService;

    @Autowired
    private WxTokenService wxTokenService;

    /**
     * 返回json字符串
     * @return
     */
    @RequestMapping("/hello")
    public @ResponseBody String Hello(){
        return "hello,SpringBoot";
    }

    /**
     * 获取用户信息json对象进行展示
     * @return
     */
    @RequestMapping("getUserInfo")
    public @ResponseBody User getUserInfo(){
        return userService.getUserInfoById(2);
    }

    /**
     * 更新用户信息
     * @param id
     * @param userName
     * @return
     */
    @RequestMapping("updateUserInfo")
    public @ResponseBody
    Map<String,Object> updateUserInfo(int id, String userName){
        Map<String,Object> resultMap=new HashMap<>();
        try {
            if (StringUtils.isNullOrEmpty(userName)){
                resultMap.put("code",1);
                resultMap.put("msg","参数为空");
            }
            User user=userService.getUserInfoById(id);
            if (user==null){
                resultMap.put("code",1);
                resultMap.put("msg","对象为空");
            }
            user.setUserName(userName);
            userService.update(user);
            resultMap.put("code",0);
            resultMap.put("msg","修改成功");
            return resultMap;
        }catch (Exception e){
            e.printStackTrace();
            resultMap.put("code",1);
            resultMap.put("msg","出现错误");
            return resultMap;
        }
    }

    /**
     * 新增用户对象数据
     * @return
     */
    @RequestMapping("addUserInfo")
    public @ResponseBody Map<String ,Object> addUserInfo(){
        Map<String,Object> resultMap=new HashMap<>();
        try {
            User user=new User();
            user.setId(1);
            user.setUserName("杨曾");
            user.setUserAdress("天庭暗杀部队5组办公室");
            user.setUserAge(24);
            user.setUserSex('1');

            userService.addUserInfo(user);
            resultMap.put("code",0);
            resultMap.put("msg","添加成功");
            return resultMap;
        }catch (Exception e){
            e.printStackTrace();
            resultMap.put("code",1);
            resultMap.put("msg","出现错误");
            return resultMap;
        }
    }

    /**
     * 跳转thymeleaf模板页面
     * @param model
     * @return
     */
    @RequestMapping("/toThymeleafIndex")
    public String toThymeleafIndex(Model model){
        model.addAttribute("msg","springBoot集成thymeleaf");
        return "index";
    }

    /**
     * 注意的是，参数id也是请求路径的之一，缺失则无法请求到此接口，所以，不需要判断参数是否为空，只有可能是错误
     * 且且且，requestMapping中的参数的为多个时，顺序不能打乱，不然路径就不对了，也不能重载，对于同样的参数的请求路径来说
     * @param id
     * @return
     */
    @RequestMapping("getUserInfo/{id}")
    public @ResponseBody
    User getUserInfo(@PathVariable("id") int id){
        try {
            return userService.getUserInfoById(id);
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 根据时间保存数据库中所有的wxToken数据到redis,测试redis
     * @return
     */
    @RequestMapping("/setInfoToRedis")
    @ResponseBody
    public Map<String,Object> setInfoToRedis(){
        try {
            List<WxToken> wxTokenList=wxTokenService.findAll();
            return writeResultRep();
        }catch (Exception e){
            e.printStackTrace();
            return writeResultFailure("运行时错误");
        }
    }

}
