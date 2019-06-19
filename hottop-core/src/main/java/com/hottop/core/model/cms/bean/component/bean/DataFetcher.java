package com.hottop.core.model.cms.bean.component.bean;

import com.google.gson.reflect.TypeToken;
import com.hottop.core.config.BaseConfiguration;
import com.hottop.core.model.cms.bean.TemplateComponent;
import com.hottop.core.model.cms.bean.component.ComponentBase;
import com.hottop.core.model.cms.bean.component.ComponentContainer;
import com.hottop.core.response.ResponseWithParadigm;
import com.hottop.core.utils.CommonUtil;
import com.hottop.core.utils.LogUtil;
import lombok.Data;
import lombok.Getter;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import java.io.Serializable;
import java.util.ArrayList;

@Getter
public class DataFetcher extends ComponentDecorator implements Serializable {
    private String url;

    private TemplateComponent templateComponent;

    public DataFetcher() {
        super(EComponentDecoratorType.dataFetcher);
    }

    @Override
    public void decorate(ComponentBase component) {
        if (validate(component)) {
            RestTemplate restTemplate = new RestTemplate();
            try {
                String responseStr = restTemplate.getForObject(CommonUtil.createUri(getUrl(),
                        getTemplateComponent()),
                        String.class);
                ResponseWithParadigm<ArrayList<ComponentBase>> response = BaseConfiguration.generalGson().fromJson(responseStr,
                        new TypeToken<ResponseWithParadigm<ArrayList<ComponentBase>>>() {
                        }.getType());
                if (response.isSuccess() && response.getData() != null) {
                    ((ComponentContainer) component).setCells(response.getData());
                }
            } catch (ResourceAccessException ex) {
                LogUtil.error("ResourceAccessException: " + ex.getMessage());
            }
        }
    }

    @Override
    public boolean validate(ComponentBase component) {
        if (component instanceof ComponentContainer) {
            return true;
        }
        return false;
    }
}
