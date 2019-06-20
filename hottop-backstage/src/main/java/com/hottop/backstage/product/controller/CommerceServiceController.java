package com.hottop.backstage.product.controller;

import com.hottop.backstage.base.controller.BackstageBaseController;
import com.hottop.core.product.service.CommerceSeriesService;
import com.hottop.core.product.service.CommerceServiceService;
import com.hottop.backstage.product.validator.CommerceServiceValidator;
import com.hottop.core.model.commerce.CommerceService;
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
 * commerceService 控制器
 */
@RestController
@RequestMapping("/service")
public class CommerceServiceController extends BackstageBaseController<CommerceService> {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private CommerceServiceService commerceServiceService;

    @Override
    public Class<CommerceService> clazz() {
        return CommerceService.class;
    }

    @Override
    public EntityBaseService service() {
        return commerceServiceService;
    }

    /**
     * 注册验证器
     *
     * @param webDataBinder
     */
    @InitBinder
    public void initBinder(WebDataBinder webDataBinder) {
        webDataBinder.setValidator(new CommerceServiceValidator());
    }

    /**
     * add
     *
     * @param commerceService
     * @return
     * @throws Exception
     */
    @RequestMapping(path = "/", method = RequestMethod.POST)
    public Response post(@Valid @RequestBody CommerceService commerceService) throws Exception {
        try {
            CommerceService save = commerceServiceService.save(commerceService);
            return ResponseUtil.addSuccessResponse(CommerceServiceService.Identification);
        } catch (Exception e) {
            logger.info("保存commerceService出错");
            LogUtil.error(e.getStackTrace());
        }
        return ResponseUtil.addFailResponse(CommerceServiceService.Identification);
    }

    // put
    @RequestMapping(path = "/{id}", method = RequestMethod.PUT)
    public Response updateCommerceService(@Valid @RequestBody CommerceService commerceService,
                                          @PathVariable Long id) {
        return commerceServiceService.updateCommerceService(commerceService, id);
    }

    @GetMapping(path = "/map")
    public Response serviceMap() {
        return commerceServiceService.getServiceMap();
    }




}
