package ru.cft.focusstart.reader;

public interface Reader {
    boolean hasNext();
    String readString(String message);
    double readDouble(String message);
}
