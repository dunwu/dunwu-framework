package io.github.dunwu.tool.data.excel;

import cn.hutool.json.JSONUtil;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.read.listener.ReadListener;
import com.alibaba.excel.util.ListUtils;
import io.github.dunwu.tool.data.mybatis.IExtDao;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * ReadListener 实现类，用于将 EasyExcel 解析成功后的数据存储到数据库中
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @date 2022-01-24
 */
@Slf4j
public class ExcelDataStorageListener<T> implements ReadListener<T> {

    /**
     * 每隔100条存储数据库，然后清理list，方便内存回收
     */
    private static final int BATCH_COUNT = 100;
    private List<T> cachedDataList = ListUtils.newArrayListWithExpectedSize(BATCH_COUNT);
    private final IExtDao<T> dao;

    /**
     * 如果使用了spring,请使用这个构造方法。每次创建Listener的时候需要把spring管理的类传进来
     *
     * @param dao DAO 实例
     */
    public ExcelDataStorageListener(IExtDao<T> dao) {
        this.dao = dao;
    }

    /**
     * 这个每一条数据解析都会来调用
     *
     * @param data    one row value. Is is same as {@link AnalysisContext#readRowHolder()}
     * @param context {@link AnalysisContext}
     */
    @Override
    public void invoke(T data, AnalysisContext context) {
        if (log.isDebugEnabled()) {
            log.debug("【EasyExcel】解析到一条数据:{}", JSONUtil.toJsonStr(data));
        }

        cachedDataList.add(data);
        // 达到BATCH_COUNT了，需要去存储一次数据库，防止数据几万条数据在内存，容易OOM
        if (cachedDataList.size() >= BATCH_COUNT) {
            saveData();
            // 存储完成清理 list
            cachedDataList = ListUtils.newArrayListWithExpectedSize(BATCH_COUNT);
        }
    }

    /**
     * 所有数据解析完成了 都会来调用
     *
     * @param context {@link AnalysisContext}
     */
    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {
        // 这里也要保存数据，确保最后遗留的数据也存储到数据库
        saveData();
        log.info("【EasyExcel】所有数据解析完成！");
    }

    /**
     * 加上存储数据库
     */
    private void saveData() {
        log.info("【EasyExcel】解析完成 {} 条数据，开始存储数据库...", cachedDataList.size());
        dao.saveBatch(cachedDataList);
        log.info("【EasyExcel】存储数据库成功！");
    }

}
