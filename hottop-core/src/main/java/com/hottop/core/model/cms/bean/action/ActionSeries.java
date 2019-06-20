package com.hottop.core.model.cms.bean.action;

import lombok.Getter;

@Getter
public class ActionSeries extends ActionBase {

    private Long seriesId;

    public ActionSeries(Long seriesId) {
        super(EActionType.seriesDetail);
        this.seriesId = seriesId;
    }
}
