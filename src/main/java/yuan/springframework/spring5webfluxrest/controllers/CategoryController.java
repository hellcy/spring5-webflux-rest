package yuan.springframework.spring5webfluxrest.controllers;

import org.reactivestreams.Publisher;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
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

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  Mono<Void> createCategory(@RequestBody Publisher<Category> categoryPublisher) {
    return categoryRepository.saveAll(categoryPublisher).then();
  }

  @PutMapping("{id}")
  @ResponseStatus(HttpStatus.OK)
  Mono<Category> updateCategory(@PathVariable String id, @RequestBody Category category) {
    category.setId(id);
    return categoryRepository.save(category);
  }

  @PatchMapping("{id}")
  @ResponseStatus(HttpStatus.OK)
  Mono<Category> patchCategory(@PathVariable String id, @RequestBody Category category) {
    Category foundCategory = categoryRepository.findById(id).block();

    if (foundCategory.getDescription() != category.getDescription()) {
      foundCategory.setDescription(category.getDescription());
      return categoryRepository.save(foundCategory);
    }

    return Mono.just(foundCategory);
  }
}
