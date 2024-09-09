package com.bootcamp.microserviceStock.adapters.driven.jpa.mysql.adapter;

import com.bootcamp.microserviceStock.adapters.driven.jpa.mysql.entity.ArticleEntity;
import com.bootcamp.microserviceStock.adapters.driven.jpa.mysql.mapper.ArticlePageMapper;
import com.bootcamp.microserviceStock.adapters.driven.jpa.mysql.mapper.IArticleEntityMapper;
import com.bootcamp.microserviceStock.adapters.driven.jpa.mysql.repository.IArticleRepository;
import com.bootcamp.microserviceStock.domain.model.Article;
import com.bootcamp.microserviceStock.domain.spi.IArticlePersistencePort;
import com.bootcamp.microserviceStock.domain.util.DomainConstants;
import com.bootcamp.microserviceStock.domain.util.Pagination;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;

import java.util.List;

@RequiredArgsConstructor
public class ArticleAdapter implements IArticlePersistencePort {
    private final IArticleRepository articleRepository;
    private final IArticleEntityMapper articleEntityMapper;

    @Override
    public void createArticle(Article article) {
        articleRepository.save(articleEntityMapper.articleToEntity(article));
    }

    @Override
    public boolean alreadyExistsByName(String name) {
        return articleRepository.findByName(name).isPresent();
    }

    @Override
    public Pagination<Article> listArticles(Integer pageNumber, Integer pageSize, String sortBy, String sortDirection) {
        Page<ArticleEntity> page;

        if ("categoryName".equalsIgnoreCase(sortBy)) {
            Page<ArticleEntity> allArticles = articleRepository.findAll(PageRequest.of(pageNumber, pageSize));
            List<ArticleEntity> sortedArticles = allArticles.getContent().stream()
                    .sorted((article1, article2) -> {
                        String firstCategoryName1 = article1.getCategoryEntityList().isEmpty() ? "" : article1.getCategoryEntityList().get(0).getName();
                        String firstCategoryName2 = article2.getCategoryEntityList().isEmpty() ? "" : article2.getCategoryEntityList().get(0).getName();
                        if (DomainConstants.SORT_DIRECTION_ASC.equalsIgnoreCase(sortDirection)) {
                            return firstCategoryName1.compareToIgnoreCase(firstCategoryName2);
                        } else {
                            return firstCategoryName2.compareToIgnoreCase(firstCategoryName1);
                        }
                    }).toList();
            Page<ArticleEntity> sortedPage = new PageImpl<>(sortedArticles, PageRequest.of(pageNumber, pageSize), allArticles.getTotalElements());
            ArticlePageMapper articlePageMapper = new ArticlePageMapper(articleEntityMapper);
            return articlePageMapper.pageToPagination(sortedPage);
        } else {
            Sort sort = Sort.by(Sort.Order.by(sortBy).with(Sort.Direction.fromString(sortDirection)));
            Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);

            page = articleRepository.findAll(pageable);
            ArticlePageMapper articlePageMapper = new ArticlePageMapper(articleEntityMapper);
            return articlePageMapper.pageToPagination(page);
        }
    }
}