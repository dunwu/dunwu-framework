package io.github.dunwu.quickstart.scheduler.controller;

import io.github.dunwu.core.*;
import io.github.dunwu.quickstart.scheduler.dto.BeanDTO;
import io.github.dunwu.quickstart.scheduler.dto.SchedulerInfoDTO;
import io.github.dunwu.quickstart.scheduler.entity.SchedulerInfo;
import io.github.dunwu.quickstart.scheduler.service.SchedulerInfoService;
import io.github.dunwu.quickstart.scheduler.service.SchedulerService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import java.io.Serializable;
import java.util.Collection;
import java.util.Map;

/**
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2019-07-31
 */
@RestController
@RequestMapping("scheduler")
@Api(tags = "scheduler", description = "SchedulerController")
public class SchedulerController {

	private final SchedulerService schedulerService;

	private final SchedulerInfoService schedulerInfoService;

	public SchedulerController(SchedulerService schedulerService,
			SchedulerInfoService schedulerInfoService) {
		this.schedulerService = schedulerService;
		this.schedulerInfoService = schedulerInfoService;
	}

	@PostMapping("createJob")
	@ApiOperation(value = "创建调度任务")
	public BaseResult createJob(@RequestBody SchedulerInfoDTO schedulerInfoDTO) {
		return schedulerService.createJob(schedulerInfoDTO);
	}

	@PostMapping("updateJob")
	@ApiOperation(value = "更新调度任务")
	public BaseResult updateJob(@RequestBody SchedulerInfoDTO schedulerInfoDTO) {
		return schedulerService.updateJob(schedulerInfoDTO);
	}

	@PostMapping("deleteJob")
	@ApiOperation(value = "删除调度任务")
	public BaseResult deleteJob(@RequestBody SchedulerInfoDTO schedulerInfoDTO) {
		return schedulerService.deleteJob(schedulerInfoDTO);
	}

	@PostMapping("pauseJob")
	@ApiOperation(value = "暂停调度任务")
	public BaseResult pauseJob(@RequestBody SchedulerInfoDTO schedulerInfoDTO) {
		return schedulerService.pauseJob(schedulerInfoDTO);
	}

	@PostMapping("resumeJob")
	@ApiOperation(value = "恢复调度任务")
	public BaseResult resumeJob(@RequestBody SchedulerInfoDTO schedulerInfoDTO) {
		return schedulerService.resumeJob(schedulerInfoDTO);
	}

	@PostMapping("executeJob")
	@ApiOperation(value = "立即执行一次调度任务")
	public BaseResult executeJob(@RequestBody SchedulerInfoDTO schedulerInfoDTO) {
		return schedulerService.executeJob(schedulerInfoDTO);
	}

	@GetMapping("/getJobBeans")
	@ApiOperation(value = "获取所有 Service 类")
	public DataListResult<BeanDTO> getJobBeans() {
		return ResultUtil.successDataListResult(schedulerService.getJobHandlers());
	}

	@PostMapping("save")
	@ApiOperation(value = "插入一条 SchedulerInfo 记录，插入成功返回 ID（选择字段，策略插入）")
	public DataResult<? extends Serializable> save(SchedulerInfo entity) {
		return schedulerInfoService.save(entity);
	}

	@PostMapping("saveBatch")
	@ApiOperation(value = "批量添加 SchedulerInfo 记录（选择字段，策略插入）")
	public BaseResult saveBatch(Collection<SchedulerInfo> entityList) {
		return schedulerInfoService.saveBatch(entityList);
	}

	@PostMapping("removeById")
	@ApiOperation(value = "根据 ID 删除一条 SchedulerInfo 记录")
	public BaseResult removeById(Serializable id) {
		return schedulerInfoService.removeById(id);
	}

	@PostMapping("removeByMap")
	@ApiOperation(value = "根据 columnMap 条件，删除 SchedulerInfo 记录")
	public BaseResult removeByMap(Map<String, Object> columnMap) {
		return schedulerInfoService.removeByMap(columnMap);
	}

	@PostMapping("remove")
	@ApiOperation(value = "根据 entity 条件，删除 SchedulerInfo 记录")
	public BaseResult remove(SchedulerInfo entity) {
		return schedulerInfoService.remove(entity);
	}

