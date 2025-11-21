package com.example.demo.util;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;

public class PageUtil {
    public static <T> Page<T> getPage(List<T> data, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        int total = data.size();
        int start = (int) pageable.getOffset();
        int end = Math.min((start + pageable.getPageSize()), total);
        List<T> content = data.subList(start, end);

        return new PageImpl<>(content, pageable, total);
    }
}
