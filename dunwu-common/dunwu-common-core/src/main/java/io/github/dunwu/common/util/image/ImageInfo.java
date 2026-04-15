package io.github.dunwu.common.util.image;

import lombok.Data;

/**
 * 图片信息
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2023-12-14
 */
@Data
public class ImageInfo {

    private Integer width;
    private Integer height;
    private Long size;
    private String format;

}
