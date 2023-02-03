package io.github.dunwu.tool.image;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

/**
 * 图片处理参数
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2019-12-10
 */
@Data
@ToString
@Builder
public class ImageProperties implements Serializable {

    private static final long serialVersionUID = 1L;

    private Region source;

    /**
     * 图片宽度
     */
    private Integer width;

    /**
     * 图片高度
     */
    private Integer height;

    /**
     * 按指定尺寸强制缩放
     */
    private Boolean keepAspectRatio;

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

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Region {

        private Integer x;
        private Integer y;
        private Integer width;
        private Integer height;

    }

}
