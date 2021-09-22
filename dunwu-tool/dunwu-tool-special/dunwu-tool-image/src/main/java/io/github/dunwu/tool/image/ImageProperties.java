package io.github.dunwu.tool.image;

import java.io.Serializable;

/**
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2019-12-10
 */
public class ImageProperties implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 图片宽度
     */
    private Integer width;

    /**
     * 图片高度
     */
    private Integer height;

    /**
     * 宽度比例
     */
    private Double xscale;

    /**
     * 高度比例
     */
    private Double yscale;

    /**
     * 总比例，相当于将xscale和yscale都设为同比例
     */
    private Double scale;

    /**
     * 旋转角度，范围为[0.0, 360.0]
     */
    private Double rotate;

    /**
     * 压缩质量，范围为[0.0, 1.0]
     */
    private Double quality;

    /**
     * 图片格式，支持jpg,jpeg,png,bmp,gif
     */
    private ImageType format;

    /**
     * 水印信息
     */
    private ImageWaterMark imageWaterMark;

    public ImageProperties() {}

    public ImageProperties(Integer width, Integer height, Double scale, Double rotate,
        Double quality, ImageType format, ImageWaterMark imageWaterMark) {
        this.width = width;
        this.height = height;
        this.scale = scale;
        this.rotate = rotate;
        this.quality = quality;
        this.format = format;
        this.imageWaterMark = imageWaterMark;
    }

    public ImageProperties(Integer width, Integer height, Double xscale, Double yscale,
        Double rotate, Double quality, ImageType format,
        ImageWaterMark imageWaterMark) {
        this.width = width;
        this.height = height;
        this.xscale = xscale;
        this.yscale = yscale;
        this.rotate = rotate;
        this.quality = quality;
        this.format = format;
        this.imageWaterMark = imageWaterMark;
    }

    public ImageType getFormat() {
        return format;
    }

    public ImageProperties setFormat(ImageType format) {
        this.format = format;
        return this;
    }

    public Integer getHeight() {
        return height;
    }

    public ImageProperties setHeight(Integer height) {
        this.height = height;
        return this;
    }

    public Double getQuality() {
        return quality;
    }

    public ImageProperties setQuality(Double quality) {
        this.quality = quality;
        return this;
    }

    public Double getRotate() {
        return rotate;
    }

    public ImageProperties setRotate(Double rotate) {
        this.rotate = rotate;
        return this;
    }

    public Double getScale() {
        return scale;
    }

    public ImageProperties setScale(Double scale) {
        this.scale = scale;
        return this;
    }

    public ImageWaterMark getImageWaterMark() {
        return imageWaterMark;
    }

    public ImageProperties setImageWaterMark(ImageWaterMark imageWaterMark) {
        this.imageWaterMark = imageWaterMark;
        return this;
    }

    public Integer getWidth() {
        return width;
    }

    public ImageProperties setWidth(Integer width) {
        this.width = width;
        return this;
    }

    public Double getXscale() {
        return xscale;
    }

    public ImageProperties setXscale(Double xscale) {
        this.xscale = xscale;
        return this;
    }

    public Double getYscale() {
        return yscale;
    }

    public ImageProperties setYscale(Double yscale) {
        this.yscale = yscale;
        return this;
    }

}
