package ru.cft.focusstart;

import ru.cft.focusstart.figure.Figure;
import ru.cft.focusstart.reader.Reader;

import java.io.EOFException;
import java.util.ArrayList;
import java.util.List;

public class FigureReader {
    Reader reader;

    FigureReader(Reader reader) {
        this.reader = reader;
    }

    Figure read() throws EOFException {
        String nameFigure = reader.readString("Choose and input type of Figure: CIRCLE, RECTANGLE, SQUARE");
        while (!nameFigure.equals("CIRCLE") && !nameFigure.equals("RECTANGLE") && !nameFigure.equals("SQUARE")) {
            if (reader.hasNext()) {
                nameFigure = reader.readString("You input wrong type. Please, choose and input type from this: CIRCLE, RECTANGLE, SQUARE");
            } else {
                throw new EOFException("You reach end of file");
            }
        }
        int numberParametersFigure = new FigureType(nameFigure).getFigureParam();

        ArrayList<Double> parameters = new ArrayList<Double>();
        for (int i = 0; i < numberParametersFigure; i++){
            parameters.add(reader.readDouble("Input double number for characteristic size above zero"));
        }
        return new FactoryFigure().createFigure(nameFigure, parameters);
    }
}
