package com.creator.mis.system.service;

import com.creator.mis.system.domain.dto.user.SysUserCreateRequest;
import com.creator.mis.system.domain.dto.user.SysUserUpdateRequest;
import com.creator.mis.system.domain.vo.user.SysUserDetailVO;

public interface ISysUserService {

    Long createUser(SysUserCreateRequest request);

    void updateUser(SysUserUpdateRequest request);

    void deleteUser(Long id);

    SysUserDetailVO getUserDetail(Long id);
}
