package com.hottop.core.cms.controller;

import com.hottop.core.cms.service.PageService;
import com.hottop.core.cms.service.TemplateService;
import com.hottop.core.controller.EntityBaseController;
import com.hottop.core.model.cms.Page;
import com.hottop.core.model.cms.Template;
import com.hottop.core.model.cms.bean.CmsValidation;
import com.hottop.core.model.cms.bean.ComponentContentDto;
import com.hottop.core.model.cms.bean.PageContent;
import com.hottop.core.response.EResponseResult;
import com.hottop.core.response.Response;
import com.hottop.core.service.EntityBaseService;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/page")
public class PageController extends EntityBaseController<Page> {

    @Autowired
    private PageService pageService;

    @Autowired
    private TemplateService templateService;

    @RequestMapping(path = "/", method = RequestMethod.GET)
    public Response getPage(HttpServletRequest request,
                                @RequestParam(value = "name", required = false) String name,
                                @RequestParam(value = "pageId", required = false) Long pageId) throws NotFoundException {
        Page page = pageService.getPageByIdOrName(pageId, name);
        if (page != null) {
            return Response.ResponseBuilder.result(EResponseResult.OK)
                    .data(pageService.decorate(page))
                    .create();
        } else {
            throw new NotFoundException(String.format("page: %s not found", name));
        }
    }

    @RequestMapping(path = "/", method = RequestMethod.POST)
    public Response newPage(HttpServletRequest request,
                                @RequestParam(value = "name") String name,
                                @RequestParam(value = "templateId") Long templateId,
                                @RequestBody ComponentContentDto contentDto) throws NotFoundException {
        Template template = templateService.findById(templateId);
        if (template != null) {
            if (CmsValidation.templateValidation(contentDto.getPageContent(), template.getContent())
                    && CmsValidation.contentValidation(contentDto.getPageContent())
                    && CmsValidation.dataDecoratorValidation(contentDto.getPageContent(), contentDto.getDataDecorator())) {
                Page page = new Page();
                page.setName(name);
                page.setPageContent(contentDto.getPageContent());
                page.setDataDecorator(contentDto.getDataDecorator());
                page = pageService.save(page);
                return Response.ResponseBuilder.result(EResponseResult.OK)
                        .data(page)
                        .create();
            } else {
                return Response.ResponseBuilder.result(EResponseResult.ERROR_CMS_VALIDATION_FAILURE)
                        .create();
            }
        } else {
            throw new NotFoundException(String.format("creating page, template(id): %s not found not found", templateId));
        }
    }

    @RequestMapping(path = "/", method = RequestMethod.PUT)
    public Response updatePage(HttpServletRequest request,
                                    @RequestParam(value = "name", required = false) String name,
                                    @RequestParam(value = "pageId", required = false) Long pageId,
                                    @RequestParam(value = "templateId") Long templateId,
                                    @RequestBody ComponentContentDto contentDto) throws NotFoundException {
        Template template = templateService.findById(templateId);
        Page page = pageService.getPageByIdOrName(pageId, name);
        if (template != null && page != null) {
            if (CmsValidation.templateValidation(contentDto.getPageContent(), template.getContent())
                    && CmsValidation.contentValidation(contentDto.getPageContent())
                    && CmsValidation.dataDecoratorValidation(contentDto.getPageContent(), contentDto.getDataDecorator())) {
                if (name != null) page.setName(name);
                page.setPageContent(contentDto.getPageContent());
                page.setDataDecorator(contentDto.getDataDecorator());
                page = pageService.save(page);
                return Response.ResponseBuilder.result(EResponseResult.OK)
                        .data(page)
                        .create();
            } else {
                return Response.ResponseBuilder.result(EResponseResult.ERROR_CMS_VALIDATION_FAILURE)
                        .create();
            }
        } else {
            throw new NotFoundException(String.format("updating page, pageName: %s, template(id): %s not found not found", name, templateId));
        }
    }

    @RequestMapping(path = "/", method = RequestMethod.DELETE)
    public Response deletePage(HttpServletRequest request,
                                   @RequestParam(value = "name", required = false) String name,
                                   @RequestParam(value = "pageId", required = false) Long pageId,
                                   @RequestBody PageContent content) throws NotFoundException {
        Page page = pageService.getPageByIdOrName(pageId, name);
        if (page != null && !page.isDeleted()) {
            pageService.delete(page);
            return Response.ResponseBuilder.result(EResponseResult.OK)
                    .data(page)
                    .create();
        } else {
            throw new NotFoundException(String.format("page: %s not found", name));
        }
    }

    @Override
    public Class<Page> clazz() {
        return Page.class;
    }

    @Override
    public EntityBaseService service() {
        return pageService;
    }
}
