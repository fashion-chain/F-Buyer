package com.hottop.core.service.provinceCity;

import com.hottop.core.model.user.ProvinceCity;
import com.hottop.core.model.user.dto.ProvinceCityDto;
import com.hottop.core.repository.EntityBaseRepository;
import com.hottop.core.repository.user.ProvinceCityRepository;
import com.hottop.core.response.EResponseResult;
import com.hottop.core.response.Response;
import com.hottop.core.service.EntityBaseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service("provinceCityService")
public class ProvinceCityService extends EntityBaseService<ProvinceCity, Long> {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private ProvinceCityRepository provinceCityRepository;

    @Override
    public EntityBaseRepository<ProvinceCity, Long> repository() {
        return provinceCityRepository;
    }

    /**
     * 查询省份城区列表
     * @return
     */
    public Response list() {
        List<ProvinceCity> all = provinceCityRepository.findAll();
        List<ProvinceCityDto> data = new ArrayList<>();
        ProvinceCityDto provinceCityDto = null;
        for (ProvinceCity p : all) {
            provinceCityDto = new ProvinceCityDto();
            BeanUtils.copyProperties(p, provinceCityDto);
            data.add(provinceCityDto);
        }
        return Response.ResponseBuilder.result(EResponseResult.OK).data(data).create();
    }
}
