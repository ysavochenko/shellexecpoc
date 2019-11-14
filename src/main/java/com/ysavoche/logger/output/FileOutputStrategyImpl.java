package com.ysavoche.logger.output;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.LocalDateTime;
import java.util.StringJoiner;
import java.util.function.Consumer;
import java.util.function.Function;

public class FileOutputStrategyImpl implements OutputStrategy {

    private Consumer<String> outConsumer;

    public FileOutputStrategyImpl(Function<String, String> inFunction, String filename) {
        this.outConsumer = (line) -> {
            String filteredOutput = inFunction.apply(line);
            if (!filteredOutput.equals("")) {
                String outputToPersist = new StringJoiner("|","","\n")
                        .add(LocalDateTime.now().toString())
                        .add(filteredOutput).toString();

                System.out.println("Writing to " + filename + " next line: " + outputToPersist);
                try {
                    Files.write(Paths.get(filename), outputToPersist.getBytes(), StandardOpenOption.CREATE, StandardOpenOption.APPEND);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        };
    }

    @Override
    public Consumer<String> getConsumer() {
        return outConsumer;
    }
}
