package com.creator.mis.system.domain.vo.user;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
public class SysUserDetailVO {

    private Long id;

    private String username;

    private String nickname;

    private String email;

    private String phone;

    private String avatar;

    private Integer sex;

    private Integer status;

    private Long deptId;

    private String remark;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

    private List<Long> roleIds;
}
