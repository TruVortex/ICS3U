
package Unit_2;

/*
 * Henry Bao
 * Mar 1, 2023
 * Unit 2, Assignment 5, Number Guessing Game
 */

import java.util.Scanner;

public class u2a5 {

    static final int SECRET = 150;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int guess;
        System.out.println("Welcome to the guessing game!\nYour job is to guess the secret number between 1 and 999 inclusive in 10 guesses.");
        for (int i = 10; i > 0;) {
            guess = sc.nextInt();
            if (guess == SECRET) {
                System.out.println("Congratulations! That is the secret number!");
                return;
            } else {
                if (guess < 1 || guess > 999) {
                    System.out.println("Invalid Input! Ensure your number is between 1 and 999!");
                    continue;
                }
                i--;
            }
            if (i == 0) {
                break;
            }
            System.out.printf("Choose a %s number! You have %d guesses left.\n", guess < SECRET ? "larger" : "smaller", i);
        }
        System.out.println("Too Bad! Try harder next time!");
    }
}
