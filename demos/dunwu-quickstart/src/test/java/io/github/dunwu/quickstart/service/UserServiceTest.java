package io.github.dunwu.quickstart.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.github.dunwu.quickstart.entity.User;
import io.github.dunwu.util.mock.MockUtil;
import io.github.dunwu.util.number.RandomUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Zhang Peng
 * @since 2019-04-22
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class UserServiceTest {

    @Autowired
    private UserService userService;

    @Test
    public void saveBatch() {
        List<User> users = new ArrayList<>();
        for (int i = 1; i <= 10; i++) {
            User user = new User();
            user.setName(MockUtil.anyCName()).setAge(RandomUtil.nextInt(10, 61)).setGender(RandomUtil.nextInt(1, 3))
                .setSalary(RandomUtil.nextDouble(0.0, 10000.0)).setEmail(MockUtil.anyEmail())
                .setRoleId(RandomUtil.nextLong(1L, 4L)).setId((long) i);
            users.add(user);
        }
        assertThat(userService.saveBatch(users)).isTrue();
    }

    @Test
    @Transactional(rollbackFor = Exception.class)
    public void transaction() throws Exception {
        List<User> users = new ArrayList<>();
        for (int i = 11; i <= 20; i++) {
            User user = new User();
            user.setName(MockUtil.anyCName()).setAge(RandomUtil.nextInt(10, 61)).setGender(RandomUtil.nextInt(1, 3))
                .setSalary(RandomUtil.nextDouble(0.0, 10000.0)).setEmail(MockUtil.anyEmail())
                .setRoleId(RandomUtil.nextLong(1L, 4L)).setId((long) i);
            users.add(user);
        }
        assertThat(userService.saveBatch(users)).isTrue();
        throw new Exception();
    }

    @Test
    public void test() {
        User user = new User();
        user.setName(MockUtil.anyCName()).setAge(RandomUtil.nextInt(10, 61)).setGender(RandomUtil.nextInt(1, 3))
            .setSalary(RandomUtil.nextDouble(0.0, 10000.0)).setEmail(MockUtil.anyEmail()).setRoleId(RandomUtil.nextLong(1L, 4L))
            .setId(Integer.toUnsignedLong(21));
        assertThat(userService.save(user)).isTrue();
        assertThat(user.getId()).isNotNull();
    }


    @Test
    public void bDelete() {
        assertThat(userService.removeById(3L)).isTrue();
        assertThat(userService.remove(new QueryWrapper<User>().lambda().eq(User::getEmail, "yif@mgsxjdvwz.jfa")))
            .isTrue();
    }


    @Test
    public void cUpdate() {
        User user = new User().setEmail("ab@c.c");
        user.setId(1L);
        assertThat(userService.updateById(user)).isTrue();
        assertThat(userService
            .update(new User().setName("mp"), Wrappers.<User>lambdaUpdate().set(User::getAge, 3).eq(User::getId, 2)))
            .isTrue();
    }


    @Test
    public void dSelect() {
        assertThat(userService.getById(1L).getEmail()).isEqualTo("ab@c.c");
        User user = userService.getOne(new QueryWrapper<User>().lambda().eq(User::getId, 2));
        assertThat(user.getName()).isEqualTo("mp");
        assertThat(user.getAge()).isEqualTo(3);
    }

    @Test
    public void orderBy() {
        List<User> users = userService.list(Wrappers.<User>query().orderByAsc("age"));
        users.forEach(System.out::println);
        assertThat(users).isNotEmpty();
    }

    @Test
    public void selectMaps() {
        List<Map<String, Object>> mapList = userService.listMaps(Wrappers.<User>query().orderByAsc("age"));
        assertThat(mapList).isNotEmpty();
        assertThat(mapList.get(0)).isNotEmpty();
        System.out.println(mapList.get(0));
    }

    @Test
    public void selectMapsPage() {
        IPage<Map<String, Object>> page =
            userService.pageMaps(new Page<>(1, 5), Wrappers.<User>query().orderByAsc("age"));
        assertThat(page).isNotNull();
        assertThat(page.getRecords()).isNotEmpty();
        assertThat(page.getRecords().get(0)).isNotEmpty();
        System.out.println(page.getRecords().get(0));
    }

    @Test
    public void orderByLambda() {
        List<User> users = userService.list(Wrappers.<User>lambdaQuery().orderByAsc(User::getAge));
        assertThat(users).isNotEmpty();
    }
}
