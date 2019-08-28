package com.oocl.web.sampleWebApp;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.HashMap;

import org.hamcrest.CoreMatchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class SampleWebAppApplicationTests {
	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private ObjectMapper objectMapper;

	@Test
    public void shouldReturnDefaultMessage() throws Exception {
        this.mockMvc.perform(get("/"))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(content().string(containsString("Hello World")));
    }
	
	@Test
    public void shouldReturnBadRequest() throws Exception {
        this.mockMvc.perform(get("/hello"))
        .andDo(print())
        .andExpect(status().isBadRequest())
        .andExpect(content().string(containsString("Hello World")));
    }
	
	@Test
    public void shouldReturnUserName() throws Exception {
        this.mockMvc.perform(get("/users"))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(MockMvcResultMatchers.jsonPath("$.username", CoreMatchers.is("����")));
    }
	
	@Test
    public void shouldReturnpostUserName() throws Exception {
		HashMap<String, String> map = new HashMap<String, String>();
		String postString = objectMapper.writeValueAsString(map);
		
        this.mockMvc.perform(
    		MockMvcRequestBuilders
    		.post("/user")
    		.contentType(MediaType.APPLICATION_JSON_UTF8)
    		.content(postString)
        	)
        		
        	.andDo(print())
        	.andExpect(status().isCreated())
        	.andExpect(MockMvcResultMatchers.jsonPath("$.id", CoreMatchers.is("100")));
    }
}
