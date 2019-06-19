package com.hottop.backstage.doll.service;

import com.hottop.core.model.doll.Doll;
import com.hottop.core.repository.EntityBaseRepository;

import com.hottop.core.response.EResponseResult;
import com.hottop.core.response.Response;
import com.hottop.core.repository.doll.DollRepository;
import com.hottop.core.service.EntityBaseService;
import com.hottop.core.utils.LogUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DollService extends EntityBaseService<Doll, Long> {

    @Autowired
    private DollRepository dollRepository;

    @Override
    public EntityBaseRepository<Doll, Long> repository() {
        return dollRepository;
    }

    @Transactional
    public Response updateDoll(Doll doll, Long id) {
        try {
            Doll doll_repo = findOne(Doll.class, id);
            transferIfFieldsNotBlank(doll, doll_repo);
            update(doll_repo);
        } catch (Exception e) {
            LogUtil.error(e.getStackTrace());
            return Response.ResponseBuilder.result(EResponseResult.ERROR_INTERVAL).message(e).create();
        }
        return Response.ResponseBuilder.result(EResponseResult.COMMON_SUCCESS_UPDATE).create();
    }

    private void transferIfFieldsNotBlank(Doll source, Doll destination) {
        if(StringUtils.isNotBlank(source.getInputString())){
            destination.setInputString(source.getInputString());
        }
        if(source.getInputLong() != null){
            destination.setInputLong(source.getInputLong());
        }
        if(source.getCascadeTreeLong() != null){
            destination.setCascadeTreeLong(source.getCascadeTreeLong());
        }
        if(source.getSelectValues() != null){
            destination.setSelectValues(source.getSelectValues());
        }
        if(StringUtils.isNotBlank(source.getSelectApiString())){
            destination.setSelectApiString(source.getSelectApiString());
        }
        if(StringUtils.isNotBlank(source.getRadioString())){
            destination.setRadioString(source.getRadioString());
        }
        if(source.getCheckboxString() != null){
            destination.setCheckboxString(source.getCheckboxString());
        }
        if(source.getDurationLong() != null){
            destination.setDurationLong(source.getDurationLong());
        }
        if(source.getDateDate() != null){
            destination.setDateDate(source.getDateDate());
        }
        if(source.getDurationDateDate() != null){
            destination.setDurationDateDate(source.getDurationDateDate());
        }
        if(source.getStatus() != null){
            destination.setStatus(source.getStatus());
        }
    }
}
