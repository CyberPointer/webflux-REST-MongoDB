package com.spring5webfluxrest.controllers;

import com.spring5webfluxrest.domain.Vendor;
import com.spring5webfluxrest.repositories.VendorRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;

class VendorControllerTest {
    VendorRepository vendorRepository;
    VendorController vendorController;
    WebTestClient webTestClient;


    @BeforeEach
    void setUp() {
        vendorRepository = Mockito.mock(VendorRepository.class);
        vendorController = new VendorController(vendorRepository);
        webTestClient = WebTestClient.bindToController(vendorController).build();
    }

    @Test
    void getAllVendors() {
        Vendor vendor1 = new Vendor();
        vendor1.setFirstName("bob");
        vendor1.setLastName("jordan");

        Vendor vendor2 = new Vendor();
        vendor2.setFirstName("mike");
        vendor2.setLastName("funk");

        given(vendorRepository.findAll()).willReturn(Flux.just(vendor1, vendor2));

        webTestClient.get()
                .uri("/api/v1/vendors/")
                .exchange()
                .expectBodyList(Vendor.class)
                .hasSize(2);
    }

    @Test
    void getVendorById() {
        Vendor vendor2 = new Vendor();
        vendor2.setFirstName("mike");

        given(vendorRepository.findById(anyString())).willReturn(Mono.just(vendor2));

        webTestClient.get()
                .uri("api/v1/vendors/1")
                .exchange()
                .expectBody(Vendor.class);

    }
}