package com.assignment.stringmixer;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Collections;
import com.assignment.stringmixer.controller.StringMixerController;
import com.assignment.stringmixer.services.StringMixerService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

public class StringMixerControllerTests {

    @Mock
    StringMixerService stringMixerService;

    @InjectMocks
    StringMixerController stringMixerController;

    private MockMvc mockMvc;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(stringMixerController).build();
    }

    @Test
    public void testGetMixedString() throws Exception {
        String s1 = "my&friend&Paul has heavy hats! &";
        String s2 = "my friend John has many many friends &";

        String s3 = "mmmmm m nnnnn y&friend&Paul has heavy hats! &";
        String s4 = "my frie n d Joh n has ma n y ma n y frie n ds n&";

        String expString1 = "2:nnnnn/1:aaaa/1:hhh/2:mmm/2:yyy/2:dd/2:ff/2:ii/2:rr/=:ee/=:ss";
        String expString2 = "1:mmmmmm/=:nnnnnn/1:aaaa/1:hhh/2:yyy/2:dd/2:ff/2:ii/2:rr/=:ee/=:ss";

        when(stringMixerService.mix(s1, s2)).thenReturn(expString1);
        when(stringMixerService.mix(s3, s4)).thenReturn(expString2);

        RequestBuilder requestBuilder1 = MockMvcRequestBuilders.get("/api/mix").param("s1", s1).param("s2", s2)
                .accept(MediaType.APPLICATION_JSON);
        RequestBuilder requestBuilder2 = MockMvcRequestBuilders.get("/api/mix").param("s1", s3).param("s2", s4)
                .accept(MediaType.APPLICATION_JSON);

        MvcResult result1 = mockMvc.perform(requestBuilder1).andReturn();
        MvcResult result2 = mockMvc.perform(requestBuilder2).andReturn();

        assertEquals(result1.getResponse().getContentAsString(), "{\"result\":\"" + expString1 + "\"}");
        assertEquals(result2.getResponse().getContentAsString(), "{\"result\":\"" + expString2 + "\"}");
    }
}