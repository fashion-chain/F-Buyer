package com.hottop.backstage.product.controller;

import com.hottop.backstage.base.controller.BackstageBaseController;
import com.hottop.core.model.commerce.CommerceRetailSpu;
import com.hottop.core.product.service.CommerceRetailSpuService;
import com.hottop.core.response.Response;
import com.hottop.core.service.EntityBaseService;
import com.hottop.core.utils.LogUtil;
import com.hottop.core.utils.ResponseUtil;
import javassist.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

// 零售商品 controller
@RestController
@RequestMapping("/retailSpu")
public class CommerceRetailSpuController extends BackstageBaseController<CommerceRetailSpu> {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private CommerceRetailSpuService commerceRetailSpuService;

    @Override
    public Class<CommerceRetailSpu> clazz() {
        return CommerceRetailSpu.class;
    }

    @Override
    public EntityBaseService service() {
        return commerceRetailSpuService;
    }

    //详情
    @GetMapping(path = "/{id}")
    @Override
    public Response getOne(@PathVariable("id") Long id) throws Exception {
        CommerceRetailSpu retailSpu = commerceRetailSpuService.getRetailSpuDetail(id);
        if(retailSpu == null) {
            return ResponseUtil.notExistResponse(CommerceRetailSpu.class.getSimpleName());
        }
        return ResponseUtil.detailSuccessResponse(CommerceRetailSpu.class.getSimpleName(), retailSpu);
    }

}
