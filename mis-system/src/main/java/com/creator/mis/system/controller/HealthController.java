package com.creator.mis.system.controller;

import com.creator.mis.common.web.Result;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Smoke endpoint; API base path is {@code /api/admin} (api-design.md).
 */
@RestController
public class HealthController {

    @GetMapping("/ping")
    public Result<String> ping() {
        return Result.success("ok");
    }
}
