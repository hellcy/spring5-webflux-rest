package yuan.springframework.spring5webfluxrest.controllers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.reactivestreams.Publisher;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import yuan.springframework.spring5webfluxrest.domain.Vendor;
import yuan.springframework.spring5webfluxrest.repositories.VendorRepository;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;

class VendorControllerTest {
  @Mock
  VendorRepository vendorRepository;

  VendorController vendorController;

  WebTestClient webTestClient;

  Vendor vendor1;
  Vendor vendor2;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);

    vendorController = new VendorController(vendorRepository);

    webTestClient = WebTestClient.bindToController(vendorController).build();

    vendor1 = Vendor.builder().firstname("Yuan").lastname("Cheng").build();
    vendor2 = Vendor.builder().firstname("Nan").lastname("Yang").build();
  }

  @Test
  void testGetAllVendors() {
    given(vendorRepository.findAll()).willReturn(Flux.just(vendor1, vendor2));

    webTestClient.get().uri(VendorController.BASE_URL + "/")
            .exchange()
            .expectBodyList(Vendor.class)
            .hasSize(2);
  }

  @Test
  void testGetVendorById() {
    given(vendorRepository.findById(anyString())).willReturn(Mono.just(vendor2));

    webTestClient.get().uri(VendorController.BASE_URL + "/someid")
            .exchange()
            .expectBody(Vendor.class);
  }

  @Test
  void testCreateVendor() {
    given(vendorRepository.saveAll(any(Publisher.class))).willReturn(Flux.just(vendor1));

    webTestClient.post().uri(VendorController.BASE_URL)
            .body(Mono.just(vendor1), Vendor.class)
            .exchange()
            .expectStatus()
            .isCreated();
  }

  @Test
  void testUpdateVendor() {
    given(vendorRepository.save(any(Vendor.class))).willReturn(Mono.just(vendor1));

    webTestClient.put().uri(VendorController.BASE_URL + "/someid")
            .body(Mono.just(vendor1), Vendor.class)
            .exchange()
            .expectStatus()
            .isOk();
  }
}