package com.it.service;

import com.it.result.CommonResult;

import java.util.Map;

public interface DataImplService {

    CommonResult insert(String data,String password);

    CommonResult output1(String data, String password);

    CommonResult alloutput(Map map);

}
