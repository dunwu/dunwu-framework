## 使用说明

在 maven 项目的 pom.xml 中引入依赖：

```
<dependency>
  <groupId>io.github.dunwu</groupId>
  <artifactId>dunwu-framework</artifactId>
  <version>${dunwu.version}</version>
</dependency>
```

在 Spring Boot 的 application.properties 添加如下配置：

```
# datasource
spring.datasource.url = jdbc:mysql://localhost:3306/test?serverTimezone=GMT%2B8&useUnicode=true&characterEncoding=utf-8
spring.datasource.driver-class-name = com.mysql.cj.jdbc.Driver
spring.datasource.username = root
spring.datasource.password = root
spring.datasource.schema = classpath:db/schema.sql
spring.datasource.data = classpath:db/data.sql

# mybatis
mybatis-plus.configuration.default-enum-type-handler = org.apache.ibatis.type.EnumOrdinalTypeHandler
mybatis.generator.java.dir = D:/Codes/ZP/Java/dunwu/codes/dunwu-framework/src/test/java
mybatis.generator.resources.dir = D:/Codes/ZP/Java/dunwu/codes/dunwu-framework/src/test/resources
mybatis.generator.package.name = io.github.dunwu.demo
mybatis.generator.table.name = user, role
```

使用 `CodeGenerator.main()` 自动围绕数据表的 CRUD 操作生成 Controller、Service、Dao、Entity 代码。

```
import io.github.dunwu.data.CodeGenerator;

public class MyCodeGenerator {
    public static void main(String[] args) {
        CodeGenerator.main();
    }
}
```

## 技术栈

| 框架或库                                                     | 说明                                                       |
| ------------------------------------------------------------ | ---------------------------------------------------------- |
| [spring](https://spring.io/projects/spring-framework)        | 传统最强 Java Web 开发框架。                               |
| [spring-boot](https://spring.io/projects/spring-boot)        | 减少 Spring 大量配置的新 Java Web 框架。**约定优于配置**。 |
| [mybatis](https://github.com/mybatis/mybatis-3)              | ORM 框架。                                                 |
| [mybatis-plus](https://github.com/baomidou/mybatis-plus)     | mybatis 增强框架。核心点在于CRUD、分页、代码生成器         |
| [swagger](https://swagger.io/)                               | 自动生成简洁美观的 REST API 文档。                         |
| [spring-boot-starter-swagger](https://github.com/SpringForAll/spring-boot-starter-swagger) | swagger 的 spring-boot 自动化配置                          |

