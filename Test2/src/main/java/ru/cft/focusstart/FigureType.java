package ru.cft.focusstart;

public class FigureType {
    private CreatorFigure Type;
    private int FigureParam;

    public FigureType(String Type) {
        switch (Type) {
            case "CIRCLE":
                this.Type = CreatorFigure.CIRCLE;
                this.FigureParam = 1;
                break;
            case "SQUARE":
                this.Type = CreatorFigure.SQUARE;
                this.FigureParam = 1;
                break;
            case "RECTANGLE":
                this.Type = CreatorFigure.RECTANGLE;
                this.FigureParam = 2;
                break;
        }
    }

    public int getFigureParam() {
        return FigureParam;
    }

    public CreatorFigure getTypeOfFigure() {
        return Type;
    }
}
