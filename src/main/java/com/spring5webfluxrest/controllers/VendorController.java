package com.spring5webfluxrest.controllers;

import com.spring5webfluxrest.domain.Vendor;
import com.spring5webfluxrest.repositories.VendorRepository;
import org.reactivestreams.Publisher;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
public class VendorController {
    private final VendorRepository vendorRepository;


    public VendorController(VendorRepository vendorRepository) {
        this.vendorRepository = vendorRepository;
    }

    @GetMapping("api/v1/vendors")
    public Flux<Vendor> listVendors() {
        return vendorRepository.findAll();
    }

    @GetMapping("api/v1/vendors/{id}")
    public Mono<Vendor> getVendorById(@PathVariable String id) {
        return vendorRepository.findById(id);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("api/v1/vendors/")
    public Mono<Void> createVendors(@RequestBody Publisher<Vendor> vendorPublisher) {
        return vendorRepository.saveAll(vendorPublisher).then();
    }

    @ResponseStatus(HttpStatus.OK)
    @PutMapping("api/v1/vendors/{id}")
    public Mono<Vendor> updateVendor(@RequestBody Vendor vendor, @PathVariable String id) {
        vendor.setId(id);
        return vendorRepository.save(vendor);
    }

    @PatchMapping("api/v1/vendors/{id}")
    public Mono<Vendor> patchVendor(@RequestBody Vendor vendor, @PathVariable String id) {

        Vendor foundVendor = vendorRepository.findById(id).block();
        if (foundVendor != null) {
            if (!foundVendor.getFirstName().equals(vendor.getFirstName())) {
                foundVendor.setFirstName(vendor.getFirstName());
            }
            if (!foundVendor.getLastName().equals(vendor.getLastName())) {
                foundVendor.setLastName(vendor.getLastName());

            }
            return vendorRepository.save(foundVendor);
        }
        return Mono.just(foundVendor);
    }

}
