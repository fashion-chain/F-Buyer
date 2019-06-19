package com.hottop.core.order.service;

import com.hottop.core.BaseConstant;
import com.hottop.core.config.BaseConfiguration;
import com.hottop.core.feature.status.EStatusEvent;
import com.hottop.core.feature.status.Status;
import com.hottop.core.feature.status.StatusFactory;
import com.hottop.core.feature.status.StatusStatusTracker;
import com.hottop.core.model.commerce.CommercePurchaseOrder;
import com.hottop.core.model.commerce.enums.EPurchaseOrderState;
import com.hottop.core.repository.EntityBaseRepository;
import com.hottop.core.repository.commerce.CommercePurchaseOrderRepository;
import com.hottop.core.response.EResponseResult;
import com.hottop.core.response.Response;
import com.hottop.core.service.EntityBaseService;
import com.hottop.core.utils.CommonUtil;
import com.hottop.core.utils.LogUtil;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service("backstagePurchaseOrderService")
public class PurchaseOrderService extends EntityBaseService<CommercePurchaseOrder, Long>{

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private CommercePurchaseOrderRepository commercePurchaseOrderRepository;

    @Override
    public EntityBaseRepository<CommercePurchaseOrder, Long> repository() {
        return commercePurchaseOrderRepository;
    }

    //修改订单状态为成功状态(预支付 -> 待发货状态)
    public Response changePurchaseOrderStatusAsSuccess(String payTradeNo) {
        return changePurchaseOrderStatus(payTradeNo, EPurchaseOrderState.preDeliver.name());
    }
    //根据订单 payTradeNo修改订单状态
    public Response changePurchaseOrderStatus(String payTradeNo, String event) {
        CommercePurchaseOrder byPayTradeNo = commercePurchaseOrderRepository.findByPayTradeNo(payTradeNo);
        if(byPayTradeNo == null) {
            return Response.ResponseBuilder.result(EResponseResult.ERROR_INTERVAL)
                    .simpleMessage("修改订单状态失败").create();
        }
        return changePurchaseOrderStatus(byPayTradeNo.getId(), event);
    }

    //根据订单id修改订单状态
    public Response changePurchaseOrderStatus(Long orderId, String status) {
        Optional<CommercePurchaseOrder> purchaseOrderOptional = commercePurchaseOrderRepository.findById(orderId);
        if(!purchaseOrderOptional.isPresent()) {
            return Response.ResponseBuilder.result(EResponseResult.ERROR_INTERVAL)
                    .simpleMessage("采购订单不存在").create();
        }
        try {
            CommercePurchaseOrder purchaseOrder = purchaseOrderOptional.get();
            String eventString = status.substring(status.lastIndexOf(BaseConstant.Common.COMMON_SPLITTER) + 1);
            EPurchaseOrderState eStatusEvent = EPurchaseOrderState.valueOf(eventString);
            Set<Enum> eStatusEvents = purchaseOrder.getStatus().availableEvents();
            if(eStatusEvent == null || !isLegalEvent(eStatusEvents, eventString)) {
                return Response.ResponseBuilder.result(EResponseResult.ERROR_INTERVAL)
                        .simpleMessage("修改订单状态非法").create();
            }
            logger.info("当前订单状态：{}", purchaseOrder.getStatus().status().status());
            StatusStatusTracker toStatus = purchaseOrder.getStatus().handle(eStatusEvent);
            purchaseOrder.setStatus(toStatus);
            return Response.ResponseBuilder.result(EResponseResult.OK)
                    .simpleMessage("修改采购单状态成功")
                    .create();

        }catch (Exception e) {
            logger.info("修改订单状态失败");
            LogUtil.error(e.getStackTrace());
            return Response.ResponseBuilder.result(EResponseResult.ERROR_INTERVAL)
                    .error(CommonUtil.printStackTraceElements(e.getStackTrace()))
                    .simpleMessage("修改订单状态失败").create();
        }
    }

    private boolean isLegalEvent(Set<Enum> set, String event) {
        for(Enum e : set) {
            if(e.name().equals(event)){
                return true;
            }
        }
        return false;
    }

    private static String purchaseOrderStatusPrefix = "commerce.purchase.status.";
    //获取当前采购单的可用事件
    public Response getOrderAvailableEvents(Long orderId) {
        Map<String, String> result = getOrderAvailableEventsMap(orderId);
        if(result == null) {
            return Response.ResponseBuilder.result(EResponseResult.ERROR_INTERVAL)
                    .simpleMessage(BaseConfiguration.getMessage("commerce.purchase.availEvents.error")).create();
        }
        return Response.ResponseBuilder.result(EResponseResult.OK)
                .data(result).create();
    }
    public Map<String,String> getOrderAvailableEventsMap(Long orderId){
        CommercePurchaseOrder order_repo = findOne(CommercePurchaseOrder.class, orderId);
        Map<String,String> result = new LinkedHashMap<>();
        if(order_repo == null) {
            return null;
        }
        Set<Enum> eStatusEvents = order_repo.getStatus().availableEvents();

        for(Enum e : eStatusEvents) {
            String message = BaseConfiguration.getMessage(purchaseOrderStatusPrefix + e.name());
            if(StringUtils.isNotBlank(message)){
                result.put("po_order_" + e.name(), message);
            }
        }
        return result;
    }

    //获取订单状态map
    public static Map<String,String> statusMap;
    public Map<String,String> getStatusMap() {
        if(statusMap == null) {
            statusMap = new LinkedHashMap<>();
            loadMap();
        }
        return statusMap;
    }
    private void loadMap() {
        String prefix = "commerce.purchase.status.";
        for(EPurchaseOrderState state : EPurchaseOrderState.values()) {
            String value = BaseConfiguration.getMessage(prefix + state.name());
            statusMap.put(state.name(), value);
        }
    }

    //更新订单
    @Transactional
    public Response updatePurchaseOrder(Long id, CommercePurchaseOrder commercePurchaseOrder) {
        try {
            CommercePurchaseOrder order_repo = findOne(CommercePurchaseOrder.class, id);
            transferIfFieldsNotBlank(commercePurchaseOrder, order_repo);
            order_repo.setUpdateTime(new Date());
            update(order_repo);
            return Response.ResponseBuilder.result(EResponseResult.OK)
                    .simpleMessage(BaseConfiguration.getMessage("commerce.purchase.update.success")).create();
        } catch (Exception e) {
            e.printStackTrace();
            logger.info("更新purchaseOrder失败");
        }
        return Response.ResponseBuilder.result(EResponseResult.ERROR_INTERVAL)
                .simpleMessage("commerce.purchase.update.fail").create();
    }

    private void transferIfFieldsNotBlank(CommercePurchaseOrder source, CommercePurchaseOrder destination) {
        if(StringUtils.isNotBlank(source.getTel())){
            destination.setTel(source.getTel());
        }
        if(StringUtils.isNotBlank(source.getAddress())){
            destination.setAddress(source.getAddress());
        }
        if(StringUtils.isNotBlank(source.getUsername())){
            destination.setUsername(source.getUsername());
        }
        if(source.getStatus() != null){
            destination.setStatus(source.getStatus());
        }
        if(source.getAmount() != null){
            destination.setAmount(source.getAmount());
        }
    }
}
