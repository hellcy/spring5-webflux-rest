package yuan.springframework.spring5webfluxrest.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import yuan.springframework.spring5webfluxrest.domain.Category;
import yuan.springframework.spring5webfluxrest.repositories.CategoryRepository;

@RestController
@RequestMapping(CategoryController.BASE_URL)
public class CategoryController {
  private final CategoryRepository categoryRepository;
  public static final String BASE_URL = "/api/v1/categories";

  public CategoryController(CategoryRepository categoryRepository) {
    this.categoryRepository = categoryRepository;
  }

  @GetMapping
  Flux<Category> getAllCategories() {
    return categoryRepository.findAll();
  }

  @GetMapping("{id}")
  Mono<Category> getCategoryById(@PathVariable String id) {
    return categoryRepository.findById(id);
  }
}
