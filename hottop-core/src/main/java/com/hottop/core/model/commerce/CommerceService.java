package com.hottop.core.model.commerce;

import com.hottop.core.config.BaseConfiguration;
import com.hottop.core.model.commerce.enums.EServiceType;
import com.hottop.core.model.zpoj.EntityBase;
import com.hottop.core.model.zpoj.bean.Image;
import com.hottop.core.model.zpoj.converter.MediaConverter;
import com.hottop.core.request.argument.filter.EFilterOperator;
import com.hottop.core.request.argument.filter.EFilterWidgetType;
import com.hottop.core.request.argument.filter.api.FieldIndicator;
import com.hottop.core.request.argument.filter.api.FilterFieldIndicator;
import com.hottop.core.request.argument.filter.api.FilterFieldWidget;
import com.hottop.core.request.argument.filter.api.ProgramInterfaceIndicator;
import com.hottop.core.utils.FieldIndicatorTypesUtil;
import lombok.Data;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.persistence.*;

@Entity
@Data
public class CommerceService extends EntityBase {
    @Column(columnDefinition = "VARCHAR(31) NOT NULL UNIQUE COMMENT '服务名' ")
    private String name;

    @Column(columnDefinition = "VARCHAR(255) NOT NULL UNIQUE COMMENT '服务描述' ")
    private String description;

    @Column(columnDefinition = "VARCHAR(15) NOT NULL COMMENT '服务类型' ")
    @Enumerated(EnumType.STRING)
    private EServiceType type;

    @Column(columnDefinition = "JSON COMMENT '服务icon' ")
    @Convert(converter = MediaConverter.class)
    private Image icon;
    
    public static ProgramInterfaceIndicator getProgramInterfaceIndicator() throws Exception {
        return ProgramInterfaceIndicator.ProgramInterfaceIndicatorBuilder.init(CommerceService.class)
                .fieldIndicators(
                        FieldIndicator.FieldIndicatorBuilder.field(CommerceService_.NAME,
                                BaseConfiguration.getMessage("commerce.service.name"),
                                FilterFieldWidget.simpleInput()).create(),
                        FieldIndicator.FieldIndicatorBuilder.field(CommerceService_.DESCRIPTION,
                                BaseConfiguration.getMessage("commerce.service.description"),
                                FilterFieldWidget.simpleInput())
                                .create(),
                        FieldIndicator.FieldIndicatorBuilder.field(CommerceService_.TYPE,
                                BaseConfiguration.getMessage("commerce.service.type"),
                                FilterFieldWidget.simpleInput()).create()
                )
                .requiredFields(CommerceService_.NAME, CommerceService_.TYPE)
                .sortableFields(CommerceService_.NAME)
                .filterFieldIndicators(
                        FilterFieldIndicator.FilterFieldIndicatorBuilder.filterField(CommerceService_.NAME,
                                BaseConfiguration.getMessage("commerce.service.name"),
                                EFilterOperator.like)
                                .widget(FilterFieldWidget.simpleInput())
                                .create()
                ).create();
    }
}
