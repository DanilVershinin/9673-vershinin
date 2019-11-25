package ru.cft.focusstart;

import ru.cft.focusstart.figure.Circle;
import ru.cft.focusstart.figure.Figure;
import ru.cft.focusstart.figure.Rectangle;
import ru.cft.focusstart.figure.Square;

import java.util.List;

public class FactoryFigure {
    public Figure createFigure(String nameFigure, List<Double> parametersFigure) throws IllegalStateException {
        Figure figure;
        switch (nameFigure) {
            case "RECTANGLE":
                figure = new Rectangle(parametersFigure.get(0), parametersFigure.get(1));
                break;
            case "CIRCLE":
                figure = new Circle(parametersFigure.get(0));
                break;
            case "SQUARE":
                figure = new Square(parametersFigure.get(0));
                break;
            default:
                throw new IllegalStateException("This if not standard type of figure" + nameFigure);
        }
        return figure;
    }
}
