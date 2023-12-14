package io.github.dunwu.tool.data.desensitized;

import cn.hutool.json.JSONUtil;
import io.github.dunwu.tool.data.desensitized.annotation.Desensitized;
import io.github.dunwu.tool.data.desensitized.annotation.NeedDesensitized;
import io.github.dunwu.tool.data.desensitized.constant.SensitiveTypeEnum;
import io.github.dunwu.tool.data.request.PageQuery;
import io.github.dunwu.tool.data.response.PageImpl;
import io.github.dunwu.tool.data.response.Result;
import io.github.dunwu.tool.io.AnsiColorUtil;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Page;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2022-11-04
 */
public class DesensitizedUtilTests {

    @Data
    @Accessors(chain = true)
    @EqualsAndHashCode(callSuper = true)
    static class Admin extends User {

        @Desensitized(type = SensitiveTypeEnum.PASSWORD)
        private String privilege;

    }

    @NeedDesensitized
    @Data
    @Accessors(chain = true)
    @EqualsAndHashCode(callSuper = true)
    static class SuperUser extends User {

        @Desensitized(type = SensitiveTypeEnum.PASSWORD)
        private String privilege;

    }

    @Data
    @Accessors(chain = true)
    static class User {

        private Long id;

        @Desensitized
        private Long userId;

        @Desensitized(type = SensitiveTypeEnum.CHINESE_NAME)
        private String name;

        @Desensitized(type = SensitiveTypeEnum.PASSWORD)
        private String password;

        @Desensitized
        private int age;

        @Desensitized
        private Job job;

    }

    @Data
    @Builder
    static class Job {

        private String name;

        @Desensitized
        private Double salary;

    }

    @NeedDesensitized
    @Data
    @Accessors(chain = true)
    static class Complex {

        private List<SuperUser> userList;

        private Page<SuperUser> userPage;

        private Map<Long, SuperUser> userMap;

    }

    private Job job;
    private SuperUser user;
    private Admin admin;

    @BeforeEach
    public void beforeEach() {
        job = new Job("工程师", 10000.0);
        user = new SuperUser();
        admin = new Admin();
        user.setPrivilege("特权").setId(1L).setUserId(10000L).setName("张三").setAge(30).setPassword("123456").setJob(job);
        admin.setPrivilege("特权").setId(1L).setUserId(10000L).setName("张三").setAge(30).setPassword("123456").setJob(job);
    }

    @Test
    @DisplayName("测试 @NeedDesensitized")
    void testNeedDesensitized() {
        DesensitizedUtil.doDesensitized(user);
        AnsiColorUtil.BOLD_BLUE.println("user:\n" + JSONUtil.toJsonStr(user));
        Assertions.assertEquals("{\"privilege\":\"**\",\"id\":1,\"name\":\"张*\",\"password\":\"******\",\"age\":30}",
            JSONUtil.toJsonStr(user));

        DesensitizedUtil.doDesensitized(admin);
        AnsiColorUtil.BOLD_BLUE.println("admin:\n" + JSONUtil.toJsonStr(admin));
        Assertions.assertEquals(
            "{\"privilege\":\"特权\",\"id\":1,\"userId\":10000,\"name\":\"张三\",\"password\":\"123456\",\"age\":30,\"job\":{\"name\":\"工程师\",\"salary\":10000}}",
            JSONUtil.toJsonStr(admin));
    }

    @Test
    @DisplayName("测试对 List 类型进行脱敏")
    void testList() {

        final List<Admin> adminList = new ArrayList<>();
        final List<SuperUser> superUserList = new ArrayList<>();
        superUserList.add(user);
        adminList.add(admin);

        DesensitizedUtil.doDesensitized(superUserList);
        AnsiColorUtil.BOLD_BLUE.println("superUserList:\n" + JSONUtil.toJsonStr(superUserList));
        Assertions.assertEquals("[{\"privilege\":\"**\",\"id\":1,\"name\":\"张*\",\"password\":\"******\",\"age\":30}]",
            JSONUtil.toJsonStr(superUserList));

        DesensitizedUtil.doDesensitized(adminList);
        AnsiColorUtil.BOLD_BLUE.println("adminList:\n" + JSONUtil.toJsonStr(adminList));
        Assertions.assertEquals(
            "[{\"privilege\":\"特权\",\"id\":1,\"userId\":10000,\"name\":\"张三\",\"password\":\"123456\",\"age\":30,\"job\":{\"name\":\"工程师\",\"salary\":10000}}]",
            JSONUtil.toJsonStr(adminList));
    }

    @Test
    @DisplayName("测试对 Map 类型进行脱敏")
    void testMap() {

        final Map<Long, Admin> adminMap = new HashMap<>();
        final Map<Long, SuperUser> superUserMap = new HashMap<>();
        superUserMap.put(user.getId(), user);
        adminMap.put(admin.getId(), admin);

        DesensitizedUtil.doDesensitized(superUserMap);
        AnsiColorUtil.BOLD_BLUE.println("superUserMap:\n" + JSONUtil.toJsonStr(superUserMap));
        Assertions.assertEquals(
            "{\"1\":{\"privilege\":\"**\",\"id\":1,\"name\":\"张*\",\"password\":\"******\",\"age\":30}}",
            JSONUtil.toJsonStr(superUserMap));

        DesensitizedUtil.doDesensitized(adminMap);
        AnsiColorUtil.BOLD_BLUE.println("adminMap:\n" + JSONUtil.toJsonStr(adminMap));
        Assertions.assertEquals(
            "{\"1\":{\"privilege\":\"特权\",\"id\":1,\"userId\":10000,\"name\":\"张三\",\"password\":\"123456\",\"age\":30,\"job\":{\"name\":\"工程师\",\"salary\":10000}}}",
            JSONUtil.toJsonStr(adminMap));
    }

