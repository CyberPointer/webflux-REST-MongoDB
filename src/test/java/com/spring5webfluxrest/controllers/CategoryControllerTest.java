package com.spring5webfluxrest.controllers;

import com.spring5webfluxrest.domain.Category;
import com.spring5webfluxrest.repositories.CategoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.reactivestreams.Publisher;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

class CategoryControllerTest {
    WebTestClient webTestClient;

    CategoryRepository categoryRepository;
    CategoryController categoryController;

    @BeforeEach
    public void setUp() {
        categoryRepository = Mockito.mock(CategoryRepository.class);
        categoryController = new CategoryController(categoryRepository);
        webTestClient = WebTestClient.bindToController(categoryController).build();
    }

    @Test
    void categoryList() {
        Category category1 = new Category();
        category1.setDescription("cat1");

        Category category2 = new Category();
        category2.setDescription("cat2");

        given(categoryRepository.findAll()).willReturn(Flux.just(category1, category2));

        webTestClient.get()
                .uri("/api/v1/categories/")
                .exchange()
                .expectBodyList(Category.class)
                .hasSize(2);
    }


    void getCategory() {
        Category category1 = new Category();
        category1.setDescription("cat1");

        given(categoryRepository.findById(anyString())).willReturn(Mono.just(category1));

        webTestClient.get()
                .uri("/api/v1/categories/1")
                .exchange()
                .expectBody(Category.class);

    }

    @Test
    public void testCreateCategory() {
        Category category1 = new Category();
        category1.setDescription("cat1");

        given(categoryRepository.saveAll(any(Publisher.class)))
                .willReturn(Flux.just(new Category()));

        Mono<Category> catToSaveMono = Mono.just(category1);

        webTestClient.post()
                .uri("/api/v1/categories/")
                .body(catToSaveMono, Category.class)
                .exchange()
                .expectStatus()
                .isCreated();
    }

    @Test
    public void testUpdate() {
        Category category1 = new Category();
        category1.setDescription("cat1");

        given(categoryRepository.save(any(Category.class))).willReturn(Mono.just(category1));

        Mono<Category> catToSaveMono = Mono.just(category1);

        webTestClient
                .put()
                .uri("/api/v1/categories/1")
                .body(catToSaveMono, Category.class)
                .exchange()
                .expectStatus()
                .isOk();
    }

    @Test
    public void testPatchWithChanges() {
        Category category1 = new Category();
        category1.setDescription("cat1");

        Category category2 = new Category();
        category2.setDescription("cat2");


        given(categoryRepository.findById(anyString())).willReturn(Mono.just(category2));
        given(categoryRepository.save(any(Category.class))).willReturn(Mono.just(category1));

        Mono<Category> catToSaveMono = Mono.just(category1);

        webTestClient
                .patch()
                .uri("/api/v1/categories/1")
                .body(catToSaveMono, Category.class)
                .exchange()
                .expectStatus()
                .isOk();

        verify(categoryRepository).save(any());
    }


}