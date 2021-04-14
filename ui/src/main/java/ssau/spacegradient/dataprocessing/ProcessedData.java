package ssau.spacegradient.dataprocessing;

import ssau.spacegradient.clientapp.client.converter.DataContainer;

public class ProcessedData {
    private DataContainer rawData = new DataContainer();
    private double[] q = new double[]{1, 0, 0, 0};

    public double[] getQ() {
        return q;
    }

    public void setQ(double[] q) {
        this.q = q;
    }

    public DataContainer getRawData() {
        return rawData;
    }

    public void setRawData(DataContainer rawData) {
        this.rawData = rawData;
    }
}
