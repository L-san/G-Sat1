package ssau.spacegradient.dataprocessing;

import ssau.spacegradient.clientapp.client.converter.DataContainer;

import java.util.function.Consumer;

public interface Algorithm extends Runnable, Consumer<DataContainer> {

    double[] getQuaternion();

    void calculatePosition(double[] a, double[] m, double[] g);

    void setConsumer(Consumer<? super ProcessedData> consumer);
}
