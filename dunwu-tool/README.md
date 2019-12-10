# 工具包

> 精选工具包，并提炼封装。

## 基本工具包

- [**guava**](https://github.com/google/guava) - Google 工具包。代码简练、优雅，并且有 Benchmark 保证代码质量和执行效率，基础工具包首选。
- [**commons-lang**](https://github.com/apache/commons-lang) - Apache 基础工具包。功能全面，使用简便，但由于工具包开发年月比较久，很多 API 并非基于泛型设计，导致工具包中有许多较为冗余的代码，不够优雅。但是，其应用十分广泛，很多优秀开源项目中使用了它。
- [**hutool**](https://github.com/looly/hutool) - 国内程序员写的工具包。功能全面，使用简便。API 有中文文档，上手比较快。

个人认为选择优先级：`guava > commons-lang > hutool`

技术选型思考：

- 中小型项目中选用基本工具包，首先以满足功能为目标，所以工具包功能要全面，使用要简易。所以 hutool 和 commons-lang 是不错的选择。
- 大型项目以及对性能要求较高的项目中，要考虑工具类的稳定以及执行效率。由于 Guava 有 Benchmark 保证代码质量和执行效率，所以是基础工具包的首选。
- 需要定制代码的，喜欢折腾的，可以考虑提炼工具类。
