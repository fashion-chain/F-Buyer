package com.hottop.core.request.argument.flag;

import com.hottop.core.BaseConstant;
import com.sun.javafx.binding.StringFormatter;
import org.apache.commons.lang.StringUtils;

import java.io.Serializable;

public class FlagPageSizeResolver implements FlagResolver, Serializable {
    private int page;
    private int size;

    public FlagPageSizeResolver(String flag) { //a string like "{page}_{offset}"
        if (StringUtils.isEmpty(flag)) {
            this.page = 0;
            this.size = BaseConstant.Response.PAGE_SIZE;
        } else {
            this.page = Integer.valueOf(flag.split("_")[0]);
            this.size = Integer.valueOf(flag.split("_")[1]);
        }
    }

    public FlagPageSizeResolver(int page, int size) {
        this.page = page;
        this.size = size;
    }

    @Override
    public int getPage() {
        return this.page;
    }

    @Override
    public int getSize() {
        return this.size;
    }

    @Override
    public long getOffset() {
        return this.page * this.size;
    }

    @Override
    public String toFlag() {
        return StringFormatter.format("%s_%s", this.page, this.size).getValue();
    }

    @Override
    public String next() {
        return StringFormatter.format("%s_%s", this.page+1, this.size).getValue();
    }

    @Override
    public String first() {
        return StringFormatter.format("%s_%s", 0, this.size).getValue();
    }

    @Override
    public String previous() {
        return StringFormatter.format("%s_%s", this.page-1, this.size).getValue();
    }
}
