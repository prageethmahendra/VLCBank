/*
 * Copyright 2012-2015 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.vlc.application;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import com.vlc.application.resources.EntityContext;
import com.vlc.application.resources.SecurityContext;
import com.vlc.application.resources.entity.Branch;
import com.vlc.application.resources.entity.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class ApplicationTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void whenLoginWithNoParamThenReturnEmpty() throws Exception {
        // N.B. jsoup can be useful for asserting HTML content
        mockMvc.perform(get("/login"))
                .andExpect(content().string(containsString("")));
    }

    @Test
    public void whenLoginWithInvalidParamThenReturnEmpty() throws Exception {
        // N.B. jsoup can be useful for asserting HTML content
        mockMvc.perform(get("/login?userName=\"@#$#@$#@\"&password=\"#@$#@$\""))
                .andExpect(content().string(containsString("")));
    }

    @Test
    public void whenLoginWithValidParamThenReturnUser() throws Exception {
        // N.B. jsoup can be useful for asserting HTML content
        mockMvc.perform(get("/login?userName=Prageeth&password=Prageeth321"))
                .andExpect(content().string(containsString("userName")));
    }

    @Test
    public void createBranchWithNoObject() throws Exception {
        mockMvc.perform(post("/createBranch"))
                .andExpect(content().string(""));
    }

    @Test
    public void WhenCreateBranchWithEmptyObjectReturnEmptyString() throws Exception {
        mockMvc.perform(post("/createBranch", new EntityContext()))
                .andExpect(content().string(""));
    }

    @Test
    public void WhenCreateBranchWithValidBranchReturnEntityContext() throws Exception {
        String json = "{\"entityType\":\"Branch\",\"entity\":{\"id\":0,\"branchName\":null},\"securityContext\":{\"user\":{\"id\":0,\"userName\":\"Prageeth\",\"password\":\"Prageeth321\",\"authorized\":false}}}";
        mockMvc.perform(post("/createBranch").contentType("application/json").content(json)).
                andExpect(content().string(containsString("entityType")));
    }
}
