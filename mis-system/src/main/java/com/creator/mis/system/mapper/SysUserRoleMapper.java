package com.creator.mis.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.creator.mis.system.entity.SysUserRole;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface SysUserRoleMapper extends BaseMapper<SysUserRole> {

    @Select("SELECT role_id FROM sys_user_role WHERE user_id = #{userId}")
    List<Long> selectRoleIdsByUserId(@Param("userId") Long userId);
}
