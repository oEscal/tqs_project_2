package com.api.demo.grid.utils;

import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.lang.Math;
import java.util.ArrayList;
import java.util.List;


public class Pagination<T> {

    private List<T> mData;

    public Pagination(List<T> mData) {
        this.mData = mData;
    }

    public PageImpl<T> pageImpl(int page, int entriesPerPage) {
        PageRequest pageRequest = PageRequest.of(page, entriesPerPage);

        int total = mData.size();
        int start = Math.toIntExact(pageRequest.getOffset()) - 1;
        int end = Math.min((start + pageRequest.getPageSize()), total);

        List<T> output = new ArrayList<>();

        if (start <= end) {
            output = mData.subList(start, end);
        }

        return new PageImpl<>(output, pageRequest, total);
    }

}
