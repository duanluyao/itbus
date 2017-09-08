package cn.dubby.itbus.controller;

import cn.dubby.itbus.util.Coder;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Hashtable;
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

    @RequestMapping(value = "qrcode")
    public Object getURLQRCode(String content) {
        try {
            Map<String, String> result = new HashMap<>();
            String contents = content;
            Hashtable<EncodeHintType, String> hints = new Hashtable<EncodeHintType, String>();
            hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
            BitMatrix matrix = null;

            matrix = new MultiFormatWriter().encode(contents, BarcodeFormat.QR_CODE, 300, 300, hints);

            String fileName = "qrcode/" + Coder.encryptBASE64(Coder.encryptMD5(content.getBytes())).trim() + ".png";
            File file = new File(template.opsForValue().get(UPLOAD_PATH_KEY) + fileName);
            if (file.exists()) {
                result.put("url", "/upload/" + fileName);
                return result;
            }

            MatrixToImageWriter.writeToPath(matrix, "png", file.toPath());


            result.put("url", "/upload/" + fileName);
            return result;
        } catch (Exception e) {
            logger.error("qrcode", e);
            return "";
        }
    }

    @RequestMapping("upload")
    public Object upload(MultipartHttpServletRequest request) {
        Map<String, MultipartFile> multipartFileMap = request.getFileMap();

        for (Map.Entry<String, MultipartFile> entry : multipartFileMap.entrySet()) {
            String path = template.opsForValue().get(UPLOAD_PATH_KEY);
            String url = System.currentTimeMillis() + entry.getValue().getOriginalFilename();
            try {
                File file = new File(path + url);
                file.setExecutable(true, false);
                file.setWritable(true, false);
                file.setReadable(true, false);
                entry.getValue().transferTo(file);
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
