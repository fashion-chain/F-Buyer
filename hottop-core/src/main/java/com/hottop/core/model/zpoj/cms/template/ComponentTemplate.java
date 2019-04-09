package com.hottop.core.model.zpoj.cms.template;

import com.hottop.core.model.zpoj.adapter.JsonDeserializationWithOptions;
import com.hottop.core.model.zpoj.cms.enums.EActionType;
import com.hottop.core.model.zpoj.cms.enums.EComponentType;
import com.hottop.core.model.zpoj.cms.enums.ETemplateType;
import lombok.Data;

import java.io.Serializable;

@Data
public class ComponentTemplate extends TemplateBase implements Serializable {

    @JsonDeserializationWithOptions.FieldRequired
    private EComponentType componentType;

    private EActionType actionType;

    public ComponentTemplate(EComponentType componentType) {
        super(ETemplateType.component);
        this.componentType = componentType;
    }

}
