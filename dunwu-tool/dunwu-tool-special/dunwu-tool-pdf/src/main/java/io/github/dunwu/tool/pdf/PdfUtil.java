package io.github.dunwu.tool.pdf;

import cn.hutool.core.io.IoUtil;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.tool.xml.XMLWorkerFontProvider;
import com.itextpdf.tool.xml.XMLWorkerHelper;
import com.openhtmltopdf.outputdevice.helper.BaseRendererBuilder;
import com.openhtmltopdf.pdfboxout.PdfRendererBuilder;
import io.github.dunwu.tool.io.ResourceUtil;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.helper.W3CDom;
import org.jsoup.nodes.Document;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileSystems;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static com.itextpdf.tool.xml.css.CSS.Property.FONT;

@Slf4j
public class PdfUtil {

    public static void createPdf(File htmlFile, File pdfFile) throws IOException {

        if (htmlFile == null || !htmlFile.exists()) {
            log.error("html 文件未指定或不存在！");
            return;
        }

        if (pdfFile == null) {
            log.error("pdf 文件未指定！");
            return;
        }

        // 加载 html
        Document document = Jsoup.parse(htmlFile, StandardCharsets.UTF_8.name());
        document.outputSettings().syntax(Document.OutputSettings.Syntax.html);

        //引入资源目录，可以单独引入css，图片文件等
        File resourceDir = ResourceUtil.toFile("classpath://");
        String baseUri = FileSystems.getDefault()
                                    .getPath(resourceDir.getAbsolutePath())
                                    .toUri()
                                    .toString();

        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(pdfFile);

            // pdf 渲染设置
            PdfRendererBuilder builder = new PdfRendererBuilder();
            builder.withUri(pdfFile.getAbsolutePath());
            builder.toStream(fos);
            builder.withW3cDocument(new W3CDom().fromJsoup(document), baseUri);
            // 设置字体
            String fontPath = resourceDir.getAbsolutePath() + File.separator + "fonts" + File.separator + "msyh.ttf";
            builder.useFont(new File(fontPath), "msyh",
                1, BaseRendererBuilder.FontStyle.NORMAL, true);
            builder.run();
        } finally {
            IoUtil.close(fos);
        }
    }

    public static File createPdf2(File pdfFile, String htmlContent) throws IOException {

        // step 1
        com.itextpdf.text.Document document = new com.itextpdf.text.Document();

        // step 2
        FileOutputStream fos = null;
        PdfWriter writer = null;
        try {
            fos = new FileOutputStream(pdfFile);
            writer = PdfWriter.getInstance(document, fos);

            // step 3
            document.open();

            // step 4
            XMLWorkerFontProvider fontImp = new XMLWorkerFontProvider(XMLWorkerFontProvider.DONTLOOKFORFONTS);
            fontImp.register(FONT);
            XMLWorkerHelper.getInstance().parseXHtml(writer, document,
                new ByteArrayInputStream(htmlContent.getBytes(StandardCharsets.UTF_8)), null, StandardCharsets.UTF_8,
                fontImp);

            // step 5
            document.close();

            return pdfFile;
        } catch (DocumentException e) {
            log.error("pdf 文件 {} 未找到！", pdfFile.getAbsolutePath(), e);
            return null;
        } finally {
            IoUtil.close(fos);
        }
    }

}


