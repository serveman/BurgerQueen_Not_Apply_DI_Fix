package app.product;

import app.product.subproduct.*;

public class ProductRepository {
    final public static int NEW_PRODUCT_ID = -1;
    Product[] products = new Product[] {
            new Hamburger(1, "새우버거", 3500, 500, 4500),
            new Hamburger(2, "치킨버거", 4000, 600, 5000),
            new Side(3, "감자튀김", 1000, 300),
            new Side(4, "어니언링", 1000, 300),
            new Drink(5, "코카콜라", 1000, 200),
            new Drink(6, "제로콜라", 1000, 0),
    };

    public Product[] getAllProducts() {
        return products;
    }

    public Product findById(int id) {
        for (Product p : products) {
            if(p.getId() == id)
                return p;
        }
        return null;
    }
}
