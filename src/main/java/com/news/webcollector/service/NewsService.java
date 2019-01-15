package com.news.webcollector.service;

import com.news.webcollector.entity.TbNews;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface NewsService extends JpaRepository<TbNews, String>//JpaRepository<实体类型，主键类型>
{

    @Override
    <S extends TbNews> S save(S tbNews);

    @Override
    List<TbNews> findAll();

    @Query(value="select guid from tb_news where news_title=?1", nativeQuery=true)
    List<String> findByTitle(String news_title);

}
