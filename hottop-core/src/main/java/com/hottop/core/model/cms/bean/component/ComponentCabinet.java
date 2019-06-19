package com.hottop.core.model.cms.bean.component;

import com.hottop.core.model.zpoj.bean.Image;
import com.hottop.core.utils.CommonUtil;
import lombok.Getter;

import java.net.URI;
import java.util.HashMap;

@Getter
public class ComponentCabinet extends ComponentBase {
    private Integer count;

    private Image background;
    private URI targetUri;
    private HashMap<String, String> targetParams;

    public ComponentCabinet(Integer count) {
        super(EComponentType.cabinet);
        this.count = count;
    }

    public ComponentCabinet setTargetUri(String uri) {
        this.targetUri = CommonUtil.createUri(uri);
        return this;
    }

    public ComponentCabinet setTargetParams(HashMap<String, String> params) {
        this.targetParams = params;
        return this;
    }

    public ComponentCabinet setBackground(Image background) {
        this.background = background;
        return this;
    }
}
