package com.hottop.core.model.cms.bean.extractor;

import com.hottop.core.model.cms.bean.action.ActionBase;
import com.hottop.core.model.cms.bean.action.ActionBrandDetail;
import com.hottop.core.model.cms.bean.action.ActionToast;
import com.hottop.core.model.cms.bean.action.EActionType;
import com.hottop.core.model.cms.bean.component.ComponentBase;
import com.hottop.core.model.cms.bean.component.ComponentImage;
import com.hottop.core.model.cms.bean.component.ComponentText;
import com.hottop.core.model.cms.bean.component.EComponentType;
import com.hottop.core.model.doll.Doll;
import com.hottop.core.model.zpoj.bean.Image;
import com.hottop.core.model.zpoj.bean.Media;
import org.springframework.stereotype.Component;

@Component
public class DollExtractor implements ICmsExtractor<Doll> {
    @Override
    public ActionBase extractAction(Doll obj, EActionType actionType) {
        switch (actionType) {
            case toast:
                return new ActionToast(obj.getInputString());
            case brandDetail:
                return new ActionBrandDetail(1L);
        }
        return null;
    }

    @Override
    public ComponentBase extractComponent(Doll obj, EComponentType componentType) {
        switch (componentType) {
            case text:
                return new ComponentText(obj.getInputString());
            case image:
                return new ComponentImage(Image.ImageBuilder.initImage(Media.EMediaType.image,
                        "testUUid",
                        "testUrl",
                        "jpg",
                        100,
                        100)
                        .create());
        }
        return null;
    }

    @Override
    public EComponentType defaultComponentType() {
        return EComponentType.text;
    }

    @Override
    public EActionType defaultActionType() {
        return EActionType.brandDetail;
    }

    @Override
    public Class<?> clazz() {
        return Doll.class;
    }
}
