# api-design.md - API 设计规范

## RESTful 路径规范
```
基础路径: /api/admin/{module}/{action}
示例:
  GET    /api/admin/user/list        # 用户列表
  POST   /api/admin/user/add         # 新增用户
  PUT    /api/admin/user/update      # 更新用户
  DELETE /api/admin/user/delete/{id} # 删除用户
  GET    /api/admin/user/detail/{id} # 用户详情
```

## 权限注解规范
```java
@PreAuthorize("hasAuthority('sys:user:list')")
@GetMapping("/list")
public Result<PageResult<SysUserVO>> list(SysUserQuery query) {
    return Result.success(userService.pageList(query));
}
```
权限标识格式: sys:{module}:{action}
常用 action: list、add、edit、delete、export、import

## 请求/响应格式

### 统一响应包裹
```java
public class Result<T> {
    private Integer code;
    private String message;
    private T data;
}
```

### 分页响应
```java
public class PageResult<T> {
    private List<T> records;
    private Long total;
    private Long size;
    private Long current;
    private Long pages;
}
```

## HTTP 方法规范
| 操作 | HTTP方法 | 路径 | 说明 |
|------|-----------|------|------|
| 查询列表 | GET | /list | 支持分页、筛选 |
| 新增 | POST | /add | RequestBody 接收 |
| 更新 | PUT | /update | RequestBody 接收 |
| 删除 | DELETE | /delete/{id} | 路径参数 |
| 详情 | GET | /detail/{id} | 单条查询 |

## 错误码定义
| 错误码 | 描述 | 场景 |
|--------|------|------|
| 200 | 成功 | 请求成功 |
| 400 | 参数错误 | 请求参数校验失败 |
| 401 | 未认证 | Token 失效或缺失 |
| 403 | 无权限 | 权限不足 |
| 500 | 服务器错误 | 系统异常 |

## 用户管理 API 示例

### 用户列表查询
GET /api/admin/user/list?page=1&size=10&username=admin&status=1

### 新增用户
POST /api/admin/user/add
```json
{
  "username": "zhangsan",
  "password": "P@ssw0rd123",
  "nickname": "张三",
  "email": "zhangsan@example.com",
  "phone": "13800138000",
  "deptId": 100,
  "roleIds": [2, 3]
}
```

### 编辑用户
PUT /api/admin/user/update
```json
{
  "id": 1,
  "nickname": "张三更新",
  "email": "new@example.com",
  "roleIds": [2, 3, 4]
}
```

### 删除用户
DELETE /api/admin/user/delete/1

### 用户详情
GET /api/admin/user/detail/1
