# 1 - Analyze Requirement

## 阶段目标

使用 `requirement-clarifier` 技能，与用户对话完成系统管理模块需求澄清。

本阶段只做澄清，不生成正式需求文档，不生成 HTML 原型。

## 可参考资料

允许参考：

- `AGENTS.md`
- `.codex/skills/requirement-clarifier.md`
- `docs/system-management-srs.md`
- `docs/system-architecture-design.md`
- `docs/database-detail-design.md`
- `docs/system-management-use-case.puml`
- `docs/user-management-activity.puml`

参考原则：

- `docs` 用于辅助发现缺失规则和已有设计约束。
- 用户最新回答优先级高于旧文档。
- 任何影响核心设计的引用内容必须向用户确认。

## 必须执行

1. 读取 `.codex/skills/requirement-clarifier.md`。
2. 复述用户提出的模块需求。
3. 判断需求属于用户、角色、部门、菜单、字典、配置、日志或其他后台模块。
4. 对照 `docs` 中已有资料，识别缺失信息。
5. 主动列出缺失的业务规则、异常分支、边界条件、权限规则和数据范围。
6. 按主题向用户连续追问。
7. 每轮追问后整理已确认事项和待确认事项。

## 输出格式

```markdown
# 阶段 1 输出：需求澄清结果

## 1. 需求理解

## 2. 参考资料

## 3. 已确认需求结论

## 4. 已确认业务规则

## 5. 已确认异常分支

## 6. 已确认权限与数据范围

## 7. 待后续确认事项

## 8. 是否可以进入阶段 2

当前阶段已完成。我会先停在这里，等待你确认。

请回复“确认，进入阶段 2”，我再生成《系统概述与功能需求.md》。
```

## 停顿规则

阶段 1 完成后必须停止。

只有当用户明确回复“确认，进入阶段 2”或同等含义时，才能进入阶段 2。

