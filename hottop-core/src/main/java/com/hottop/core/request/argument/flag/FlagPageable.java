package com.hottop.core.request.argument.flag;

import com.hottop.core.BaseConstant;
import com.hottop.core.request.argument.sort.SortResolver;
import com.hottop.core.request.argument.sort.SortStringResolver;
import org.springframework.data.domain.Pageable;

import java.io.Serializable;

public abstract class FlagPageable implements Pageable, Serializable {
    private FlagResolver flagResolver;
    private SortResolver sortResolver;

    public FlagPageable(FlagResolver flagResolver) {
        this.flagResolver = flagResolver;
    }

    public FlagPageable(FlagResolver flagResolver, SortResolver sortResolver) {
        this(flagResolver);
        setSortResolver(sortResolver);
    }

    @Override
    public int getPageNumber() {
        return flagResolver.getPage();
    }

    @Override
    public int getPageSize() {
        return flagResolver.getSize();
    }

    @Override
    public long getOffset() {
        return flagResolver.getOffset();
    }

    public abstract Pageable previous();

    @Override
    public Pageable previousOrFirst() {
        return this.hasPrevious() ? this.previous() : this.first();
    }

    @Override
    public boolean hasPrevious() {
        return flagResolver.getPage() > 0;
    }

    public FlagResolver getFlagResolver() {
        return flagResolver;
    }

    public SortResolver getSortResolver() {
        return sortResolver;
    }

    public void setSortResolver(SortResolver sortResolver) {
        this.sortResolver = sortResolver;
    }
}
