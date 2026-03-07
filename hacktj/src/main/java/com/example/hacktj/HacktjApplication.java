package com.example.hacktj;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.hacktj.repository.WordRepository;
import com.example.hacktj.service.WordService;

@SpringBootApplication
@RestController
public class HacktjApplication {
  @Autowired
  WordRepository L1;
  @Autowired
  WordService L1Service;

  public static void main(String[] args) {
    SpringApplication.run(HacktjApplication.class, args);
  }
  @GetMapping("/hello")
  public String hello(@RequestParam(value = "name", defaultValue = "World") String name) {
    return String.format("Hello %s!", name);
  }
}