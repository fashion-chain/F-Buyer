package com.hottop.backstage.product.service;

import com.hottop.core.model.commerce.CommerceBrand;
import com.hottop.core.model.zpoj.bean.Image;
import com.hottop.core.repository.EntityBaseRepository;
import com.hottop.core.repository.commerce.CommerceBrandRepository;
import com.hottop.core.response.EResponseResult;
import com.hottop.core.response.Response;
import com.hottop.core.service.EntityBaseService;
import com.hottop.core.utils.CommonUtil;
import com.hottop.core.utils.alioss.AliOssClient;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
public class CommerceBrandService extends EntityBaseService<CommerceBrand, Long> {


    private static Logger logger = LoggerFactory.getLogger(CommerceBrandService.class);

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
    public CommerceBrand save(CommerceBrand brand, MultipartFile file) {
        try {
            Response response = AliOssClient.saveImg(file, AliOssClient.ECustomPath.PRODUCT, null);
            if (response.getCode().equals(EResponseResult.OK.getCode())) {
                Image image = (Image) response.getData();
                brand.setAvatar(image);
            }
        } catch (Exception e) {
            logger.info("新增商标图片出错：%s", CommonUtil.printStackTraceElements(e.getStackTrace()));
        }
        return save(brand);
    }

    /**
     * 更新商标信息
     *
     * @param brand
     * @param file
     * @return
     */
    @Transactional
    public CommerceBrand update(CommerceBrand brand, MultipartFile file) {
        CommerceBrand old = commerceBrandRepository.findByName(brand.getName());
        if(old == null){
            logger.info("更新商标name不存在");
            return null;
        }
        try {
            if ( file != null) {
                Image oldImage = old.getAvatar();
                Response response = AliOssClient.saveImg(file, AliOssClient.ECustomPath.PRODUCT, oldImage.getUuid());
                Image image = (Image) response.getData();
                old.setAvatar(image);
            }
            if (StringUtils.isNotBlank(brand.getCountry())) old.setCountry(brand.getCountry());
            if (StringUtils.isNotBlank(brand.getDescription())) old.setDescription(brand.getDescription());
        } catch (Exception e) {
            logger.info("更新商标图片出错：%s", CommonUtil.printStackTraceElements(e.getStackTrace()));
        }
        return update(old);
    }

}
