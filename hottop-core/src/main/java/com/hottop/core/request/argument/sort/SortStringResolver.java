package com.hottop.core.request.argument.sort;

import org.apache.commons.lang.StringUtils;
import org.springframework.data.domain.Sort;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class SortStringResolver implements SortResolver, Serializable {
    private String sortString;
    private List<Sort.Order> orders;

    public SortStringResolver(String sortString) {  // a string like "-price,createDate"
        this.sortString = sortString;
        this.orders = new ArrayList<>();
    }

    @Override
    public Sort sort() {
        Sort sortObj = Sort.unsorted();
        if (this.orders.size() == 0 && StringUtils.isNotEmpty(this.sortString)) {
            String[] sorts = StringUtils.split(this.sortString, ",");
            for(String sort : sorts) {
                Sort.Direction direction;
                String queryDirection = sort.substring(0, 1);
                switch (queryDirection) {
                    case "-":
                        direction = Sort.Direction.DESC;
                        break;
                    case "+":
                    default:
                        direction = Sort.Direction.ASC;
                        break;
                }
                this.orders.add(new Sort.Order(direction,
                            StringUtils.replaceEach(sort.trim(), new String[]{"-", "+"}, new String[]{"", ""})));
            }
        }
        return Sort.by(this.orders);//this.orders.size() > 0 ? new Sort(this.orders) : Sort.unsorted();
    }

    @Override
    public String sortString() {
        return this.sortString;
    }
}
