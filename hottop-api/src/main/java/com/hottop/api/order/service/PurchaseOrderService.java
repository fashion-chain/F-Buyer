package com.hottop.api.order.service;

import com.hottop.core.model.commerce.enums.EPurchaseOrderState;
import com.hottop.core.order.vo.PurchaseOrderDetailVo;
import com.hottop.core.order.vo.PurchaseOrderVo;
import com.hottop.api.user.service.UserAddressService;
import com.hottop.api.user.service.UserService;
import com.hottop.core.config.BaseConfiguration;
import com.hottop.core.feature.status.EStatusEvent;
import com.hottop.core.feature.status.Status;
import com.hottop.core.feature.status.StatusFactory;
import com.hottop.core.feature.status.StatusStatusTracker;
import com.hottop.core.model.commerce.CommercePurchaseOrder;
import com.hottop.core.model.commerce.CommercePurchaseOrderSku;
import com.hottop.core.model.commerce.CommerceSku;
import com.hottop.core.model.merchant.MerchantTrade;
import com.hottop.core.model.user.User;
import com.hottop.core.model.user.UserAddress;
import com.hottop.core.repository.EntityBaseRepository;
import com.hottop.core.repository.commerce.CommercePurchaseOrderRepository;
import com.hottop.core.repository.commerce.CommercePurchaseOrderSkuRepository;
import com.hottop.core.repository.commerce.CommerceSkuRepository;
import com.hottop.core.response.EResponseResult;
import com.hottop.core.response.Response;
import com.hottop.core.service.EntityBaseService;
import com.hottop.core.utils.CommonUtil;
import com.hottop.core.utils.LogUtil;
import com.hottop.core.utils.SnowflakeIdWorker;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class PurchaseOrderService extends EntityBaseService<CommercePurchaseOrder, Long>{

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private CommercePurchaseOrderRepository commercePurchaseOrderRepository;

    @Override
    public EntityBaseRepository<CommercePurchaseOrder, Long> repository() {
        return commercePurchaseOrderRepository;
    }

    @Autowired
    private UserAddressService userAddressService;

    @Autowired
    private UserService userService;

    @Autowired
    private SnowflakeIdWorker snowflakeIdWorker;

    @Autowired
    private CommercePurchaseOrderSkuRepository commercePurchaseOrderSkuRepository;

    @Autowired
    private CommerceSkuRepository commerceSkuRepository;

    /**
     * 创建 采购订单
     * @param purchaseOrderVo
     * @return
     */
    @Transactional
    public Response createPurchaseOrder(PurchaseOrderVo purchaseOrderVo, String username) {
        UserAddress userAddress = purchaseOrderVo.getUserAddress();
        userAddress = userAddressService.findOne(UserAddress.class, userAddress.getId());
        User user = userService.getUserByIdentification(username);
        if(userAddress == null || user == null){
            logger.info("userAddress或user为空");
            return Response.ResponseBuilder.result(EResponseResult.ERROR_INTERVAL)
                    .simpleMessage("新建采购订单失败").create();
        }
        CommercePurchaseOrder order = new CommercePurchaseOrder();
        order.setUserId(user.getId());
        order.setAddress(userAddress.getAddress());
        order.setTel(user.getTel());
        order.setUsername(username);
        StatusStatusTracker mt_pay_prepay = null;
        try {
            mt_pay_prepay = StatusFactory.tracker(MerchantTrade.class, StatusFactory.getStatus(MerchantTrade.class, "pay_prepay"));
            order.setStatus(mt_pay_prepay);
            Long orderAmount = 0l;//订单金额
            Long payAmount = 0l;//,支付金额
            List<CommercePurchaseOrderSku> commerceSkuList = purchaseOrderVo.getCommercePurchaseOrderSkuList();
            List<Long> skuIds = commerceSkuList.stream().map(sku -> sku.getId()).collect(Collectors.toList());
            Map<Long, CommerceSku> skuMap = commerceSkuRepository.findAllById(skuIds).stream().collect(Collectors.toMap(sku -> sku.getId(), sku -> sku, (k1, k2) -> k1));
            for (CommercePurchaseOrderSku sku : commerceSkuList) {
                if(skuMap.containsKey(sku.getSkuId())){
                    orderAmount += skuMap.get(sku.getSkuId()).getSalePrice() * sku.getQuantity();
                    payAmount += skuMap.get(sku.getSkuId()).getSalePrice() * sku.getQuantity();
                } else {
                    throw new Exception("新建采购订单失败-skuId非法");
                }
            }
            order.setAmount(orderAmount);
            order.setPayAmount(payAmount);
            order.setPayTradeNo(snowflakeIdWorker.nextId()+"");
            CommercePurchaseOrder save_order = save(order);
            //purchaseOrderSkuList
            ArrayList<CommercePurchaseOrderSku> orderSkus = new ArrayList<>();
            for(CommercePurchaseOrderSku sku : commerceSkuList) {
                sku.setUserId(user.getId());
                sku.setPurchaseOrderId(order.getId());
            }
            List<CommercePurchaseOrderSku> commercePurchaseOrderSkus = commercePurchaseOrderSkuRepository.saveAll(commerceSkuList);
            PurchaseOrderVo result = new PurchaseOrderVo();
            result.setUserAddress(userAddress);
            result.setCommercePurchaseOrderSkuList(commercePurchaseOrderSkus);
            return Response.ResponseBuilder.result(EResponseResult.OK).data(result).simpleMessage("新建采购订单成功").create();
        } catch (Exception e) {
            logger.info("新增采购订单失败");
            LogUtil.error(e.getStackTrace());
        }
        return Response.ResponseBuilder.result(EResponseResult.ERROR_INTERVAL)
                .simpleMessage("新建采购订单失败").create();
    }

    /**
     * 取消订单
     * @param id
     * @return
     */
    @Transactional
    public Response cancelPurchaseOrder(Long id) {
        Optional<CommercePurchaseOrder> purchaseOrder_option = commercePurchaseOrderRepository.findById(id);
        if(!purchaseOrder_option.isPresent()) {
            return Response.ResponseBuilder.result(EResponseResult.ERROR_INTERVAL)
                    .simpleMessage("采购订单不存在").create();
        }
        try {
            CommercePurchaseOrder purchaseOrder = purchaseOrder_option.get();
            if(!isCurrentUserLegal(purchaseOrder.getUserId())) {
                return Response.ResponseBuilder.result(EResponseResult.ERROR_INTERVAL)
                        .simpleMessage("用户采购订单不存在").create();
            }
            EPurchaseOrderState orderCloseEvent = EPurchaseOrderState.orderClose;
            Set<Enum> eStatusEvents = purchaseOrder.getStatus().availableEvents();
            if(orderCloseEvent == null || !isLegalEvent(eStatusEvents, orderCloseEvent.name())) {
                return Response.ResponseBuilder.result(EResponseResult.ERROR_INTERVAL)
                        .simpleMessage("修改订单状态非法").create();
            }
            logger.info("当前订单状态：{}", purchaseOrder.getStatus().status().status());
            StatusStatusTracker toStatus = purchaseOrder.getStatus().handle(orderCloseEvent);
            purchaseOrder.setStatus(toStatus);
            update(purchaseOrder);
            return Response.ResponseBuilder.result(EResponseResult.OK)
                    .simpleMessage("修改采购订单状态成功").create();
        }catch (Exception e){
            e.printStackTrace();
        }
        return Response.ResponseBuilder.result(EResponseResult.OK)
                .simpleMessage("修改采购订单状态失败").create();
    }
    private boolean isLegalEvent(Set<Enum> set, String event) {
        for(Enum e : set) {
            if(e.name().equals(event)){
                return true;
            }
        }
        return false;
    }

    private boolean isCurrentUserLegal(Long userId) {
        UserDetails userDetails = null;
        String username = null;
        try {
            userDetails = (UserDetails) SecurityContextHolder.getContext()
                    .getAuthentication()
                    .getPrincipal();
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (userDetails == null) {
            return false;
        }
        username = userDetails.getUsername();
        User userByIdentification = userService.getUserByIdentification(username);
        if(userByIdentification == null) {
            logger.info("当前用户为空：{}", username);
            return false;
        }
        logger.info("当前订单属于当前用户吗?{}", userByIdentification.getId() == userId ? "属于" : "不属于");
        return userByIdentification.getId() == userId;
    }


    public Response getOrderDetail(Long id) {
        Optional<CommercePurchaseOrder> purchaseOrder = commercePurchaseOrderRepository.findById(id);
        if(!purchaseOrder.isPresent()) {
            return Response.ResponseBuilder.result(EResponseResult.ERROR_INTERVAL)
                    .simpleMessage("采购订单不存在").create();
        }
        List<CommercePurchaseOrderSku> orderDetails = commercePurchaseOrderSkuRepository.findAllByPurchaseOrderId(id);
        PurchaseOrderDetailVo purchaseOrderVo = new PurchaseOrderDetailVo();
        purchaseOrderVo.setCommercePurchaseOrder(purchaseOrder.get());
        purchaseOrderVo.setCommercePurchaseOrderSkuList(orderDetails);
        return Response.ResponseBuilder.result(EResponseResult.OK)
                .data(purchaseOrderVo).simpleMessage("查看订单详情成功").create();
    }

    //根据订单号，查询订单
    public CommercePurchaseOrder findByPayTradeNo(String payTradeNo) {
        return commercePurchaseOrderRepository.findByPayTradeNo(payTradeNo);
    }

    
}
