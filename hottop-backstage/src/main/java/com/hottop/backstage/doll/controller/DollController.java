package com.hottop.backstage.doll.controller;

import com.hottop.backstage.base.controller.BackstageBaseController;
import com.hottop.backstage.doll.service.DollService;
import com.hottop.core.model.cms.CmsUtil;
import com.hottop.core.model.cms.bean.extractor.ICmsExtractor;
import com.hottop.core.model.doll.Doll;
import com.hottop.core.response.EResponseResult;
import com.hottop.core.response.Response;
import com.hottop.core.service.EntityBaseService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.LinkedHashMap;
import java.util.Map;

@RestController
@RequestMapping("/doll")
public class DollController extends BackstageBaseController<Doll> {
    @Autowired
    private DollService dollService;

    @Override
    public Class<Doll> clazz() {
        return Doll.class;
    }

    @RequestMapping(path = "/checkboxValues", method = RequestMethod.GET)
    public Response checkboxValues() {
        LinkedHashMap<String, String> result = new LinkedHashMap<>();
        result.put("chk_显示名1", "chk_val1");
        result.put("chk_显示名2", "chk_val2");
        result.put("chk_显示名3", "chk_val3");
        result.put("chk_显示名4", "chk_val4");
        return Response.ResponseBuilder.result(EResponseResult.OK)
                .data(result)
                .create();
    }

    @RequestMapping(path = "/selectValues", method = RequestMethod.GET)
    public Response selectValues() {
        LinkedHashMap<String, String> result = new LinkedHashMap<>();
        result.put("sel_显示名1", "sel_val1");
        result.put("sel_显示名2", "sel_val2");
        result.put("sel_显示名3", "sel_val3");
        result.put("sel_显示名4", "sel_val4");
        return Response.ResponseBuilder.result(EResponseResult.OK)
                .data(result)
                .create();
    }


    @RequestMapping(path = "/{id}", method = RequestMethod.PUT)
    public Response update(@RequestBody Doll doll, @PathVariable("id") Long id) {
        return dollService.updateDoll(doll, id);
    }

    //新增
    @RequestMapping(path = "/", method = RequestMethod.POST)
    public Response add(@RequestBody Doll doll) throws Exception{
        return create(doll);
    }

    @RequestMapping(path = "/statusValues", method = RequestMethod.GET)
    public Response statusValues(HttpServletResponse response) {
        return Response.ResponseBuilder.result(EResponseResult.OK)
                .data(new String[]{"STATUS_VALUE1", "STATUS_VALUE2", "STATUS_VALUE3", "STATUS_VALUE4"})
                .create();
    }


    @Override
    public EntityBaseService service() {
        return dollService;
    }
}
