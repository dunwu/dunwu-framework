package io.github.dunwu.common.dal.util;

import cn.hutool.json.JSONUtil;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.read.listener.ReadListener;
import com.alibaba.excel.util.ListUtils;
import io.github.dunwu.common.dal.mybatis.IExtDao;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * EasyExcel 数据入库监听器
 * <p>
 * 实现了 {@link ReadListener} 接口，用于在解析 Excel 文件时，
 * 将读取到的数据分批（默认每 100 条）批量存储到数据库中，以避免内存溢出（OOM）。
 *
 * @param <T> 实体类型
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2022-01-24
 */
@Slf4j
public class ExcelDataStorageListener<T> implements ReadListener<T> {

    /**
     * 批量存储阈值
     * <p>
     * 每隔 100 条数据存储一次数据库，然后清理缓存列表，释放内存。
     */
    private static final int BATCH_COUNT = 100;

    /**
     * 缓存的数据列表
     */
    private List<T> cachedDataList = ListUtils.newArrayListWithExpectedSize(BATCH_COUNT);

    /**
     * 数据访问对象，用于执行批量保存操作
     */
    private final IExtDao<T> dao;

    /**
     * 构造监听器实例
     * <p>
     * 如果使用了 Spring，请在创建 Listener 时将 Spring 管理的 DAO 实例传入。
     *
     * @param dao 数据访问对象实例
     */
    public ExcelDataStorageListener(IExtDao<T> dao) {
        this.dao = dao;
    }

    /**
     * 处理每一行解析到的数据
     * <p>
     * 将数据加入缓存列表，当缓存数量达到 {@link #BATCH_COUNT} 时，执行批量保存操作并清空缓存。
     *
     * @param data    当前行数据，等同于 {@link AnalysisContext#readRowHolder()}
     * @param context 分析上下文
     */
    @Override
    public void invoke(T data, AnalysisContext context) {
        if (log.isDebugEnabled()) {
            log.debug("【EasyExcel】解析到一条数据:{}", JSONUtil.toJsonStr(data));
        }

        cachedDataList.add(data);
        // 达到 BATCH_COUNT 了，需要去存储一次数据库，防止数据几万条数据在内存，容易 OOM
        if (cachedDataList.size() >= BATCH_COUNT) {
            saveData();
            // 存储完成清理 list
            cachedDataList = ListUtils.newArrayListWithExpectedSize(BATCH_COUNT);
        }
    }

    /**
     * 所有数据解析完成后的回调方法
     * <p>
     * 在此方法中保存最后遗留的数据，确保所有数据都存储到数据库中。
     *
     * @param context 分析上下文
     */
    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {
        // 这里也要保存数据，确保最后遗留的数据也存储到数据库
        saveData();
        log.info("【EasyExcel】所有数据解析完成！");
    }

    /**
     * 执行批量数据存储操作
     * <p>
     * 调用 DAO 的批量保存方法将缓存中的数据写入数据库。
     */
    private void saveData() {
        log.info("【EasyExcel】解析完成 {} 条数据，开始存储数据库...", cachedDataList.size());
        dao.saveBatch(cachedDataList);
        log.info("【EasyExcel】存储数据库成功！");
    }

}
