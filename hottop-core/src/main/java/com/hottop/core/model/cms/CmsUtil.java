package com.hottop.core.model.cms;

import com.hottop.core.model.cms.bean.TemplateComponent;
import com.hottop.core.model.cms.bean.component.bean.ComponentDecorator;
import com.hottop.core.utils.EncryptUtil;
import org.springframework.http.server.ServerHttpResponse;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Map;

public class CmsUtil {
    private static final String USING_EXTRACTOR_HEADER = EncryptUtil.randomAlphaNumeric(5);

    public static void useExtractor(HttpServletResponse response, TemplateComponent templateComponent) {
        response.addHeader(USING_EXTRACTOR_HEADER, templateComponent.toString());
    }

    public static boolean shouldUseExtractor(ServerHttpResponse response) {
        boolean shouldUserExtractor = false;
        shouldUserExtractor = response.getHeaders().containsKey(USING_EXTRACTOR_HEADER);
        if (shouldUserExtractor) {
            response.getHeaders().remove(USING_EXTRACTOR_HEADER);
        }
        return shouldUserExtractor;
    }

    public static TemplateComponent getTemplateFromResponseHeader(ServerHttpResponse response) {
        return new TemplateComponent(response.getHeaders().get(USING_EXTRACTOR_HEADER).get(0));
    }

    public static void clearUseExtractorHeader(ServerHttpResponse response) {
        response.getHeaders().remove(USING_EXTRACTOR_HEADER);
    }
}