    @Test
    @DisplayName("测试对 Page 类型进行脱敏")
    void testPage() {
        final List<Admin> adminList = new ArrayList<>();
        final List<SuperUser> superUserList = new ArrayList<>();
        superUserList.add(user);
        adminList.add(admin);
        final PageImpl<Admin> adminPage = new PageImpl<>(adminList, PageQuery.of(1, 10), 1);
        final PageImpl<SuperUser> superUserPage = new PageImpl<>(superUserList, PageQuery.of(1, 10), 1);

        DesensitizedUtil.doDesensitized(superUserPage);
        AnsiColorUtil.BOLD_BLUE.println("superUserPage:\n" + JSONUtil.toJsonStr(superUserPage.getContent()));
        Assertions.assertEquals("[{\"privilege\":\"**\",\"id\":1,\"name\":\"张*\",\"password\":\"******\",\"age\":30}]",
            JSONUtil.toJsonStr(superUserPage.getContent()));

        DesensitizedUtil.doDesensitized(adminPage);
        AnsiColorUtil.BOLD_BLUE.println("adminPage:\n" + JSONUtil.toJsonStr(adminPage.getContent()));
        Assertions.assertEquals(
            "[{\"privilege\":\"特权\",\"id\":1,\"userId\":10000,\"name\":\"张三\",\"password\":\"123456\",\"age\":30,\"job\":{\"name\":\"工程师\",\"salary\":10000}}]",
            JSONUtil.toJsonStr(adminPage.getContent()));
    }

    @Test
    @DisplayName("测试对 Result 类型进行脱敏")
    void testResult() {
        Result userResult = new Result();
        userResult.setData(user);
        DesensitizedUtil.doDesensitized(userResult);
        AnsiColorUtil.BOLD_BLUE.println("userResult:\n" + JSONUtil.toJsonStr(userResult));
        Assertions.assertEquals(
            "{\"code\":0,\"msg\":\"成功\",\"data\":{\"privilege\":\"**\",\"id\":1,\"name\":\"张*\",\"password\":\"******\",\"age\":30}}",
            JSONUtil.toJsonStr(userResult));

        Result adminResult = new Result();
        adminResult.setData(admin);
        DesensitizedUtil.doDesensitized(adminResult);
        AnsiColorUtil.BOLD_BLUE.println("adminResult:\n" + JSONUtil.toJsonStr(adminResult));
        Assertions.assertEquals(
            "{\"code\":0,\"msg\":\"成功\",\"data\":{\"privilege\":\"特权\",\"id\":1,\"userId\":10000,\"name\":\"张三\",\"password\":\"123456\",\"age\":30,\"job\":{\"name\":\"工程师\",\"salary\":10000}}}",
            JSONUtil.toJsonStr(adminResult));
    }

    @Test
    @DisplayName("测试对复合类型进行脱敏")
    void testComplex() {
        SuperUser user1 = new SuperUser();
        SuperUser user2 = new SuperUser();
        SuperUser user3 = new SuperUser();
        user1.setPrivilege("特权1")
             .setId(1L)
             .setUserId(10000L)
             .setName("张三")
             .setAge(30)
             .setPassword("123456")
             .setJob(job);
        user2.setPrivilege("特权2")
             .setId(2L)
             .setUserId(10000L)
             .setName("李四")
             .setAge(30)
             .setPassword("123456")
             .setJob(job);
        user3.setPrivilege("特权3")
             .setId(3L)
             .setUserId(10000L)
             .setName("王五")
             .setAge(30)
             .setPassword("123456")
             .setJob(job);

        List<SuperUser> list = new ArrayList<>();
        list.add(user1);

        Map<Long, SuperUser> map = new HashMap<>();
        map.put(2L, user2);

        Page<SuperUser> page = new PageImpl<>(Collections.singletonList(user3), PageQuery.of(1, 10), 1);
        Complex complex = new Complex();
        complex.setUserList(list).setUserMap(map).setUserPage(page);

        DesensitizedUtil.doDesensitized(complex);
        AnsiColorUtil.BOLD_BLUE.println("complex:\n" + JSONUtil.toJsonStr(complex));
        Assertions.assertEquals(
            "{\"userList\":[{\"privilege\":\"***\",\"id\":1,\"name\":\"张*\",\"password\":\"******\",\"age\":30}],\"userPage\":[{\"privilege\":\"***\",\"id\":3,\"name\":\"王*\",\"password\":\"******\",\"age\":30}],\"userMap\":{\"2\":{\"privilege\":\"***\",\"id\":2,\"name\":\"李*\",\"password\":\"******\",\"age\":30}}}",
            JSONUtil.toJsonStr(complex));
    }

}
