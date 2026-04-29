package com.creator.mis.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.creator.mis.common.exception.BusinessException;
import com.creator.mis.system.domain.dto.user.SysUserCreateRequest;
import com.creator.mis.system.domain.dto.user.SysUserUpdateRequest;
import com.creator.mis.system.domain.vo.user.SysUserDetailVO;
import com.creator.mis.system.entity.SysUser;
import com.creator.mis.system.entity.SysUserRole;
import com.creator.mis.system.mapper.SysUserMapper;
import com.creator.mis.system.mapper.SysUserRoleMapper;
import com.creator.mis.system.service.ISysUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SysUserServiceImpl implements ISysUserService {

    private final SysUserMapper sysUserMapper;
    private final SysUserRoleMapper sysUserRoleMapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long createUser(SysUserCreateRequest request) {
        String username = normalize(request.getUsername());
        ensureUsernameUnique(username);

        SysUser user = new SysUser();
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setNickname(normalize(request.getNickname()));
        user.setEmail(normalize(request.getEmail()));
        user.setPhone(normalize(request.getPhone()));
        user.setAvatar(normalize(request.getAvatar()));
        user.setSex(request.getSex());
        user.setStatus(request.getStatus());
        user.setDeptId(request.getDeptId());
        user.setRemark(normalize(request.getRemark()));
        user.setPwdUpdateTime(LocalDateTime.now());

        int inserted = sysUserMapper.insert(user);
        if (inserted != 1 || user.getId() == null) {
            throw new BusinessException(500, "Create user failed");
        }

        saveUserRoles(user.getId(), request.getRoleIds());
        return user.getId();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateUser(SysUserUpdateRequest request) {
        SysUser existing = sysUserMapper.selectById(request.getId());
        if (existing == null) {
            throw new BusinessException(404, "User not found");
        }

        SysUser user = new SysUser();
        user.setId(request.getId());
        user.setNickname(normalize(request.getNickname()));
        user.setEmail(normalize(request.getEmail()));
        user.setPhone(normalize(request.getPhone()));
        user.setAvatar(normalize(request.getAvatar()));
        user.setSex(request.getSex());
        user.setStatus(request.getStatus());
        user.setDeptId(request.getDeptId());
        user.setRemark(normalize(request.getRemark()));

        int updated = sysUserMapper.updateById(user);
        if (updated != 1) {
            throw new BusinessException(500, "Update user failed");
        }

        saveUserRoles(request.getId(), request.getRoleIds());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteUser(Long id) {
        SysUser existing = sysUserMapper.selectById(id);
        if (existing == null) {
            throw new BusinessException(404, "User not found");
        }

        int updated = sysUserMapper.update(
            null,
            new LambdaUpdateWrapper<SysUser>()
                .eq(SysUser::getId, id)
                .set(SysUser::getDeleted, 1)
        );
        if (updated != 1) {
            throw new BusinessException(500, "Delete user failed");
        }
    }

    @Override
    public SysUserDetailVO getUserDetail(Long id) {
        SysUser user = sysUserMapper.selectById(id);
        if (user == null) {
            throw new BusinessException(404, "User not found");
        }

        return SysUserDetailVO.builder()
            .id(user.getId())
            .username(user.getUsername())
            .nickname(user.getNickname())
            .email(user.getEmail())
            .phone(user.getPhone())
            .avatar(user.getAvatar())
            .sex(user.getSex())
            .status(user.getStatus())
            .deptId(user.getDeptId())
            .remark(user.getRemark())
            .createTime(user.getCreateTime())
            .updateTime(user.getUpdateTime())
            .roleIds(sysUserRoleMapper.selectRoleIdsByUserId(id))
            .build();
    }

    private void ensureUsernameUnique(String username) {
        Long count = sysUserMapper.selectCount(new LambdaQueryWrapper<SysUser>()
            .eq(SysUser::getUsername, username));
        if (count != null && count > 0) {
            throw new BusinessException("Username already exists");
        }
    }

    private void saveUserRoles(Long userId, List<Long> roleIds) {
        sysUserRoleMapper.delete(new LambdaQueryWrapper<SysUserRole>()
            .eq(SysUserRole::getUserId, userId));

        List<Long> normalizedRoleIds = distinctRoleIds(roleIds);
        if (normalizedRoleIds.isEmpty()) {
            return;
        }

        for (Long roleId : normalizedRoleIds) {
            SysUserRole userRole = new SysUserRole();
            userRole.setUserId(userId);
            userRole.setRoleId(roleId);
            sysUserRoleMapper.insert(userRole);
        }
    }

    private List<Long> distinctRoleIds(List<Long> roleIds) {
        if (CollectionUtils.isEmpty(roleIds)) {
            return List.of();
        }
        LinkedHashSet<Long> set = new LinkedHashSet<>();
        for (Long roleId : roleIds) {
            if (roleId != null) {
                set.add(roleId);
            }
        }
        return new ArrayList<>(set);
    }

    private String normalize(String value) {
        return StringUtils.hasText(value) ? value.trim() : "";
    }
}
