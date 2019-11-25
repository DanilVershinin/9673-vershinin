package ru.cft.focusstart.reader;

import java.io.InputStream;
import java.util.Scanner;

public class ConsoleReader implements Reader {
    private Scanner scanner;

    public ConsoleReader(InputStream inputStream) {
        this.scanner = new Scanner(inputStream);
    }

    @Override
    public boolean hasNext() {
        return true;
    }

    @Override
    public String readString(String message) {
        System.out.println(message);
        return scanner.nextLine();
    }

    @Override
    public double readDouble(String message) {
        while (true) {
            System.out.println(message);
            while (!scanner.hasNextDouble()) {
                System.out.println(message);
                scanner.nextLine();
            }
            double result = scanner.nextDouble();
            if (result > 0) {
                return result;
            }
        }
    }
}
