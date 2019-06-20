package com.hottop.backstage.order.controller;

import com.hottop.backstage.base.controller.BackstageBaseController;
import com.hottop.core.BaseConstant;
import com.hottop.core.config.BaseConfiguration;
import com.hottop.core.feature.status.StatusStatusTracker;
import com.hottop.core.order.service.PurchaseOrderService;
import com.hottop.core.model.commerce.CommercePurchaseOrder;
import com.hottop.core.request.argument.annotation.Filter;
import com.hottop.core.request.argument.filter.IFilterResolver;
import com.hottop.core.response.EResponseResult;
import com.hottop.core.response.Response;
import com.hottop.core.service.EntityBaseService;
import com.hottop.core.utils.CommonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

//后台订单列表
@RestController
@RequestMapping("/purchaseOrder")
public class PurchaseOrderController extends BackstageBaseController<CommercePurchaseOrder>{

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

    //修改订单状态
    @PutMapping(path = "/{orderId}/{event}")
    public Response changeOrderStatus(@PathVariable("orderId") Long orderId, @PathVariable("event") String event) {
        return purchaseOrderService.changePurchaseOrderStatus(orderId, event);
    }

    @GetMapping(path = "/availableEvents/{orderId}")
    public Response getOrderAvailableEvents(@PathVariable("orderId") Long orderId) {
        return purchaseOrderService.getOrderAvailableEvents(orderId);
    }

    //订单列表
    @GetMapping(path = "/filter")
    @Override
    public Response filter(@Filter IFilterResolver<CommercePurchaseOrder> filterResolver) throws Exception {
        Response filter = super.filter(filterResolver);
        List<CommercePurchaseOrder> orderList = (List<CommercePurchaseOrder>) filter.getData();
        for (CommercePurchaseOrder order : orderList) {
            transferStatus(order);
            //transferStatusMap(order);
        }
        String s = BaseConfiguration.generalGson().toJson(orderList);
        return filter;
    }
    //转化状态名称
    private void transferStatus(CommercePurchaseOrder order) {
        Map<String, String> statusMap = purchaseOrderService.getStatusMap();
        String status = order.getStatus().status().status();
        String orderStatusName = status.substring(status.lastIndexOf(BaseConstant.Common.COMMON_SPLITTER) + 1);
        if (statusMap.containsKey(orderStatusName)) {
            order.setStatusShowName(statusMap.get(orderStatusName));
        } else {
            order.setStatusShowName(BaseConfiguration.getMessage("common.status.unknow"));
        }
    }
    //转化状态map
    private void transferStatusMap(CommercePurchaseOrder order) {
        Map<String, String> orderAvailableEventsMap = purchaseOrderService.getOrderAvailableEventsMap(order.getId());
        orderAvailableEventsMap.put(order.getStatus().status().status(), order.getStatusShowName());
        order.setStatusMap(orderAvailableEventsMap);
    }

    //查看订单详情
    @GetMapping(path = "/{id}")
    @Override
    public Response getOne(@PathVariable("id") Long id) throws Exception {
        Response resp = super.getOne(id);
        CommercePurchaseOrder order = (CommercePurchaseOrder) resp.getData();
        transferStatus(order);
        transferStatusMap(order);
        return resp;
    }

    //订单状态map
    @GetMapping(path = "/statusMap")
    public Response getStatusMap() {
        return Response.ResponseBuilder.result(EResponseResult.OK)
                .data(purchaseOrderService.getStatusMap()).create();
    }

    //purchaseOrder更新
    @PutMapping(path = "/{id}")
    public Response updatePurchaseOrder(@PathVariable("id") Long id, @RequestBody CommercePurchaseOrder commercePurchaseOrder) {
        return purchaseOrderService.updatePurchaseOrder(id, commercePurchaseOrder);
    }

}
