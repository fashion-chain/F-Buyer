package com.hottop.backstage.product.controller;

import com.google.gson.reflect.TypeToken;
import com.hottop.backstage.base.controller.BackstageBaseController;
import com.hottop.backstage.product.service.CommerceBrandService;
import com.hottop.core.config.BaseConfiguration;
import com.hottop.core.controller.EntityBaseController;
import com.hottop.core.model.commerce.CommerceBrand;
import com.hottop.core.model.zpoj.commerce.bean.CommerceBrandDto;
import com.hottop.core.request.argument.annotation.Filter;
import com.hottop.core.request.argument.annotation.Flag;
import com.hottop.core.request.argument.filter.IFilterResolver;
import com.hottop.core.request.argument.filter.SimpleFilterResolver;
import com.hottop.core.request.argument.flag.FlagPageable;
import com.hottop.core.response.EResponseResult;
import com.hottop.core.response.Response;
import com.hottop.core.service.EntityBaseService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/brand")
@Api("商标管理")
public class CommerceBrandController extends BackstageBaseController<CommerceBrand> {

    @Autowired
    private CommerceBrandService commerceBrandService;

    /**
     * 新增商标
     *
     * @param jsonStr
     * @param file
     * @return
     */
    @RequestMapping(path = "/", method = RequestMethod.POST)
//    @ApiOperation("新增商标")
//    @ApiImplicitParams({
//        @ApiImplicitParam(paramType = "create", name="jsonStr", value="商标对象json字符串", required = true, dataType = "String"),
//        @ApiImplicitParam(paramType = "create", name="file", value="商标Image文件", required = true, dataType = "File")
//    })
    public Response post(@RequestParam("jsonStr") String jsonStr,
                         @RequestParam("file") MultipartFile file) {
        CommerceBrand brand = BaseConfiguration.generalGson().fromJson(jsonStr, new TypeToken<CommerceBrand>() {
        }.getType());
        return Response.ResponseBuilder.result(EResponseResult.OK)
                .data(commerceBrandService.save(brand, file))
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
     * @param file    可以为空
     * @param jsonStr 商标json字符串
     * @return
     */
    @RequestMapping(path = "/update", method = RequestMethod.POST)
//    @ApiOperation("更新商标")
//    @ApiImplicitParams({
//            @ApiImplicitParam(paramType = "create", name="jsonStr", value="商标对象json字符串", required = true, dataType = "String"),
//            @ApiImplicitParam(paramType = "create", name="file", value="商标Image文件", required = false, dataType = "File")
//    })
    public Response update(@RequestParam(name = "file", required = false) MultipartFile file,
                           @RequestParam(name = "jsonStr") String jsonStr) {
        CommerceBrand brand = BaseConfiguration.generalGson().fromJson(jsonStr, new TypeToken<CommerceBrand>() {
        }.getType());
        return Response.ResponseBuilder.result(EResponseResult.OK)
                .data(commerceBrandService.update(brand, file))
                .create();
    }

    @Override
    public Class<CommerceBrand> clazz() {
        return CommerceBrand.class;
    }

    @Override
    public EntityBaseService service() {
        return commerceBrandService;
    }
}
