package Service;

import entities.Category;
import entities.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import Service.Warehouse;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class WarehouseTest {
    private Warehouse warehouse;

    @BeforeEach
    void setUp() {
        warehouse = new Warehouse();

        warehouse.addProduct(new Product("1", "XboxOne", Category.ELECTRONICS, 7, LocalDate.now().minusDays(3), LocalDate.now().minusDays(3)));
        warehouse.addProduct(new Product("2", "Hammer", Category.TOOLS, 8, LocalDate.now().minusDays(3), LocalDate.now()));
        warehouse.addProduct(new Product("3", "T-shirt", Category.CLOTHING, 3,LocalDate.now().minusDays(2), LocalDate.now().minusDays(2)));
        warehouse.addProduct(new Product("4", "PlayStation 4", Category.ELECTRONICS, 9, LocalDate.now().minusDays(3), LocalDate.now().minusDays(3)));
        warehouse.addProduct(new Product("5", "Apple", Category.FOOD, 6, LocalDate.now().minusDays(5), LocalDate.now().minusDays(4)));
        warehouse.addProduct(new Product("6", "Computer", Category.ELECTRONICS, 7, LocalDate.now().minusDays(2), LocalDate.now().minusDays(2)));

    }

    @Test
    void testAddProduct() {
        Product product = new Product("7", "Laptop", Category.ELECTRONICS, 9, LocalDate.now(), LocalDate.now());
        warehouse.addProduct(product);

        assertEquals(7, warehouse.getAllProducts().size());
        assertThrows(IllegalArgumentException.class, () -> warehouse.addProduct(new Product("2", "", Category.GAMES, 8, LocalDate.now(), LocalDate.now())));
    }

    @Test
    void testModifyProduct() {
        Product product = new Product("7", "Laptop", Category.ELECTRONICS, 9, LocalDate.now(), LocalDate.now());
        warehouse.addProduct(product);

        warehouse.modifyProduct("7", "Gaming Laptop", Category.GAMES, 10);

        Product modifiedProduct = warehouse.getProductById("7");
        assertEquals("Gaming Laptop", modifiedProduct.name());
        assertEquals(Category.GAMES, modifiedProduct.category());
        assertEquals(10, modifiedProduct.rating());
    }

    @Test
    void testGetProductsByCategorySorted() {

        List<Product> sortedProducts = warehouse.getProductsByCategorySorted(Category.ELECTRONICS);

        assertEquals(3, sortedProducts.size());

        assertEquals(3, warehouse.getProductsByCategorySorted(Category.ELECTRONICS).size());
        assertEquals(1, warehouse.getProductsByCategorySorted(Category.TOOLS).size());
        assertEquals(1, warehouse.getProductsByCategorySorted(Category.CLOTHING).size());
        assertEquals(1, warehouse.getProductsByCategorySorted(Category.FOOD).size());
    }

    @Test
    void testGetProductsCreatedAfter() {
        List<Product> products = warehouse.getProductsCreatedAfter(LocalDate.now().minusDays(3));

        assertEquals(2, products.size());

        assertEquals("T-shirt", products.get(0).name());
        assertEquals("Computer", products.get(1).name());

    }

    @Test
    void testGetProductsModifiedSinceCreation() {
        List<Product> modifiedProducts = warehouse.getProductsModifiedSinceCreation();

        assertEquals(2, modifiedProducts.size());

        assertTrue(modifiedProducts.stream().anyMatch(p -> p.name().equals("Hammer")));
        assertTrue(modifiedProducts.stream().anyMatch(p -> p.name().equals("Apple")));

        assertFalse(modifiedProducts.stream().anyMatch(p -> p.name().equals("XboxOne")));
        assertFalse(modifiedProducts.stream().anyMatch(p -> p.name().equals("PlayStation 4")));
        assertFalse(modifiedProducts.stream().anyMatch(p -> p.name().equals("T-shirt")));
        assertFalse(modifiedProducts.stream().anyMatch(p -> p.name().equals("Computer")));
    }
}
