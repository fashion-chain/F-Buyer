package com.hottop.core.request.argument.sort;

import org.springframework.data.domain.Sort;

public interface SortResolver {
    Sort sort();

    String sortString();
}
