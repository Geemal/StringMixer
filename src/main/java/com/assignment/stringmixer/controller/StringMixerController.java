package com.assignment.stringmixer.controller;

import java.util.Collections;

import com.assignment.stringmixer.services.StringMixerService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api")
public class StringMixerController {
    private final StringMixerService stringMixerService;

    @Autowired
    public StringMixerController(StringMixerService stringMixerService) {
        this.stringMixerService = stringMixerService;
    }

    @GetMapping(value = "/mix")
    public Object getMixedString(@RequestParam("s1") String s1, @RequestParam("s2") String s2) {
        String result = stringMixerService.mix(s1, s2);
        return Collections.singletonMap("result", result);
    }

}