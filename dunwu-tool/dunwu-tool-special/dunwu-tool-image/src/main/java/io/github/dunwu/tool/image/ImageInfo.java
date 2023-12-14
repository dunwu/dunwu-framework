package io.github.dunwu.tool.image;

import lombok.Data;

/**
 * 图片信息
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @date 2023-12-14
 */
@Data
public class ImageInfo {

    private Integer width;
    private Integer height;
    private Long size;
    private String format;

}
