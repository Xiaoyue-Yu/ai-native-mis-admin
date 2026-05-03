# Product Workflows - 系统管理模块产品工作流入口

## 用途

本文件是系统管理模块产品工作流的总入口，用来指导 AI 按阶段完成需求澄清、需求文档、业务规则文档、HTML 原型和最终校验。

AI 执行本工作流时，必须按阶段读取 `.codex/workflows/product-workflows/` 下的子工作流文件。每个阶段完成后都必须停顿，等待人类明确确认后，才能进入下一阶段。

## 是否可以参考 docs

可以参考 `docs` 目录中的内容，而且建议优先参考。参考资料包括：

- `docs/system-management-srs.md`：已有系统管理模块 SRS，可用于理解功能范围、角色、需求编号、当前实现状态。
- `docs/system-architecture-design.md`：已有系统架构设计，可用于理解技术边界、分层约束、接口协作和安全策略。
- `docs/database-detail-design.md`：已有数据库详细设计，可用于理解表结构、字段、索引、逻辑删除、审计字段和初始化数据。
- `docs/system-management-use-case.puml`：用例图，可用于理解用户角色和核心用例。
- `docs/user-management-activity.puml`：用户管理活动图，可用于理解用户管理流程。

但 AI 必须遵守以下限制：

- `docs` 只能作为上下文参考，不能替代阶段 1 的需求澄清。
- 如果 `docs` 与用户最新口径冲突，应优先询问用户确认。
- 如果 `docs` 中的信息会影响字段、权限、状态、流程或数据库设计，必须在阶段 1 或对应阶段中显式确认。
- 不得把 `docs` 中的“待实现”“规划能力”当作已经确认的最终需求。
- 引用 `docs` 的内容时，应说明来源，例如“参考 `docs/system-management-srs.md`”。

## 推荐目录结构

```text
.codex/
  skills/
    requirement-clarifier.md
    frontend-design.md
  workflows/
    product-workflows.md
    product-workflows/
      1-analyze-requirement.md
      2-generate-system-overview-functional-requirements.md
      3-generate-nonfunctional-business-rules.md
      4-generate-prototype.md
      5-verify-iterate.md
```

## 阶段总览

| 阶段 | 子工作流文件 | 目标产物 | 使用 Skill | 是否强制停顿 |
|------|--------------|----------|------------|--------------|
| 阶段 1 | `1-analyze-requirement.md` | 需求澄清结果 | `requirement-clarifier` | 是 |
| 阶段 2 | `2-generate-system-overview-functional-requirements.md` | 《系统概述与功能需求.md》 | 无强制 skill | 是 |
| 阶段 3 | `3-generate-nonfunctional-business-rules.md` | 《非功能性需求与业务规则.md》 | 无强制 skill | 是 |
| 阶段 4 | `4-generate-prototype.md` | HTML 原型页面 | `frontend-design` | 是 |
| 阶段 5 | `5-verify-iterate.md` | 覆盖校验与迭代清单 | 无强制 skill | 是 |

## 执行总规则

AI 必须严格遵守：

1. 每次只执行一个阶段。
2. 每个阶段只读取当前阶段必要的输入。
3. 每个阶段完成后必须停止。
4. 未收到人类明确确认，不得进入下一阶段。
5. 不得一次性生成所有文档和原型。
6. 不得把未确认的推测写成正式结论。
7. 需要参考 `docs` 时，必须先判断该资料是否与当前阶段相关。

## 标准确认语

每个阶段结束时，AI 必须使用类似确认语：

```markdown
当前阶段已完成。我会先停在这里，等待你确认。

请回复“确认，进入阶段 X”，我再继续下一阶段。
```

## 阶段入口

执行具体阶段时，请读取对应文件：

- 阶段 1：`.codex/workflows/product-workflows/1-analyze-requirement.md`
- 阶段 2：`.codex/workflows/product-workflows/2-generate-system-overview-functional-requirements.md`
- 阶段 3：`.codex/workflows/product-workflows/3-generate-nonfunctional-business-rules.md`
- 阶段 4：`.codex/workflows/product-workflows/4-generate-prototype.md`
- 阶段 5：`.codex/workflows/product-workflows/5-verify-iterate.md`

