package com.hottop.core.model.zpoj.cms;

import com.hottop.core.model.zpoj.cms.enums.EActionType;

public interface IAction {
    String getSchema();

    String getHost();

    EActionType getPath();

    String getParams();

    String convertToAString();
}
