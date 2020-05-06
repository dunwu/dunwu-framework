# dunwu-dependencies

> 本项目用于管理常用第三方包的版本。

**使用方法**

在 pom.xml 中引入如下配置：

```xml
<dependencyManagement>
  <dependencies>
    <dependency>
      <groupId>io.github.dunwu</groupId>
      <artifactId>dunwu-dependencies</artifactId>
      <version>xxx</version>
      <type>pom</type>
      <scope>import</scope>
    </dependency>
  </dependencies>
</dependencyManagement>
```
