package com.hottop.brand;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.hottop.core.config.BaseConfiguration;
import com.hottop.core.model.commerce.CommerceBrand;
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

    /**
     * 测试brand 新增方法
     *
     * @throws Exception
     */
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
     */
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

    public String toPrettyFormat(String json) {
        JsonParser jsonParser = new JsonParser();
        JsonObject jsonObject = jsonParser.parse(json).getAsJsonObject();
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        return gson.toJson(jsonObject);
    }


}
