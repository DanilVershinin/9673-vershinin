package ru.cft.focusstart;

import ru.cft.focusstart.figure.Figure;
import ru.cft.focusstart.reader.Reader;

import java.io.EOFException;
import java.util.ArrayList;

public class FigureReader {
    private Reader reader;

    public FigureReader(Reader reader) {
        this.reader = reader;
    }

    public Figure read() throws EOFException {
        String nameFigure = getNameFigure();
        FigureType typeFigure = new FigureType(nameFigure);
        int numberParametersFigure = typeFigure.getFigureParam();

        ArrayList<Double> parameters = new ArrayList<>();
        for (int i = 0; i < numberParametersFigure; i++) {
            parameters.add(this.reader.readDouble("Input double number for characteristic size above zero"));
        }
        return typeFigure.getTypeOfFigure().createFigure(nameFigure, parameters);
    }

    private String getNameFigure() throws EOFException {
        String result = this.reader.readString("Choose and input type of Figure: CIRCLE, RECTANGLE, SQUARE");
        while (!result.equals("CIRCLE") && !result.equals("RECTANGLE") && !result.equals("SQUARE")) {
            if (this.reader.hasNext()) {
                result = this.reader.readString("You input wrong type.\nPlease, choose and input type from this: CIRCLE, RECTANGLE, SQUARE");
            } else {
                throw new EOFException("Standard type of figure isn't found in a file!!!");
            }
        }
        return result;
    }
}
