package com.bootcamp.microserviceStock.configuration;

import com.bootcamp.microserviceStock.adapters.driven.jpa.mysql.adapter.ArticleAdapter;
import com.bootcamp.microserviceStock.adapters.driven.jpa.mysql.adapter.BrandAdapter;
import com.bootcamp.microserviceStock.adapters.driven.jpa.mysql.adapter.CategoryAdapter;
import com.bootcamp.microserviceStock.adapters.driven.jpa.mysql.mapper.IArticleEntityMapper;
import com.bootcamp.microserviceStock.adapters.driven.jpa.mysql.mapper.IBrandEntityMapper;
import com.bootcamp.microserviceStock.adapters.driven.jpa.mysql.mapper.ICategoryEntityMapper;
import com.bootcamp.microserviceStock.adapters.driven.jpa.mysql.repository.IArticleRepository;
import com.bootcamp.microserviceStock.adapters.driven.jpa.mysql.repository.IBrandRepository;
import com.bootcamp.microserviceStock.adapters.driven.jpa.mysql.repository.ICategoryRepository;
import com.bootcamp.microserviceStock.domain.api.IArticleServicePort;
import com.bootcamp.microserviceStock.domain.api.IBrandServicePort;
import com.bootcamp.microserviceStock.domain.api.ICategoryServicePort;
import com.bootcamp.microserviceStock.domain.api.useCase.ArticleUseCase;
import com.bootcamp.microserviceStock.domain.api.useCase.BrandUseCase;
import com.bootcamp.microserviceStock.domain.api.useCase.CategoryUseCase;
import com.bootcamp.microserviceStock.domain.spi.IArticlePersistencePort;
import com.bootcamp.microserviceStock.domain.spi.IBrandPersistencePort;
import com.bootcamp.microserviceStock.domain.spi.ICategoryPersistencePort;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class BeanConfiguration {
    private final ICategoryRepository categoryRepository;
    private final ICategoryEntityMapper categoryEntityMapper;
    private final IBrandRepository brandRepository;
    private final IBrandEntityMapper brandEntityMapper;
    private final IArticleRepository articleRepository;
    private final IArticleEntityMapper articleEntityMapper;

    @Bean
    public ICategoryPersistencePort categoryPersistencePort() {
        return new CategoryAdapter(categoryRepository, categoryEntityMapper);
    }

    @Bean
    public ICategoryServicePort categoryServicePort() {
        return new CategoryUseCase(categoryPersistencePort());
    }

    @Bean
    public IBrandPersistencePort brandPersistencePort() {
        return new BrandAdapter(brandRepository, brandEntityMapper);
    }

    @Bean
    public IBrandServicePort brandServicePort() {
        return new BrandUseCase(brandPersistencePort());
    }

    @Bean
    public IArticlePersistencePort articlePersistencePort() {
        return new ArticleAdapter(articleRepository, articleEntityMapper);
    }

    @Bean
    public IArticleServicePort articleServicePort() {
        return new ArticleUseCase(articlePersistencePort(), categoryPersistencePort(), brandPersistencePort());
    }
}