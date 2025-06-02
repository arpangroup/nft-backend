package com.arpangroup.nft_core;

import com.arpangroup.product_catalog.entity.Category;
import com.arpangroup.product_catalog.entity.Product;
import com.arpangroup.product_catalog.repository.CategoryRepository;
import com.arpangroup.referral_service.hierarchy.UserHierarchy;
import com.arpangroup.referral_service.hierarchy.UserHierarchyRepository;
import com.arpangroup.user_service.entity.User;
import com.arpangroup.user_service.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

import java.math.BigDecimal;
import java.util.List;

@SpringBootApplication(scanBasePackages = {
		"com.arpangroup.nft_core",
		"com.arpangroup.nft_common",
		"com.arpangroup.product_catalog",
		"com.arpangroup.user_service",
		"com.arpangroup.referral_service"
})
@EnableAspectJAutoProxy
public class NftCoreApplication implements CommandLineRunner {
	@Autowired UserRepository userRepository;
	@Autowired UserHierarchyRepository userHierarchyRepository;
	@Autowired CategoryRepository categoryRepository;

	public static void main(String[] args) {
		SpringApplication.run(NftCoreApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		initUsers(false);
		initProducts();
	}

	private void initUsers(boolean onlyRootUser) {
		if (onlyRootUser) {
			User rootUser = new User("U1", 3, BigDecimal.ZERO);
			userRepository.save(rootUser);
			return;
		}

		userRepository.saveAll(List.of(
				new User("U1", 3, BigDecimal.ZERO),
				new User("U2", 2, BigDecimal.ZERO),
				new User("U3", 2, BigDecimal.ZERO),
				new User("U4", 1, BigDecimal.ZERO)
		));

		userHierarchyRepository.saveAll(List.of(
			new UserHierarchy(1L, 2L, 1),
			new UserHierarchy(1L, 3L, 2),
			new UserHierarchy(1L, 4L, 3),
			new UserHierarchy(2L, 3L, 1),
			new UserHierarchy(2L, 4L, 2),
			new UserHierarchy(3L, 4L, 1)
		));





	}

	private void initProducts() {
		Category arts = new Category("Arts");
		Category sports = new Category("Sports");

		List<Product> products = List.of(
				new Product("White Line Grafiti", BigDecimal.valueOf(100)),
				new Product("Abstract Triangle", BigDecimal.valueOf(200)),
				new Product("Lake Landscape", BigDecimal.valueOf(300)),
				new Product("Blue Red Art", BigDecimal.valueOf(400))
		);

		products.forEach(product -> {
			product.setCategory(arts);
		});

		arts.setProducts(products); // or arts.addProducts(products)

		categoryRepository.saveAll(List.of(arts, sports));
	}
}
