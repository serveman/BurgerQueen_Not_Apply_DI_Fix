package app.play;

import app.product.*;
import app.product.subproduct.*;

import java.util.Arrays;
import java.util.Scanner;

import static app.product.ProductRepository.NEW_PRODUCT_ID;

public class Cart {
    private Scanner scan = new Scanner(System.in);
    private Product[] items = new Product[0];
    private ProductRepository productRepository;
    private Menu menu;

    public Cart(ProductRepository productRepository, Menu menu) {
        this.productRepository = productRepository;
        this.menu = menu;
    }


    public void printCart() {
        System.out.println("๐ ์ฅ๋ฐ๊ตฌ๋");
        System.out.println("-".repeat(60));

        printCartItemDetails();

        int totalPrice = calculateTotalPrice();

        System.out.println("-".repeat(60));
        System.out.printf("ํฉ๊ณ : %d์\n", totalPrice);

        System.out.println("์ด์ ์ผ๋ก ๋์๊ฐ๋ ค๋ฉด ์ํฐ๋ฅผ ๋๋ฅด์ธ์. ");
        scan.nextLine();
    }

    protected void printCartItemDetails() {
        for(Product item: items) {
            if(item instanceof Hamburger) {
                System.out.printf("  %-8s %6d์ (๋จํ)\n",
                        item.getName(),
                        item.getPrice()
                );
            }
            else if(item instanceof Side) {
                System.out.printf("  %-8s %6d์ (์ผ์ฒฉ %d๊ฐ)\n",
                        item.getName(),
                        item.getPrice(),
                        ((Side) item).getKetchup()
                );
            }
            else if(item instanceof Drink) {
                System.out.printf("  %-8s %6d์ (๋นจ๋ %s)\n",
                        item.getName(),
                        item.getPrice(),
                        ((Drink) item).hasStraw() ? "์์" : "์์"
                );
            }
            else {
                BurgerSet burgerSet = (BurgerSet) item;
                System.out.printf("  %s %6d์ (%s(์ผ์ฒฉ %d๊ฐ), %s(๋นจ๋ %s))\n",
                        burgerSet.getName(),
                        burgerSet.getPrice(),
                        burgerSet.getSide().getName(),
                        burgerSet.getSide().getKetchup(),
                        burgerSet.getDrink().getName(),
                        burgerSet.getDrink().hasStraw() ? "์์" : "์์"
                );
            }
        }
    }

    protected int calculateTotalPrice() {
        int result = 0;
        for(Product item: items) {
            result += item.getPrice();
        }
        return result;
    }

    private void chooseOption(Product product) {

        String input;

        if (product instanceof Hamburger) {
            Hamburger h = (Hamburger) product;
            System.out.printf(
                    "๋จํ์ผ๋ก ์ฃผ๋ฌธํ์๊ฒ ์ด์? (1)_๋จํ(%d์) (2)_์ธํธ(%d์)\n",
                    h.getPrice(), h.getBurgerSetPrice()
            );
            input = scan.nextLine();
            h.setIsBurgerSet(input.equals("2"));
        }

	else if (product instanceof Side) {
            System.out.println("์ผ์ฒฉ์ ๋ช๊ฐ๊ฐ ํ์ํ์ ๊ฐ์?");
            input = scan.nextLine();
            ((Side) product).setKetchup(Integer.parseInt(input));
        }

	else if (product instanceof Drink) {
            System.out.println("๋นจ๋๊ฐ ํ์ํ์ ๊ฐ์? (1)_์ (2)_์๋์ค");
            input = scan.nextLine();
            ((Drink) product).setHasStraw(input.equals("1"));
        }
    }

    public void addToCart(int productId) {

        Product product = productRepository.findById(productId);
        Product newProduct;

        chooseOption(product);

        // ํ๋ฒ๊ฑฐ ์ธํธ๋ฅผ ๊ณจ๋๋ค๋ฉด ์ธํธ ๋ฉ๋ด๋ฅผ ๊ตฌ์ฑํด์ผํจ
        if (product instanceof Hamburger) {
            Hamburger h = (Hamburger) product;
            if(h.isBurgerSet()) product = composeSet(h);
        }

        if (product instanceof Hamburger)   newProduct = new Hamburger((Hamburger) product);
        else if (product instanceof Side)   newProduct = new Side((Side) product);
        else if (product instanceof Drink)  newProduct = new Drink((Drink) product);
        else                                newProduct = (BurgerSet) product;

        items = Arrays.copyOf(items, items.length+1);
        items[items.length-1] = newProduct;

        System.out.printf("[๐ฃ] %s๋ฅผ(์) ์ฅ๋ฐ๊ตฌ๋์ ๋ด์์ต๋๋ค.\n", product.getName());
    }

    private BurgerSet composeSet(Hamburger hamburger) {
        System.out.println("์ฌ์ด๋๋ฅผ ๊ณจ๋ผ์ฃผ์ธ์");
        menu.printSides(false);

        int sideId = Integer.parseInt(scan.nextLine());
        Side side = (Side) productRepository.findById(sideId);
        chooseOption(side);


        System.out.println("์๋ฃ๋ฅผ ๊ณจ๋ผ์ฃผ์ธ์.");
        menu.printDrinks(false);

        int drinkId = Integer.parseInt(scan.nextLine());
        Drink drink = (Drink) productRepository.findById(drinkId);
        chooseOption(drink);


        String name = hamburger.getName() + "์ธํธ";
        int price = hamburger.getBurgerSetPrice();
        int kcal = hamburger.getKcal() + side.getKcal() + drink.getKcal();

        return new BurgerSet(
                NEW_PRODUCT_ID,
                name, price, kcal,
                new Hamburger(hamburger),
                new Side(side),
                new Drink(drink)
        );
    }
}
