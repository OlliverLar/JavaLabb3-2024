package Service;

import entities.Category;
import entities.Product;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class Warehouse {
    private final List<Product> products = new ArrayList<>();

    public void addProduct(Product product) {
        if (product.name() == null || product.name().isBlank()) {
            throw new IllegalArgumentException("Product name cannot be empty");
        }
        products.add(product);
    }

    public void modifyProduct(String id, String newName, Category newCategory, int newRating) {
        products.stream()
                .filter(product -> product.id().equals(id))
                .findFirst()
                .ifPresent(product -> {
                    products.remove(product);
                    products.add(new Product(
                            product.id(),
                            newName,
                            newCategory,
                            newRating,
                            product.creationDate(),
                            LocalDate.now()
                    ));
                });
    }

    public List<Product> getAllProducts() {
        return Collections.unmodifiableList(products);
    }

    public Product getProductById(String id) {
        return products.stream()
                .filter(product -> product.id().equals(id))
                .findFirst()
                .orElse(null);
    }

    public List<Product> getProductsByCategorySorted(Category category) {
        return products.stream()
                .filter(product -> product.category() == category)
                .sorted((p1, p2) -> p2.name().compareTo(p1.name()))
                .collect(Collectors.toList());
    }

    public List<Product> getProductsCreatedAfter(LocalDate date) {
        return products.stream()
                .filter(product -> product.creationDate().isAfter(date))
                .collect(Collectors.toList());
    }

    public List<Product> getProductsModifiedSinceCreation() {
        return products.stream()
                .filter(product -> !product.creationDate().equals(product.lastModifiedDate()))
                .collect(Collectors.toList());
    }
}
