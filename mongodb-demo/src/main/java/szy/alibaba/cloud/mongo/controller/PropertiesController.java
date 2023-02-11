package szy.alibaba.cloud.mongo.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import szy.alibaba.cloud.mongo.config.MongoSettingsProperties;

@RestController
public class PropertiesController {

    @Autowired
    MongoSettingsProperties properties;

    @RequestMapping("/property/username")
    public String getUsername(){
        return properties.getUsername();
    }

    @RequestMapping("/property")
    public String getProperty(){
        return properties.toString();
    }
}
