package io.github.dunwu.demo.test;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import io.github.dunwu.demo.dao.RoleDao;
import io.github.dunwu.demo.dao.UserDao;
import io.github.dunwu.demo.entity.User;
import org.apache.commons.collections.CollectionUtils;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.List;

@Rollback
@RunWith(SpringRunner.class)
@SpringBootTest
public class WrapperTest {

    @Resource
    private UserDao userDao;
    @Resource
    private RoleDao roleDao;

    @Test
    public void tests() {
        System.out.println("----- 普通查询 ------");
        List<User> plainUsers = userDao.selectList(new QueryWrapper<User>().eq("role_id", 2L));
        List<User> lambdaUsers = userDao.selectList(new QueryWrapper<User>().lambda().eq(User::getRoleId, 2L));
        Assert.assertEquals(plainUsers.size(), lambdaUsers.size());
        print(plainUsers);

        System.out.println("----- 带子查询(sql注入) ------");
        List<User> plainUsers2 =
            userDao.selectList(new QueryWrapper<User>().inSql("role_id", "select id from role where id = 2"));
        List<User> lambdaUsers2 = userDao
            .selectList(new QueryWrapper<User>().lambda().inSql(User::getRoleId, "select id from role where id = 2"));
        Assert.assertEquals(plainUsers2.size(), lambdaUsers2.size());
        print(plainUsers2);

        System.out.println("----- 带嵌套查询 ------");
        List<User> plainUsers3 = userDao.selectList(
            new QueryWrapper<User>().nested(i -> i.eq("role_id", 2L).or().eq("role_id", 3L)).and(i -> i.ge("age", 20)));
        List<User> lambdaUsers3 = userDao.selectList(
            new QueryWrapper<User>().lambda().nested(i -> i.eq(User::getRoleId, 2L).or().eq(User::getRoleId, 3L))
                .and(i -> i.ge(User::getAge, 20)));
        Assert.assertEquals(plainUsers3.size(), lambdaUsers3.size());
        print(plainUsers3);

        System.out.println("----- 自定义(sql注入) ------");
        List<User> plainUsers4 = userDao.selectList(new QueryWrapper<User>().apply("role_id = 2"));
        print(plainUsers4);

        UpdateWrapper<User> uw = new UpdateWrapper<>();
        uw.set("email", null);
        uw.eq("id", 4);
        userDao.update(new User(), uw);
        User u4 = userDao.selectById(4);
        Assert.assertNull(u4.getEmail());
    }

    @Test
    public void lambdaQueryWrapper() {
        System.out.println("----- 普通查询 ------");
        List<User> plainUsers = userDao.selectList(new LambdaQueryWrapper<User>().eq(User::getRoleId, 2L));
        List<User> lambdaUsers = userDao.selectList(new QueryWrapper<User>().lambda().eq(User::getRoleId, 2L));
        Assert.assertEquals(plainUsers.size(), lambdaUsers.size());
        print(plainUsers);

        System.out.println("----- 带子查询(sql注入) ------");
        List<User> plainUsers2 = userDao
            .selectList(new LambdaQueryWrapper<User>().inSql(User::getRoleId, "select id from role where id = 2"));
        List<User> lambdaUsers2 = userDao
            .selectList(new QueryWrapper<User>().lambda().inSql(User::getRoleId, "select id from role where id = 2"));
        Assert.assertEquals(plainUsers2.size(), lambdaUsers2.size());
        print(plainUsers2);

        System.out.println("----- 带嵌套查询 ------");
        List<User> plainUsers3 = userDao.selectList(
            new LambdaQueryWrapper<User>().nested(i -> i.eq(User::getRoleId, 2L).or().eq(User::getRoleId, 3L))
                .and(i -> i.ge(User::getAge, 20)));
        List<User> lambdaUsers3 = userDao.selectList(
            new QueryWrapper<User>().lambda().nested(i -> i.eq(User::getRoleId, 2L).or().eq(User::getRoleId, 3L))
                .and(i -> i.ge(User::getAge, 20)));
        Assert.assertEquals(plainUsers3.size(), lambdaUsers3.size());
        print(plainUsers3);

        System.out.println("----- 自定义(sql注入) ------");
        List<User> plainUsers4 = userDao.selectList(new QueryWrapper<User>().apply("role_id = 2"));
        print(plainUsers4);

        UpdateWrapper<User> uw = new UpdateWrapper<>();
        uw.set("email", null);
        uw.eq("id", 4);
        userDao.update(new User(), uw);
        User u4 = userDao.selectById(4);
        Assert.assertNull(u4.getEmail());
    }

    private <T> void print(List<T> list) {
        if (!CollectionUtils.isEmpty(list)) {
            list.forEach(System.out::println);
        }
    }
}
