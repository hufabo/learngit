package com.imooc.girl;

import com.imooc.girl.domain.Girl;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GirlRepository extends JpaRepository<Girl,Integer> {
    public List<Girl> findGirlByAge(Integer age);
}
