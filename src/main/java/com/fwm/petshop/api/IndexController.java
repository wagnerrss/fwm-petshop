package com.fwm.petshop.api;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/")
public class IndexController {

    @GetMapping
    public String get(){
        return "Get api FWM para PetShop";
    }
    @PostMapping
    public String post(){
        return "Post api FWM para PetShop";
    }
    @PutMapping
    public String put(){
        return "Put api FWM para PetShop";
    }
    @DeleteMapping
    public String delete(){
        return "Delete api FWM para PetShop";
    }
}
