package ru.cft.focusstart;


public class Resource {
    private int id;
    private static int numberId = 0;

    Resource() {
        this.id = generateId();
    }

    private synchronized int generateId() {
        return numberId++;
    }

    public int getId() {
        return this.id;
    }
}
