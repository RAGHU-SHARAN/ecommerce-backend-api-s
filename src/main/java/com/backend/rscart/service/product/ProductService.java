package com.backend.rscart.service.product;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.backend.rscart.dto.ImageDto;
import com.backend.rscart.dto.ProductDto;
import com.backend.rscart.exceptions.AlreadyExistsException;
import com.backend.rscart.exceptions.ResourceNotFoundException;
import com.backend.rscart.model.CartItem;
import com.backend.rscart.model.Category;
import com.backend.rscart.model.Image;
import com.backend.rscart.model.OrderItem;
import com.backend.rscart.model.Product;
import com.backend.rscart.repository.CartItemRepository;
import com.backend.rscart.repository.CategoryRepository;
import com.backend.rscart.repository.ImageRepository;
import com.backend.rscart.repository.OrderItemRepository;
import com.backend.rscart.repository.OrderRepository;
import com.backend.rscart.repository.ProductRepository;
import com.backend.rscart.request.AddProductRequest;
import com.backend.rscart.request.ProductUpdateRequest;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductService implements IProductService {
	private final ProductRepository productRepository;
	private final CategoryRepository categoryRepository;
	private final ModelMapper modelMapper;
	private final ImageRepository imageRepository;
	private final CartItemRepository cartItemRepository;
	private final OrderRepository orderRepository;
	private final OrderItemRepository orderItemRepository;

	@Override
	public Product addProduct(AddProductRequest request) {
		if (productExists(request.getName(), request.getBrand())) {
			throw new AlreadyExistsException(request.getBrand() + " " + request.getName()
					+ " already exists, you may update this product instead!");
		}
		Category category = Optional.ofNullable(categoryRepository.findByName(request.getCategory().getName()))
				.orElseGet(() -> {
					Category newCategory = new Category(request.getCategory().getName());
					return categoryRepository.save(newCategory);
				});
		request.setCategory(category);
		return productRepository.save(createProduct(request, category));
	}

	private boolean productExists(String name, String brand) {
		return productRepository.existsByNameAndBrand(name, brand);
	}

	private Product createProduct(AddProductRequest request, Category category) {
		return new Product(request.getName(), request.getBrand(), request.getPrice(), request.getInventory(),
				request.getDescription(), category);
	}

	@Override
	public Product getProductById(Long id) {
		return productRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Product not found!"));
	}

	@Override
	public void deleteProductById(Long id) {
		List<CartItem> cartItems = cartItemRepository.findByProductId(id);
		List<OrderItem> orderItems = orderItemRepository.findByProductId(id);
		productRepository.findById(id).ifPresentOrElse(product -> {
			// Functional approach for category removal
			Optional.ofNullable(product.getCategory()).ifPresent(category -> category.getProducts().remove(product));
			product.setCategory(null);

			// Functional approach for updating cart items
			cartItems.stream().peek(cartItem -> cartItem.setProduct(null)).peek(CartItem::setTotalPrice)
					.forEach(cartItemRepository::save);

			// Functional approach for updating order items
			orderItems.stream().peek(orderItem -> orderItem.setProduct(null)).forEach(orderItemRepository::save);

			productRepository.delete(product);
		}, () -> {
			throw new EntityNotFoundException("Product not found!");
		});
	}

	@Override
	public Product updateProduct(ProductUpdateRequest request, Long productId) {
		return productRepository.findById(productId)
				.map(existingProduct -> updateExistingProduct(existingProduct, request)).map(productRepository::save)
				.orElseThrow(() -> new ResourceNotFoundException("Product not found!"));
	}

	private Product updateExistingProduct(Product existingProduct, ProductUpdateRequest request) {
		existingProduct.setName(request.getName());
		existingProduct.setBrand(request.getBrand());
		existingProduct.setPrice(request.getPrice());
		existingProduct.setInventory(request.getInventory());
		existingProduct.setDescription(request.getDescription());

		Category category = categoryRepository.findByName(request.getCategory().getName());
		existingProduct.setCategory(category);
		return existingProduct;

	}

	@Override
	public List<Product> getAllProducts() {
		return productRepository.findAll();
	}

	@Override
	public List<Product> getProductsByCategory(String category) {
		return productRepository.findByCategoryName(category);
	}

	@Override
	public List<Product> getProductsByBrand(String brand) {
		return productRepository.findByBrand(brand);
	}

	@Override
	public List<Product> getProductsByCategoryAndBrand(String category, String brand) {
		return productRepository.findByCategoryNameAndBrand(category, brand);
	}

	@Override
	public List<Product> getProductsByName(String name) {
		return productRepository.findByName(name);
	}

	@Override
	public List<Product> getProductsByBrandAndName(String brand, String name) {
		return productRepository.findByBrandAndName(brand, name);
	}

	@Override
	public Long countProductsByBrandAndName(String brand, String name) {
		return productRepository.countByBrandAndName(brand, name);
	}

	@Override
	public List<ProductDto> getConvertedProducts(List<Product> products) {
		return products.stream().map(this::convertToDto).toList();
	}

	@Override
	public ProductDto convertToDto(Product product) {
		ProductDto productDto = modelMapper.map(product, ProductDto.class);
		List<Image> images = imageRepository.findByProductId(product.getId());
		List<ImageDto> imageDtos = images.stream().map(image -> modelMapper.map(image, ImageDto.class)).toList();
		productDto.setImages(imageDtos);
		return productDto;
	}

	@Override
	public List<Product> findDistinctProductsByName() {
		List<Product> products = productRepository.findAll();
		Map<String, Product> distinctProductsMap = products.stream()
				.collect(Collectors.toMap(Product::getName, product -> product, (existing, replacement) -> existing));
		return new ArrayList<>(distinctProductsMap.values());
	}

	@Override
	public List<String> getAllDistinctBrands() {
		return productRepository.findAll().stream().map(Product::getBrand).distinct().collect(Collectors.toList());
	}

}
