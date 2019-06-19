package com.hottop.backstage.product.controller;

import com.hottop.backstage.base.controller.BackstageBaseController;
import com.hottop.core.product.service.CommerceSpecificationService;
import com.hottop.core.model.commerce.CommerceSpecification;
import com.hottop.core.response.EResponseResult;
import com.hottop.core.response.Response;
import com.hottop.core.service.EntityBaseService;
import com.hottop.core.utils.LogUtil;
import com.hottop.core.utils.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 *规格
 */
@RestController
@RequestMapping("/specification")
public class CommerceSpecificationController extends BackstageBaseController<CommerceSpecification> {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private CommerceSpecificationService commerceSpecificationService;

    @Override
    public Class<CommerceSpecification> clazz() {
        return CommerceSpecification.class;
    }

    @Override
    public EntityBaseService service() {
        return commerceSpecificationService;
    }

    /**
     * 新增
     *
     * @param entity
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/", method = RequestMethod.POST)
    public Response post(@Valid @RequestBody CommerceSpecification entity) throws Exception {
        try {
            commerceSpecificationService.save(entity);
            return ResponseUtil.addSuccessResponse(CommerceSpecification.class.getSimpleName());
        } catch (Exception e) {
            logger.info("保存commerceSpecification-规格出错");
            LogUtil.error(e.getStackTrace());
        }
        return ResponseUtil.addFailResponse(CommerceSpecification.class.getSimpleName());
    }

    /**
     * 更新
     * @param commerceSpecification
     * @param id
     * @return
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public Response update(@RequestBody CommerceSpecification commerceSpecification,
                           @PathVariable Long id) {
        return commerceSpecificationService.updateSpecification(commerceSpecification, id);
    }

    @RequestMapping(path = "/{id}", method = RequestMethod.GET)
    @Override
    public Response getOne(@PathVariable("id") Long id) throws Exception {
        return super.getOne(id);
    }


}
