package io.github.dunwu.common.dal.util;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.io.IoUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.poi.excel.BigExcelWriter;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.EasyExcelFactory;
import com.alibaba.excel.ExcelReader;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.excel.read.builder.ExcelReaderBuilder;
import com.alibaba.excel.read.metadata.ReadSheet;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.google.common.collect.Lists;
import io.github.dunwu.common.dal.mybatis.IExtDao;
import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Excel 处理工具类
 * <p>
 * 封装了基于 Hutool 和 EasyExcel 的常用 Excel 读写操作，
 * 支持大数据量导出、文件下载、流读取以及批量数据导入等功能。
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2022-01-24
 */
@Slf4j
public class ExcelUtil {

    /**
     * 导入 Excel 文件流，并将解析的数据批量存储到数据库中
     * <p>
     * 使用 {@link ExcelDataStorageListener} 进行分批处理，避免内存溢出。
     *
     * @param inputStream Excel 文件输入流
     * @param clazz       实体类类型
     * @param dao         数据访问对象，用于执行批量保存
     * @param <T>         实体类型
     */
    public static <T> void saveExcelData(InputStream inputStream, Class<T> clazz, IExtDao<T> dao) {
        EasyExcel.read(inputStream, clazz, new ExcelDataStorageListener<>(dao)).sheet().doRead();
    }

    /**
     * 导出 Excel 文件到 HTTP 响应流
     * <p>
     * 使用 Hutool 的 {@link BigExcelWriter} 将 List&lt;Map&gt; 数据写入 Excel 并下载。
     *
     * @param response HTTP 响应对象
     * @param list     包含数据的 Map 列表
     */
    public static void downloadExcel(HttpServletResponse response, List<Map<String, Object>> list) {
        downloadExcel(response, list, true);
    }

    /**
     * 导出 Excel 文件到 HTTP 响应流
     * <p>
     * 使用 Hutool 的 {@link BigExcelWriter} 将 List&lt;Map&gt; 数据写入 Excel 并下载。
     * 支持配置是否在导出完成后删除临时文件。
     *
     * @param response      HTTP 响应对象
     * @param list          包含数据的 Map 列表
     * @param deleteOnExit  是否在 JVM 退出时删除临时文件
     */
    public static void downloadExcel(HttpServletResponse response, List<Map<String, Object>> list,
        boolean deleteOnExit) {

        String tempPath = System.getProperty("java.io.tmpdir") + IdUtil.fastSimpleUUID() + ".xlsx";
        File file = new File(tempPath);
        BigExcelWriter writer = cn.hutool.poi.excel.ExcelUtil.getBigWriter(file);

        // response为HttpServletResponse对象
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=utf-8");
        // test.xls是弹出下载对话框的文件名，不能为中文，中文请自行编码
        response.setHeader("Content-Disposition", "attachment;filename=file.xlsx");

        ServletOutputStream out = null;
        try {
            out = response.getOutputStream();

            // 一次性写出内容，使用默认样式，强制输出标题
            writer.write(list, true);
            writer.flush(out, true);
            if (deleteOnExit) {
                // 终止后删除临时文件
                file.deleteOnExit();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            // 此处记得关闭输出Servlet流
            IoUtil.close(out);
        }
    }

    /**
     * 使用 EasyExcel 导出 Excel 文件到 HTTP 响应流
     * <p>
     * 支持指定文件名，并自动处理中文文件名的 URL 编码。
     *
     * @param response HTTP 响应对象
     * @param list     数据列表
     * @param clazz    实体类类型，用于映射表头
     * @param fileName 下载文件名（不含扩展名）
     * @param <T>      实体类型
     * @throws IOException 如果写入响应流失败
     */
    public static <T> void downloadEasyExcel(HttpServletResponse response, Collection<T> list, Class<T> clazz,
        String fileName) throws IOException {
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=utf-8");
        if (StrUtil.isBlank(fileName)) {
            fileName = "导出数据";
        }
        // 这里URLEncoder.encode可以防止中文乱码 当然和easyexcel没有关系
        String encodeFileName = URLEncoder.encode(fileName, "UTF-8").replaceAll("\\+", "%20");
        response.setHeader("Content-disposition", "attachment;filename*=utf-8''" + encodeFileName + ".xlsx");
        List<T> finalList;
        if (CollectionUtil.isEmpty(list)) {
            finalList = new ArrayList<>();
        } else {
            finalList = new ArrayList<>(list);
        }
        EasyExcel.write(response.getOutputStream(), clazz).sheet("数据").doWrite(finalList);
    }

    /**
     * 使用 EasyExcel 导出 Excel 文件到 HTTP 响应流
     * <p>
     * 使用默认文件名 "导出数据"。
     *
     * @param response HTTP 响应对象
     * @param list     数据列表
     * @param clazz    实体类类型
     * @param <T>      实体类型
     * @throws IOException 如果写入响应流失败
     */
    public static <T> void downloadEasyExcel(HttpServletResponse response, Collection<T> list, Class<T> clazz)
        throws IOException {
        downloadEasyExcel(response, list, clazz, null);
    }

    /**
     * 读取 Excel 输入流，并将其内容转换为 Java 实体列表
     * <p>
     * 使用 EasyExcel 读取第一个 Sheet 页的数据。
     *
     * @param inputStream Excel 文件输入流
     * @param clazz       目标实体类型
     * @param <T>         实体类型
     * @return 解析后的实体列表
     */
    public static <T> List<T> readExcel(InputStream inputStream, Class<T> clazz) {
        final List<T> dataList = Lists.newArrayList();
        try {
            AnalysisEventListener<T> listener = new AnalysisEventListener<T>() {
                @Override
                public void invoke(T obj, AnalysisContext context) {
                    dataList.add(obj);
                }

                @Override
                public void doAfterAllAnalysed(AnalysisContext context) { }
            };

            ExcelReaderBuilder builder = EasyExcelFactory.read(inputStream, listener);
            ExcelReader excelReader = builder.build();
            ReadSheet sheet = new ReadSheet();
            sheet.setSheetNo(0);
            sheet.setClazz(clazz);
            excelReader.read(sheet);
            // 解析每行结果在listener中处理
        } catch (Exception e) {
            log.error("excel 文件读取失败失败！", e);
        } finally {
            try {
                assert inputStream != null;
                inputStream.close();
            } catch (IOException e) {
                log.error("关闭资源失败", e);
            }
        }
        return dataList;
    }

    /**
     * 将数据列表转换为 Excel 格式的字节输出流
     * <p>
     * 使用 EasyExcel 生成 XLSX 格式的 Excel 内容。
     *
     * @param list  数据列表
     * @param clazz 实体类类型
     * @param <T>   实体类型
     * @return 包含 Excel 内容的 {@link ByteArrayOutputStream}
     */
    public static <T> OutputStream createExcelStream(List<T> list, Class<T> clazz) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        EasyExcel.write(outputStream, clazz).excelType(ExcelTypeEnum.XLSX).sheet("数据").doWrite(() -> list);
        return outputStream;
    }

}
