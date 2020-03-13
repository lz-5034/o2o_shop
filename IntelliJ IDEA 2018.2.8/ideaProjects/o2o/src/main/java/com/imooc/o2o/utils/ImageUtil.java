package com.imooc.o2o.utils;

import com.imooc.o2o.dto.ImageHolder;
import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.geometry.Positions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

/**
 * @author LZ
 * @date 2020/2/20 22:44:10
 * @description 图片工具类
 */
public class ImageUtil {

    private static String basePath = Thread.currentThread().getContextClassLoader().getResource("").getPath();
    private static final SimpleDateFormat SIMPLE_DATE_FORMAT = new SimpleDateFormat("yyyyMMddhhmmss");
    private static final Random RANDOM = new Random();
    private static Logger logger = LoggerFactory.getLogger(ImageUtil.class);


    /**
     * 将CommonsMultipartFile转换成File类
     * @param cFile
     * @return
     */
    public static File transferCommonsMultipartFileToFile(CommonsMultipartFile cFile) {
        File newFile = new File(cFile.getOriginalFilename());
        try {
            cFile.transferTo(newFile);  //将cFile文件流的内容写入新创建文件newFile
        } catch (IOException e) {
            logger.error(e.toString());
            e.printStackTrace();
        }
        return newFile;
    }

    /**
     * 处理缩略图，并返回新生成图片的相对路径
     * @param shopImg      封装了用户上传图片文件的  输入流和文件名
     * @param targetAddr   处理完图片后的路径
     * @param type   处理 缩略图片thumbnail 还是 普通的图片normal
     * @return
     */
    public static String generateThumbnail(ImageHolder shopImg, String targetAddr, String type) {
        String relativeAddr = "";
        try {
            //处理编码问题
            basePath= URLDecoder.decode(basePath,"UTF-8");
            targetAddr= URLDecoder.decode(targetAddr,"UTF-8");

            String realFileName = getRandomFileName();  //生成一个随机文件名
            String extension = getFileExtension(shopImg.getImageName()); //获取用户上传文件的扩展名
            makeDirPath(targetAddr);    //创建目标路径文件夹

            relativeAddr = targetAddr + realFileName + extension;    //目标文件的相对路径路径
            logger.debug("current relativeAddr is : "+relativeAddr);

            File dest = new File(PathUtil.getImgBasePath() + relativeAddr); //目标文件绝对路径
            logger.debug("current complete addr is : "+PathUtil.getImgBasePath() + relativeAddr);

            //生成水印
            if (type.equals("thumbnail")){  //处理缩略图水印
                Thumbnails.of(shopImg.getImage()).size(200,200)
                        .watermark(Positions.BOTTOM_RIGHT, ImageIO.read(new File(basePath+"/1.jpg")),0.25f)
                        .outputQuality(0.8f).toFile(dest);
            } else if (type.equals("normal")) { //处理正常图片水印
                Thumbnails.of(shopImg.getImage()).size(337,640)
                        .watermark(Positions.BOTTOM_RIGHT, ImageIO.read(new File(basePath+"/1.jpg")),0.25f)
                        .outputQuality(0.9f).toFile(dest);
            }
        } catch (IOException e) {
            logger.error(e.toString());
            e.printStackTrace();
        }
        return relativeAddr;
    }

    /**
     * 创建目标路径所涉及到的目录,即/home/work/lz/xxx.jpg
     * 则 home work lz 这三个文件夹都得自动创建
     * @param targetAddr
     */
    public static void makeDirPath(String targetAddr) {
        String realFilePath = PathUtil.getImgBasePath() + targetAddr; //获取绝对路径
        File file = new File(realFilePath);
        if (!file.exists()) {
            file.mkdirs();
        }
    }

    /**
     * 获取输入文件流的扩展名
     * @param fileName
     * @return
     */
    public static String getFileExtension(String fileName) {
        return fileName.substring(fileName.lastIndexOf("."));   //返回扩展名
    }

    /**
     * 生成一个随机的文件名,当前时间+五位随机数
     * @return
     */
    public static String getRandomFileName() {
        //生成五位随机数
        int num = RANDOM.nextInt(89999) + 10000;
        String nowTime = SIMPLE_DATE_FORMAT.format(new Date());
        return nowTime + num;
    }

    /**
     * 判断shopPath是文件的路径还是目录的路径
     * 如果shopPath是文件路径则删除该文件
     * 如果shopPath是目录路径则删除该目录下所有的文件
     * @param shopPath
     */
    public static void deleteFileOrPath(String shopPath) {
        File fileOrPath = new File(PathUtil.getImgBasePath() + shopPath);
        if (fileOrPath.exists()) {
            if (fileOrPath.isDirectory()) {
                File[] files = fileOrPath.listFiles();
                for (int i = 0; i < files.length; i++) {
                    files[i].delete();
                }
            }
            fileOrPath.delete();
        }
    }

    public static void main(String[] args) throws IOException {
        System.out.println(basePath);
        basePath= URLDecoder.decode(basePath,"UTF-8");
        Thumbnails.of(new File("D:/2.jpeg")).size(200,200)
                .watermark(Positions.BOTTOM_RIGHT, ImageIO.read(new File(basePath+"/1.jpg")),0.25f)
                .outputQuality(0.8f).toFile("d:/2new.jpeg");
    }
}
