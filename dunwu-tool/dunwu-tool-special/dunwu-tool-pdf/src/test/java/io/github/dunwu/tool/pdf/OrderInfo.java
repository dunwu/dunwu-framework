package io.github.dunwu.tool.pdf;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderInfo {

    private String title;
    private String partner;
    private String settlementContent;
    private String settlementProduct;

}
