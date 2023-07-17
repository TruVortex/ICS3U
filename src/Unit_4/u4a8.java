package Unit_4;

/*
 * Henry
 * Feb 10, 2023
 * Unit 4, Assignment 8, Encryption/Decryption
 */

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

public class u4a8 {

    static final String ORIGINAL = "AEIJOPRSTVX ";
    static final String SUBSTITUTE = "@=!?*#&$+^%_";
    static final String PATH = "src/Unit_4/";

    public static void main(String[] args) throws IOException {
        Scanner sc = new Scanner(System.in), in;
        String action;
        File inFile, outFile;
        PrintWriter out;

        System.out.print("Encrypt or decrypt: ");
        action = sc.next();
        System.out.print("File to be read from: ");
        inFile = new File(PATH + sc.next());
        in = new Scanner(inFile);
        System.out.print("File to be output to: ");
        outFile = new File(PATH + sc.next());
        out = new PrintWriter(outFile);
        if (action.equals("encrypt")) {
            while (in.hasNextLine()) {
                String before = in.nextLine().toUpperCase(), after = "", after1 = "";
                for (int i = 0; i < before.length(); i++) {
                    if (ORIGINAL.contains("" + before.charAt(i))) {
                        after += SUBSTITUTE.charAt(ORIGINAL.indexOf(before.charAt(i)));
                    } else {
                        after += before.charAt(i);
                    }
                }
                after = after.substring((after.length() + 1) / 2) + after.substring(0, (after.length() + 1) / 2);
                after = after.substring(after.length() - 2) + after.substring(2, after.length() - 2) + after.substring(0, 2);
                after = after.substring(0, (after.length() - 3) / 2) + after.substring((after.length() + 1) / 2, (after.length() + 5) / 2) + after.substring((after.length() - 3) / 2, (after.length() + 1) / 2) + after.substring((after.length() + 5) / 2);
                for (int i = 1; i < after.length(); i += 2) {
                    after1 += after.charAt(i);
                    after1 += after.charAt(i - 1);
                }
                if (after.length() % 2 == 1) {
                    after1 += after.charAt(after.length() - 1);
                }
                out.println(after1);
            }
        } else {
            while (in.hasNext()) {
                String before = in.next().toUpperCase(), after = "", after1 = "";
                for (int i = 1; i < before.length(); i += 2) {
                    after += before.charAt(i);
                    after += before.charAt(i - 1);
                }
                if (before.length() % 2 == 1) {
                    after += before.charAt(before.length() - 1);
                }
                after = after.substring(0, (after.length() - 3) / 2) + after.substring((after.length() + 1) / 2, (after.length() + 5) / 2) + after.substring((after.length() - 3) / 2, (after.length() + 1) / 2) + after.substring((after.length() + 5) / 2);
                after = after.substring(after.length() - 2) + after.substring(2, after.length() - 2) + after.substring(0, 2);
                after = after.substring(after.length() / 2) + after.substring(0, after.length() / 2);
                for (int i = 0; i < after.length(); i++) {
                    if (SUBSTITUTE.contains("" + after.charAt(i))) {
                        after1 += ORIGINAL.charAt(SUBSTITUTE.indexOf(after.charAt(i)));
                    } else {
                        after1 += after.charAt(i);
                    }
                }
                out.println(after1.toLowerCase());
            }
        }
        out.close();
    }
}
