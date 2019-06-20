package com.hottop.core.product.service;

import com.hottop.core.model.commerce.CommerceSeries;
import com.hottop.core.repository.EntityBaseRepository;
import com.hottop.core.repository.commerce.CommerceSeriesRepository;
import com.hottop.core.service.EntityBaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("commerceSeriesService")
public class CommerceSeriesService extends EntityBaseService<CommerceSeries,Long>{

    @Autowired
    private CommerceSeriesRepository commerceSeriesRepository;

    @Override
    public EntityBaseRepository<CommerceSeries, Long> repository() {
        return commerceSeriesRepository;
    }


}
