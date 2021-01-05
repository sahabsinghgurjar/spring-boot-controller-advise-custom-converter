package com.example.demo;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DemoRestResource {

    @Value("${customProperties}")
    private String customProperties;

    @Value("${defProperties}")
    private String defProperties;

    @Value("${prof1Properties}")
    private String prof1Properties;

    @GetMapping("/test")
    public ResponseEntity<DemoResponse> getStatus(@RequestBody TestUser user) {
        System.out.println(customProperties);
        System.out.println(defProperties);
        System.out.println(prof1Properties);
        if(user.getUserName().equalsIgnoreCase("throwError")){
          throw new RuntimeException("custom error");
        }
        DemoResponse demoResponse = new DemoResponse();
        demoResponse.setMsg("successfully execution");
        demoResponse.setUserName(user.getUserName());
        return ResponseEntity.ok(demoResponse);
    }
}
