# 分布式调度

## 支持的功能

- 动态配置调度任务，指定调度的 Java 方法、传入的参数、调度触发时间
- 停止指定的某个调度任务、停止所有的调度任务
- 立即启动某个调度任务

## 应用技术

### `@Scheduler`

如果应用不是分布式应用，切调度任务比较简单，不需要动态增减调度任务。则可以考虑使用 Spring Boot 自带的   

`@Scheduler`，用法可以参考：[Scheduling Tasks](https://spring.io/guides/gs/scheduling-tasks/)

### Quartz

