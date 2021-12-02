package yuan.springframework.spring5webfluxrest.controllers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import yuan.springframework.spring5webfluxrest.domain.Category;
import yuan.springframework.spring5webfluxrest.repositories.CategoryRepository;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static reactor.core.publisher.Mono.when;

class CategoryControllerTest {

  @Mock
  CategoryRepository categoryRepository;

  CategoryController categoryController;

  WebTestClient webTestClient;

  Category category1;

  Category category2;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
    categoryController = new CategoryController(categoryRepository);

    webTestClient = WebTestClient.bindToController(categoryController).build();

    category1 = Category.builder().description("category1").build();
    category2 = Category.builder().description("category2").build();
  }

  @Test
  void testGetAllCategories() {

    // use Behavior Driven
    // these two ways are the same
    //when(categoryRepository.findAll()).thenReturn(Flux.just(category1, category2));
    given(categoryRepository.findAll()).willReturn(Flux.just(category1, category2));

    webTestClient.get().uri(CategoryController.BASE_URL + "/")
            .exchange()
            .expectBodyList(Category.class)
            .hasSize(2);

  }

  @Test
  void testGetCategoryById() {
    given(categoryRepository.findById(anyString())).willReturn(Mono.just(category2));

    webTestClient.get().uri(CategoryController.BASE_URL + "/someid")
            .exchange()
            .expectBody(Category.class);
  }
}