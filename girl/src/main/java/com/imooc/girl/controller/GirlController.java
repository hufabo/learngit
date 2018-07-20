package com.imooc.girl.controller;

import com.imooc.girl.GirlRepository;
import com.imooc.girl.domain.Girl;
import com.imooc.girl.domain.Result;
import com.imooc.girl.utils.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
public class GirlController {
    @Autowired
    private GirlRepository girlRepository;

    //查询所有女孩儿
    @GetMapping(value="/girl")
    public List<Girl> girlList(){
        return girlRepository.findAll();
    }


    //添加一个女孩儿
    @PostMapping(value="/addGirl")
    public Object addGirl(@Valid Girl girl, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return ResultUtil.error(1, bindingResult.getFieldError().getDefaultMessage());
        }
        girl.setAge(girl.getAge());
        girl.setCupSize(girl.getCupSize());
        return ResultUtil.success(girlRepository.save(girl));
    }

    //根据id查询一个女孩儿
    @GetMapping(value = "/girl/{id}")
    public Optional<Girl> girlFindOne(@PathVariable("id")  Integer id){
        return girlRepository.findById(id);
    }

    //根据id删除一个女孩儿
    @DeleteMapping("/girls/{id}")
    public void deleteGirl(Girl girl){
        girlRepository.delete(girl);
    }

    //根据id更新一个女孩儿的信息
    @PutMapping(value = "/upgirl/{id}")
    public void updateGirl(Girl girl){
        girl.setCupSize(girl.getCupSize());
        girl.setAge(girl.getAge());
        girl.setId(girl.getId());
        girlRepository.save(girl);
    }

    //根据年龄查询女孩儿
    @GetMapping(value = "/girlAge/{age}")
    public List<Girl> findByAge(@PathVariable("age") Integer age){
        return  girlRepository.findGirlByAge(age);
    }
}
