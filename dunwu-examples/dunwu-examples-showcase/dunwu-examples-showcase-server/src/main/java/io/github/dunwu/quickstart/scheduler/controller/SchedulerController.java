package io.github.dunwu.quickstart.scheduler.controller;

import io.github.dunwu.core.*;
import io.github.dunwu.quickstart.scheduler.dto.BeanDTO;
import io.github.dunwu.quickstart.scheduler.entity.Scheduler;
import io.github.dunwu.quickstart.scheduler.service.SchedulerService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.Map;

/**
 * 调度信息表
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2019-09-12
 */
@RestController
@RequestMapping("scheduler")
@Api(tags = "scheduler")
public class SchedulerController {

	private final SchedulerService service;

	public SchedulerController(SchedulerService service) {
		this.service = service;
	}

	@GetMapping("count")
	@ApiOperation(value = "根据 entity 条件，查询 Scheduler 总记录数")
	public DataResult<Integer> count(Scheduler entity) {
		return service.count(entity);
	}

	@GetMapping("countAll")
	@ApiOperation(value = "查询 Scheduler 总记录数")
	public DataResult<Integer> countAll() {
		return service.count();
	}

	@PostMapping("createJob")
	@ApiOperation(value = "创建调度任务")
	public BaseResult createJob(@RequestBody Scheduler scheduler) {
		return service.createJob(scheduler);
	}

	@PostMapping("deleteJob")
	@ApiOperation(value = "删除调度任务")
	public BaseResult deleteJob(@RequestBody Scheduler scheduler) {
		return service.deleteJob(scheduler);
	}

	@PostMapping("executeJob")
	@ApiOperation(value = "立即执行一次调度任务")
	public BaseResult executeJob(@RequestBody Scheduler scheduler) {
		return service.executeJob(scheduler);
	}

	@GetMapping("getById")
	@ApiOperation(value = "根据 ID 查询 Scheduler 记录")
	public DataResult<Scheduler> getById(String id) {
		return service.getById(id);
	}

	@GetMapping("getOne")
	@ApiOperation(value = "根据 entity 查询一条 Scheduler 记录")
	public DataResult<Scheduler> getOne(Scheduler entity) {
		return service.getOne(entity);
	}

	@GetMapping("list")
	@ApiOperation(value = "根据 entity 条件，查询匹配条件的 Scheduler 记录")
	public DataListResult<Scheduler> list(Scheduler entity) {
		return service.list(entity);
	}

	@GetMapping("listAll")
	@ApiOperation(value = "查询所有 Scheduler 记录")
	public DataListResult<Scheduler> listAll() {
		return service.list();
	}

	@GetMapping("listByIds")
	@ApiOperation(value = "根据 ID 批量查询 Scheduler 记录")
	public DataListResult<Scheduler> listByIds(@RequestParam Collection<String> idList) {
		return service.listByIds(idList);
	}

	@GetMapping("listByMap")
	@ApiOperation(value = "根据 columnMap 批量查询 Scheduler 记录")
	public DataListResult<Scheduler> listByMap(Map<String, Object> columnMap) {
		return service.listByMap(columnMap);
	}

	@GetMapping("page")
	@ApiOperation(value = "根据 entity 条件，翻页查询 SchedulerInfo 记录")
	public PageResult<Scheduler> page(Pagination<Scheduler> pagination,
		Scheduler entity) {
		return service.page(pagination, entity);
	}

	@GetMapping("pageAll")
	@ApiOperation(value = "翻页查询所有 SchedulerInfo 记录")
	public PageResult<Scheduler> pageAll(Pagination<Scheduler> pagination) {
		return service.page(pagination);
	}

	@PostMapping("pauseJob")
	@ApiOperation(value = "暂停调度任务")
	public BaseResult pauseJob(@RequestBody Scheduler scheduler) {
		return service.pauseJob(scheduler);
	}

	@PostMapping("remove")
	@ApiOperation(value = "根据 entity 条件，删除 Scheduler 记录")
	public BaseResult remove(@RequestBody Scheduler entity) {
		return service.remove(entity);
	}

	@PostMapping("removeById")
	@ApiOperation(value = "根据 ID 删除一条 Scheduler 记录")
	public BaseResult removeById(@RequestBody String id) {
		return service.removeById(id);
	}

	@PostMapping("removeByIds")
	@ApiOperation(value = "根据 ID 批量删除 Scheduler 记录")
	public BaseResult removeByIds(@RequestBody Collection<String> idList) {
		return service.removeByIds(idList);
	}

	@PostMapping("removeByMap")
	@ApiOperation(value = "根据 columnMap 条件，删除 Scheduler 记录")
	public BaseResult removeByMap(@RequestBody Map<String, Object> columnMap) {
		return service.removeByMap(columnMap);
	}

	@PostMapping("resumeJob")
	@ApiOperation(value = "恢复调度任务")
	public BaseResult resumeJob(@RequestBody Scheduler scheduler) {
		return service.resumeJob(scheduler);
	}

	@PostMapping("save")
	@ApiOperation(value = "插入一条 Scheduler 记录，插入成功返回 ID（选择字段，策略插入）")
	public DataResult<Integer> save(@RequestBody Scheduler entity) {
		return service.save(entity);
	}

	@PostMapping("saveBatch")
	@ApiOperation(value = "批量添加 Scheduler 记录（选择字段，策略插入）")
	public BaseResult saveBatch(@RequestBody Collection<Scheduler> entityList) {
		return service.saveBatch(entityList);
	}

	@PostMapping("saveOrUpdate")
	@ApiOperation(value = "ID 存在则更新记录，否则插入一条记录")
	public BaseResult saveOrUpdate(@RequestBody Scheduler entity) {
		return service.saveOrUpdate(entity);
	}

	@PostMapping("saveOrUpdateBatch")
	@ApiOperation(value = "批量添加或更新 Scheduler 记录")
	public BaseResult saveOrUpdateBatch(@RequestBody Collection<Scheduler> entityList) {
		return service.saveOrUpdateBatch(entityList);
	}

	@PostMapping("update")
	@ApiOperation(value = "根据 origin 条件，更新 Scheduler 记录")
	public BaseResult update(@RequestBody Scheduler target,
		@RequestParam Scheduler origin) {
		return service.update(target, origin);
	}

	@PostMapping("updateBatchById")
	@ApiOperation(value = "根据 ID 批量修改 Scheduler 记录")
	public BaseResult updateBatchById(@RequestBody Collection<Scheduler> entityList) {
		return service.updateBatchById(entityList);
	}

	@PostMapping("updateById")
	@ApiOperation(value = "根据 ID 选择修改一条 Scheduler 记录")
	public BaseResult updateById(@RequestBody Scheduler entity) {
		return service.updateById(entity);
	}

	@PostMapping("updateJob")
	@ApiOperation(value = "更新调度任务")
	public BaseResult updateJob(@RequestBody Scheduler scheduler) {
		return service.updateJob(scheduler);
	}

	@GetMapping("/getJobBeans")
	@ApiOperation(value = "获取所有 Service 类")
	public DataListResult<BeanDTO> getJobBeans() {
		return ResultUtil.successDataListResult(service.getJobHandlers());
	}

}
