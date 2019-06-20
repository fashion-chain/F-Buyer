package com.hottop.core.controller;

import com.hottop.core.config.BaseConfiguration;
import com.hottop.core.model.fileUpload.UploadFile;
import com.hottop.core.model.zpoj.bean.Image;
import com.hottop.core.model.zpoj.bean.Media;
import com.hottop.core.repository.fileUpload.UploadFileRepository;
import com.hottop.core.response.EResponseResult;
import com.hottop.core.response.Response;
import com.hottop.core.utils.CommonUtil;
import com.hottop.core.utils.ResponseUtil;
import com.hottop.core.utils.alioss.AliOssClient;
import org.apache.tomcat.util.http.fileupload.FileUpload;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * 图片上传控制器
 */
@RestController
public class ImageUploadController {

    private static Logger logger = LoggerFactory.getLogger(ImageUploadController.class);

    @Autowired
    private UploadFileRepository uploadFileRepository;


    /**
     * 统一图片上传
     *
     * @param file
     * @param to   文件传到哪里 PRODUCT
     * @return
     */
    @RequestMapping(path = "/imageUpload", method = RequestMethod.POST)
    public Response imageUpload(@RequestParam(name = "file") MultipartFile file,
                                @RequestParam(name = "to") String to) {
        AliOssClient.ECustomPath[] values = AliOssClient.ECustomPath.values();
        AliOssClient.ECustomPath path = null;
        boolean legalTo = false;
        for (AliOssClient.ECustomPath value : values) {
            if (value.getCustomPath().equalsIgnoreCase(to)) {
                legalTo = true;
                path = value;
                break;
            }
        }
        if(!legalTo) return ResponseUtil.createErrorResponse(EResponseResult.UploadFile_ERROR_PATH);
        try {
            Response response = AliOssClient.saveImg(file, path, null);
            if (response.getCode().equals(EResponseResult.OK.getCode())) {
                Image image = (Image) response.getData();
                image.setUrl(CommonUtil.imageSetUrlPrefix(image.getUrl()));
                //image 信息保存到数据库
                UploadFile uploadFile = new UploadFile();
                uploadFile.setFileJson(BaseConfiguration.generalGson().toJson(image));
                uploadFile.setFileType(Image.EMediaType.image.name());
                uploadFileRepository.save(uploadFile);
                return response;
            }
        } catch (Exception e) {
            logger.info("图片上传出错：{}", CommonUtil.printStackTraceElements(e.getStackTrace()));
        }
        return Response.ResponseBuilder.result(EResponseResult.ERROR_INTERVAL).data("图片上传出错").create();
    }

    public static void main(String[] args) {
        AliOssClient.ECustomPath[] values = AliOssClient.ECustomPath.values();
        String to = "PRODUCT";
        for (AliOssClient.ECustomPath value : values) {
            System.out.println(value.getCustomPath());
            if(value.getCustomPath().equalsIgnoreCase(to)){
                System.out.println("匹配到:" + to);
            }
        }

    }
}
