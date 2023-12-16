package io.github.dunwu.tool.pdf;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.io.FileUtil;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.util.Map;

public class FreemarkerUtilTest {

    @Test
    public void test() throws IOException {

        OrderInfo orderInfo = OrderInfo.builder()
                                       .title("账单信息")
                                       .partner("合作伙伴XXX")
                                       .settlementContent("结算内容XXX")
                                       .settlementProduct("结算产品XXX")
                                       .build();
        Map<String, Object> modelMap = BeanUtil.beanToMap(orderInfo);

        String pdfFilePath = "D:\\test.pdf";
        String htmlFilePath = "D:\\temp.html";
        File pdfFile = new File(pdfFilePath);
        File htmlFile = null;
        try {
            // 基于 freemark 模板生成 html
            htmlFile = FreemarkerUtil.getMergedFile(modelMap, "template.ftl", htmlFilePath);
            Assertions.assertThat(htmlFile).isNotNull();
            Assertions.assertThat(htmlFile.exists()).isTrue();

            PdfUtil.createPdf(htmlFile, pdfFile);
            Assertions.assertThat(pdfFile).isNotNull();
            Assertions.assertThat(pdfFile.exists()).isTrue();

            // createPdf2(pdfFile, htmlContent);
        } finally {
            FileUtil.del(pdfFile);
            FileUtil.del(htmlFile);
        }
    }

}
