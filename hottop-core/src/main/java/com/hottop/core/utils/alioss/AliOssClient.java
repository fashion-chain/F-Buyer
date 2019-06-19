package com.hottop.core.utils.alioss;

import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.PutObjectResult;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.hottop.core.config.BaseConfiguration;
import com.hottop.core.model.zpoj.bean.Image;
import com.hottop.core.model.zpoj.bean.Media;
import com.hottop.core.response.EResponseResult;
import com.hottop.core.response.Response;
import com.hottop.core.utils.CommonUtil;
import com.hottop.core.utils.HttpUtil;
import com.hottop.core.utils.LogUtil;
import com.hottop.core.utils.ResponseUtil;
import lombok.Data;
import org.apache.commons.lang.StringUtils;
import org.hibernate.boot.jaxb.SourceType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.util.*;

/**
 * Created by lq on 2019/3/20.
 * 阿里oss客户端
 */
@Data
@Component
@PropertySource("classpath:business.properties")
public class AliOssClient {

    private static final Logger logger = LoggerFactory.getLogger(AliOssClient.class);

    /**
     * 阿里云oss服务 accessKeyId 固定值
     */
    private static String accessKeyId;

    @Value("${ali.oss.accessKeyId}")
    public void setAccessKeyId(String accessKeyId) {
        AliOssClient.accessKeyId = accessKeyId;
    }

    /**
     * 阿里云oss服务 accessKeySecret 固定值
     */
    private static String accessKeySecret;

    @Value("${ali.oss.accessKeySecret}")
    public void setAccessKeySecret(String accessKeySecret) {
        AliOssClient.accessKeySecret = accessKeySecret;
    }

    /**
     * 阿里云oss服务 endpoint地域节点 固定值
     */
    private static String endpoint;

    @Value("${ali.oss.endpoint}")
    public void setEndpoint(String endpoint) {
        AliOssClient.endpoint = endpoint;
    }

    /**
     * 阿里云oss服务 bucketName存储空间 可选
     */
    public static String bucketName;

    @Value("${ali.oss.bucketName}")
    public void setBucketName(String bucketName) {
        AliOssClient.bucketName = bucketName;
    }

    public static Long imageMaxSize = 20 * 1024 * 1024l;

    @Value("${ali.oss.image.max}")
    public void setImageMaxSize(Long imageMaxSize) {
        AliOssClient.imageMaxSize = imageMaxSize * 1024 * 1024l;
    }
    public static Long videoMaxSize = 30 * 1024 * 1024l;

    @Value("${ali.oss.video.max}")
    public void setVideoMaxSize(Long videoMaxSize) {
        AliOssClient.videoMaxSize = videoMaxSize * 1024 * 1024l;
    }

    /**
     * 阿里云OSS服务器地址
     * https://fashionet-test.oss-cn-beijing.aliyuncs.com
     */
    public static String aliOssUrl ;

    @Value("${ali.oss.urls}")
    public void setAliOssUrl(String aliOssUrl) {
        AliOssClient.aliOssUrl = aliOssUrl;
    }

    public static String getAliOssUrl() {
        String[] urls = aliOssUrl.split(",");
        return urls[new Random().nextInt(urls.length)];
    }

    /**
     * 自定义路径
     */
    public enum ECustomPath {
        PRODUCT("product"),
        IconService("iconService"),;
        private String customPath;

        private ECustomPath(String customPath) {
            this.customPath = customPath;
        }

        public String getCustomPath() {
            return customPath;
        }

        public void setCustomPath(String customPath) {
            this.customPath = customPath;
        }
    }

