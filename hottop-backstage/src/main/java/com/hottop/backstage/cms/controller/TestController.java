package com.hottop.backstage.cms.controller;

import com.hottop.core.model.zpoj.bean.Image;
import com.hottop.core.response.EResponseResult;
import com.hottop.core.response.Response;
import com.hottop.core.utils.alioss.AliOssClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.text.SimpleDateFormat;
import java.util.Date;

@RestController
@RequestMapping("/test")
public class TestController {

    @RequestMapping(path = "/page", method = RequestMethod.GET)
    private Response pageTest() {
        return Response.ResponseBuilder
                .result(EResponseResult.OK)
                .create();
    }



    @RequestMapping(path = "/saveImg", method = RequestMethod.POST)
    private Response putImageTest(MultipartFile file) throws Exception {
        Response response = AliOssClient.saveImg(file, AliOssClient.ECustomPath.PRODUCT, null);
        Image image = (Image) response.getData();

        return Response.ResponseBuilder
                .result(EResponseResult.OK)
                .create();
    }
}
