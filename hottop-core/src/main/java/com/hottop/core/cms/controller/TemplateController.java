package com.hottop.core.cms.controller;

import com.hottop.core.cms.service.TemplateService;
import com.hottop.core.controller.EntityBaseController;
import com.hottop.core.model.cms.Template;
import com.hottop.core.model.cms.bean.TemplateContent;
import com.hottop.core.response.EResponseResult;
import com.hottop.core.response.Response;
import com.hottop.core.service.EntityBaseService;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/template")
public class TemplateController extends EntityBaseController<Template> {

    @Autowired
    private TemplateService templateService;

    @RequestMapping(path = "/", method = RequestMethod.GET)
    public Response getTemplate(HttpServletRequest request,
                                @RequestParam(value = "name", required = false) String name,
                                @RequestParam(value = "templateId", required = false) Long templateId) throws NotFoundException {
        Template template = templateService.getTemplateByIdOrName(templateId, name);
        if (template != null) {
            return Response.ResponseBuilder.result(EResponseResult.OK)
                    .data(template)
                    .create();
        } else {
            throw new NotFoundException(String.format("template: %s not found", name));
        }
    }

    @RequestMapping(path = "/", method = RequestMethod.POST)
    public Response newTemplate(HttpServletRequest request,
                                @RequestParam(value = "name") String name,
                                @RequestBody TemplateContent content) {
        Template template = new Template();
        template.setName(name);
        template.setContent(content);
        template = templateService.save(template);
        return Response.ResponseBuilder.result(EResponseResult.OK)
                .data(template)
                .create();
    }

    @RequestMapping(path = "/", method = RequestMethod.PUT)
    public Response updateTemplate(HttpServletRequest request,
                                   @RequestParam(value = "name", required = false) String name,
                                   @RequestParam(value = "templateId", required = false) Long templateId,
                                   @RequestBody TemplateContent content) throws NotFoundException {
        Template template = templateService.getTemplateByIdOrName(templateId, name);
        if (template != null) {
            template.setContent(content);
            template = templateService.save(template);
            return Response.ResponseBuilder.result(EResponseResult.OK)
                    .data(template)
                    .create();
        } else {
            throw new NotFoundException(String.format("template: %s not found", name));
        }
    }

    @RequestMapping(path = "/", method = RequestMethod.DELETE)
    public Response deleteTemplate(HttpServletRequest request,
                                   @RequestParam(value = "name", required = false) String name,
                                   @RequestParam(value = "templateId", required = false) Long templateId) throws NotFoundException {
        Template template = templateService.getTemplateByIdOrName(templateId, name);
        if (template != null && !template.isDeleted()) {
            templateService.delete(template);
            return Response.ResponseBuilder.result(EResponseResult.OK)
                    .data(template)
                    .create();
        } else {
            throw new NotFoundException(String.format("template: %s not found", name));
        }
    }

    @Override
    public Class<Template> clazz() {
        return Template.class;
    }

    @Override
    public EntityBaseService service() {
        return templateService;
    }
}
