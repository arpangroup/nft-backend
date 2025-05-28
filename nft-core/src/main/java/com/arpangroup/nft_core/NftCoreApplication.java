package com.arpangroup.nft_core;

import com.arpangroup.product_service.entity.Category;
import com.arpangroup.product_service.entity.Product;
import com.arpangroup.product_service.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.math.BigDecimal;
import java.util.List;

@SpringBootApplication(scanBasePackages = {
		"com.arpangroup.nft_core",
		"com.arpangroup.nft_common",
		"com.arpangroup.product_service",
		"com.arpangroup.user_service",
		"com.arpangroup.referral_service"
})
public class NftCoreApplication implements CommandLineRunner {
	@Autowired
	public CategoryRepository categoryRepository;

	public static void main(String[] args) {
		SpringApplication.run(NftCoreApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		initProducts();
	}

	private void initProducts() {
		Category arts = new Category("Arts");
		Category sports = new Category("Sports");

		List<Product> products = List.of(
				new Product("White Line Grafiti", BigDecimal.valueOf(100)),
				new Product("Abstract Triangle", BigDecimal.valueOf(100)),
				new Product("Lake Landscape", BigDecimal.valueOf(100)),
				new Product("Blue Red Art", BigDecimal.valueOf(100))
		);

		products.forEach(product -> {
			product.setCategory(arts);
		});

		arts.setProducts(products); // or arts.addProducts(products)

		categoryRepository.saveAll(List.of(arts, sports));
	}
}
