package com.hottop.core.product.service;

import com.hottop.core.model.commerce.CommerceAttribute;
import com.hottop.core.repository.EntityBaseRepository;
import com.hottop.core.repository.commerce.CommerceAttributeRepository;
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

/**
 * 属性 service
 */
@Service
public class CommerceAttributeService extends EntityBaseService<CommerceAttribute, Long>{

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private CommerceAttributeRepository commerceAttributeRepository;

    @Override
    public EntityBaseRepository<CommerceAttribute, Long> repository() {
        return commerceAttributeRepository;
    }

    //转换
    private void transferIfFieldsNotBlank(CommerceAttribute source, CommerceAttribute destination) {
        if(StringUtils.isNotBlank(source.getName())){
            destination.setName(source.getName());
        }
        if(source.getRecommendationValues() != null){
            destination.setRecommendationValues(source.getRecommendationValues());
        }
        if(source.getType() != null){
            destination.setType(source.getType());
        }
    }

    @Transactional
    public Response updateAttribute(CommerceAttribute commerceAttribute, Long id) {
        try {
            CommerceAttribute commerceAttribute_repo = findOne(CommerceAttribute.class, id);
            transferIfFieldsNotBlank(commerceAttribute, commerceAttribute_repo);
            CommerceAttribute update = super.update(commerceAttribute_repo);
            return ResponseUtil.updateSuccessResponse(CommerceAttribute.class.getSimpleName(), update);
        } catch (Exception e) {
            LogUtil.error(e.getStackTrace());
        }
        return ResponseUtil.updateFailResponse(CommerceAttribute.class.getSimpleName());
    }
}
