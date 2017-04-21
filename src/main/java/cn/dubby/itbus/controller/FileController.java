package cn.dubby.itbus.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Created by yangzheng03 on 2017/4/21.
 */
@RestController
@RequestMapping("file")
public class FileController {

    private static final Logger logger = LoggerFactory.getLogger(FileController.class);

    @Autowired
    private RedisTemplate<String, String> template;

    private static final String UPLOAD_PATH_KEY = "UPLOAD_PATH_KEY";

    @RequestMapping("upload")
    public Object upload(MultipartHttpServletRequest request) {
        Map<String, MultipartFile> multipartFileMap = request.getFileMap();

        for (Map.Entry<String, MultipartFile> entry : multipartFileMap.entrySet()) {
            String path = template.opsForValue().get(UPLOAD_PATH_KEY);
            String url = System.currentTimeMillis() + entry.getValue().getOriginalFilename();
            try {
                entry.getValue().transferTo(new File(path + url));
            } catch (IOException e) {
                logger.error("file upload error", e);
            }

            Map result = new HashMap();

            result.put("success", 1);
            result.put("url", "/upload/" + url);

            return result;
        }
        Map result = new HashMap();

        result.put("success", 0);
        result.put("message", "没有文件");

        return result;
    }

}
