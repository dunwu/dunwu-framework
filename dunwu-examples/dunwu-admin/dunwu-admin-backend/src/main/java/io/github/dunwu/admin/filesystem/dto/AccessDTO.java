package io.github.dunwu.admin.filesystem.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;

/**
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2019-07-23
 */
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class AccessDTO {

    private String ip;

    private Date date;

    private Integer count;

}
