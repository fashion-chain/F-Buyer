package com.hottop.core;

import com.hottop.core.model.cms.bean.TemplateComponent;
import com.hottop.core.model.cms.bean.action.EActionType;
import com.hottop.core.model.cms.bean.component.EComponentType;
import com.hottop.core.utils.CommonUtil;
import com.hottop.core.utils.PrecisionUtil;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URI;
import java.net.URLEncoder;

@SpringBootTest
public class CoreTest {

    @Test
    public void uriTest() {
        String orig = "http://www.baidu.com/t?q=123&y=444";
        String a = "/12399?a=123i123i{}JSDIOFSD?O()*(^!@#*(!?@#)(__+&b=123isdfjsjjkewj";
        URI aUri = CommonUtil.createUri(a);

        URI bUri = CommonUtil.createUri(a, new TemplateComponent(EComponentType.image, EActionType.toast));
        String cUri = CommonUtil.setOrReplaceQueryParam(bUri.toString(), "ct1", "ttttt");
    }

    @Test
    public void precisionTest() {

        System.out.println(0.05+0.01);
        System.out.println(1.0-0.42);
        System.out.println(4.015*100);
        System.out.println(123.3/100);

        BigDecimal bd = new BigDecimal("1.123123123123");
        System.out.println(bd.toString());

        Assert.assertEquals(444000000, PrecisionUtil.add(123000000L, 321000000L).longValue());
        Assert.assertEquals("444000000", PrecisionUtil.addResultString(123000000L, 321000000L));

        Assert.assertEquals("1.01", PrecisionUtil.getYuanByCent(101L));
        Assert.assertEquals("-9.99", PrecisionUtil.getYuanByCent(-999L));

        Assert.assertEquals(0, PrecisionUtil.getCentByYuan(0.001d).longValue());
        Assert.assertEquals(1, PrecisionUtil.getCentByYuan(0.01d).longValue());
        Assert.assertEquals(-1, PrecisionUtil.getCentByYuan(-0.01d).longValue());
        Assert.assertEquals(0, PrecisionUtil.getCentByYuan(-0.007d).longValue());

        Assert.assertEquals(PrecisionUtil.minus(10086L, 5043L).longValue(), 5043);
        Assert.assertEquals(PrecisionUtil.minusResultString(10086L, 5043L), "5043");

        Assert.assertEquals(PrecisionUtil.multiply(4L, 16L).longValue(), 64);
        Assert.assertEquals(PrecisionUtil.multiplyResultString(4L, 16L), "64");

        Assert.assertEquals(PrecisionUtil.divide(32L, 2L).longValue(), 16);
        Assert.assertEquals(PrecisionUtil.divideResultString(32L, 2L), "16");

        Assert.assertEquals(PrecisionUtil.getCentByYuan("1.008").longValue(), 100l);
    }


}
