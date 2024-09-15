<p align="center">
  <img width="300" src="src/main/resources/assets/airpower.svg"/> <b>JavaStarter</b>
</p>

<p align="center">
  <img src="https://svg.hamm.cn?key=Lang&value=Java17&bg=green"/>
  <img src="https://svg.hamm.cn?key=Base&value=SpringBoot3"/>
  <img src="https://svg.hamm.cn?key=Data&value=MySQL8"/>
  <img src="https://svg.hamm.cn?key=Cache&value=Redis"/>
</p>

# AirPowerJavaStarter

via [Github](https://github.com/HammCn/AirPowerJavaStarter) / [Gitee](https://gitee.com/air-power/AirPowerJavaStarter)

## 一、初始化代码库

### 1. 创建项目文件夹

创建项目文件夹， 如 `project`，然后使用 **Git** 将源代码clone至此目录。

### 2. 下载源代码

- 通过 **Github** 代码仓库初始化

  ```shell
  git clone https://github.com/HammCn/AirPowerJavaStarter.git
  ```

- 通过 **Gitee** 代码仓库初始化(推荐)

  ```shell
  git clone https://gitee.com/air-power/AirPowerJavaStarter.git
  ```

### 3. IDEA打开项目文件夹

使用 IDEA 打开 `AirPowerJavaStarter` 目录，刷新项目的 `maven` 依赖，等待依赖安装完成即可。

## 二、项目架构

### 1. 环境变量说明

我们使用了 `JPA` 的自动初始化数据库 `ddl-auto: create-drop` 模式，所以你在此项目中看不到SQL文件。

所以在初始化代码库完成后只需要先创建数据库，并设置 `utf8mb4_unicode_ci` 字符集。

接下来在环境变量中配置 `ddl-auto: create-drop` 即可。

> 请注意，生产环境请勿使用这种方式。

### 2. 基本架构说明

我们使用标准的 `Controller`/`Service`/`Repository` 架构，原则上不涉及 `EO`/`VO`/`DTO` 等，整个项目使用 `Entity` 作为数据结构。

> 一些比较特殊的需求除外。

### 3. 注解

我们提供了一系列的注解：

#### 3.1 ``@ApiController``

标记为控制器方法，等同于 `@RequestMapping` + `@RestController` 的整合。

#### 3.2 `@Description` 与 `@Document` 

类或属性的文案，将显示在错误信息、验证信息、文档等处。

#### 3.3 `@Desensitize` 与 `@DesensitizeExclude`

标记脱敏字段和不脱敏的接口。

#### 3.4 `@ExcelColumn`

标记为Excel导出列，可配置导出列的数据类型。

#### 3.5 `@ReadOnly`、`@Exclude` 和 `@Expose`

标记列在指定的过滤器下暴露或者过滤。可为属性标记 `@ReadOnly` 表示该属性不参与控制器修改。

#### 3.6 `@Filter` 

标记过滤器，`3.5` 中的规则可使用此类过滤器进行过滤或者暴露。

#### 3.7 `@Extends` 

标记控制器需要从父类控制器中继承或排除哪些方法。

#### 3.8 `@Search`

标记属性参与搜索，可配置为模糊匹配、精确匹配、相等。

#### 3.9 `@Dictionary`

标记为字典属性，可使用下方 `4` 中的枚举字典接口的实现类。

### 4. 枚举字典

枚举字典需要实现 `IDictionary` 接口，即可使用 `3.9` 中的注解对属性进行标记，会自动进行判断和翻译。

### 5. `Root` 系类超类

所有参与API数据交互的部分都需要继承 `RootModel`, 一切需要入库的数据都需要继承 `RootEntity`。

所有控制器均需要继承 `RootController`，其中，如果是数据库相关的控制器，需要继承 `RootEntityController`。

### 6. 自定义异常

自定义异常需要实现 `IException` 接口，即可使用异常的快捷抛出等方法。

### 7. 标准树

实现了 `ITree` 的类都可实现标准的树结构，可使用 `TreeUtil` 的一系列方法。

### 8. 系统配置

`ServiceConfig`、`CookieConfig`、`WebSocketConfig` 等可以保存一些基础的服务配置，可通过环境变量注入。

### 9. `Utils`

提供了大量的工具包以供使用，可以查看 `cn.hamm.airpower.util` 包下的类，也可以直接使用 `Utils.getXXX()` 直接获取工具类使用。

## 三、快速开始

还有更多的说明文档正在编写中，敬请期待。

接下来就可以愉快的添砖Java了！
