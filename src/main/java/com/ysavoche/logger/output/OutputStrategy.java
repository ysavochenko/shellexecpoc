package com.ysavoche.logger.output;

import java.util.function.Consumer;

public interface OutputStrategy {

    Consumer<String> getConsumer();

}
