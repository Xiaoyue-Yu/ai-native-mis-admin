package com.creator.mis.system.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.creator.mis.common.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@TableName("sys_user")
public class SysUser extends BaseEntity {

    private String username;

    private String password;

    private String nickname;

    private String email;

    private String phone;

    private String avatar;

    private Integer sex;

    private Integer status;

    private Long deptId;

    private String remark;

    private String loginIp;

    private LocalDateTime loginTime;

    private LocalDateTime pwdUpdateTime;
}
