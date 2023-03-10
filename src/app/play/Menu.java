package app.play;

import app.product.Product;
import app.product.subproduct.*;

import java.util.Scanner;

public class Menu {
    Product[] products;
    Scanner scan = new Scanner(System.in);

    public Menu(Product[] products) {
        this.products = products;
    }

    public void printMenu() {
        System.out.println("[π»] λ©λ΄");
        System.out.println("-".repeat(60));

        printHamburgers(true);
        printSides(true);
        printDrinks(true);

        System.out.println();
        System.out.println("π (0) μ₯λ°κ΅¬λ");
        System.out.println("π¦ (+) μ£Όλ¬ΈνκΈ°");
        System.out.println("-".repeat(60));
        System.out.print("[π£] λ©λ΄λ₯Ό μ νν΄μ£ΌμΈμ : ");
    }

    protected void printDrinks(boolean isShow) {
        System.out.println("π₯€ μλ£");
        for(Product p: products) {
            if(p instanceof Drink) {
                printEachMenu(p, isShow);
            }
        }
        System.out.println();
    }

    protected void printSides(boolean isShow) {
        System.out.println("π μ¬μ΄λ");
        for(Product p: products) {
            if(p instanceof Side) {
                printEachMenu(p, isShow);
            }
        }
        System.out.println();
    }

    protected void printHamburgers(boolean isShow) {
        System.out.println("π νλ²κ±°");
        for(Product p: products) {
            if(p instanceof Hamburger) {
                printEachMenu(p, isShow);
            }
        }
        System.out.println();
    }

    private static void printEachMenu(Product p,boolean isShow) {
        System.out.printf(
                "   (%d) %s %5dKcal",
                p.getId(), p.getName(), p.getKcal()
        );
        if(isShow) System.out.printf(" %5dμ", p.getPrice());

        System.out.println();
    }

}
