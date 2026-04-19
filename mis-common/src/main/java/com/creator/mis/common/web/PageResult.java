package com.creator.mis.common.web;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.util.Collections;
import java.util.List;

/**
 * Paged list payload (api-design.md).
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PageResult<T> implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private List<T> records;
    private Long total;
    private Long size;
    private Long current;
    private Long pages;

    public static <T> PageResult<T> empty(long current, long size) {
        return new PageResult<>(Collections.emptyList(), 0L, size, current, 0L);
    }
}
