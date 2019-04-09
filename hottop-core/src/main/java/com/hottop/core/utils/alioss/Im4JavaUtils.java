package com.hottop.core.utils.alioss;

import com.hottop.core.utils.CommonUtil;
import org.im4java.core.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.util.Map;

/**
 * Created by lq on 2019/3/20.
 * imagemagick 工具类
 */
public class Im4JavaUtils {

    private static Logger logger = LoggerFactory.getLogger(Im4JavaUtils.class);

    enum CommentType {
        convert("转换处理"), composite("图片合成"), identify("图片信息");
        private String name;

        CommentType(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

    private static ImageCommand getImageCommand(CommentType commentType) {
        ImageCommand imageCommand = null;
        switch (commentType) {
            case convert:
                imageCommand = new ConvertCmd();
                break;
            case composite:
                imageCommand = new CompositeCmd();
                break;
            case identify:
                imageCommand = new IdentifyCmd();
                break;
        }
        //windows 下需要加这一行
        if (System.getProperty("os.name").toLowerCase().startsWith("win")) {
            imageCommand.setSearchPath("");
        }
        return imageCommand;
    }

    /*
     获取上传文件temp临时文件夹
     */
    public static String getImageTempFilePath() {
        String result = "";
        try {
            File path = new File(ResourceUtils.getURL("classpath:").getPath());
            if (!path.exists()) path = new File("");
            File upload = new File(path.getAbsolutePath(), "static/images/temp/");
            if (!upload.exists()) upload.mkdirs();
            logger.info(String.format("图片存储临时目录:%s", upload.getAbsolutePath()));
            result = upload.getAbsolutePath();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 裁剪图片
     *
     * @param srcImagePath
     * @param newImagePath
     * @param width
     * @param height
     * @param x
     * @param y
     * @return
     */
    public static boolean cutImage(String srcImagePath, String newImagePath,
                                   Integer width, Integer height, Integer x, Integer y) {
        try {
            IMOperation operation = new IMOperation();
            operation.addImage(srcImagePath);
            operation.crop(width, height, x, y);
            operation.addImage(newImagePath);
            ImageCommand convert = getImageCommand(CommentType.convert);
            convert.run(operation);
            logger.info(String.format("imagemagick裁剪图片,%s,%s", srcImagePath, newImagePath));
        } catch (Exception e) {
            e.printStackTrace();
            logger.info(CommonUtil.printStackTraceElements(e.getStackTrace()));
            return false;
        }
        return true;
    }

    /**
     * 缩放图片
     *
     * @param srcImagePath
     * @param newImagePath
     * @param width
     * @param height
     * @return
     */
    public static boolean zoomImage(String srcImagePath, String newImagePath,
                                    Integer width, Integer height) {
        try {
            IMOperation op = new IMOperation();
            op.addImage(srcImagePath);
            op.resize(width, height);
            op.addImage(newImagePath);
            ImageCommand convert = getImageCommand(CommentType.convert);
            convert.run(op);
            logger.info(String.format("缩放图片成功，%s,%s", srcImagePath, newImagePath));
        } catch (Exception e) {
            CommonUtil.printStackTraceElements(e.getStackTrace());
            return false;
        }
        return true;
    }

    /**
     * 压缩图片
     *
     * @param srcImagePath 原图片地址
     * @param newImagePath 新图片地址
     * @param quality      压缩比例
     *                     图片压缩比，有效值范围是0.0-100.0，数值越大，缩略图越清晰。
     * @return
     */
    public static boolean compressImage(String srcImagePath, String newImagePath, Double quality) throws Exception {
        IMOperation op = new IMOperation();
        op.addImage(srcImagePath);
        op.quality(quality);
        op.addImage(newImagePath);
        ImageCommand convert = getImageCommand(CommentType.convert);
        convert.run(op);
        logger.info(String.format("压缩图片成功，%s,%s", srcImagePath, newImagePath));
        return true;
    }


    /**
     * 给图片添加水印
     *
     * @param srcImagePath
     * @param newImagePath
     * @param context
     * @return
     */
    public static boolean textImage(String srcImagePath, String newImagePath, String context) {
        try {
            IMOperation op = new IMOperation();
            op.addImage(srcImagePath);
            op.font("Arial").gravity("southeast").pointsize(60).fill("#F2F2F2").draw("text 10,10 " + context);
            op.addImage(newImagePath);
            ImageCommand convert = getImageCommand(CommentType.composite);
            convert.run(op);
            logger.info(String.format("压缩图片成功，%s,%s", srcImagePath, newImagePath));
        } catch (Exception e) {
            e.printStackTrace();
            logger.info(CommonUtil.printStackTraceElements(e.getStackTrace()));
            return false;
        }
        return true;
    }


    public static void main(String[] args) throws Exception {
        //测试裁剪图片
        String srcImagePath = "/Users/lq/Desktop/ht/hottop-backend/hottop-backstage/target/classes/static/images/temp/cad1e491b3fd4813ae21cefe7e928172-img-source.jpg";
        srcImagePath = "/Users/lq/Downloads/a.jpg";
        String newImagePath = "/Users/lq/Downloads/a1.jpg";
        cutImage(srcImagePath, newImagePath, 100, 100, 100, 100);

        //测试缩放图片
        String newImagePath2 = "/Users/lq/Downloads/a2.jpg";
        //zoomImage(srcImagePath,newImagePath2,400,500);

        //测试压缩图片
        String newImagePath3 = "/Users/lq/Downloads/a2.jpg";
        File file = new File(srcImagePath);
        boolean exists = file.exists();

        System.out.println(exists);
        //compressImage(srcImagePath,newImagePath3,80.0);


        //测试给图片添加水印
        String newImagePath4 = "f:\\4new.JPG";
        //textImage(srcImagePath,newImagePath4,"老婆我爱你");
    }

}
