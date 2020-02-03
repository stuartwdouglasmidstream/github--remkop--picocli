package picocli.codegen.util;

import picocli.CommandLine.Option;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class OutputFileMixin {

    @Option(names = {"-o", "--output"}, description = "Output file to write the result to. " +
            "If not specified, the output is written to the standard output stream.")
    File outputFile;

    public void write(String text) throws IOException {
        if (text != null && text.length() > 0) {
            if (outputFile == null) {
                System.out.print(text); // assume that text ends in line separator
            } else {
                writeToFile(text);
            }
        }
    }

    private void writeToFile(String result) throws IOException {
        FileWriter writer = null;
        try {
            File parent = outputFile.getAbsoluteFile().getParentFile();
            if (parent != null && !parent.exists() && !parent.mkdirs()) {
                System.err.println("Unable to mkdirs for " + outputFile.getAbsolutePath());
            }
            writer = new FileWriter(outputFile);
            writer.write(result);
        } finally {
            if (writer != null) {
                writer.close();
            }
        }
    }
}
