package com.hottop.core.product.service;

import com.hottop.core.model.commerce.CommerceSpecification;
import com.hottop.core.repository.EntityBaseRepository;
import com.hottop.core.repository.commerce.CommerceSpecificationRepository;
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
 * 规格 service
 */
@Service
public class CommerceSpecificationService extends EntityBaseService<CommerceSpecification, Long> {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private CommerceSpecificationRepository commerceSpecificationRepository;
    @Override
    public EntityBaseRepository<CommerceSpecification, Long> repository() {
        return commerceSpecificationRepository;
    }

    //转换
    private void transferIfFieldsNotBlank(CommerceSpecification source, CommerceSpecification destination) {
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

    /**
     * update 规格
     * @param commerceSpecification
     * @param id
     * @return
     */
    @Transactional
    public Response updateSpecification(CommerceSpecification commerceSpecification, Long id) {
        try {
            CommerceSpecification specification_repo = findOne(CommerceSpecification.class, id);
            transferIfFieldsNotBlank(commerceSpecification, specification_repo);
            specification_repo.setDeleteTime(null);//把删除时间 置为空
            CommerceSpecification update = update(specification_repo);
            return ResponseUtil.updateSuccessResponse(CommerceSpecification.class.getSimpleName(), update);
        } catch (Exception e) {
            LogUtil.error(e.getStackTrace());
        }
        return ResponseUtil.updateFailResponse(CommerceSpecification.class.getSimpleName());
    }
}
