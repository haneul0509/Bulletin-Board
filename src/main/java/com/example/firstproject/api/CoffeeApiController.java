package com.example.firstproject.api;

import com.example.firstproject.dto.CoffeeDto;
import com.example.firstproject.entity.Article;
import com.example.firstproject.entity.Coffee;
import com.example.firstproject.repository.CoffeeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@Slf4j
@RestController
public class CoffeeApiController {

    @Autowired
    private CoffeeRepository coffeeRepository;

    //Read_모두보기
    @GetMapping("/api/coffees")
    public List<Coffee> allRead(){
        return coffeeRepository.findAll();
    }

    //Read_일부 보기
    @GetMapping("/api/coffees/{id}")
    public Coffee someRead(@PathVariable Long id){
        return coffeeRepository.findById(id).orElse(null);
    }

    //Post
    @PostMapping(("/api/coffees"))
    public Coffee create(@RequestBody CoffeeDto dto){
        Coffee coffee = dto.toEntity();

        return coffeeRepository.save(coffee);
    }

    //Patch
    @PatchMapping("/api/coffees/{id}")
    public ResponseEntity<Coffee> update(@RequestBody CoffeeDto dto, @PathVariable Long id){
        //엔티티 변환
        Coffee coffee = dto.toEntity();

        //타겟 찾기
        Coffee target = coffeeRepository.findById(id).orElse(null);

        //잘못된 요청 처리
        if(target == null || coffee.getId() != id){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

        //업데이트 및 정상 응답 처리
        target.patch(coffee);
        Coffee saved = coffeeRepository.save(target);
        return ResponseEntity.status(HttpStatus.OK).body(saved);
    }

    //Delete
    @DeleteMapping("/api/coffees/{id}")
    public ResponseEntity<Coffee> delete(@PathVariable Long id){
        //1. 대상 찾기
        Coffee target = coffeeRepository.findById(id).orElse(null);
        //2. 잘못된 요청 처리
        if(target == null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
        //3. 대상 삭제
        coffeeRepository.delete(target);
        return ResponseEntity.status(HttpStatus.OK).body(null);
    }

}
