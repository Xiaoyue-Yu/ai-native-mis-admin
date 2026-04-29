package com.creator.mis.system.service.impl;

import com.creator.mis.common.exception.BusinessException;
import com.creator.mis.system.domain.dto.user.SysUserCreateRequest;
import com.creator.mis.system.domain.dto.user.SysUserUpdateRequest;
import com.creator.mis.system.domain.vo.user.SysUserDetailVO;
import com.creator.mis.system.entity.SysUser;
import com.creator.mis.system.entity.SysUserRole;
import com.creator.mis.system.mapper.SysUserMapper;
import com.creator.mis.system.mapper.SysUserRoleMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SysUserServiceImplTest {

    @Mock
    private SysUserMapper sysUserMapper;

    @Mock
    private SysUserRoleMapper sysUserRoleMapper;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private SysUserServiceImpl sysUserService;

    private SysUserCreateRequest createRequest;

    @BeforeEach
    void setUp() {
        createRequest = new SysUserCreateRequest();
        createRequest.setUsername("alice");
        createRequest.setPassword("P@ssw0rd123");
        createRequest.setNickname("Alice");
        createRequest.setEmail("alice@example.com");
        createRequest.setPhone("13800138000");
        createRequest.setAvatar("https://example.com/avatar.png");
        createRequest.setSex(2);
        createRequest.setStatus(1);
        createRequest.setDeptId(9L);
        createRequest.setRemark("remark");
        createRequest.setRoleIds(Arrays.asList(1L, 2L, 2L, null));
    }

    @Test
    void createUserShouldEncryptPasswordAndPersistDistinctRoles() {
        when(sysUserMapper.selectCount(any())).thenReturn(0L);
        when(passwordEncoder.encode("P@ssw0rd123")).thenReturn("encoded");
        doAnswer(invocation -> {
            SysUser user = invocation.getArgument(0);
            user.setId(100L);
            return 1;
        }).when(sysUserMapper).insert(any(SysUser.class));

        Long userId = sysUserService.createUser(createRequest);

        assertEquals(100L, userId);
        ArgumentCaptor<SysUser> userCaptor = ArgumentCaptor.forClass(SysUser.class);
        verify(sysUserMapper).insert(userCaptor.capture());
        assertEquals("alice", userCaptor.getValue().getUsername());
        assertEquals("encoded", userCaptor.getValue().getPassword());

        ArgumentCaptor<SysUserRole> roleCaptor = ArgumentCaptor.forClass(SysUserRole.class);
        verify(sysUserRoleMapper).delete(any());
        verify(sysUserRoleMapper, times(2)).insert(roleCaptor.capture());
        List<SysUserRole> savedRoles = roleCaptor.getAllValues();
        assertEquals(1L, savedRoles.get(0).getRoleId());
        assertEquals(2L, savedRoles.get(1).getRoleId());
    }

    @Test
    void createUserShouldRejectDuplicateUsername() {
        when(sysUserMapper.selectCount(any())).thenReturn(1L);

        BusinessException ex = assertThrows(BusinessException.class, () -> sysUserService.createUser(createRequest));

        assertEquals("Username already exists", ex.getMessage());
        verify(sysUserMapper, never()).insert(any());
    }

    @Test
    void createUserShouldHandleBlankOptionalFieldsAndEmptyRoles() {
        createRequest.setNickname("  ");
        createRequest.setEmail(null);
        createRequest.setPhone("");
        createRequest.setAvatar("   ");
        createRequest.setRemark(null);
        createRequest.setRoleIds(null);

        when(sysUserMapper.selectCount(any())).thenReturn(null);
        when(passwordEncoder.encode("P@ssw0rd123")).thenReturn("encoded");
        doAnswer(invocation -> {
            SysUser user = invocation.getArgument(0);
            user.setId(101L);
            return 1;
        }).when(sysUserMapper).insert(any(SysUser.class));

        Long userId = sysUserService.createUser(createRequest);

        assertEquals(101L, userId);
        ArgumentCaptor<SysUser> userCaptor = ArgumentCaptor.forClass(SysUser.class);
        verify(sysUserMapper, times(1)).insert(userCaptor.capture());
        assertEquals("", userCaptor.getValue().getNickname());
        assertEquals("", userCaptor.getValue().getEmail());
        assertEquals("", userCaptor.getValue().getAvatar());
        verify(sysUserRoleMapper).delete(any());
        verify(sysUserRoleMapper, never()).insert(any());
    }

    @Test
    void createUserShouldFailWhenInsertResultIsUnexpected() {
        when(sysUserMapper.selectCount(any())).thenReturn(0L);
        when(passwordEncoder.encode("P@ssw0rd123")).thenReturn("encoded");
        when(sysUserMapper.insert(any(SysUser.class))).thenReturn(0);

        BusinessException ex = assertThrows(BusinessException.class, () -> sysUserService.createUser(createRequest));

        assertEquals(500, ex.getCode());
        assertEquals("Create user failed", ex.getMessage());
    }

    @Test
    void updateUserShouldPersistFieldsAndReplaceRoles() {
        SysUserUpdateRequest request = new SysUserUpdateRequest();
        request.setId(12L);
        request.setNickname("New Name");
        request.setEmail("new@example.com");
        request.setPhone("13900139000");
        request.setAvatar("https://example.com/new.png");
        request.setSex(1);
        request.setStatus(0);
        request.setDeptId(11L);
        request.setRemark("updated");
        request.setRoleIds(List.of(3L, 4L, 3L));

        SysUser existing = new SysUser();
        existing.setId(12L);
        when(sysUserMapper.selectById(12L)).thenReturn(existing);
        when(sysUserMapper.updateById(any(SysUser.class))).thenReturn(1);

        sysUserService.updateUser(request);

        ArgumentCaptor<SysUser> userCaptor = ArgumentCaptor.forClass(SysUser.class);
        verify(sysUserMapper).updateById(userCaptor.capture());
        assertEquals(12L, userCaptor.getValue().getId());
        assertEquals("New Name", userCaptor.getValue().getNickname());
        assertEquals("new@example.com", userCaptor.getValue().getEmail());

        ArgumentCaptor<SysUserRole> roleCaptor = ArgumentCaptor.forClass(SysUserRole.class);
        verify(sysUserRoleMapper).delete(any());
        verify(sysUserRoleMapper, times(2)).insert(roleCaptor.capture());
        List<SysUserRole> savedRoles = roleCaptor.getAllValues();
        assertEquals(3L, savedRoles.get(0).getRoleId());
        assertEquals(4L, savedRoles.get(1).getRoleId());
    }

    @Test
    void updateUserShouldFailWhenUserMissing() {
        SysUserUpdateRequest request = new SysUserUpdateRequest();
        request.setId(404L);
        request.setNickname("missing");
        request.setSex(0);
        request.setStatus(1);

        when(sysUserMapper.selectById(404L)).thenReturn(null);

        BusinessException ex = assertThrows(BusinessException.class, () -> sysUserService.updateUser(request));

        assertEquals(404, ex.getCode());
        assertEquals("User not found", ex.getMessage());
    }

    @Test
    void updateUserShouldFailWhenUpdateResultIsUnexpected() {
        SysUserUpdateRequest request = new SysUserUpdateRequest();
        request.setId(12L);
        request.setNickname("updated");
        request.setEmail("updated@example.com");
        request.setPhone("13900139000");
        request.setAvatar("");
        request.setSex(1);
        request.setStatus(1);
        request.setDeptId(1L);
        request.setRemark("remark");
        request.setRoleIds(List.of());

        SysUser existing = new SysUser();
        existing.setId(12L);
        when(sysUserMapper.selectById(12L)).thenReturn(existing);
        when(sysUserMapper.updateById(any(SysUser.class))).thenReturn(0);

        BusinessException ex = assertThrows(BusinessException.class, () -> sysUserService.updateUser(request));

        assertEquals(500, ex.getCode());
        assertEquals("Update user failed", ex.getMessage());
    }

    @Test
    void deleteUserShouldSoftDelete() {
        SysUser existing = new SysUser();
        existing.setId(7L);
        when(sysUserMapper.selectById(7L)).thenReturn(existing);
        when(sysUserMapper.updateById(any(SysUser.class))).thenReturn(1);

        sysUserService.deleteUser(7L);

        ArgumentCaptor<SysUser> userCaptor = ArgumentCaptor.forClass(SysUser.class);
        verify(sysUserMapper).updateById(userCaptor.capture());
        assertEquals(7L, userCaptor.getValue().getId());
        assertEquals(1, userCaptor.getValue().getDeleted());
    }

    @Test
    void deleteUserShouldFailWhenMissing() {
        when(sysUserMapper.selectById(9L)).thenReturn(null);

        BusinessException ex = assertThrows(BusinessException.class, () -> sysUserService.deleteUser(9L));

        assertEquals(404, ex.getCode());
        assertEquals("User not found", ex.getMessage());
    }

    @Test
    void deleteUserShouldFailWhenUpdateResultIsUnexpected() {
        SysUser existing = new SysUser();
        existing.setId(10L);
        when(sysUserMapper.selectById(10L)).thenReturn(existing);
        when(sysUserMapper.updateById(any(SysUser.class))).thenReturn(0);

        BusinessException ex = assertThrows(BusinessException.class, () -> sysUserService.deleteUser(10L));

        assertEquals(500, ex.getCode());
        assertEquals("Delete user failed", ex.getMessage());
    }

    @Test
    void getUserDetailShouldReturnRoleIds() {
        SysUser user = new SysUser();
        user.setId(5L);
        user.setUsername("bob");
        user.setNickname("Bob");
        user.setStatus(1);
        when(sysUserMapper.selectById(5L)).thenReturn(user);
        when(sysUserRoleMapper.selectRoleIdsByUserId(5L)).thenReturn(List.of(8L, 9L));

        SysUserDetailVO detail = sysUserService.getUserDetail(5L);

        assertEquals("bob", detail.getUsername());
        assertIterableEquals(List.of(8L, 9L), detail.getRoleIds());
    }

    @Test
    void getUserDetailShouldFailWhenMissing() {
        when(sysUserMapper.selectById(anyLong())).thenReturn(null);

        BusinessException ex = assertThrows(BusinessException.class, () -> sysUserService.getUserDetail(2L));

        assertEquals(404, ex.getCode());
        assertEquals("User not found", ex.getMessage());
    }
}
