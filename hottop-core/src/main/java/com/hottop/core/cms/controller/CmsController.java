package com.hottop.core.cms.controller;

import com.hottop.core.model.cms.bean.action.EActionType;
import com.hottop.core.model.cms.bean.component.EComponentType;
import com.hottop.core.response.EResponseResult;
import com.hottop.core.response.Response;
import com.hottop.core.utils.CommonUtil;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

@RestController
@RequestMapping(path = "/cms")
public class CmsController {
    @RequestMapping(path = "/components", method = RequestMethod.GET)
    public Response components() {
        HashMap<EComponentType, Object> componentInterface = new HashMap<>();
        for (EComponentType componentType: EComponentType.values()) {
            componentInterface.put(componentType, CommonUtil.getMapFromObj(componentType));
        }
        return Response.ResponseBuilder.result(EResponseResult.OK)
                .data(componentInterface)
                .create();
    }

    @RequestMapping(path = "/actions", method = RequestMethod.GET)
    public Response actions() {
        HashMap<EActionType, Object> actionInterface = new HashMap<>();
        for (EActionType actionType: EActionType.values()) {
            actionInterface.put(actionType, CommonUtil.getMapFromObj(actionType));
        }
        return Response.ResponseBuilder.result(EResponseResult.OK)
                .data(actionInterface)
                .create();
    }

}
