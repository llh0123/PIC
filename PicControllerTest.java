package com.sx.picScale.controller;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.mock.web.MockMultipartHttpServletRequest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import java.io.*;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by lenovo on 2017/8/17.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = {"classpath:spring-mybatis.xml", "classpath:spring-mvc.xml"})
public class PicControllerTest {
    @Autowired
    PicController picController;
    MockMvc mockMvc;

    @Before
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(picController).build();
    }

    @Test
    public void uploadTest() throws Exception {
        FileInputStream fis = new FileInputStream("D:\\study\\PictureScalling\\target\\PictureScalling\\pic\\1.jpg");
        MockMultipartFile file = new MockMultipartFile("file", "1.gif", "image/jpeg", fis);
        MockMultipartHttpServletRequest request = new MockMultipartHttpServletRequest();
       MockHttpServletResponse response=new MockHttpServletResponse();
        request.addFile(file);
        picController.upload(request, response, file);


    }

    @Test
    public void uploadConTest() throws Exception {
        FileInputStream fis = new FileInputStream("D:\\study\\PictureScalling\\target\\PictureScalling\\pic\\1.jpg");
        MockMultipartFile file = new MockMultipartFile("file", "1.gif", "image/jpeg", fis);
        MockMultipartHttpServletRequest request = new MockMultipartHttpServletRequest();
        MockHttpServletResponse response=new MockHttpServletResponse();
        request.setParameter("w0","100");
        request.setParameter("h0","100");
        request.addFile(file);
        picController.uploadCon(request,response,file,1);
    }

    @Test
    public void conTest() throws Exception {
        mockMvc.perform(post("http://localhost:8081/conNum").param("num", "1")).andExpect(status().isOk()).andDo(print());
    }

    public static byte[] imageToByteArray(String imgPath) throws FileNotFoundException {
        BufferedInputStream in;
        try {
            in = new BufferedInputStream(new FileInputStream(imgPath));
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            int size = 0;
            byte[] temp = new byte[1024];
            while ((size = in.read(temp)) != -1) {
                out.write(temp, 0, size);
            }
            in.close();
            return out.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

}
