package com.creator.mis.system.domain.dto.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.List;

@Data
public class SysUserCreateRequest {

    @NotBlank(message = "Username must not be blank")
    @Size(max = 50, message = "Username must be at most 50 characters")
    private String username;

    @NotBlank(message = "Password must not be blank")
    @Size(min = 8, max = 64, message = "Password length must be between 8 and 64 characters")
    private String password;

    @NotBlank(message = "Nickname must not be blank")
    @Size(max = 50, message = "Nickname must be at most 50 characters")
    private String nickname;

    @Email(message = "Email format is invalid")
    @Size(max = 100, message = "Email must be at most 100 characters")
    private String email;

    @Pattern(regexp = "^$|^1\\d{10}$", message = "Phone format is invalid")
    private String phone;

    @Size(max = 500, message = "Avatar URL must be at most 500 characters")
    private String avatar;

    @NotNull(message = "Sex must not be null")
    @Min(value = 0, message = "Sex value is invalid")
    @Max(value = 2, message = "Sex value is invalid")
    private Integer sex;

    @NotNull(message = "Status must not be null")
    @Min(value = 0, message = "Status value is invalid")
    @Max(value = 1, message = "Status value is invalid")
    private Integer status;

    private Long deptId;

    @Size(max = 500, message = "Remark must be at most 500 characters")
    private String remark;

    private List<Long> roleIds;
}
