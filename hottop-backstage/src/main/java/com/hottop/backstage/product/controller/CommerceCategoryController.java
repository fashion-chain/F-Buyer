package com.hottop.backstage.product.controller;

import com.hottop.backstage.base.controller.BackstageBaseController;
import com.hottop.core.product.service.CommerceCategoryService;
import com.hottop.backstage.product.validator.CommerceCategoryValidator;
import com.hottop.core.model.commerce.CommerceCategory;
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
 * 品类 controller
 */
@RestController
@RequestMapping("/category")
public class CommerceCategoryController extends BackstageBaseController<CommerceCategory> {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private CommerceCategoryService commerceCategoryService;

    @Override
    public Class<CommerceCategory> clazz() {
        return CommerceCategory.class;
    }

    @Override
    public EntityBaseService service() {
        return commerceCategoryService;
    }

    /**
     * 注册验证器
     *
     * @param webDataBinder
     */
    @InitBinder
    public void initBinder(WebDataBinder webDataBinder) {
        webDataBinder.setValidator(new CommerceCategoryValidator());
    }

    /**
     * 新增 commerceCategory
     *
     * @param commerceCategory
     * @return
     * @throws Exception
     */
    @RequestMapping(path = "/", method = RequestMethod.POST)
    public Response post(@Valid @RequestBody CommerceCategory commerceCategory) throws Exception {
        try {
            if (commerceCategoryService.hasNameAlready(commerceCategory.getName())){
                return Response.ResponseBuilder.result(EResponseResult.ERROR_INTERVAL)
                        .simpleMessage("分类名称已存在").create();
            }
            CommerceCategory save = commerceCategoryService.save(commerceCategory);
            return ResponseUtil.addSuccessResponse(CommerceCategory.class.getSimpleName());
        } catch (Exception e) {
            logger.info("保存commerceCategory品类出错");
            LogUtil.error(e.getMessage());
        }
        return ResponseUtil.addFailResponse(CommerceCategory.class.getSimpleName());
    }

    @RequestMapping(path = "/{id}", method = RequestMethod.PUT)
    public Response updateCategory(@Valid @RequestBody CommerceCategory commerceCategory,
                                   @PathVariable("id") Long id) {
        return commerceCategoryService.updateCategory(commerceCategory, id);
    }

    //根据分类ID，获取category tree
    @GetMapping(path = "/tree/{id}")
    public Response getCategoryTree(@PathVariable("id") Long id) {
        return commerceCategoryService.getCategoryTree(id);
    }

    //获取category tree
    @GetMapping(path = "/tree")
    public Response getCategoryTree() {
        return commerceCategoryService.getCategoryTree();
    }

    //获取category tree
    @GetMapping(path = "/treeList")
    public Response getCategoryListTree() {
        return commerceCategoryService.getCategoryListTree();
    }

    @GetMapping(path = "/map")
    public Response getCategoryIdNameMap() {
        return Response.ResponseBuilder.result(EResponseResult.OK).data(commerceCategoryService.getCategoryIdNameMap())
                .simpleMessage("查询category map成功").create();
    }

}
