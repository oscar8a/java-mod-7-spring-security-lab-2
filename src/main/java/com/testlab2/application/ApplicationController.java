package com.testlab2.application;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ApplicationController {
    private CryptoService cpService;

    public ApplicationController(CryptoService cryptoService){
        this.cpService = cryptoService;
    }

    @GetMapping("/hello")
    public String shouldGreetDefault(@RequestParam(
            name = "targetName",
            defaultValue = "Oscar") String name) {
        return String.format("Hello %s", name);
    }

    @GetMapping("/status")
    public String status() {
        return "Congratulations - you must be an admin since you can see the application's status information";
    }

    @GetMapping("/getCryptoPrice/{cryptocurrency}")
    public String getPriceOfCoin(@PathVariable String cryptocurrency) {
        return "The Price of " + cryptocurrency + " is: " + cpService.getCoinPrice(cryptocurrency);
    }
}