    /**
     * 向ali oss服务器上传对象
     *
     * @param bucketName
     * @param bucketUrl
     * @param inputStream
     * @return
     */
    public static void putObject(String bucketName, String bucketUrl, InputStream inputStream) throws Exception {
        OSSClient client = new OSSClient(endpoint, accessKeyId, accessKeySecret);
        try {
            PutObjectResult putObjectResult = client.putObject(bucketName, bucketUrl, inputStream);
            logger.info("ali object 上传结果：{}", putObjectResult);
        } catch (Exception e) {
            logger.info(CommonUtil.printStackTraceElements(e.getStackTrace()));
            throw e;
        } finally {
            client.shutdown();
        }
    }

    /**
     * 上传视频
     * @param file
     * @param path
     * @param updateVideoFileName
     * @return
     */
    public static Response saveVideo (MultipartFile file, ECustomPath path, @Nullable String updateVideoFileName) {
        if (file == null) {
            return ResponseUtil.createErrorResponse(EResponseResult.UploadFile_ERROR_FileNotNull);
        }
        if (file.getSize() > videoMaxSize) {
            return ResponseUtil.createErrorResponse(EResponseResult.UploadFile_ERROR_VideoMax);
        }
        String fileName = null;
        StringBuffer getFileInfoUrl = new StringBuffer();
        try {
            String originalFileName = file.getOriginalFilename();
            String fileSuffix = originalFileName.substring(originalFileName.lastIndexOf(".") + 1);
            String filePrefix = "";
            if (StringUtils.isEmpty(updateVideoFileName)) {
                filePrefix = UUID.randomUUID().toString().replaceAll("-", "");
                fileName = filePrefix + fileSuffix;
            } else {
                fileName = updateVideoFileName;
            }
            String bucketUrl = path.getCustomPath() + "/" + fileName;
            putObject(bucketName, bucketUrl, file.getInputStream());
            getFileInfoUrl.append("http://").append(bucketName).append(".")
                    .append(endpoint + "/").append(path.getCustomPath() + "/")
                    .append(fileName);
            //todo ...
        } catch (Exception e) {
            LogUtil.error(e.getStackTrace());
        }
        return null;
    }

    /**
     * 保存或者更新图片
     *
     * @param file
     * @param path
     * @param updatedImgFileName
     * @return
     * @throws Exception
     */
    public static Response saveImg(MultipartFile file, ECustomPath path, @Nullable String updatedImgFileName) throws Exception {
        if (file == null) {
            return ResponseUtil.createErrorResponse(EResponseResult.UploadFile_ERROR_FileNotNull);
        }
        if (file.getSize() > imageMaxSize) {
            return ResponseUtil.createErrorResponse(EResponseResult.UploadFile_ERROR_ImageMax);
        }
        //返回的文件访问路径，阿里云文件路径
        StringBuilder url = new StringBuilder();
        String src_file_path = "";
        String compress_file_path = "";
        File srcFile = null;
        File compressFile = null;
        Image image = null;
        String fileName_compress = null;
        try {
            String originalFileName = file.getOriginalFilename();
            String fileSuffix = originalFileName.substring(originalFileName.lastIndexOf(".") + 1);
            String filePrefix = "";
            if (StringUtils.isEmpty(updatedImgFileName)) {
                filePrefix = UUID.randomUUID().toString().replaceAll("-", "");
                fileName_compress = filePrefix + "." + fileSuffix;
            } else {
                fileName_compress = updatedImgFileName;
            }
            String fileName = filePrefix + "-img-source." + fileSuffix;
            //把文件保存到临时文件夹
            String tmpImgFilePath = Im4JavaUtils.getImageTempFilePath();
            src_file_path = tmpImgFilePath + File.separator + fileName;
            compress_file_path = tmpImgFilePath + File.separator + fileName_compress;
            //write2Local(file.getInputStream(), src_file_path);
            FileCopyUtils.copy(file.getInputStream(), new FileOutputStream(new File(src_file_path)));
            //压缩文件
            boolean isCompressSuccess = Im4JavaUtils.compressImage(src_file_path, compress_file_path, 80.0);
            if(isCompressSuccess == true ){
                compressFile = new File(compress_file_path);
            }else {
                compressFile = new File(src_file_path);
            }
            srcFile = new File(src_file_path);
            FileInputStream compressFileInputStream = new FileInputStream(compressFile);
            //发送到阿里云oss
            String bucketUrl = path.getCustomPath() + "/" + fileName_compress;
            putObject(bucketName, bucketUrl, compressFileInputStream);
            url.append("http://").append(bucketName).append(".")
                    .append(endpoint + "/").append(path.getCustomPath() + "/")
                    .append(fileName_compress);
            image = getImageInfo(url.toString(), bucketUrl, filePrefix);
            logger.info(String.format("上传图片的信息是：%s", BaseConfiguration.generalGson().toJson(image)));
            return Response.ResponseBuilder.result(EResponseResult.OK).data(image).create();
        } catch (Exception e) {
            e.printStackTrace();
            CommonUtil.printStackTraceElements(e.getStackTrace());
        } finally {
            if (compressFile != null) {
                compressFile.delete();
            }
            if (srcFile != null) {
                srcFile.delete();
            }
        }
        return Response.ResponseBuilder.result(EResponseResult.ERROR_INTERVAL).create();

    }

