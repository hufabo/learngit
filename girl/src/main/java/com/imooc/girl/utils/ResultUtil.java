package com.imooc.girl.utils;

import com.imooc.girl.domain.Result;

public class ResultUtil {
    //添加成功的返回结果
    public  static Result success(Object object){
        Result result=new Result();
        result.setCode(0);
        result.setMsg("成功");
        result.setData(object);
        return  result;
    }
    public static Result success(){
        return null;
    }
    //添加失败的返回结果信息
    public static Result error(Integer code,String msg){
        Result result=new Result();
        result.setCode(code);
        result.setMsg(msg);
        return result;
    }


}
