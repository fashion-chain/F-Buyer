package com.hottop.core.cms.service;

import com.hottop.core.model.cms.Page;
import com.hottop.core.model.cms.bean.IContentDecorator;
import com.hottop.core.model.cms.bean.PageContent;
import com.hottop.core.model.cms.bean.component.ComponentBase;
import com.hottop.core.model.cms.bean.component.EComponentType;
import com.hottop.core.model.cms.bean.component.bean.ComponentDecorator;
import com.hottop.core.repository.EntityBaseRepository;
import com.hottop.core.repository.cms.PageRepository;
import com.hottop.core.service.EntityBaseService;
import com.hottop.core.utils.LogUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Map;
import java.util.Optional;

@Service
public class PageService extends EntityBaseService<Page, Long> {
    @Autowired
    private PageRepository pageRepository;

    @Transactional(readOnly = true)
    public Page findByName(String name) {
        return pageRepository.findByName(name);
    }

    @Transactional(readOnly = true)
    public Page findById(Long id) {
        Optional<Page> pageOptional = pageRepository.findById(id);
        return pageOptional.isPresent() ? pageOptional.get(): null;
    }

    public Page getPageByIdOrName(Long id, String name) {
        Page page = null;
        if (id != null) {
            page = findById(id);
        }
        if (page == null) {
            page = findByName(name);
        }
        return page;
    }

    @Override
    public EntityBaseRepository<Page, Long> repository() {
        return pageRepository;
    }

    public PageContent decorate(Page pageInstance) {
        if (pageInstance.getDataDecorator().getDecorators() != null) {
            for (Map.Entry<Integer, ArrayList<ComponentDecorator>> decorators: pageInstance.getDataDecorator().getDecorators().entrySet()) {
                if (decorators != null) {
                    for (ComponentDecorator decorator: decorators.getValue()) {
                        decorator.decorate(pageInstance.getPageContent().getComponents().get(decorators.getKey()));
                    }
                }
            }
        }
        return pageInstance.getPageContent();
    }

    public PageContent decorate(Page pageInstance, IContentDecorator contentDecorator) {
        if (contentDecorator == null) {
            return decorate(pageInstance);
        }
        PageContent decoratedContent = decorate(pageInstance);
        boolean decorated = false;
        for (ComponentBase component: decoratedContent.getComponents()) {
            if (component.getComponentType() == contentDecorator.decorateComponentType()) {
                contentDecorator.decorator().decorate(component);
                decorated = true;
            }
        }
        if (!decorated) {
            try {
                ComponentBase decoratedComponent = EComponentType.getCorrespondingType(contentDecorator.decorateComponentType()).newInstance();
                contentDecorator.decorator().decorate(decoratedComponent);
                decoratedContent.getComponents().add(decoratedComponent);
            } catch (Exception ex) {
                LogUtil.error("error in pageService.decorate " + ex.getMessage());
            }
        }
        return decoratedContent;
    }
}
