package ru.cft.focusstart.writer;

public class DataConsoleWriter implements Writer {
    @Override
    public void write(String string) {
        System.out.println(string);
    }
}
