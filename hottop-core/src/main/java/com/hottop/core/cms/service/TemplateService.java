package com.hottop.core.cms.service;

import com.hottop.core.model.cms.Template;
import com.hottop.core.repository.EntityBaseRepository;
import com.hottop.core.repository.cms.TemplateRepository;
import com.hottop.core.service.EntityBaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TemplateService extends EntityBaseService<Template, Long> {
    @Autowired
    private TemplateRepository templateRepository;

    @Override
    public EntityBaseRepository<Template, Long> repository() {
        return templateRepository;
    }

    public Template getTemplateByIdOrName(Long id, String name) {
        Template template = null;
        if (id != null) {
            template = findById(id);
        }
        if (template == null) {
            template = findByName(name);
        }
        return template;
    }

    public Template findByName(String name) {
        return templateRepository.findByName(name);
    }

    public Template findById(Long id) {
        Optional<Template> templateOptional = templateRepository.findById(id);
        return templateOptional.isPresent() ? templateOptional.get(): null;

    }
}
