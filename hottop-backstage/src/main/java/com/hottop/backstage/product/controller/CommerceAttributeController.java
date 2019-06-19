package com.hottop.backstage.product.controller;

import com.hottop.backstage.base.controller.BackstageBaseController;
import com.hottop.core.product.service.CommerceAttributeService;
import com.hottop.backstage.product.validator.CommerceAttributeValidator;
import com.hottop.core.model.commerce.CommerceAttribute;
import com.hottop.core.model.commerce.enums.EAttributeType;
import com.hottop.core.response.EResponseResult;
import com.hottop.core.response.Response;
import com.hottop.core.service.EntityBaseService;
import com.hottop.core.utils.LogUtil;
import com.hottop.core.utils.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * 属性 controller
 */
@RestController
@RequestMapping("/attribute")
public class CommerceAttributeController extends BackstageBaseController<CommerceAttribute> {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private CommerceAttributeService commerceAttributeService;

    @Override
    public Class<CommerceAttribute> clazz() {
        return CommerceAttribute.class;
    }

    @Override
    public EntityBaseService service() {
        return commerceAttributeService;
    }

    /**
     * 注册验证器
     *
     * @param webDataBinder
     */
    @InitBinder
    public void initBinder(WebDataBinder webDataBinder) {
        webDataBinder.setValidator(new CommerceAttributeValidator());
    }

    /**
     * 属性 add
     *
     * @param commerceAttribute
     * @return
     * @throws Exception
     */
    @RequestMapping(path = "/", method = RequestMethod.POST)
    public Response post(@Valid @RequestBody CommerceAttribute commerceAttribute) throws Exception {
        try {
            CommerceAttribute save = commerceAttributeService.save(commerceAttribute);
            return ResponseUtil.addSuccessResponse(CommerceAttribute.class.getSimpleName());
        } catch (Exception e) {
            logger.info("属性新增失败");
            LogUtil.error(e.getStackTrace());
        }
        return ResponseUtil.addFailResponse(CommerceAttribute.class.getSimpleName());
    }

    @RequestMapping(path = "/{id}", method = RequestMethod.PUT)
    public Response updateAttribute(@Valid @RequestBody CommerceAttribute commerceAttribute,
                                    @PathVariable("id") Long id) {
        return commerceAttributeService.updateAttribute(commerceAttribute, id);
    }

    @GetMapping(path = "/type")
    public Response getAttributeType() {
        EAttributeType[] values = EAttributeType.values();
        return Response.ResponseBuilder.result(EResponseResult.OK).data(values).create();
    }



}
