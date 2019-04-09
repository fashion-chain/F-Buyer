package com.hottop.core.model.zpoj.cms.template;

import com.hottop.core.model.zpoj.adapter.JsonDeserializationWithOptions;
import com.hottop.core.model.zpoj.cms.ITemplate;
import com.hottop.core.model.zpoj.cms.enums.ETemplateType;
import com.hottop.core.model.zpoj.cms.enums.EWidgetType;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;

@Data
public class WidgetTemplate extends TemplateBase implements Serializable {

    @JsonDeserializationWithOptions.FieldRequired
    private EWidgetType widgetType;

    private ArrayList<ITemplate> templates;

    public WidgetTemplate(EWidgetType widgetType) {
        super(ETemplateType.widget);
        this.widgetType = widgetType;
    }
}
