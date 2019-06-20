package com.hottop;

import com.hottop.core.model.cms.bean.TemplateComponent;
import com.hottop.core.model.cms.bean.action.EActionType;
import com.hottop.core.model.cms.bean.component.EComponentType;
import com.hottop.core.utils.CommonUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.net.URI;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CommonTest {

    @Test
    public void uriTest() {
        String orig = "http://www.baidu.com/t";
        String a = "/12399?a=123i123i{}JSDIOFSD?O()*(^!@#*(!?@#)(__+&b=123isdfjsjjkewj";
        URI aUri = CommonUtil.createUri(a);

        URI bUri = CommonUtil.createUri(orig, new TemplateComponent(EComponentType.image, EActionType.toast));
    }
}
