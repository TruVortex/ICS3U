package Unit_1;

/*
 * Henry
 * Feb 8, 2023
 * Unit 1, Assignment 3, Receipt Generator
 */

import java.util.Scanner;

public class u1a3 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int apples, oranges;
        double appleCost, orangeCost, lychees, lycheeCost, blueberries, blueberryCost;
        arrows();
        System.out.printf("%36s\n", "Chow Chow Fruit Centre");
        arrows();
        System.out.println();
        System.out.printf("%-35s", "Number of apples purchased?");
        apples = sc.nextInt();
        appleCost = apples * 0.83;
        System.out.printf("%-35s", "Number of oranges purchased?");
        oranges = sc.nextInt();
        orangeCost = oranges * 0.75;
        System.out.printf("%-35s", "Amount of lychees purchased?");
        lychees = sc.nextDouble();
        lycheeCost = lychees * 2.49;
        System.out.printf("%-35s", "Amount of blueberries purchased?");
        blueberries = sc.nextDouble();
        blueberryCost = blueberries * 1.42;
        System.out.println();
        dashes("Receipt");
        System.out.println();
        System.out.printf("%-32s%-13s%s\n\n", "Description", "Quantity", "Price");
        System.out.printf("%-32s%7d%11.2f\n", "Apples @ $0.83/each", apples, appleCost);
        System.out.printf("%-32s%7d%11.2f\n", "Oranges @ $0.75/each", oranges, orangeCost);
        System.out.printf("%-32s%7.2f%11.2f\n", "Lychees @ $2.49/lbs", lychees, lycheeCost);
        System.out.printf("%-32s%7.2f%11.2f\n", "Blueberries @ $1.42/lb", blueberries, blueberryCost);
        System.out.println();
        double total = appleCost + orangeCost + lycheeCost + blueberryCost;
        System.out.printf("%40s%10.2f\n", "Subtotal", total);
        System.out.printf("%40s%10.2f\n", "H.S.T. (13%)", total * 0.13);
        System.out.println();
        System.out.printf("%40s%10.2f\n", "Net Total", total * 1.13);
        System.out.println();
        dashes("THANK YOU FOR SHOPPING WITH US TODAY!");
    }

    static void arrows() {
        for (int i = 0; i < 25; i++) {
            System.out.print("<>");
        }
        System.out.println();
    }

    static void dashes(String str) {
        for (int i = 0; i < (51 - str.length()) / 2; i++) {
            System.out.print("-");
        }
        System.out.print(str);
        for (int i = 0; i < (50 - str.length()) / 2; i++) {
            System.out.print("-");
        }
        System.out.println();
    }
}
