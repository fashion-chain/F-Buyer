package com.hottop.core.request.argument.flag;

import com.hottop.core.BaseConstant;
import com.hottop.core.request.argument.sort.SortResolver;
import com.hottop.core.request.argument.sort.SortStringResolver;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.io.Serializable;

public class FlagPageSizeRequest extends FlagPageable implements Serializable {

    public static FlagPageSizeRequest DEFAULT = new FlagPageSizeRequest(
            new FlagPageSizeResolver(0, BaseConstant.Response.PAGE_SIZE).toFlag(),
            new SortStringResolver(""));

    public FlagPageSizeRequest(String flag) {
        super(new FlagPageSizeResolver(flag));
    }

    public FlagPageSizeRequest(String flag, SortResolver sortResolver) {
        super(new FlagPageSizeResolver(flag));
        setSortResolver(sortResolver);
    }


    @Override
    public Sort getSort() {
        return getSortResolver() != null ? getSortResolver().sort() : null;
    }

    @Override
    public Pageable previous() {
        return new FlagPageSizeRequest(getFlagResolver().previous());
    }

    @Override
    public Pageable next() {
        return new FlagPageSizeRequest(getFlagResolver().next());
    }

    @Override
    public Pageable first() {
        return new FlagPageSizeRequest(getFlagResolver().first());
    }
}
