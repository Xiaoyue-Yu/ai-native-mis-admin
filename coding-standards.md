# coding-standards.md - 编码规范

## Java 后端规范

### 实体类命名
- 系统管理表实体类统一使用 Sys 前缀
- 示例: SysUser、SysRole、SysDept、SysMenu
- 所有实体类必须继承 BaseEntity
- 使用 Lombok 注解: @Data、@Builder、@NoArgsConstructor

### BaseEntity 基类字段
```java
public abstract class BaseEntity {
    @TableId(type = IdType.AUTO)
    private Long id;
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;
    @TableField(fill = FieldFill.INSERT)
    private String createBy;
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private String updateBy;
    @TableLogic
    private Integer deleted;
}
```

### Service 层规范
- 接口命名: ISysUserService
- 实现类命名: SysUserServiceImpl
- 注入方式: 使用 @RequiredArgsConstructor 构造器注入
- 禁止使用 @Autowired 字段注入

### 异常处理
- 业务异常统一使用 BusinessException
- 全局异常处理器 GlobalExceptionHandler 捕获
- 禁止在业务代码中 try-catch 后不处理

## Vue 前端规范

### 组件规范
- 统一使用 Composition API + <script setup lang="ts">
- 组件命名: 大驼峰式，如 UserList.vue、RoleForm.vue
- 组件文件结构: <template> + <script setup> + <style scoped>

### 状态管理
- 使用 Pinia 进行全局状态管理
- Store 命名: useUserStore、usePermissionStore
- API 请求统一封装在 api/ 目录

### UI 组件
- 统一使用 Element Plus 组件库
- 表格: el-table + el-pagination
- 表单: el-form + el-form-item + 自定义验证规则

## 数据库规范

### 表命名
- 系统表统一使用 sys_ 前缀
- 字段命名统一 snake_case
- 必须包含公共字段: id、create_time、update_time、
  create_by、update_by、deleted

### 索引规范
- 主键索引: idx_{table}_pk
- 唯一索引: uk_{table}_{column}
- 普通索引: idx_{table}_{column}

## Git 提交规范
- feat: 新功能
- fix: 修复问题
- docs: 文档更新
- refactor: 代码重构
- test: 测试相关
- 示例: feat(sys-user): 添加用户导出Excel功能
