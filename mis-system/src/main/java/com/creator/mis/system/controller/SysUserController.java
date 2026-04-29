package com.creator.mis.system.controller;

import com.creator.mis.common.web.Result;
import com.creator.mis.system.domain.dto.user.SysUserCreateRequest;
import com.creator.mis.system.domain.dto.user.SysUserUpdateRequest;
import com.creator.mis.system.domain.vo.user.SysUserDetailVO;
import com.creator.mis.system.service.ISysUserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class SysUserController {

    private final ISysUserService sysUserService;

    @PostMapping("/add")
    public Result<Long> add(@Valid @RequestBody SysUserCreateRequest request) {
        return Result.success(sysUserService.createUser(request));
    }

    @PutMapping("/update")
    public Result<Void> update(@Valid @RequestBody SysUserUpdateRequest request) {
        sysUserService.updateUser(request);
        return Result.success();
    }

    @DeleteMapping("/delete/{id}")
    public Result<Void> delete(@PathVariable Long id) {
        sysUserService.deleteUser(id);
        return Result.success();
    }

    @GetMapping("/detail/{id}")
    public Result<SysUserDetailVO> detail(@PathVariable Long id) {
        return Result.success(sysUserService.getUserDetail(id));
    }
}
