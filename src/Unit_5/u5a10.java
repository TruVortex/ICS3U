package Unit_5;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

public class u5a10 {
    public static void main(String[] args) throws IOException {
        int fileLength = 0, count = 0, year;
        double salesTotal = 0;
        int[] employees;
        double[] sales;
        Scanner sc = new Scanner(System.in), inputScanner;
        PrintWriter outputFile;
        System.out.print("What year is the file from? ");
        year = sc.nextInt();
        inputScanner = new Scanner(new File("src/" + year + "_sales_summary.txt"));
        outputFile = new PrintWriter(new FileWriter("src/" + year + "_bonus_summary.txt"));
        while (inputScanner.hasNextLine()) {
            inputScanner.nextLine();
            fileLength++;
        }
        employees = new int[fileLength - 2];
        sales = new double[fileLength - 2];
        inputScanner = new Scanner(new File("src/" + year + "_sales_summary.txt"));
        inputScanner.nextLine();
        inputScanner.nextLine();
        for (int i = 0; i < fileLength - 2; i++) {
            employees[i] = inputScanner.nextInt();
            sales[i] = inputScanner.nextDouble();
        }
        outputFile.println("Employee ID    Sales Total   Bonus Total");
        for (int i = 0; i < 40; i++) {
            outputFile.print("*");
        }
        outputFile.println();
        outer:
        for (int i = 0; i < fileLength - 2; i++) {
            double curSales = 0;
            for (int j = 0; j < fileLength - 2; j++) {
                if (employees[i] == employees[j]) {
                    if (j < i) {
                        continue outer;
                    }
                    curSales += sales[j];
                }
            }
            outputFile.printf("%4d%22.2f%14.2f\n", employees[i], curSales, curSales * 0.05);
            salesTotal += curSales;
            count++;
        }
        for (int i = 0; i < 40; i++) {
            outputFile.print("*");
        }
        outputFile.printf("\nNumber of Employees: %d\nSales Total: %.2f\nBonus Total: %.2f", count, salesTotal, salesTotal * 0.05);
        inputScanner.close();
        outputFile.close();
    }
}