package com.hottop.backstage.product.controller;

import com.hottop.backstage.base.controller.BackstageBaseController;
import com.hottop.core.product.service.CommerceSpuService;
import com.hottop.core.model.commerce.CommerceSpu;
import com.hottop.core.model.commerce.enums.ESpuStatus;
import com.hottop.core.request.argument.annotation.Filter;
import com.hottop.core.request.argument.filter.IFilterResolver;
import com.hottop.core.response.EResponseResult;
import com.hottop.core.response.Response;
import com.hottop.core.service.EntityBaseService;
import com.hottop.core.utils.ResponseUtil;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import sun.tools.attach.HotSpotVirtualMachine;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/spu")
public class CommerceSpuController extends BackstageBaseController<CommerceSpu> {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private CommerceSpuService commerceSpuService;

    @Override
    public Class<CommerceSpu> clazz() {
        return CommerceSpu.class;
    }

    @Override
    public EntityBaseService service() {
        return commerceSpuService;
    }


    /**
     * 查看详情
     * @param id
     * @return
     * @throws Exception
     */
    @RequestMapping(path = "/{id}", method = RequestMethod.GET)
    @Override
    public Response getOne(@PathVariable("id") Long id) throws Exception {
        CommerceSpu spu = commerceSpuService.getSpuDetail(id);
        if (spu == null) {
            return ResponseUtil.notExistResponse(CommerceSpu.class.getSimpleName());
        }
        return ResponseUtil.detailSuccessResponse(CommerceSpu.class.getSimpleName(), spu);
    }

    /**
     * add 新增
     * @return
     * @throws Exception
     */
    @RequestMapping(path = "/", method = RequestMethod.POST)
    public Response post(@Valid @RequestBody CommerceSpu commerceSpu) throws Exception {
        return commerceSpuService.addSpu(commerceSpu);
    }

    //filter 查询
    @GetMapping("/filter")
    @Override
    public Response filter(@Filter IFilterResolver<CommerceSpu> filterResolver) throws Exception {
        Response filter = super.filter(filterResolver);
        try {
            List<CommerceSpu> data = (List<CommerceSpu>)filter.getData();
            for (CommerceSpu spu : data) {
                commerceSpuService.transferSpu(spu);
            }
            return filter;
        }catch (Exception e ){
            logger.info("商品filter Data 获取失败");
            return filter;
        }
    }
    private ArrayList<String> serviceIdToName(Map<Long,String> serviceMap, List<Long> serviceIds) {
        ArrayList<String> servicesName = new ArrayList<>();
        if(serviceIds == null) {
            return servicesName;
        }
        for (Long id : serviceIds) {
            if(serviceMap.containsKey(id)){
                servicesName.add(serviceMap.get(id));
            }
        }
        return servicesName;
    }

    /**
     * update 更新
     * @param id
     * @return
     */
    @RequestMapping(path = "/{id}", method = RequestMethod.PUT)
    public Response update(@Valid @RequestBody CommerceSpu commerceSpu,
                           @PathVariable Long id) throws Exception {
        return commerceSpuService.updateSpu(commerceSpu, id);
    }

    /**
     * 商品上架
     * @param id
     * @return
     */
    @GetMapping(path = "/productUp/{id}")
    public Response updateSpuUp(@PathVariable("id") Long id) {
        return commerceSpuService.changeSpuStatus(ESpuStatus.up, id);
    }

    /**
     * 商品下架
     * @param id
     * @return
     */
    @GetMapping(path = "/productDown/{id}")
    public Response updateSpuDown(@PathVariable("id") Long id) {
        return commerceSpuService.changeSpuStatus(ESpuStatus.down, id);
    }

    //查看商品的状态
    @GetMapping(path = "/status")
    public Response getStatus() {
        Map<String, String> statusMap = CommerceSpu.getStatusMap();
        return Response.ResponseBuilder.result(EResponseResult.OK).data(statusMap).create();
    }

}
