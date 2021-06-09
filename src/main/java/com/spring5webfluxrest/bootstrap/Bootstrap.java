package com.spring5webfluxrest.bootstrap;

import com.spring5webfluxrest.domain.Category;
import com.spring5webfluxrest.domain.Vendor;
import com.spring5webfluxrest.repositories.CategoryRepository;
import com.spring5webfluxrest.repositories.VendorRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class Bootstrap implements CommandLineRunner {
    private final CategoryRepository categoryRepository;
    private final VendorRepository vendorRepository;

    public Bootstrap(CategoryRepository categoryRepository, VendorRepository vendorRepository) {
        this.categoryRepository = categoryRepository;
        this.vendorRepository = vendorRepository;
    }

    public void loadData() {
        if (categoryRepository.count().block() == 0) {
            loadCategories();
            System.out.println("######## LOADING CATEGORIES");
        }
        if (vendorRepository.count().block() == 0) {
            loadVendors();
        }

    }

    private void loadCategories() {
        Category category1 = new Category();
        // category1.setId("1");
        category1.setDescription("Fruits");

        Category category2 = new Category();
        //  category2.setId("2");
        category2.setDescription("Nuts");

        Category category3 = new Category();
        //  category3.setId("3");
        category3.setDescription("Breads");

        Category category4 = new Category();
        //  category4.setId("4");
        category3.setDescription("Eggs");

        categoryRepository.save(category1).block();
        categoryRepository.save(category2).block();
        categoryRepository.save(category3).block();
        categoryRepository.save(category4).block();

        System.out.println("Loaded categories " + categoryRepository.count().block());

    }

    private void loadVendors() {
        Vendor vendor = new Vendor();
        //   vendor.setId("1");
        vendor.setFirstName("Joe");
        vendor.setLastName("Buck");

        Vendor vendor1 = new Vendor();
        //  vendor1.setId("2");
        vendor1.setFirstName("Michael");
        vendor1.setLastName("Weston");

        Vendor vendor2 = new Vendor();
        //   vendor2.setId("3");
        vendor2.setFirstName("Bill");
        vendor2.setLastName("Nershi");

        Vendor vendor3 = new Vendor();
        //   vendor3.setId("4");
        vendor3.setFirstName("Jimmy");
        vendor3.setLastName("Buffet");

        vendorRepository.save(vendor).block();
        vendorRepository.save(vendor1).block();
        vendorRepository.save(vendor2).block();
        vendorRepository.save(vendor3).block();
        System.out.println("loaded vendors " + vendorRepository.count().block());
    }

    @Override
    public void run(String... args) throws Exception {
        loadData();
    }
}
