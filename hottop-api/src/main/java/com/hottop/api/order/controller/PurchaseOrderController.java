package com.hottop.api.order.controller;

import com.hottop.api.base.controller.ApiBaseController;
import com.hottop.api.order.service.PurchaseOrderService;
import com.hottop.core.order.validator.PurchaseOrderValidator;
import com.hottop.core.order.vo.PurchaseOrderVo;
import com.hottop.core.model.commerce.CommercePurchaseOrder;
import com.hottop.core.request.argument.annotation.Filter;
import com.hottop.core.request.argument.filter.IFilterResolver;
import com.hottop.core.response.Response;
import com.hottop.core.service.EntityBaseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * 采购单 controller
 */
@RestController
@RequestMapping("/purchaseOrder")
public class PurchaseOrderController extends ApiBaseController<CommercePurchaseOrder> {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private PurchaseOrderService purchaseOrderService;

    @Override
    public Class<CommercePurchaseOrder> clazz() {
        return CommercePurchaseOrder.class;
    }

    @Override
    public EntityBaseService service() {
        return purchaseOrderService;
    }

    /**
     * 注册验证器
     *
     * @param webDataBinder
     */
    @InitBinder
    public void initBinder(WebDataBinder webDataBinder) {
        webDataBinder.setValidator(new PurchaseOrderValidator());
    }

    /**
     * 点击 "结算"-> "采购单详情页"
     * 生成订单接口
     *
     * @return
     */
    @PostMapping(path = "/")
    public Response createPurchaseOrder(@Valid @RequestBody PurchaseOrderVo purchaseOrderVo) {

        String username = SecurityContextHolder.getContext()
                .getAuthentication()
                .getName();
        logger.info("'结算'的当前用户名：{}", username);
        return purchaseOrderService.createPurchaseOrder(purchaseOrderVo, username);
    }

    /**
     * 取消订单
     * @return
     */
    @GetMapping(path = "/cancel/{id}")
    public Response cancelPurchaseOrder(@PathVariable Long id) {
        return purchaseOrderService.cancelPurchaseOrder(id);
    }

    /**
     * 查询订单详情
     * @return
     */
    @GetMapping(path = "/{orderId}")
    public Response getOrderDetail(@PathVariable("orderId") Long orderId) {
        return purchaseOrderService.getOrderDetail(orderId);
    }

    //查看订单列表component
    @GetMapping(path = "/list")
    public Response getOrderList() {

        return null;
    }

}
