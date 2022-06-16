package com.it.controller;


import com.it.result.CommonResult;
import com.it.service.DataImplService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
public class UserController {



    @Autowired
    private DataImplService dataImplService;


    @RequestMapping("/insert")
    public CommonResult insert(@RequestParam String data,
                                 @RequestParam String password) {
        return dataImplService.insert(data,password);
    }



    @RequestMapping("/output1")
    public CommonResult output(@RequestParam String data,
                               @RequestParam String password
    ) {
        return dataImplService.output1(data,password);
    }


    @RequestMapping("/alloutput")
    public CommonResult alloutput(@RequestBody Map map) {
        return dataImplService.alloutput(map);
    }



}
