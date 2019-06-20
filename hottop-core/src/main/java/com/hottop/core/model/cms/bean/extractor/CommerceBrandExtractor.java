package com.hottop.core.model.cms.bean.extractor;

import com.hottop.core.model.cms.bean.action.ActionBase;
import com.hottop.core.model.cms.bean.action.ActionBrandDetail;
import com.hottop.core.model.cms.bean.action.EActionType;
import com.hottop.core.model.cms.bean.component.ComponentBase;
import com.hottop.core.model.cms.bean.component.ComponentImage;
import com.hottop.core.model.cms.bean.component.ComponentNavigator;
import com.hottop.core.model.cms.bean.component.EComponentType;
import com.hottop.core.model.commerce.CommerceBrand;
import com.hottop.core.model.zpoj.bean.Image;
import org.springframework.stereotype.Component;

@Component
public class CommerceBrandExtractor implements ICmsExtractor<CommerceBrand> {
    @Override
    public ActionBase extractAction(CommerceBrand obj, EActionType actionType) {
        switch (actionType) {
            case brandDetail:
                return new ActionBrandDetail(obj.getId());
        }
        return null;
    }

    @Override
    public ComponentBase extractComponent(CommerceBrand obj, EComponentType componentType) {
        switch (componentType) {
            case image:
                return new ComponentImage(obj.getAvatar() != null && obj.getAvatar().isImage() ? (Image) obj.getAvatar() : null);
            case navigator:
                return new ComponentNavigator(obj.getAvatar() != null && obj.getAvatar().isImage() ? (Image) obj.getAvatar() : null,
                        obj.getName());
        }
        return null;
    }

    @Override
    public EComponentType defaultComponentType() {
        return EComponentType.navigator;
    }

    @Override
    public EActionType defaultActionType() {
        return EActionType.brandDetail;
    }

    @Override
    public Class<?> clazz() {
        return CommerceBrand.class;
    }
}
