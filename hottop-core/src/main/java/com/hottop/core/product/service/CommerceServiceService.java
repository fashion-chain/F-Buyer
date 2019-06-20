package com.hottop.core.product.service;

import com.hottop.core.model.commerce.CommerceService;
import com.hottop.core.repository.EntityBaseRepository;
import com.hottop.core.repository.commerce.CommerceServiceRepository;
import com.hottop.core.response.EResponseResult;
import com.hottop.core.response.Response;
import com.hottop.core.service.EntityBaseService;
import com.hottop.core.utils.LogUtil;
import com.hottop.core.utils.ResponseUtil;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * commerceService service
 */
@Service
public class CommerceServiceService extends EntityBaseService<CommerceService, Long>{

    private final Logger logger = LoggerFactory.getLogger(getClass());

    public final static String Identification = "commerceService";

    @Autowired
    private CommerceServiceRepository commerceServiceRepository;

    @Override
    public EntityBaseRepository<CommerceService, Long> repository() {
        return commerceServiceRepository;
    }

    //转换
    private void transferIfFieldsNotBlank(CommerceService source, CommerceService destination) {
        if(StringUtils.isNotBlank(source.getName())){
            destination.setName(source.getName());
        }
        if(StringUtils.isNotBlank(source.getDescription())){
            destination.setDescription(source.getDescription());
        }
        if(source.getType() != null){
            destination.setType(source.getType());
        }
        if(source.getIcon() != null){
            destination.setIcon(source.getIcon());
        }
    }

    @Transactional
    public Response updateCommerceService(CommerceService commerceService, Long id) {
        try {
            CommerceService commerceService_repo = findOne(CommerceService.class, id);
            transferIfFieldsNotBlank(commerceService, commerceService_repo);
            CommerceService update = update(commerceService_repo);
            return ResponseUtil.updateSuccessResponse(Identification);
        } catch (Exception e) {
            logger.info("commerceService 更新失败");
            LogUtil.error(e.getStackTrace());
        }
        return ResponseUtil.updateFailResponse(Identification);
    }

    public Response getServiceMap() {
        List<CommerceService> allService = commerceServiceRepository.findAllByDeleteTimeIsNull();
        //key是显示名，value是真实值
        Map<Long, String> result = allService.stream().collect(Collectors.toMap(CommerceService::getId, CommerceService::getName, (k1, k2) -> k1));
        return Response.ResponseBuilder.result(EResponseResult.OK).data(result).create();
    }

}
