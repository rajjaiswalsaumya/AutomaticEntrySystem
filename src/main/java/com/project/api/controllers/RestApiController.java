package com.project.api.controllers;

/**
 * Created by rohitgupta on 12/27/15.
 */

import com.project.web.configs.ServiceVersionProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.Map;

/**
 * Created by rohit on 27-01-2015.
 */

@RestController
@RequestMapping("/api")
public class RestApiController {

    @Autowired
    ServiceVersionProperties serviceVersionProperties;

    @RequestMapping(value = "/rest")
    public Map<String, String> api() {
        return Collections.singletonMap("version", serviceVersionProperties.getVersion());
    }
}
