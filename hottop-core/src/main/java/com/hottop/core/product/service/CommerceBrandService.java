package com.hottop.core.product.service;

import com.hottop.core.config.BaseConfiguration;
import com.hottop.core.config.RedisConfig;
import com.hottop.core.model.commerce.CommerceBrand;
import com.hottop.core.model.zpoj.bean.Image;
import com.hottop.core.model.zpoj.commerce.bean.CommerceBrandDto;
import com.hottop.core.repository.EntityBaseRepository;
import com.hottop.core.repository.commerce.CommerceBrandRepository;
import com.hottop.core.response.EResponseResult;
import com.hottop.core.response.Response;
import com.hottop.core.service.EntityBaseService;
import com.hottop.core.utils.LogUtil;
import com.hottop.core.utils.RedisUtil;
import com.hottop.core.utils.ResponseUtil;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class CommerceBrandService extends EntityBaseService<CommerceBrand, Long> {


    private static Logger logger = LoggerFactory.getLogger(CommerceBrandService.class);

    @Autowired
    private RedisConfig redisConfig;

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private CommerceBrandRepository commerceBrandRepository;

    @Override
    public EntityBaseRepository<CommerceBrand, Long> repository() {
        return commerceBrandRepository;
    }

    @Transactional(readOnly = true)
    public Page<CommerceBrand> findAllByCountry(String country, Pageable pageable) {
        if (StringUtils.isEmpty(country)) {
            return commerceBrandRepository.findAll(pageable);
        }
        return commerceBrandRepository.findAllByCountry(country, pageable);
    }

    @Transactional
    public Response update(CommerceBrandDto commerceBrandDto, Long id) {
        try {
            Optional<CommerceBrand> commerceBrand_optional = commerceBrandRepository.findById(id);
            if(!commerceBrand_optional.isPresent()) throw new Exception("更新商标出错-id不存在");
            CommerceBrand commerceBrand = commerceBrand_optional.get();
            transferIfFieldNotNull(commerceBrandDto, commerceBrand);
            CommerceBrand update = update(commerceBrand);
            return ResponseUtil.updateSuccessResponse(CommerceBrand.class.getSimpleName());
        } catch (Exception e) {
            e.printStackTrace();
            LogUtil.error(e.getStackTrace());
            logger.info("更新商标出错");
        }
        return ResponseUtil.updateFailResponse(CommerceBrand.class.getSimpleName());
    }

    public Response getBrandsByCids(ArrayList<Long> cids) {
        List<CommerceBrand> byCategoryIdIn = commerceBrandRepository.findByCategoryIdIn(cids);
        return Response.ResponseBuilder.result(EResponseResult.OK).data(byCategoryIdIn).create();
    }

    public Response getBrandsByCid(Long cid) {
        List<CommerceBrand> brands = commerceBrandRepository.findByCategoryId(cid);
        return Response.ResponseBuilder.result(EResponseResult.OK).data(brands).create();
    }

    private void transferIfFieldNotNull(CommerceBrandDto commerceBrandDto, CommerceBrand commerceBrand) {
        if(StringUtils.isNotBlank(commerceBrandDto.getAvatar())) {
            String avatar = commerceBrandDto.getAvatar();
            Image image = BaseConfiguration.generalGson().fromJson(avatar, Image.class);
            commerceBrand.setAvatar(image);
        }
        if(StringUtils.isNotBlank(commerceBrandDto.getCountry())) {
            commerceBrand.setCountry(commerceBrandDto.getCountry());
        }
        if(StringUtils.isNotBlank(commerceBrandDto.getDescription())) {
            commerceBrand.setDescription(commerceBrandDto.getDescription());
        }
        if(StringUtils.isNotBlank(commerceBrandDto.getName())) {
            commerceBrand.setName(commerceBrandDto.getName());
        }
        if(commerceBrandDto.getCategoryId() != null) {
            commerceBrand.setCategoryId(commerceBrandDto.getCategoryId());
        }
    }

    private final static String redis_key_brand_countries = "redis_key_brand_countries";

    public Response getAllCountries() {
        return getAllCountries(false);//默认强制刷新
    }

    public Response getAllCountries(boolean refresh) {
        List<String> allCountry = null;
        try {
            Map<String, String> result = new HashMap<>();
            if(refresh == true) {
                allCountry = new ArrayList<String>(Arrays.asList(BaseConfiguration.getMessage("commerce.brand.all.country").split(",")));
                result = allCountry.stream().collect(Collectors.toMap(k -> k, v -> v, (k1, k2) -> k1));
                redisUtil.set(redis_key_brand_countries, result,60 * 30l);
            } else {
                result = (Map<String, String>) redisUtil.get(redis_key_brand_countries);
                if(result == null) {
                    getAllCountries(true);
                }
            }
            return Response.ResponseBuilder.result(EResponseResult.OK).data(result).create();
        } catch (Exception e) {
            LogUtil.error(e.getStackTrace());
            return Response.ResponseBuilder.result(EResponseResult.ERROR_INTERVAL).simpleMessage("查询商标所有国家失败").create();
        }
    }

    private final static String redis_key_brand_names = "redis_key_brand_names";
    public Response getBrandNames() {
        try {
            Map<Long, String> result = (Map<Long, String>) redisUtil.get(redis_key_brand_names);
            if(result == null) {
                List<CommerceBrand> brands = commerceBrandRepository.findAll();
                result = brands.stream().collect(Collectors.toMap(k -> k.getId(), v -> v.getName(), (k1,k2) -> k1));
                redisUtil.set(redis_key_brand_names, result, redisConfig.getExpireTime());
            }
            return Response.ResponseBuilder.result(EResponseResult.OK).data(result).create();
        } catch (Exception e){
            LogUtil.error(e.getStackTrace());
            return Response.ResponseBuilder.result(EResponseResult.ERROR_INTERVAL).simpleMessage("查询商标名称map失败").create();
        }
    }

}
