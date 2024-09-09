package com.bootcamp.microserviceStock.adapters.driven.jpa.mysql.adapter;

import com.bootcamp.microserviceStock.adapters.driven.jpa.mysql.entity.ArticleEntity;
import com.bootcamp.microserviceStock.adapters.driven.jpa.mysql.entity.BrandEntity;
import com.bootcamp.microserviceStock.adapters.driven.jpa.mysql.entity.CategoryEntity;
import com.bootcamp.microserviceStock.adapters.driven.jpa.mysql.mapper.IArticleEntityMapper;
import com.bootcamp.microserviceStock.adapters.driven.jpa.mysql.repository.IArticleRepository;
import com.bootcamp.microserviceStock.domain.model.Article;
import com.bootcamp.microserviceStock.domain.model.Brand;
import com.bootcamp.microserviceStock.domain.model.Category;
import com.bootcamp.microserviceStock.domain.util.Pagination;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
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

    @Test
    @DisplayName("Validation that it returns paginated articles 'asc'")
    void listArticlesAsc() {
        int pageNumber = 0;
        int pageSize = 3;
        String sortBy = "name";
        String sortDirection = "asc";

        ArticleEntity articleEntity1 = new ArticleEntity(1L, "SmartX Pro 5G Smartphone", "Experience ultra-fast connectivity with the latest 5G-enabled smartphone, designed for superior performance.", 5,
                new BigDecimal("1000.50"),
                new BrandEntity(1L, null, null),
                List.of(new CategoryEntity(1L, null, null))
        );
        ArticleEntity articleEntity2 = new ArticleEntity(2L, "Organic Bamboo Bedding Set", "Sleep better with our luxurious bamboo bedding, naturally breathable and hypoallergenic.", 15,
                new BigDecimal("299.99"),
                new BrandEntity(2L, null, null),
                List.of(
                        new CategoryEntity(2L, null, null),
                        new CategoryEntity(3L, null, null)
                )
        );

        List<ArticleEntity> articleEntities = List.of(articleEntity2, articleEntity1);
        Page<ArticleEntity> page = new PageImpl<>(articleEntities, PageRequest.of(pageNumber, pageSize),2);

        Mockito.when(articleRepository.findAll(any(Pageable.class))).thenReturn(page);
        Mockito.when(articleEntityMapper.entityToDomain(articleEntity1)).thenReturn(new Article(1L, "SmartX Pro 5G Smartphone", "Experience ultra-fast connectivity with the latest 5G-enabled smartphone, designed for superior performance.", 5,
                new BigDecimal("1000.50"),
                new Brand(1L, null, null),
                List.of(new Category(1L, null, null))));
        Mockito.when(articleEntityMapper.entityToDomain(articleEntity2)).thenReturn(new Article(2L, "Organic Bamboo Bedding Set", "Sleep better with our luxurious bamboo bedding, naturally breathable and hypoallergenic.", 15,
                new BigDecimal("299.99"),
                new Brand(1L, null, null),
                List.of(
                        new Category(2L, null, null),
                        new Category(3L, null, null))));

        Pagination<Article> result = articleAdapter.listArticles(pageNumber, pageSize, sortBy, sortDirection);

        assertThat(result.getContent()).hasSize(2);
        assertThat(result.getPageNumber()).isZero();
        assertThat(result.getPageSize()).isEqualTo(3);
        assertEquals(2L, result.getContent().get(0).getId(), "The Article ID should be 2L");
        assertEquals(1L, result.getContent().get(1).getId(), "The Article ID should be 1L");

        verify(articleRepository, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @DisplayName("Validation that it returns paginated articles 'desc'")
    void listArticlesDesc() {
        int pageNumber = 0;
        int pageSize = 3;
        String sortBy = "name";
        String sortDirection = "desc";

        ArticleEntity articleEntity1 = new ArticleEntity(1L, "SmartX Pro 5G Smartphone", "Experience ultra-fast connectivity with the latest 5G-enabled smartphone, designed for superior performance.", 5,
                new BigDecimal("1000.50"),
                new BrandEntity(1L, null, null),
                List.of(new CategoryEntity(1L, null, null))
        );
        ArticleEntity articleEntity2 = new ArticleEntity(2L, "Organic Bamboo Bedding Set", "Sleep better with our luxurious bamboo bedding, naturally breathable and hypoallergenic.", 15,
                new BigDecimal("299.99"),
                new BrandEntity(2L, null, null),
                List.of(
                        new CategoryEntity(2L, null, null),
                        new CategoryEntity(3L, null, null)
                )
        );

        List<ArticleEntity> articleEntities = List.of(articleEntity1, articleEntity2);
        Page<ArticleEntity> page = new PageImpl<>(articleEntities, PageRequest.of(pageNumber, pageSize),2);

        Mockito.when(articleRepository.findAll(any(Pageable.class))).thenReturn(page);
        Mockito.when(articleEntityMapper.entityToDomain(articleEntity1)).thenReturn(new Article(1L, "SmartX Pro 5G Smartphone", "Experience ultra-fast connectivity with the latest 5G-enabled smartphone, designed for superior performance.", 5,
                new BigDecimal("1000.50"),
                new Brand(1L, null, null),
                List.of(new Category(1L, null, null))));
        Mockito.when(articleEntityMapper.entityToDomain(articleEntity2)).thenReturn(new Article(2L, "Organic Bamboo Bedding Set", "Sleep better with our luxurious bamboo bedding, naturally breathable and hypoallergenic.", 15,
                new BigDecimal("299.99"),
                new Brand(1L, null, null),
                List.of(
                        new Category(2L, null, null),
                        new Category(3L, null, null))));

        Pagination<Article> result = articleAdapter.listArticles(pageNumber, pageSize, sortBy, sortDirection);

        assertThat(result.getContent()).hasSize(2);
        assertThat(result.getPageNumber()).isZero();
        assertThat(result.getPageSize()).isEqualTo(3);
        assertEquals(1L, result.getContent().get(0).getId(), "The Article ID should be 1L");
        assertEquals(2L, result.getContent().get(1).getId(), "The Article ID should be 2L");

        verify(articleRepository, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @DisplayName("Validation that it return paginated articles 'asc' sorted by 'brandName'")
    void listArticlesAscSortedByBrandName() {
        int pageNumber = 0;
        int pageSize = 3;
        String sortBy = "brandName";
        String sortDirection = "asc";

        ArticleEntity articleEntity1 = new ArticleEntity(1L, "SmartX Pro 5G Smartphone", "Experience ultra-fast connectivity with the latest 5G-enabled smartphone, designed for superior performance.", 5,
                new BigDecimal("1000.50"),
                new BrandEntity(1L, null, null),
                List.of(new CategoryEntity(1L, null, null))
        );
        ArticleEntity articleEntity2 = new ArticleEntity(2L, "Organic Bamboo Bedding Set", "Sleep better with our luxurious bamboo bedding, naturally breathable and hypoallergenic.", 15,
                new BigDecimal("299.99"),
                new BrandEntity(2L, null, null),
                List.of(
                        new CategoryEntity(2L, null, null),
                        new CategoryEntity(3L, null, null)
                )
        );

        List<ArticleEntity> articleEntities = List.of(articleEntity1, articleEntity2);
        Page<ArticleEntity> page = new PageImpl<>(articleEntities, PageRequest.of(pageNumber, pageSize, Sort.by(Sort.Order.by(sortBy).with(Sort.Direction.fromString(sortDirection)))),2);

        Mockito.when(articleRepository.findAll(any(Pageable.class))).thenReturn(page);
        Mockito.when(articleEntityMapper.entityToDomain(articleEntity1)).thenReturn(new Article(1L, "SmartX Pro 5G Smartphone", "Experience ultra-fast connectivity with the latest 5G-enabled smartphone, designed for superior performance.", 5,
                new BigDecimal("1000.50"),
                new Brand(1L, null, null),
                List.of(new Category(1L, null, null))));
        Mockito.when(articleEntityMapper.entityToDomain(articleEntity2)).thenReturn(new Article(2L, "Organic Bamboo Bedding Set", "Sleep better with our luxurious bamboo bedding, naturally breathable and hypoallergenic.", 15,
                new BigDecimal("299.99"),
                new Brand(1L, null, null),
                List.of(
                        new Category(2L, null, null),
                        new Category(3L, null, null))));

        Pagination<Article> result = articleAdapter.listArticles(pageNumber, pageSize, sortBy, sortDirection);

        assertThat(result.getContent()).hasSize(2);
        assertThat(result.getPageNumber()).isZero();
        assertThat(result.getPageSize()).isEqualTo(3);
        assertEquals(1L, result.getContent().get(0).getId(), "The Article ID should be 1L");
        assertEquals(2L, result.getContent().get(1).getId(), "The Article ID should be 2L");

        verify(articleRepository, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @DisplayName("Validation that it return paginated articles 'desc' sorted by 'brandName'")
    void listArticlesDescSortedByBrandName() {
        int pageNumber = 0;
        int pageSize = 3;
        String sortBy = "brandName";
        String sortDirection = "desc";

        ArticleEntity articleEntity1 = new ArticleEntity(1L, "SmartX Pro 5G Smartphone", "Experience ultra-fast connectivity with the latest 5G-enabled smartphone, designed for superior performance.", 5,
                new BigDecimal("1000.50"),
                new BrandEntity(1L, null, null),
                List.of(new CategoryEntity(1L, null, null))
        );
        ArticleEntity articleEntity2 = new ArticleEntity(2L, "Organic Bamboo Bedding Set", "Sleep better with our luxurious bamboo bedding, naturally breathable and hypoallergenic.", 15,
                new BigDecimal("299.99"),
                new BrandEntity(2L, null, null),
                List.of(
                        new CategoryEntity(2L, null, null),
                        new CategoryEntity(3L, null, null)
                )
        );

        List<ArticleEntity> articleEntities = List.of(articleEntity2, articleEntity1);
        Page<ArticleEntity> page = new PageImpl<>(articleEntities, PageRequest.of(pageNumber, pageSize, Sort.by(Sort.Order.by(sortBy).with(Sort.Direction.fromString(sortDirection)))),2);

        Mockito.when(articleRepository.findAll(any(Pageable.class))).thenReturn(page);
        Mockito.when(articleEntityMapper.entityToDomain(articleEntity1)).thenReturn(new Article(1L, "SmartX Pro 5G Smartphone", "Experience ultra-fast connectivity with the latest 5G-enabled smartphone, designed for superior performance.", 5,
                new BigDecimal("1000.50"),
                new Brand(1L, null, null),
                List.of(new Category(1L, null, null))));
        Mockito.when(articleEntityMapper.entityToDomain(articleEntity2)).thenReturn(new Article(2L, "Organic Bamboo Bedding Set", "Sleep better with our luxurious bamboo bedding, naturally breathable and hypoallergenic.", 15,
                new BigDecimal("299.99"),
                new Brand(1L, null, null),
                List.of(
                        new Category(2L, null, null),
                        new Category(3L, null, null))));

        Pagination<Article> result = articleAdapter.listArticles(pageNumber, pageSize, sortBy, sortDirection);

        assertThat(result.getContent()).hasSize(2);
        assertThat(result.getPageNumber()).isZero();
        assertThat(result.getPageSize()).isEqualTo(3);
        assertEquals(2L, result.getContent().get(0).getId(), "The Article ID should be 2L");
        assertEquals(1L, result.getContent().get(1).getId(), "The Article ID should be 1L");

        verify(articleRepository, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @DisplayName("Validation that it return paginated articles 'asc' sorted by 'categoryName'")
    void listArticlesAscSortedByCategoryName() {
        int pageNumber = 0;
        int pageSize = 3;
        String sortBy = "categoryName";
        String sortDirection = "asc";

        ArticleEntity articleEntity1 = new ArticleEntity(1L, "SmartX Pro 5G Smartphone", "Experience ultra-fast connectivity with the latest 5G-enabled smartphone, designed for superior performance.", 5,
                new BigDecimal("1000.50"),
                new BrandEntity(1L, null, null),
                List.of(new CategoryEntity(1L, "Electronics", null))
        );
        ArticleEntity articleEntity2 = new ArticleEntity(2L, "Organic Bamboo Bedding Set", "Sleep better with our luxurious bamboo bedding, naturally breathable and hypoallergenic.", 15,
                new BigDecimal("299.99"),
                new BrandEntity(2L, null, null),
                List.of(
                        new CategoryEntity(2L, "Home & Kitchen", null),
                        new CategoryEntity(3L, "Fashion", null)
                )
        );

        List<ArticleEntity> articleEntities = List.of(articleEntity1, articleEntity2);
        Page<ArticleEntity> page = new PageImpl<>(articleEntities, PageRequest.of(pageNumber, pageSize, Sort.by(Sort.Order.by(sortBy).with(Sort.Direction.fromString(sortDirection)))),2);

        Mockito.when(articleRepository.findAll(any(Pageable.class))).thenReturn(page);
        Mockito.when(articleEntityMapper.entityToDomain(articleEntity1)).thenReturn(new Article(1L, "SmartX Pro 5G Smartphone", "Experience ultra-fast connectivity with the latest 5G-enabled smartphone, designed for superior performance.", 5,
                new BigDecimal("1000.50"),
                new Brand(1L, null, null),
                List.of(new Category(1L, "Electronics", null))));
        Mockito.when(articleEntityMapper.entityToDomain(articleEntity2)).thenReturn(new Article(2L, "Organic Bamboo Bedding Set", "Sleep better with our luxurious bamboo bedding, naturally breathable and hypoallergenic.", 15,
                new BigDecimal("299.99"),
                new Brand(1L, null, null),
                List.of(
                        new Category(2L, "Home & Kitchen", null),
                        new Category(3L, "Fashion", null))));

        Pagination<Article> result = articleAdapter.listArticles(pageNumber, pageSize, sortBy, sortDirection);

        assertThat(result.getContent()).hasSize(2);
        assertThat(result.getPageNumber()).isZero();
        assertThat(result.getPageSize()).isEqualTo(3);
        assertEquals(1L, result.getContent().get(0).getId(), "The Article ID should be 1L");
        assertEquals(2L, result.getContent().get(1).getId(), "The Article ID should be 2L");

        verify(articleRepository, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @DisplayName("Validation that it return paginated articles 'desc' sorted by 'categoryName'")
    void listArticlesDescSortedByCategoryName() {
        int pageNumber = 0;
        int pageSize = 3;
        String sortBy = "categoryName";
        String sortDirection = "desc";

        ArticleEntity articleEntity1 = new ArticleEntity(1L, "SmartX Pro 5G Smartphone", "Experience ultra-fast connectivity with the latest 5G-enabled smartphone, designed for superior performance.", 5,
                new BigDecimal("1000.50"),
                new BrandEntity(1L, null, null),
                List.of(new CategoryEntity(1L, "Electronics", null))
        );
        ArticleEntity articleEntity2 = new ArticleEntity(2L, "Organic Bamboo Bedding Set", "Sleep better with our luxurious bamboo bedding, naturally breathable and hypoallergenic.", 15,
                new BigDecimal("299.99"),
                new BrandEntity(2L, null, null),
                List.of(
                        new CategoryEntity(2L, "Home & Kitchen", null),
                        new CategoryEntity(3L, "Fashion", null)
                )
        );

        List<ArticleEntity> articleEntities = List.of(articleEntity2, articleEntity1);
        Page<ArticleEntity> page = new PageImpl<>(articleEntities, PageRequest.of(pageNumber, pageSize, Sort.by(Sort.Order.by(sortBy).with(Sort.Direction.fromString(sortDirection)))),2);

        Mockito.when(articleRepository.findAll(any(Pageable.class))).thenReturn(page);
        Mockito.when(articleEntityMapper.entityToDomain(articleEntity1)).thenReturn(new Article(1L, "SmartX Pro 5G Smartphone", "Experience ultra-fast connectivity with the latest 5G-enabled smartphone, designed for superior performance.", 5,
                new BigDecimal("1000.50"),
                new Brand(1L, null, null),
                List.of(new Category(1L, "Electronics", null))));
        Mockito.when(articleEntityMapper.entityToDomain(articleEntity2)).thenReturn(new Article(2L, "Organic Bamboo Bedding Set", "Sleep better with our luxurious bamboo bedding, naturally breathable and hypoallergenic.", 15,
                new BigDecimal("299.99"),
                new Brand(1L, null, null),
                List.of(
                        new Category(2L, "Home & Kitchen", null),
                        new Category(3L, "Fashion", null))));

        Pagination<Article> result = articleAdapter.listArticles(pageNumber, pageSize, sortBy, sortDirection);

        assertThat(result.getContent()).hasSize(2);
        assertThat(result.getPageNumber()).isZero();
        assertThat(result.getPageSize()).isEqualTo(3);
        assertEquals(2L, result.getContent().get(0).getId(), "The Article ID should be 2L");
        assertEquals(1L, result.getContent().get(1).getId(), "The Article ID should be 1L");

        verify(articleRepository, times(1)).findAll(any(Pageable.class));
    }
}