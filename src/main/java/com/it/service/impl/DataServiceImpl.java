package com.it.service.impl;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Map;

import com.it.mapper.DataMapper;
import com.it.result.CommonResult;
import com.it.service.DataImplService;
import com.it.util.PBE;
import lombok.SneakyThrows;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class DataServiceImpl implements DataImplService {


    @Autowired
    private DataMapper dataMapper;

    // 加密
    @SneakyThrows
    @Override
    public CommonResult insert(String data, String password) {
        byte[] input = data.getBytes();
        // 初始化盐
        byte[] salt = PBE.initSalt();
        // 加密
        byte[] data1 = PBE.encrypt(input, password, salt);
        System.err.println("加密后data字符串\t" + Base64.getEncoder().encodeToString(data1));
        byte[] output = PBE.decrypt(data1,password,salt);
        String outputStr = new String(output);
        System.err.println("加密后字符串\t" + outputStr);
        dataMapper.addData(data,Base64.getEncoder().encodeToString(data1),password,Base64.getEncoder().encodeToString(salt));
        return new CommonResult<>(200, "加密成功",Base64.getEncoder().encodeToString(data1));
    }

    // 解密
    @SneakyThrows
    public CommonResult output1(String data, String password) {
        String salt=dataMapper.selectSalt(data,password);
        if(salt == null || salt == "")
        {
            return new CommonResult<>(500, "解密失败","密码或密钥错误");
        }
        byte[] data1 = Base64.getDecoder().decode(data);
        byte[] salt1 = Base64.getDecoder().decode(salt);
        byte[] output = PBE.decrypt(data1,password,salt1);
        String outputStr = new String(output);
        return new CommonResult<>(200, "解密成功",outputStr);
    }

    // 解密
    @SneakyThrows
    @Override
    public CommonResult alloutput(Map map) {
        List<String> list = (List) map.get("list");
        ArrayList<String> arr = new ArrayList<String> ();
        for (int i = 0; i < list.size(); i++) {
            Object lo = list.get(i);
            Map entry = (Map) lo;
            String data = (String) entry.get("data");
            String password = (String) entry.get("password");
          //  System.out.println(data + " " + password);
            String salt = dataMapper.selectSalt(data, password);
            System.out.println(salt);
            if (salt == null || salt == "") {
                return new CommonResult<>(500, "解密失败,密钥或密文错误");
            }
            byte[] data1 = Base64.getDecoder().decode(data);
            byte[] salt1 = Base64.getDecoder().decode(salt);
            byte[] output = PBE.decrypt(data1, password, salt1);
            String outputStr = new String(output);
            arr.add(outputStr);
        }
        return new CommonResult<>(200, "解密成功",arr);
    }


}
