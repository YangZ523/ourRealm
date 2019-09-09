package com.ourrealm.boys.YZ.Controllers.AppControllers;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ourrealm.boys.YZ.utils.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/getJsonDataCont")
public class getJsonDataController {

    /**
     * 获取手机端上传的json数据，string,json数组
     */
    @ResponseBody
    @RequestMapping(value = "/getUserArrInfo",method = RequestMethod.POST)
    public Object getUserArrInfo (@RequestBody JSONObject obj){
        Map<String,Object> resultMap=new HashMap<>();
        try {

            /**
             * 分析：在调用JSON.parse(str)时，str非标准json格式的字符串
             *
             * 解决方案：将str转为标准格式的json字符串,即【{"k":"v","k":"v","k":"v"...............................
             */

            //解析json字符串,通过key获取obj中的value
            String data=obj.toJSONString();//所以这里先转换成字符串
            obj= JSON.parseObject(data);//再转换成json对象
            String userArr=obj.getString("userArr");//再获取其中的数组的值
            String fromName=obj.getString("fromName");
            if(StringUtils.isNotEmpty(userArr)&&StringUtils.isNotEmpty(fromName)){
                //两个字符串都不为空，则进行逻辑开发
                System.out.println("获取到的来源姓名是fromName："+fromName);
                //对json数组的操作
                JSONArray userArray=JSONArray.parseArray(userArr);//数组值在此进行转换成json数组对象
                for (int i=0;i<userArray.size();i++){
                    System.out.println("数组中对象为："+userArray.get(i));
                    System.out.println("转换成json字符串："+JSONObject.toJSONString(userArray.get(i)));
                    System.out.println("转换成json对象："+JSONObject.parse(JSONObject.toJSONString(userArray.get(i))));
                    System.out.println("其中的值有userName:"+JSONObject.parseObject(JSONObject.toJSONString(userArray.get(i))).getString("userName"));
                }
                //操作完毕
                resultMap.put("code",0);
                resultMap.put("msg","操作成功");
            }else {
                resultMap.put("code",1);
                resultMap.put("msg","字段有空");
            }
            return resultMap;
        }catch (Exception e){
            e.printStackTrace();
            resultMap.put("code",1);
            resultMap.put("msg","运行时错误");
            return resultMap;
        }
    }

}
