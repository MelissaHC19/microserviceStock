package com.bootcamp.microserviceStock.adapters.driven.jpa.mysql.adapter;

import com.bootcamp.microserviceStock.adapters.driven.jpa.mysql.entity.ArticleEntity;
import com.bootcamp.microserviceStock.adapters.driven.jpa.mysql.mapper.IArticleEntityMapper;
import com.bootcamp.microserviceStock.adapters.driven.jpa.mysql.repository.IArticleRepository;
import com.bootcamp.microserviceStock.domain.model.Article;
import com.bootcamp.microserviceStock.domain.model.Brand;
import com.bootcamp.microserviceStock.domain.model.Category;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.mockito.BDDMockito.given;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class ArticleAdapterTest {
    @Mock
    private IArticleRepository articleRepository;

    @Mock
    private IArticleEntityMapper articleEntityMapper;

    @InjectMocks
    private ArticleAdapter articleAdapter;

    @Test
    @DisplayName("Validation that an article is created correctly in the DB")
    void createArticle() {
        Article article = new Article(1L, "SmartHome AI Assistant Hub", "Control your home with voice commands and seamless automation through this all-in-one smart hub.", 10,
                new BigDecimal("3050.99"),
                new Brand(1L, null, null),
                List.of(new Category(1L, null, null),
                        new Category(2L, null, null))
        );
        ArticleEntity articleEntity = articleEntityMapper.articleToEntity(article);
        given(articleRepository.save(articleEntity)).willReturn(articleEntity);
        articleAdapter.createArticle(article);
        verify(articleRepository, times(1)).save(articleEntity);
    }

    @Test
    @DisplayName("Validation when article already exists in the DB")
    void alreadyExistsByNameTrue() {
        String name = "SmartHome AI Assistant Hub";
        Mockito.when(articleRepository.findByName(name)).thenReturn(Optional.of(new ArticleEntity()));
        boolean exists = articleAdapter.alreadyExistsByName(name);
        assertTrue(exists);
        verify(articleRepository, times(1)).findByName(name);
    }

    @Test
    @DisplayName("Validation when article doesn't exists in the DB")
    void alreadyExistsByNameFalse() {
        String name = "Quantum Sound Wireless Earbuds";
        Mockito.when(articleRepository.findByName(name)).thenReturn(Optional.empty());
        boolean exists = articleAdapter.alreadyExistsByName(name);
        assertFalse(exists);
        verify(articleRepository, times(1)).findByName(name);
    }
}