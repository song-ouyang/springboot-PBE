package com.it.mapper;


import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface DataMapper {

   void addData(String text, String data, String password, String salt);

   String selectSalt(String data, String password);


}
