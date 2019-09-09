package com.ourrealm.boys.YZ.Controllers.AppControllers;

import com.mysql.cj.util.StringUtils;
import com.ourrealm.boys.YZ.utils.DateUtil;
import com.ourrealm.boys.YZ.utils.FileUtil;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RequestMapping("AppUploadFile")
@Controller
public class UploadFileController {

    /**
     * 手机端上传单例图片进行保存
     * 解析：因为使用RequestParam声明文件参数，其他的非文件参数尽管是字符串也需要用@RequestParam注解进行声明
     */
    @RequestMapping(value = "/sendPicture",method = RequestMethod.POST)
    @ResponseBody
    public Object sendPicture(HttpServletRequest request,
                              @RequestParam(value = "file",required = false)MultipartFile file,
                              @RequestParam(value = "fileName",required = false)String fileName){

        //声明一个返回结果参数Map
        Map<String,Object> resultMap=new HashMap<>();

        try {
            //对于正常的string类型参数，进行操作如平常，无区别
            if (StringUtils.isNullOrEmpty(fileName)){
                System.out.println("上传的图片的名字是"+fileName);
            }else {
                resultMap.put("code",1);
                resultMap.put("msg","图片名称为空");
                return resultMap;
            }

            //上传图片
            byte[] bytes=file.getBytes();
            //生成照片文件名称，时间加5位随机数字加文件后缀名，也可以按照上传的，随意,注意的是，后缀要一致
            String newFileName=FileUtil.generateName(file.getOriginalFilename());

            String sep="/";

            //以下是表明上传图片的存储文件夹，如果不存在，创建一个
            String uri="upload/fileRes"+sep+ DateUtil.format(new Date(),"yyyyMMdd");
            String uploadDir=request.getRealPath("/")+uri;//此处根路径需要修改，参考：https://blog.csdn.net/piaoxuan1987/article/details/8541839
            File dirPath=new File(uploadDir);//根据路径声明文件夹对象
            if (!dirPath.exists()){
                //如果该文件夹不存在，创造存储文件夹
                dirPath.mkdirs();
            }

            //根据路径，声明上传文件对象
            File uploadFile=new File(uploadDir+sep+newFileName);
            //springFrameWork中的工具，将bytes中的字节按照uploadFile的文件对象进行复制粘贴，也就是输出
            FileCopyUtils.copy(bytes,uploadFile);
            //至此结束，至于图片的存储路径就是

            System.out.println("uploadFile的路径代表："+uploadDir+sep+newFileName);
            System.out.println("uploadDir的路径代表："+uploadDir);
            System.out.println("uri的路径代表："+uri);
            System.out.println("newFileName文件名字："+newFileName);

            resultMap.put("code",0);
            resultMap.put("msg","上传成功");
            return resultMap;
        }catch (Exception e){
            e.printStackTrace();
            resultMap.put("code",1);
            resultMap.put("msg","运行是错误");
            return resultMap;
        }

    }

    /**
     * 手机端上传多例图片进行保存
     */

    /**
     * 手机端上传单例文件进行保存
     */

    /**
     * 手机端上传多例文件进行保存
     */

}
