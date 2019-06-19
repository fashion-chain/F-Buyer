package com.hottop.brand;

import com.google.gson.*;
import com.hottop.core.config.BaseConfiguration;
import com.hottop.core.model.commerce.CommerceBrand;
import com.hottop.core.model.zpoj.bean.Image;
import com.hottop.core.model.zpoj.commerce.bean.CommerceBrandDto;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.util.Date;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TestBrandController {

    @Autowired
    private WebApplicationContext wac;

    private MockMvc mockMvc;

    @Before
    public void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
    }

    private String toPrettyFormat(String json) {
        JsonParser jsonParser = new JsonParser();
        JsonObject jsonObject = jsonParser.parse(json).getAsJsonObject();
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        return gson.toJson(jsonObject);
    }

    /**
     * 测试brand 新增方法
     *
     * @throws Exception
     * V1.0 弃用
     */
    @Deprecated
    @Test
    public void testBrandAdd() throws Exception {
        String uploadFilePath = "/Users/lq/Downloads/d.jpg";
        File file = new File(uploadFilePath);
        String fileName = file.getName();
        MockMultipartFile mockMultipartFile = new MockMultipartFile("file", fileName, MediaType.TEXT_PLAIN_VALUE, new FileInputStream(file));
        CommerceBrandDto commerceBrandDto = new CommerceBrandDto();
        commerceBrandDto.setName("macbook" + new Date().getTime()); //name 唯一
        commerceBrandDto.setCountry("china");
        commerceBrandDto.setDescription("macbook 18 touchbar");
        String jsonStr = BaseConfiguration.generalGson().toJson(commerceBrandDto);
        mockMvc.perform(MockMvcRequestBuilders.multipart("/commerce/")
                .file(mockMultipartFile)
                .param("jsonStr", jsonStr))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    /**
     * 测试brand 更新
     *
     * @throws Exception
     * V1.0 弃用
     */
    @Deprecated
    @Test
    public void testBrandUpdate() throws Exception {
        String uploadFilePath = "/Users/lq/Downloads/b.jpg";
        File file = new File(uploadFilePath);
        String fileName = file.getName();
        MockMultipartFile mockMultipartFile = new MockMultipartFile("file", fileName,
                MediaType.TEXT_PLAIN_VALUE, new FileInputStream(file));
        CommerceBrandDto commerceBrandDto = new CommerceBrandDto();
        commerceBrandDto.setName("macbook1553939548189");//已经存在的商品名
        commerceBrandDto.setCountry("china updated");
        commerceBrandDto.setDescription("macbook 18 touchbar updated");
        String jsonStr = BaseConfiguration.generalGson().toJson(commerceBrandDto);
//        mockMvc.perform(MockMvcRequestBuilders.multipart("/commerce/update")
//                .file(mockMultipartFile)
//                .param("jsonStr", jsonStr) )
//                .andExpect(MockMvcResultMatchers.status().isOk());
        CommerceBrandDto commerceBrandDto2 = new CommerceBrandDto();
        commerceBrandDto2.setName("macbook1553939548189");//已经存在的商品名
        commerceBrandDto2.setCountry("china updated no file");
        commerceBrandDto2.setDescription("macbook 18 touchbar updated no file");
        String jsonStr2 = BaseConfiguration.generalGson().toJson(commerceBrandDto2);
        mockMvc.perform(MockMvcRequestBuilders.post("/commerce/update")
                .param("jsonStr", jsonStr2))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testBrandList_fields() throws Exception {
        //demo filterString: filter:price_<_200,price_>=_500;province_in_beijing|shanghai;title_like_saint
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/commerce/fields"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
        System.out.println(String.format("返回结果：%s", mvcResult.getResponse().getContentAsString()));
    }
    @Test
    public void testBrandList_view() throws Exception {
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/commerce/view=list_country"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
        System.out.println(String.format("view返回结果：%s", mvcResult.getResponse().getContentAsString()));
    }

    /**
     * ?filter=country_!in_china%27canada;name_like_abc;description_like_a,country_:_china&flag=0_18&sort=-name,+description&fields=name
     * @throws Exception
     */
    @Test
    public void testBrandLists_filter() throws Exception {
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/brand/filter?filter=country_:_china&flag=1_1&sort=-name&fields=name"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
        String result = mvcResult.getResponse().getContentAsString();
        System.out.println(String.format("view返回结果：" ));
        System.out.println(toPrettyFormat(result));
    }

    /**
     * 测试文件上传
     * 图片上传
     * v2.0
     */
    @Test
    public void testImageUplod() throws Exception {
        File file = new File("/Users/lq/Downloads/d.jpg");
        MockMultipartFile mockMultipartFile = new MockMultipartFile("file", file.getName(), MediaType.MULTIPART_FORM_DATA_VALUE, new FileInputStream(file));
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.multipart("/imageUpload")
                    .file(mockMultipartFile)
                    .param("to", "PRODUCT"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
        System.out.println(result.getResponse().getContentAsString());
        //{"code":0,"data":{"url":"product/d30407f00e9340709831f9dc93e32100.jpg","uuid":"d30407f00e9340709831f9dc93e32100","format":"jpg","width":1024,"height":640,"mediaType":"image"},"message":null,"error":null,"flag":null,"flagPre":null,"success":true}
    }

    // 测试添加商标
    @Test
    public void testAddCommerceBrand() throws Exception {
        String imgUploadResultJsonStr = "{\n" +
                "    \"code\": 0,\n" +
                "    \"data\": {\n" +
                "        \"width\": 1680,\n" +
                "        \"height\": 1050,\n" +
                "        \"type\": \"image\",\n" +
                "        \"url\": \"product/4a367862047d4c6abf5b20a1b8a1e5d4.jpg\",\n" +
                "        \"uuid\": \"4a367862047d4c6abf5b20a1b8a1e5d4\",\n" +
                "        \"format\": \"jpg\"\n" +
                "    }\n" +
                "}";
        JsonElement resultJson = new JsonParser().parse(imgUploadResultJsonStr);
        JsonObject imgJson = resultJson.getAsJsonObject().getAsJsonObject("data");

        CommerceBrandDto commerceBrand = new CommerceBrandDto();
        commerceBrand.setDescription("品牌描述1");
        commerceBrand.setCountry("品牌国家1");
        commerceBrand.setAvatar(imgJson.toString());
        commerceBrand.setName("品牌名1");
        System.out.println("上传json str：" + BaseConfiguration.generalGson().toJson(commerceBrand));
        /*MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/brand/").content(BaseConfiguration.generalGson().toJson(commerceBrand))
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
        System.out.println("结果：" + result.getResponse().getContentAsString());*/
    }

    //测试更新商标
    @Test
    public void testUpdateCommerceBrand() throws Exception {
        //只更新品牌描述
        CommerceBrandDto commerceBrandDto = new CommerceBrandDto();
        commerceBrandDto.setDescription("品牌描述2");
        String putJsonStr = BaseConfiguration.generalGson().toJson(commerceBrandDto);
        System.out.println("上传jsonStr：" + putJsonStr);
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.put("/brand/1")
                .content(putJsonStr)
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
        System.out.println("结果：" + result.getResponse().getContentAsString());
    }


}
