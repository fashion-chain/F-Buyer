package com.hottop.backstage.cms.controller;

import com.hottop.core.model.zpoj.cms.ITemplate;
import com.hottop.core.model.zpoj.cms.enums.EActionType;
import com.hottop.core.model.zpoj.cms.enums.EComponentType;
import com.hottop.core.model.zpoj.cms.enums.ETemplateType;
import com.hottop.core.model.zpoj.cms.enums.EWidgetType;
import com.hottop.core.response.EResponseResult;
import com.hottop.core.response.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;

@RestController
@RequestMapping("/cms")
public class CmsController {
    Logger logger = LoggerFactory.getLogger(this.getClass());

    @RequestMapping(path = "/interfaces", method = RequestMethod.GET)
    public Response getITemplates(HttpServletRequest request) {
        HashMap<String, ArrayList<String>> interfaces = new HashMap<>();
        for (ETemplateType templateType : ETemplateType.values()) {
            ArrayList<String> childTypes = new ArrayList<>();
            switch (templateType) {
                case component:
                    for (EComponentType componentType: EComponentType.values()) {
                        childTypes.add(componentType.name());
                    }
                    break;
                case widget:
                    for (EWidgetType widgetType: EWidgetType.values()) {
                        childTypes.add(widgetType.name());
                    }
                    break;
            }
            interfaces.put(templateType.name(), childTypes);
        }
        ArrayList<String> actionTypes = new ArrayList<>();
        for (EActionType actionType: EActionType.values()) {
            actionTypes.add(actionType.name());
        }
        interfaces.put("action", actionTypes);
        return Response.ResponseBuilder.result(EResponseResult.OK)
                .data(interfaces)
                .create();
    }

    @RequestMapping(path = "/component", method = RequestMethod.GET)
    public Response getComponent(HttpServletRequest request,
                                 @RequestParam(value = "component_type", required = false) String componentTypeString) {
        EComponentType componentType = EComponentType.valueOf(componentTypeString);

        return Response.ResponseBuilder
                .result(EResponseResult.OK)
                .create();
    }

    @RequestMapping(path = "/action", method = RequestMethod.GET)
    public Response getAction() {
        return Response.ResponseBuilder
                .result(EResponseResult.OK)
                .create();
    }


    @RequestMapping(path = "/template", method = RequestMethod.POST)
    public Response postTemplate(HttpServletRequest request,
                                 @RequestBody HashMap<String, ITemplate> templateMapper) {

        return Response.ResponseBuilder
                .result(EResponseResult.OK)
                .data(templateMapper)
                .create();
    }
}
