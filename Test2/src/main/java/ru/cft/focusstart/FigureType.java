package ru.cft.focusstart;

public class FigureType {
    private int figureParam;

    public FigureType(String Type) {
        switch (Type) {
            case "RECTANGLE":
                this.figureParam = 2;
                break;
            case "CIRCLE":
            case "SQUARE":
                this.figureParam = 1;
                break;
            default:
                this.figureParam = 0;
                break;
        }
    }

    public int getFigureParam() {
        return this.figureParam;
    }
}
