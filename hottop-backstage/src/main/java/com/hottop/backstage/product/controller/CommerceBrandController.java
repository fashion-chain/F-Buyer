package com.hottop.backstage.product.controller;


import com.hottop.backstage.base.controller.BackstageBaseController;
import com.hottop.backstage.product.service.CommerceBrandService;
import com.hottop.core.config.BaseConfiguration;
import com.hottop.core.model.commerce.CommerceBrand;
import com.hottop.core.model.zpoj.bean.Image;
import com.hottop.core.model.zpoj.commerce.bean.CommerceBrandDto;
import com.hottop.core.request.argument.annotation.Flag;
import com.hottop.core.request.argument.flag.FlagPageable;
import com.hottop.core.response.EResponseResult;
import com.hottop.core.response.Response;
import com.hottop.core.service.EntityBaseService;
import io.swagger.annotations.Api;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/brand")
@Api("商标管理")
public class CommerceBrandController extends BackstageBaseController<CommerceBrand> {

    private static Logger logger = LoggerFactory.getLogger(CommerceBrandController.class);

    @Autowired
    private CommerceBrandService commerceBrandService;

    @Override
    public Class<CommerceBrand> clazz() {
        return CommerceBrand.class;
    }

    @Override
    public EntityBaseService service() {
        return commerceBrandService;
    }

    /**
     * 新增商标
     *
     * @return
     */
    @RequestMapping(path = "/", method = RequestMethod.POST)
//    @ApiOperation("新增商标")
//    @ApiImplicitParams({
//        @ApiImplicitParam(paramType = "create", name="jsonStr", value="商标对象json字符串", required = true, dataType = "String"),
//        @ApiImplicitParam(paramType = "create", name="file", value="商标Image文件", required = true, dataType = "File")
//    })
    public Response post(@RequestBody CommerceBrandDto commerceBrandDto) {
        String avatarStr = commerceBrandDto.getAvatar();
        Image image = BaseConfiguration.generalGson().fromJson(avatarStr, Image.class);
        CommerceBrand commerceBrand = new CommerceBrand();
        BeanUtils.copyProperties(commerceBrandDto, commerceBrand);
        commerceBrand.setAvatar(image);
        logger.info("新增商标对象jsonStr:{}", BaseConfiguration.generalGson().toJson(commerceBrand));
        return Response.ResponseBuilder.result(EResponseResult.OK)
                .data(commerceBrandService.save(commerceBrand))
                .create();
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
//    @ApiOperation("更新商标")
//    @ApiImplicitParams({
//            @ApiImplicitParam(paramType = "create", name="jsonStr", value="商标对象json字符串", required = true, dataType = "String"),
//            @ApiImplicitParam(paramType = "create", name="file", value="商标Image文件", required = false, dataType = "File")
//    })
    public Response update(@RequestBody CommerceBrandDto commerceBrandDto, @PathVariable("brandId") Long brandId) {
        logger.info("brandId:{},类型：{}", brandId, brandId.longValue());
        return Response.ResponseBuilder.result(EResponseResult.OK)
                .data(commerceBrandService.update(commerceBrandDto, brandId))
                .create();
    }

}
