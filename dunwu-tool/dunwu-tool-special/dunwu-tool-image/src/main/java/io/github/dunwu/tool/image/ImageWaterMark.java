package io.github.dunwu.tool.image;

import java.awt.image.BufferedImage;

/**
 * 图片水印
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2019-12-10
 */
public class ImageWaterMark {

    /**
     * 水印在图片中的位置
     */
    private WaterMarkPositions position;

    /**
     * 水印图像
     */
    private BufferedImage image;

    /**
     * 水印透明度
     */
    private Float opacity;

    public ImageWaterMark() {}

    public ImageWaterMark(WaterMarkPositions position, BufferedImage image, Float opacity) {
        this.position = position;
        this.image = image;
        this.opacity = opacity;
    }

    @Override
    public String toString() {
        return "ImageWaterMark{" +
            "position=" + position +
            ", image=" + image +
            ", opacity=" + opacity +
            '}';
    }

    public WaterMarkPositions getPosition() {
        return position;
    }

    public void setPosition(WaterMarkPositions position) {
        this.position = position;
    }

    public BufferedImage getImage() {
        return image;
    }

    public void setImage(BufferedImage image) {
        this.image = image;
    }

    public Float getOpacity() {
        return opacity;
    }

    public void setOpacity(Float opacity) {
        this.opacity = opacity;
    }

    /**
     * 水印位置枚举
     */
    public enum WaterMarkPositions {

        /**
         * 左上
         */
        TOP_LEFT,

        /**
         * 上中
         */
        TOP_CENTER,

        /**
         * 上右
         */
        TOP_RIGHT,

        /**
         * 中左
         */
        CENTER_LEFT,

        /**
         * 正中
         */
        CENTER,

        /**
         * 中右
         */
        CENTER_RIGHT,

        /**
         * 底左
         */
        BOTTOM_LEFT,

        /**
         * 底中
         */
        BOTTOM_CENTER,

        /**
         * 底右
         */
        BOTTOM_RIGHT
    }

}
