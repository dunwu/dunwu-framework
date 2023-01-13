# dunwu-framework

> dunwu-framework 项目是一个 Java 脚手架项目。

[![License](https://img.shields.io/badge/license-Apache%202-blue)](https://www.apache.org/licenses/LICENSE-2.0.html) [![Maven](https://img.shields.io/badge/maven--central-v3.5.4-blue)](https://maven.apache.org/ref/3.5.4/) [![Build Status](https://travis-ci.com/dunwu/dunwu-framework.svg?branch=master)](https://travis-ci.com/dunwu/dunwu-framework)

## 项目结构

- `dunwu-dependencies`：管理项目中所有常见 jar 包的版本。
- `dunwu-parent`：所有模块的父 pom，管理各种插件及构建。
- `dunwu-tool`：dunwu 工具包模块，对常见工具集进行二次封装，并提供一些通用工具类，旨在提高开发效能。
- `dunwu-boot`：在 Spring Boot 的基础上进行扩展，针对一些未覆盖的场景，提供 starter 包，以支持即开即用。