    /**
     * 根据阿里oss imageUrl获取图片信息
     *
     * @param imageUrl
     * @return
     */
    public static Image getImageInfo(String imageUrl, String imageUrl_relative, String uuid) {
        String resJson = HttpUtil.httpsRequest(imageUrl + "?x-oss-process=image/info", "GET", null);
        Gson g = new Gson();
        JsonObject jsonResult = g.fromJson(resJson, JsonObject.class);
        logger.info(String.format("从阿里云获取到的图片信息：%s", jsonResult.toString()));
        String format = jsonResult.getAsJsonObject("Format").get("value").getAsString();
        Integer width = jsonResult.getAsJsonObject("ImageWidth").get("value").getAsInt();
        Integer height = jsonResult.getAsJsonObject("ImageHeight").get("value").getAsInt();
        Image image = Image.ImageBuilder.initImage(Media.EMediaType.image, uuid, imageUrl_relative, format, width, height).create();
        return image;
    }

    //inputStream 写入到临时文件
    private static void write2Local(InputStream in, String filePath) throws IOException {
        int index;
        byte[] buff = new byte[4096];
        FileOutputStream os = new FileOutputStream(filePath);
        while ((index = in.read(buff)) != -1) {
            os.write(buff, 0, index);
            os.flush();
        }
        os.close();
        in.close();
    }

    //

/*    public static Response saveMultiFile(HttpServletRequest httpServletRequest, ECustomPath path) {
        MultipartHttpServletRequest multipartHttpServletRequest = (MultipartHttpServletRequest) httpServletRequest;
        Map<String, MultipartFile> fileMap = multipartHttpServletRequest.getFileMap();
        if (fileMap == null || fileMap.size() == 0) {
            return ResponseUtil.createErrorResponse(EResponseResult.COMMON_ERROR_FileUploadName);
        }
        Collection<MultipartFile> files = fileMap.values();
        boolean isImageMagicKAvailable = true;
        File compressFile = null;
        for (MultipartFile file : files) {
            if (isImageMagicKAvailable == true) {
                compressFile = Im4JavaUtils.compressImage(file);
            } else {
                compressFile =
            }
            if (isImageMagicKAvailable == true && compressFile.getName().contains("original")) isImageMagicKAvailable = false;
            //发送到阿里云oss
            String bucketUrl = path.getCustomPath() + "/" + compressFile.getName();
            try {
                putObject(bucketName, bucketUrl, new FileInputStream(compressFile));
                image = getImageInfo(url.toString(), bucketUrl, filePrefix);

            } catch (Exception e) {
                LogUtil.error(e.getStackTrace());
            }
            logger.info(String.format("上传图片的信息是：%s", BaseConfiguration.generalGson().toJson(image)));
        }
        return null;
    }*/


}
