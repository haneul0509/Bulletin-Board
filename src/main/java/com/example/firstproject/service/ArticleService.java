package com.example.firstproject.service;

import com.example.firstproject.repository.ArticleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service //서비스 객체 생성
public class ArticleService {
    @Autowired
    private ArticleRepository articleRepository; //게시글 리파지터리 객체 주입
}

