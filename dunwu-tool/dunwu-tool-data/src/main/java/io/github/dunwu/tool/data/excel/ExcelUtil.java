package io.github.dunwu.tool.data.excel;

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
import com.google.common.collect.Lists;
import io.github.dunwu.tool.data.mybatis.IExtDao;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

/**
 * Excel 工具类
 *
 * @author peng.zhang
 * @date 2022-01-24
 */
@Slf4j
public class ExcelUtil {

    /**
     * 导入Excel文件InputStream，并存储数据
     */
    public static <T> void saveExcelData(InputStream inputStream, Class<T> clazz, IExtDao<T> dao) {
        EasyExcel.read(inputStream, clazz, new ExcelDataStorageListener<>(dao)).sheet().doRead();
    }

    /**
     * 导出excel
     */
    public static void downloadExcel(HttpServletResponse response, List<Map<String, Object>> list) {
        downloadExcel(response, list, true);
    }

    /**
     * 导出excel
     */
    public static void downloadExcel(HttpServletResponse response, List<Map<String, Object>> list,
        boolean deleteOnExit) {

        String tempPath = System.getProperty("java.io.tmpdir") + IdUtil.fastSimpleUUID() + ".xlsx";
        File file = new File(tempPath);
        BigExcelWriter writer = cn.hutool.poi.excel.ExcelUtil.getBigWriter(file);

        //response为HttpServletResponse对象
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=utf-8");
        //test.xls是弹出下载对话框的文件名，不能为中文，中文请自行编码
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
            //此处记得关闭输出Servlet流
            IoUtil.close(out);
        }
    }

    /**
     * 根据传入的 list 列表，导出 excel 表单
     *
     * @param response {@link HttpServletResponse} 实体
     * @param list     {@link T} 列表
     * @param clazz    {@link T} 类型
     * @param fileName 文件名
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
        EasyExcel.write(response.getOutputStream(), clazz).sheet("数据").doWrite(list);
    }

    /**
     * 根据传入的 list 列表，导出 excel 表单
     *
     * @param response {@link HttpServletResponse} 实体
     * @param list     {@link T} 列表
     * @param clazz    {@link T} 类型
     */
    public static <T> void downloadEasyExcel(HttpServletResponse response, Collection<T> list, Class<T> clazz)
        throws IOException {
        downloadEasyExcel(response, list, clazz, null);
    }

    /**
     * 读取 Excel 内容，并转换为 Java 实体列表
     *
     * @param inputStream IO 流
     * @param clazz       将要转换的 Java 实体类型
     * @param <T>         泛型
     * @return /
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
            sheet.setSheetNo(1);
            sheet.setClazz(clazz);
            excelReader.read(sheet);
            // 解析每行结果在listener中处理
        } catch (Exception e) {
            log.error("文件{}读取失败 失败堆栈为{}", inputStream, e);
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

}
