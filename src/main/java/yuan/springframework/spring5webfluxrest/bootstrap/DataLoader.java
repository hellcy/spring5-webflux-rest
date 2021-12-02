package yuan.springframework.spring5webfluxrest.bootstrap;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import yuan.springframework.spring5webfluxrest.domain.Category;
import yuan.springframework.spring5webfluxrest.domain.Vendor;
import yuan.springframework.spring5webfluxrest.repositories.CategoryRepository;
import yuan.springframework.spring5webfluxrest.repositories.VendorRepository;

@Component
public class DataLoader implements CommandLineRunner {

  private final CategoryRepository categoryRepository;
  private final VendorRepository vendorRepository;

  public DataLoader(CategoryRepository categoryRepository, VendorRepository vendorRepository) {
    this.categoryRepository = categoryRepository;
    this.vendorRepository = vendorRepository;
  }

  @Override
  public void run(String... args) throws Exception {
    if (categoryRepository.count().block() == 0) {
      loadCategory();
    }

    if (vendorRepository.count().block() == 0) {
      loadVendor();
    }
  }

  private void loadCategory() {
    System.out.println("##### LOADING DATA ON BOOTSTRAP #####");

    categoryRepository.save(Category.builder().description("Fruits").build()).block();
    categoryRepository.save(Category.builder().description("Nuts").build()).block();
    categoryRepository.save(Category.builder().description("Breads").build()).block();
    categoryRepository.save(Category.builder().description("Meats").build()).block();
    categoryRepository.save(Category.builder().description("Eggs").build()).block();

    System.out.println("Loaded categories: " + categoryRepository.count().block());
  }

  private void loadVendor() {
    vendorRepository.save(Vendor.builder().firstname("Yuan").lastname("Cheng").build()).block();

    vendorRepository.save(Vendor.builder().firstname("Nan").lastname("Yang").build()).block();

    vendorRepository.save(Vendor.builder().firstname("Meixi").lastname("Zhu").build()).block();

    vendorRepository.save(Vendor.builder().firstname("Anyu").lastname("Zhang").build()).block();

    vendorRepository.save(Vendor.builder().firstname("Jie").lastname("Han").build()).block();

    System.out.println("Loaded vendors: " + vendorRepository.count().block());
  }
}
