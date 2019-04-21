package io.github.dunwu.quickstart.controller;

import io.github.dunwu.core.Page;
import io.github.dunwu.core.Result;
import io.github.dunwu.quickstart.entity.Role;
import io.github.dunwu.quickstart.service.RoleService;
import io.github.dunwu.web.controller.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author Zhang Peng
 * @since 2019-04-21
 */
@RestController
@RequestMapping("/role")
public class RoleController extends BaseController<Role> {
    private final RoleService service;

    @Autowired
    public RoleController(RoleService service) {
        super(service);
        this.service = service;
    }

    @Override
    @GetMapping("count")
    public Result<Integer> count(Role entity) {
        return super.count(entity);
    }

    @Override
    @GetMapping("list")
    public Result<Role> list(Role entity) {
        return super.list(entity);
    }

    @Override
    @GetMapping("listWithPage")
    public Result<Role> listWithPage(Role entity, Page page) {
        return super.listWithPage(entity, page);
    }

    @Override
    @PostMapping("save")
    public Result save(Role entity) {
        return super.save(entity);
    }

    @Override
    @PostMapping("saveBatch")
    public Result saveBatch(List<Role> entityList) {
        return super.saveBatch(entityList);
    }

    @Override
    @PostMapping("remove")
    public Result remove(Role entity) {
        return super.remove(entity);
    }
}
