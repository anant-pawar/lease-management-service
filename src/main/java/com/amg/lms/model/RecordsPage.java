package com.amg.lms.model;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class RecordsPage<T> {
    private final int currentPage;
    private final long totalItems;
    private final int totalPages;
    private final List<T> records;
}
