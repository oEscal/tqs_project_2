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
        int start = Math.toIntExact(pageRequest.getOffset());
        int end = Math.min((start + pageRequest.getPageSize()), total);

        if (start > end)
            return new PageImpl<T>(new ArrayList<>(), pageRequest, total);

        return new PageImpl<T>(mData.subList(start, end), pageRequest, total);
    }

}