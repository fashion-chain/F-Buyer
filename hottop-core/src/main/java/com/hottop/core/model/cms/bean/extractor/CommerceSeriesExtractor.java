package com.hottop.core.model.cms.bean.extractor;

import com.hottop.core.model.cms.bean.action.ActionBase;
import com.hottop.core.model.cms.bean.action.ActionSeries;
import com.hottop.core.model.cms.bean.action.EActionType;
import com.hottop.core.model.cms.bean.component.ComponentBase;
import com.hottop.core.model.cms.bean.component.ComponentSeries;
import com.hottop.core.model.cms.bean.component.EComponentType;
import com.hottop.core.model.commerce.CommerceSeries;
import com.hottop.core.model.commerce.CommerceService;
import com.hottop.core.model.commerce.CommerceSpu;
import com.hottop.core.product.service.CommerceSpuService;
import com.hottop.core.repository.commerce.CommerceSeriesRepository;
import com.hottop.core.repository.commerce.CommerceSpuRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

//系列
public class CommerceSeriesExtractor implements ICmsExtractor<CommerceSeries>{

    @Autowired
    private CommerceSeriesRepository commerceSeriesRepository;

    @Autowired
    private CommerceSpuRepository commerceSpuRepository;

    @Override
    public ActionBase extractAction(CommerceSeries obj, EActionType actionType) {
        switch (actionType) {
            case seriesDetail:
                return new ActionSeries(obj.getId());
        }
        return null;
    }

    @Override
    public ComponentBase extractComponent(CommerceSeries obj, EComponentType componentType) {
        switch (componentType){
            case series:
                return transferToComponent(obj);
        }
        return null;
    }

    @Override
    public EComponentType defaultComponentType() {
        return EComponentType.series;
    }

    @Override
    public EActionType defaultActionType() {
        return EActionType.seriesDetail;
    }

    @Override
    public Class<?> clazz() {
        return CommerceSeries.class;
    }

    //转化
    public ComponentSeries transferToComponent (CommerceSeries series) {
        ComponentSeries result = new ComponentSeries();
        result.setImage(series.getTopBackground());
        result.setTitle(series.getName());
        result.setDescription(series.getDescription());
        return result;
    }
}
