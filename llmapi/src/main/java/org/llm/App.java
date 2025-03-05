package org.llm;

import java.util.Scanner;

/**
 * Hello world!
 */
public class App {
    public static void main(String[] args) {
        if (args.length == 0) {
            Scanner scanner = new Scanner(System.in);
            String input = scanner.nextLine();
            System.out.println(input);
        }
    }
}
