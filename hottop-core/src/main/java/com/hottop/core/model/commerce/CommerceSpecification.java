package com.hottop.core.model.commerce;

import com.hottop.core.config.BaseConfiguration;
import com.hottop.core.model.commerce.enums.ESpecificationType;
import com.hottop.core.model.zpoj.EntityBase;
import com.hottop.core.model.zpoj.bean.Image;
import com.hottop.core.model.zpoj.converter.ListStringConverter;
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
import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;

@Entity
@Data
public class CommerceSpecification extends EntityBase {

    @Column(columnDefinition = "VARCHAR(15) NOT NULL COMMENT '规格名' ")
    private String name;

    @Column(columnDefinition = "JSON COMMENT '推荐值' ")
    @Convert(converter = ListStringConverter.class)
    private ArrayList<String> recommendationValues;

    @Column(columnDefinition = "VARCHAR(15) NOT NULL UNIQUE COMMENT '规格类型' ")
    @Enumerated(EnumType.STRING)
    private ESpecificationType type;//image，string

    public static ProgramInterfaceIndicator getProgramInterfaceIndicator() throws Exception{
        return ProgramInterfaceIndicator.ProgramInterfaceIndicatorBuilder.init(CommerceSpecification.class)
                .fieldIndicators(
                        FieldIndicator.FieldIndicatorBuilder.field(CommerceSpecification_.NAME,
                                BaseConfiguration.getMessage("commerce.specification.name"),
                                FilterFieldWidget.simpleInput()
                        ).create(),
                        FieldIndicator.FieldIndicatorBuilder.field(CommerceSpecification_.RECOMMENDATION_VALUES,
                                BaseConfiguration.getMessage("commerce.specification.recommendationValues"),
                                FilterFieldWidget.FilterFieldWidgetBuilder.init(EFilterWidgetType.extra).extras("spuSpecificationChosen", 1).create())
                                .render(FieldIndicatorTypesUtil.StrArray)
                                .create(),
                        FieldIndicator.FieldIndicatorBuilder.field(CommerceSpecification_.TYPE,
                                BaseConfiguration.getMessage("commerce.specification.type"),
                                FilterFieldWidget.FilterFieldWidgetBuilder.init(EFilterWidgetType.select).create()
                        ).create()
                )
                .requiredFields(CommerceSpecification_.NAME, CommerceSpecification_.TYPE)
                .sortableFields(CommerceSpecification_.NAME)
                .filterFieldIndicators(
                        FilterFieldIndicator.FilterFieldIndicatorBuilder.filterField(CommerceSpecification_.NAME,
                                BaseConfiguration.getMessage("commerce.specification.name"),
                                EFilterOperator.like)
                                .widget(FilterFieldWidget.FilterFieldWidgetBuilder.init(EFilterWidgetType.input).create()
                        ).create(),
                        FilterFieldIndicator.FilterFieldIndicatorBuilder.filterField(CommerceSpecification_.TYPE,
                                BaseConfiguration.getMessage("commerce.specification.type"),
                                EFilterOperator.equal)
                                .widget(FilterFieldWidget.FilterFieldWidgetBuilder.init(EFilterWidgetType.select).uri("/specification/type", RequestMethod.GET).create()
                        ).create()
                ).create();
    }
}
