# dunwu-external

dunwu-external 项目用于同一管理各种常用 Jar 包的版本。

使用方法：

在 pom.xml 中添加以下配置。

```xml
<dependencyManagement>
    <dependencies>
      <dependency>
        <groupId>io.github.dunwu</groupId>
        <artifactId>dunwu-external</artifactId>
        <version>${dunwu.version}</version>
        <type>pom</type>
        <scope>import</scope>
      </dependency>
    </dependencies>
</dependencyManagement>
```