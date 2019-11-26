package ru.cft.focusstart.writer;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class DataFileWriter implements Writer {
    private File fileOutput;

    public DataFileWriter(File fileOutput) {
        this.fileOutput = fileOutput;
    }

    @Override
    public void write(String stringForWriting) throws IOException {
        try (FileWriter fileWriter = new FileWriter(this.fileOutput)) {
            fileWriter.write(stringForWriting);
        }
    }
}
