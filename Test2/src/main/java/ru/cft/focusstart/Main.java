package ru.cft.focusstart;


import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import ru.cft.focusstart.exceptions.FIleNotFoundOrEmptyException;
import ru.cft.focusstart.figure.Figure;
import ru.cft.focusstart.reader.*;
import ru.cft.focusstart.writer.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Main {

    private static final Logger log = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) throws EOFException {
        Figure result;
        try {
            result = getFigure(args);
        } catch (Exception e) {
            log.error("Name exception: {}, Message: {}", e.getClass().getCanonicalName(), e.getMessage());
            result = new FigureReader(new ConsoleReader(System.in)).read();
        }


        try {
            if (args.length >= 2) {
                File fileOutput = new File(args[1]);
                new DataWriterToFile(fileOutput).write(result.printDescription());
            } else {
                new DataWriterToConsole().write(result.printDescription());
            }
        } catch (Exception e) {
            log.error("Name exception: {}, Message: {}", e.getClass().getCanonicalName(), e.getMessage());
            new DataWriterToConsole().write(result.printDescription());
        }
    }

	public static Figure getFigure(String[] args) throws Exception {
		if (args.length >= 1) {
			File fileInput = new File(args[0]);
			if (fileInput.exists() && fileInput.length() != 0) {
				return new FigureReader(new FileReader(fileInput)).read();
			}
		}
		return new FigureReader(new ConsoleReader(System.in)).read();

	}
}
