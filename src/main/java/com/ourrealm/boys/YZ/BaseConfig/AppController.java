package com.ourrealm.boys.YZ.BaseConfig;


import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

/**
 * @author yangz
 * 微信端工具controlelr
 */
public class AppController {

    //获取得到微信端传递过来的参数
    public static String getJson(HttpServletRequest request) throws Exception{
        StringBuilder sb=new StringBuilder();//使用StringBuileder效率快，但是不线程安全
        BufferedReader br = new BufferedReader(new InputStreamReader(request.getInputStream(), "UTF-8"));
        String s;
        while ((s=br.readLine())!=null){
            //当读取到request里面有数据，就回继续循环下去
            sb.append(s);//将参数进行拼接
        }
        System.out.println("=======getJson==========\n" + sb.toString() + "\n=======getJson==========");
        return sb.toString();
    }


    /**
     * 操作失败返回的map定义
     */
    public static Map<String,Object> writerResultFailure(){
        Map<String,Object> map=new HashMap<String, Object>();
        map.put("code", ResultStatus.RESULT_STATUS_FAILURE);//失败的状态嘛
        map.put("msg",CodeEnum.getName(ResultStatus.RESULT_STATUS_FAILURE));//获取到操作失败的返回信息
        return map;
    }

    /**
     * 自定义错误返回信息
     */
    public static Map<String,Object> writeResultFailure(Object object){
        Map<String,Object> map=new HashMap<String, Object>();
        map.put("code", ResultStatus.RESULT_STATUS_FAILURE);
        map.put("msg",object);
        return map;
    }

    /**
     * 操作成功返回
     */
    public static Map<String, Object> writeResultRep() {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("code", ResultStatus.RESULT_STATUS_SUCCESS);
        map.put("msg", CodeEnum.getName(ResultStatus.RESULT_STATUS_SUCCESS));
        return map;
    }

    /**
     * 操作成功携带数据返回返回
     */
    public static Map<String, Object> writeResultRep(Object object) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("code", ResultStatus.RESULT_STATUS_SUCCESS);
        map.put("msg", CodeEnum.getName(ResultStatus.RESULT_STATUS_SUCCESS));
        map.put("data", object);
        return map;
    }

    /***自定义返回成功**/
    public static Map<String, Object> writeResultRep(Object object,String msg){
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("code", ResultStatus.RESULT_STATUS_SUCCESS);
        map.put("msg", msg);
        map.put("data", object);
        return map;
    }

    /**
     * 其它返回码  throws Exception
     */
    public static Map<String, Object> writeResultRep(int code, String msg) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("code", code);
        map.put("msg", msg);
        return map;
    }



}













