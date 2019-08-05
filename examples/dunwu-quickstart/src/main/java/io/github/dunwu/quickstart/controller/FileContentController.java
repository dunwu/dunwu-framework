package io.github.dunwu.quickstart.controller;

import io.github.dunwu.core.BaseResult;
import io.github.dunwu.core.DataListResult;
import io.github.dunwu.core.DataResult;
import io.github.dunwu.core.PageResult;
import io.github.dunwu.quickstart.dao.FileContentDao;
import io.github.dunwu.quickstart.entity.FileContent;
import io.github.dunwu.web.controller.CrudController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.*;

import java.io.Serializable;
import java.util.Collection;

/**
 * 文件内容表 前端控制器
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2019-07-24
 */
@RestController
@RequestMapping("/file-content")
@Api(tags = "FileContent", description = "文件内容表 CRUD Controller")
public class FileContentController extends CrudController<FileContent> {

    public FileContentController(FileContentDao dao) {
        super(dao);
    }

    @Override
    @GetMapping("count")
    @ApiOperation(value = "返回符合查询条件的 FileContent 记录数，如果 entity 为 null，返回所有记录数")
    public DataResult<Integer> count(FileContent entity) {
        return super.count(entity);
    }

    @Override
    @GetMapping("list")
    @ApiOperation(value = "返回符合查询条件的 FileContent 记录，如果 entity 为 null，返回所有记录")
    public DataListResult<FileContent> list(FileContent entity) {
        return super.list(entity);
    }

    @Override
    @GetMapping("page")
    @ApiOperation(value = "分页查询符合条件的 FileContent 记录，如果 entity 为 null，返回所有记录的分页查询结果")
    public PageResult<FileContent> page(FileContent entity, PageResult.Page page) {
        return super.page(entity, page);
    }

    @Override
    @PostMapping("save")
    @ApiOperation(value = "插入一条 FileContent 记录（选择字段）")
    public BaseResult save(FileContent entity) {
        return super.save(entity);
    }

    @Override
    @PostMapping("saveBatch")
    @ApiOperation(value = "批量添加 FileContent 记录（选择字段）")
    public BaseResult saveBatch(Collection<FileContent> entityList) {
        return super.saveBatch(entityList);
    }

    @Override
    @PostMapping("remove")
    @ApiOperation(value = "删除符合条件的 FileContent 记录")
    public BaseResult remove(FileContent entity) {
        return super.remove(entity);
    }

    @Override
    @PostMapping("removeById")
    @ApiOperation(value = "根据 ID 删除一条 FileContent 记录")
    public BaseResult removeById(Long id) {
        return super.removeById(id);
    }

    @Override
    @PostMapping("removeByIds")
    @ApiOperation(value = "根据 ID 批量删除 FileContent 记录")
    public BaseResult removeByIds(Collection<? extends Serializable> idList) {
        return super.removeByIds(idList);
    }

    @Override
    @PostMapping("update")
    @ApiOperation(value = "根据 origin 条件，更新一条 FileContent 记录 target")
    public BaseResult update(
        @RequestBody @ApiParam(name = "target", value = "FileContent 对象（json 格式）", required = true) FileContent target,
        FileContent origin) {
        return super.update(target, origin);
    }

    @Override
    @PostMapping("updateById")
    @ApiOperation(value = "根据 ID 选择修改一条 FileContent 记录")
    public BaseResult updateById(FileContent entity) {
        return super.updateById(entity);
    }

    @Override
    @PostMapping("updateBatchById")
    @ApiOperation(value = "根据 ID 批量修改 FileContent 记录")
    public BaseResult updateBatchById(Collection<FileContent> entityList) {
        return super.updateBatchById(entityList);
    }
}
