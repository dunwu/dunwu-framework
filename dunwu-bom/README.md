# dunwu-bom

dunwu-bom 负责统一版本管理，以避免出现依赖包多版本问题。

使用方式：在 pom.xml 中的 dependencyManagement 元素中添加如下内容：

```xml
<dependencyManagement>
  <dependencies>
    <dependency>
      <groupId>io.github.dunwu</groupId>
      <artifactId>dunwu-bom</artifactId>
      <version>${dunwu.version}</version>
      <type>pom</type>
      <scope>import</scope>
    </dependency>
  </dependencies>
</dependencyManagement>
```

这样，项目中的依赖就就不需要再指定 version 了（当然，前提是 dunwu-bom 已经管理了这个库）。
