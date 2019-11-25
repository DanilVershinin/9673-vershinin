package ru.cft.focusstart.reader;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Scanner;

public class FileReader implements Reader {
    File file;
    Scanner scanner;

    public FileReader(File file) throws IOException {
        this.file = file;
        this.scanner = new Scanner(new FileInputStream(file));
    }

    @Override
    public boolean hasNext() {
        return this.scanner.hasNextLine();
    }

    @Override
    public String readString(String message) {
        return this.scanner.nextLine();
    }

    @Override
    public double readDouble(String message) {
        while (true) {
            while (!scanner.hasNextDouble()) {
                scanner.nextLine();
            }
            double result = scanner.nextDouble();
            if (result > 0) {
                return result;
            }
        }
    }
}
