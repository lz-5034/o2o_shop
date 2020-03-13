package com.imooc.o2o.dto;

import java.io.InputStream;

/**
 * @author LZ
 * @date 2020/3/1 09:54:09
 * @description 封装上传的图片流和图片文件名
 */
public class ImageHolder {
    private String imageName;
    private InputStream image;

    public ImageHolder(String imageName, InputStream image) {
        this.imageName = imageName;
        this.image = image;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    public InputStream getImage() {
        return image;
    }

    public void setImage(InputStream image) {
        this.image = image;
    }
}
