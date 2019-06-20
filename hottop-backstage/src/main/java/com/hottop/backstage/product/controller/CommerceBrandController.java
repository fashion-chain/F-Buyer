package com.hottop.backstage.product.controller;


import com.hottop.backstage.base.controller.BackstageBaseController;
import com.hottop.core.model.cms.CmsUtil;
import com.hottop.core.model.cms.annotation.ComponentConverter;
import com.hottop.core.model.cms.bean.component.EComponentType;
import com.hottop.core.model.commerce.CommerceAttribute;
import com.hottop.core.product.service.CommerceBrandService;
import com.hottop.core.product.service.CommerceCategoryService;
import com.hottop.core.config.BaseConfiguration;
import com.hottop.core.model.commerce.CommerceBrand;
import com.hottop.core.model.zpoj.bean.Image;
import com.hottop.core.model.zpoj.commerce.bean.CommerceBrandDto;
import com.hottop.core.request.argument.annotation.Flag;
import com.hottop.core.request.argument.flag.FlagPageable;
import com.hottop.core.request.argument.validator.commerce.CommerceBrandValidator;
import com.hottop.core.response.EResponseResult;
import com.hottop.core.response.Response;
import com.hottop.core.service.EntityBaseService;
import com.hottop.core.utils.LogUtil;
import com.hottop.core.utils.ResponseUtil;
import com.hottop.core.utils.tree.Node;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/brand")
public class CommerceBrandController extends BackstageBaseController<CommerceBrand> {

    private static Logger logger = LoggerFactory.getLogger(CommerceBrandController.class);

    @Autowired
    private CommerceBrandService commerceBrandService;

    @Autowired
    private CommerceCategoryService commerceCategoryService;

    @Override
    public Class<CommerceBrand> clazz() {
        return CommerceBrand.class;
    }

    @Override
    public EntityBaseService service() {
        return commerceBrandService;
    }

    @InitBinder
    public void initBinder(WebDataBinder webDataBinder) {
        webDataBinder.setValidator(new CommerceBrandValidator());
    }

    /**
     * 新增商标
     *
     * @return
     */
    @RequestMapping(path = "/", method = RequestMethod.POST)
    //@PreAuthorize("hasAuthority('brand_add')")
    public Response post(@RequestBody CommerceBrandDto commerceBrandDto) {
        try {
            String avatarStr = commerceBrandDto.getAvatar();
            Image image = BaseConfiguration.generalGson().fromJson(avatarStr, Image.class);
            CommerceBrand commerceBrand = new CommerceBrand();
            BeanUtils.copyProperties(commerceBrandDto, commerceBrand);
            commerceBrand.setAvatar(image);
            logger.info("新增商标对象jsonStr:{}", BaseConfiguration.generalGson().toJson(commerceBrand));
            return ResponseUtil.addSuccessResponse(CommerceBrand.class.getSimpleName());
        } catch (Exception e) {
            LogUtil.error(e.getStackTrace());
        }
        return ResponseUtil.addFailResponse(CommerceBrand.class.getSimpleName());
    }

    @RequestMapping(path = "/list", method = RequestMethod.GET)
    public Response list(String country,
                         @Flag FlagPageable flagPageable) {
        return Response.ResponseBuilder.result(EResponseResult.OK)
                .data(commerceBrandService.findAllByCountry(country, flagPageable).getContent())
                .create();
    }

    /**
     * 更新商标
     *
     * @return
     */
    @RequestMapping(path = "/{brandId}", method = RequestMethod.PUT)
    //@PreAuthorize("hasAuthority('brand_update')")
    public Response update(@RequestBody CommerceBrandDto commerceBrandDto, @PathVariable("brandId") Long brandId) {
        logger.info("brandId:{},类型：{}", brandId, brandId.longValue());
        return commerceBrandService.update(commerceBrandDto, brandId);
    }

    /**
     * 根据
     *
     * @param cid
     * @return
     */
    @GetMapping(path = "/getByCid/{cid}")
    public Response getBrandByCid(@PathVariable("cid") Long cid) {
        Response brandsByCids = commerceBrandService.getBrandsByCid(cid);
        List<CommerceBrand> brands = (List<CommerceBrand>) brandsByCids.getData();
        if (brands == null || brands.size() <= 0) {
            return Response.ResponseBuilder.result(EResponseResult.ERROR_INTERVAL)
                    .simpleMessage("当前商品分类下没有可用的品牌").create();
        }
        ArrayList<Map> result = new ArrayList<>();
        for (CommerceBrand brand : brands) {
            Map<String, String> map = new HashMap<>();
            map.put("key", brand.getId().toString());
            map.put("value", brand.getName());
            result.add(map);
        }
        return Response.ResponseBuilder.result(EResponseResult.OK).data(result).create();
    }

    //查询所有的国家
    @GetMapping(path = "/country")
    public Response getAllCountries(Boolean refresh) {
        if (refresh == null) {
            return commerceBrandService.getAllCountries();
        } else {
            return commerceBrandService.getAllCountries(refresh);
        }
    }


    //查看商标brand详情
    @RequestMapping(path = "/{id}", method = RequestMethod.GET)
    //@PreAuthorize("hasAuthority('brand_detail')")
    @Override
    @ComponentConverter(component = EComponentType.image)
    public Response getOne(@PathVariable("id") Long id) throws Exception {
        Response resp = super.getOne(id);
        CommerceBrand brand = (CommerceBrand) resp.getData();
        Response categoryTree_resp = commerceCategoryService.getCategoryTree(brand.getCategoryId());
        if (categoryTree_resp.getCode().equals(0)) {
            brand.setCategoryIdTree((ArrayList<Node>) categoryTree_resp.getData());
        }
        return resp;
    }

    //删除
    @RequestMapping(path = "/{id}", method = RequestMethod.DELETE)
    //@PreAuthorize("hasAnyAuthority('brand_delete')")
    @Override
    public Response delete(@PathVariable("id") Long id) throws Exception {
        return super.delete(id);
    }
}
