# AGENTS.md - MIS系统管理模块

## 项目概述
本项目是基于 Spring Boot 3 + Vue3 的 MIS系统管理模块，采用
Harness Engineering 方法论进行开发。系统涵盖用户管理、
角色权限、部门管理、菜单管理、数据字典、操作日志、
系统配置七大核心模块。

## 技术栈
- **后端**: Spring Boot 3.2 + MyBatis-Plus 3.5 + MySQL 8.0 + Redis 7
- **前端**: Vue 3.4 + TypeScript + Element Plus + Pinia + Vite 5
- **工具**: Flyway 数据库迁移 + Maven 构建 + Git Flow

## 模块列表
| 模块名称 | 表前缀 | 描述 |
|---------|---------|------|
| 用户管理 | sys_user | 用户CRUD、密码策略、角色分配 |
| 角色权限 | sys_role | RBAC3模型、角色管理、权限分配 |
| 部门管理 | sys_dept | 树形结构、组织架构管理 |
| 菜单管理 | sys_menu | 动态路由、菜单权限控制 |
| 数据字典 | sys_dict | 字典类型与字典项管理 |
| 操作日志 | sys_log | AOP自动记录、日志查询 |
| 系统配置 | sys_config | 参数配置、缓存管理 |

## Agent 职责划分

### Initializer Agent
- 读取配置文件（AGENTS.md、coding-standards.md、api-design.md）
- 生成项目脚手架、数据库迁移脚本
- 创建基础实体类、工具类

### Requirement Agent
- 利用 `requirement-clarifier` 技能进行需求澄清，明确目标用户、范围边界、业务流程、数据口径与约束条件
- 产出分章节的 Markdown 需求文档（按模块/场景/流程拆分，便于评审与迭代）
- 利用 `frontend-design` 技能生成 HTML 页面原型（关键页面结构、交互流与组件布局）

### Coding Agent
- 根据 feature_list.json 执行具体功能开发
- 调用 Sub-agent 完成特定任务
- 遵循 coding-standards.md 编码规范

## 会话上下文规则
1. 每次会话开始时，必须重新加载配置文件
2. 使用 feature_list.json 跟踪进度
3. 每完成一个 feature，更新 status 字段
4. 跨会话传递关键上下文信息
5. 在需求分析阶段，必须严格遵循 `.codex/workflows/product-workflows.md` 定义的 SOP

## Sub-agent 定义

### CRUD Generator
- 用途: 生成标准增删改查代码
- 输入: 模块名、表名、字段定义
- 输出: Controller、Service、Mapper、Vue页面

### RBAC Agent
- 用途: 处理权限相关逻辑
- 输入: 模块名、操作类型
- 输出: 权限标识、数据范围过滤

### Requirement Clarifier（需求澄清专家）
- 用途: 使用 `requirement-clarifier` 进行澄清与结构化需求输出
- 输入: 业务目标、现状痛点、角色与权限、关键流程、数据字典/口径、非功能需求
- 输出: 分章节 Markdown 需求文档（含范围、用户故事/用例、流程、验收标准、风险与假设）

### UI/UX Designer（前端原型专家）
- 用途: 使用 `frontend-design` 生成可评审的页面原型与交互说明
- 输入: 页面清单、路由/菜单结构、字段与校验规则、交互状态（空/加载/错误/权限）
- 输出: HTML 页面原型（页面布局、组件结构、交互流说明，可直接用于产品评审）

### Test Agent
- 用途: 生成单元测试
- 输入: 模块名、接口定义
- 输出: JUnit 5 测试类

### Migration Agent
- 用途: 生成 Flyway 迁移脚本
- 输入: 表定义、索引定义
- 输出: SQL 迁移文件

## 反模式警告
- 禁止在 Controller 层直接操作数据库
- 禁止硬编码权限标识
- 禁止跳过 Service 层直接调用 Mapper
- 禁止使用 SELECT *
- 禁止在代码中硬编码数据库连接信息