	@PostMapping("removeByIds")
	@ApiOperation(value = "根据 ID 批量删除 SchedulerInfo 记录")
	public BaseResult removeByIds(Collection<? extends Serializable> idList) {
		return schedulerInfoService.removeByIds(idList);
	}

	@PostMapping("updateById")
	@ApiOperation(value = "根据 ID 选择修改一条 SchedulerInfo 记录")
	public BaseResult updateById(SchedulerInfo entity) {
		return schedulerInfoService.updateById(entity);
	}

	@PostMapping("update")
	@ApiOperation(value = "根据 origin 条件，更新 SchedulerInfo 记录")
	public BaseResult update(@RequestBody SchedulerInfo target,
			@RequestParam SchedulerInfo origin) {
		return schedulerInfoService.update(target, origin);
	}

	@PostMapping("updateBatchById")
	@ApiOperation(value = "根据 ID 批量修改 SchedulerInfo 记录")
	public BaseResult updateBatchById(Collection<SchedulerInfo> entityList) {
		return schedulerInfoService.updateBatchById(entityList);
	}

	@PostMapping("saveOrUpdate")
	@ApiOperation(value = "ID 存在则更新记录，否则插入一条记录")
	public BaseResult saveOrUpdate(SchedulerInfo entity) {
		return schedulerInfoService.saveOrUpdate(entity);
	}

	@PostMapping("saveOrUpdateBatch")
	@ApiOperation(value = "批量添加或更新 SchedulerInfo 记录")
	public BaseResult saveOrUpdateBatch(Collection<SchedulerInfo> entityList) {
		return schedulerInfoService.saveOrUpdateBatch(entityList);
	}

	@GetMapping("getById")
	@ApiOperation(value = "根据 ID 查询 SchedulerInfo 记录")
	public DataResult<SchedulerInfo> getById(String id) {
		return schedulerInfoService.getById(id);
	}

	@GetMapping("listByIds")
	@ApiOperation(value = "根据 ID 批量查询 SchedulerInfo 记录")
	public DataListResult<SchedulerInfo> listByIds(
			Collection<? extends Serializable> idList) {
		return schedulerInfoService.listByIds(idList);
	}

	@GetMapping("listByMap")
	@ApiOperation(value = "根据 columnMap 批量查询 SchedulerInfo 记录")
	public DataListResult<SchedulerInfo> listByMap(Map<String, Object> columnMap) {
		return schedulerInfoService.listByMap(columnMap);
	}

	@GetMapping("getOne")
	@ApiOperation(value = "根据 entity 查询一条 SchedulerInfo 记录")
	public DataResult<SchedulerInfo> getOne(SchedulerInfo entity) {
		return schedulerInfoService.getOne(entity);
	}

	@GetMapping("count")
	@ApiOperation(value = "根据 entity 条件，查询 SchedulerInfo 总记录数")
	public DataResult<Integer> count(SchedulerInfo entity) {
		return schedulerInfoService.count(entity);
	}

	@GetMapping("countAll")
	@ApiOperation(value = "查询 SchedulerInfo 总记录数")
	public DataResult<Integer> countAll() {
		return schedulerInfoService.count();
	}

	@GetMapping("list")
	@ApiOperation(value = "根据 entity 条件，查询匹配条件的 SchedulerInfo 记录")
	public DataListResult<SchedulerInfo> list(SchedulerInfo entity) {
		return schedulerInfoService.list(entity);
	}

	@GetMapping("listAll")
	@ApiOperation(value = "查询所有 SchedulerInfo 记录")
	public DataListResult<SchedulerInfo> listAll() {
		return schedulerInfoService.list();
	}

	@GetMapping("page")
	@ApiOperation(value = "根据 entity 条件，翻页查询 UserInfo 记录")
	public PageResult<SchedulerInfo> page(Pagination<SchedulerInfo> pagination,
			SchedulerInfo entity) {
		return schedulerInfoService.page(pagination, entity);
	}

	@GetMapping("pageAll")
	@ApiOperation(value = "翻页查询所有 UserInfo 记录")
	public PageResult<SchedulerInfo> pageAll(Pagination<SchedulerInfo> pagination) {
		return schedulerInfoService.page(pagination);
	}

}
