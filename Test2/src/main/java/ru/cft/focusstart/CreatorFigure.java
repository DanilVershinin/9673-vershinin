package ru.cft.focusstart;

import ru.cft.focusstart.figure.*;

import java.util.List;

public enum CreatorFigure {
    CIRCLE{
        @Override
        public Figure createFigure(String nameFigure, List<Double> parametersFigure){
            return new Circle(parametersFigure.get(0));
        }
    },
    SQUARE{
        @Override
        public Figure createFigure(String nameFigure, List<Double> parametersFigure){
            return new Square(parametersFigure.get(0));
        }
    },
    RECTANGLE{
        @Override
        public Figure createFigure(String nameFigure, List<Double> parametersFigure){
            return new Rectangle(parametersFigure.get(0), parametersFigure.get(1));
        }
    };

    public abstract Figure createFigure(String nameFigure, List<Double> parametersFigure);
}
