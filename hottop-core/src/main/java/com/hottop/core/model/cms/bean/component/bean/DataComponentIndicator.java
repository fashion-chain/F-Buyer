package com.hottop.core.model.cms.bean.component.bean;

import com.hottop.core.model.cms.bean.TemplateComponent;
import com.hottop.core.model.cms.bean.component.ComponentBase;
import com.hottop.core.model.cms.bean.component.ComponentEndless;
import com.hottop.core.utils.CommonUtil;
import lombok.Getter;

import java.io.Serializable;

@Getter
public class DataComponentIndicator extends ComponentDecorator implements Serializable {

    private TemplateComponent templateComponent;

    public DataComponentIndicator() {
        super(EComponentDecoratorType.dataComponentIndicator);
    }

    @Override
    public void decorate(ComponentBase component) {
        if (validate(component)) {
            ComponentEndless componentEndless = (ComponentEndless) component;
            componentEndless.setUrl(CommonUtil.createUri(componentEndless.getUrl(), templateComponent).toString());
        }
    }

    @Override
    public boolean validate(ComponentBase component) {
        if (component instanceof ComponentEndless) {
            return true;
        }
        return false;
    }
}
