package yuan.springframework.spring5webfluxrest.controllers;

import org.reactivestreams.Publisher;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import yuan.springframework.spring5webfluxrest.domain.Vendor;
import yuan.springframework.spring5webfluxrest.repositories.VendorRepository;

@RestController
@RequestMapping(VendorController.BASE_URL)
public class VendorController {
  private final VendorRepository vendorRepository;
  public static final String BASE_URL = "/api/v1/vendors";

  public VendorController(VendorRepository vendorRepository) {
    this.vendorRepository = vendorRepository;
  }

  @GetMapping
  @ResponseStatus(HttpStatus.OK)
  public Flux<Vendor> getAllVendors() {
    return vendorRepository.findAll();
  }

  @GetMapping("{id}")
  @ResponseStatus(HttpStatus.OK)
  public Mono<Vendor> getVendorById(@PathVariable String id) {
    return vendorRepository.findById(id);
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public Mono<Void> createVendor(@RequestBody Publisher<Vendor> vendorStream) {
    return vendorRepository.saveAll(vendorStream).then();
  }

  @PutMapping("{id}")
  @ResponseStatus(HttpStatus.OK)
  public Mono<Vendor> updateVendor(@PathVariable String id, @RequestBody Vendor vendor) {
    vendor.setId(id);
    return vendorRepository.save(vendor);
  }

  @PatchMapping("{id}")
  @ResponseStatus(HttpStatus.OK)
  public Mono<Vendor> patchVendor(@PathVariable String id, @RequestBody Vendor vendor) {
    Vendor foundVendor = vendorRepository.findById(id).block();

    if (vendor.getFirstname() != foundVendor.getFirstname()) {
      foundVendor.setFirstname(vendor.getFirstname());
    }

    if (vendor.getLastname() != foundVendor.getLastname()) {
      foundVendor.setLastname(vendor.getLastname());
    }

    return vendorRepository.save(foundVendor);
  }
}
