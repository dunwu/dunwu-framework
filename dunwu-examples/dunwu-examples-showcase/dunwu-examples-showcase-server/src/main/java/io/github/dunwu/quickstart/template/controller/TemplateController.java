package io.github.dunwu.quickstart.template.controller;

import io.github.dunwu.core.*;
import io.github.dunwu.quickstart.template.entity.Template;
import io.github.dunwu.quickstart.template.service.TemplateService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.Map;

/**
 * 模板配置表
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2019-09-12
 */
@RestController
@RequestMapping("template")
@Api(tags = "template")
public class TemplateController {

	private final TemplateService service;

	public TemplateController(TemplateService service) {
		this.service = service;
	}

	@GetMapping("count")
	@ApiOperation(value = "根据 entity 条件，查询 Template 总记录数")
	public DataResult<Integer> count(Template entity) {
		return service.count(entity);
	}

	@GetMapping("countAll")
	@ApiOperation(value = "查询 Template 总记录数")
	public DataResult<Integer> countAll() {
		return service.count();
	}

	@GetMapping("getById")
	@ApiOperation(value = "根据 ID 查询 Template 记录")
	public DataResult<Template> getById(String id) {
		return service.getById(id);
	}

	@GetMapping("getOne")
	@ApiOperation(value = "根据 entity 查询一条 Template 记录")
	public DataResult<Template> getOne(Template entity) {
		return service.getOne(entity);
	}

	@GetMapping("list")
	@ApiOperation(value = "根据 entity 条件，查询匹配条件的 Template 记录")
	public DataListResult<Template> list(Template entity) {
		return service.list(entity);
	}

	@GetMapping("listAll")
	@ApiOperation(value = "查询所有 Template 记录")
	public DataListResult<Template> listAll() {
		return service.list();
	}

	@GetMapping("listByIds")
	@ApiOperation(value = "根据 ID 批量查询 Template 记录")
	public DataListResult<Template> listByIds(@RequestParam Collection<String> idList) {
		return service.listByIds(idList);
	}

	@GetMapping("listByMap")
	@ApiOperation(value = "根据 columnMap 批量查询 Template 记录")
	public DataListResult<Template> listByMap(Map<String, Object> columnMap) {
		return service.listByMap(columnMap);
	}

	@GetMapping("page")
	@ApiOperation(value = "根据 entity 条件，翻页查询 Template 记录")
	public PageResult<Template> page(Pagination<Template> pagination, Template entity) {
		return service.page(pagination, entity);
	}

	@GetMapping("pageAll")
	@ApiOperation(value = "翻页查询所有 Template 记录")
	public PageResult<Template> pageAll(Pagination<Template> pagination) {
		return service.page(pagination);
	}

	@PostMapping("remove")
	@ApiOperation(value = "根据 entity 条件，删除 Template 记录")
	public BaseResult remove(@RequestBody Template entity) {
		return service.remove(entity);
	}

	@PostMapping("removeById")
	@ApiOperation(value = "根据 ID 删除一条 Template 记录")
	public BaseResult removeById(@RequestBody String id) {
		return service.removeById(id);
	}

	@PostMapping("removeByIds")
	@ApiOperation(value = "根据 ID 批量删除 Template 记录")
	public BaseResult removeByIds(@RequestBody Collection<String> idList) {
		return service.removeByIds(idList);
	}

	@PostMapping("removeByMap")
	@ApiOperation(value = "根据 columnMap 条件，删除 Template 记录")
	public BaseResult removeByMap(@RequestBody Map<String, Object> columnMap) {
		return service.removeByMap(columnMap);
	}

	@PostMapping("save")
	@ApiOperation(value = "插入一条 Template 记录，插入成功返回 ID（选择字段，策略插入）")
	public DataResult<Integer> save(@RequestBody Template entity) {
		return service.save(entity);
	}

	@PostMapping("saveBatch")
	@ApiOperation(value = "批量添加 Template 记录（选择字段，策略插入）")
	public BaseResult saveBatch(@RequestBody Collection<Template> entityList) {
		return service.saveBatch(entityList);
	}

	@PostMapping("saveOrUpdate")
	@ApiOperation(value = "ID 存在则更新记录，否则插入一条记录")
	public BaseResult saveOrUpdate(@RequestBody Template entity) {
		return service.saveOrUpdate(entity);
	}

	@PostMapping("saveOrUpdateBatch")
	@ApiOperation(value = "批量添加或更新 Template 记录")
	public BaseResult saveOrUpdateBatch(@RequestBody Collection<Template> entityList) {
		return service.saveOrUpdateBatch(entityList);
	}

	@PostMapping("update")
	@ApiOperation(value = "根据 origin 条件，更新 Template 记录")
	public BaseResult update(@RequestBody Template target,
		@RequestParam Template origin) {
		return service.update(target, origin);
	}

	@PostMapping("updateBatchById")
	@ApiOperation(value = "根据 ID 批量修改 Template 记录")
	public BaseResult updateBatchById(@RequestBody Collection<Template> entityList) {
		return service.updateBatchById(entityList);
	}

	@PostMapping("updateById")
	@ApiOperation(value = "根据 ID 选择修改一条 Template 记录")
	public BaseResult updateById(@RequestBody Template entity) {
		return service.updateById(entity);
	}

}
