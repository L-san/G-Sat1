package ssau.spacegradient.dataprocessing;

public class Filter {
    private double[] z = new double[9];

    public void doFiltering(double[] z) {
        this.z = z;
    }

    public double[] getX_hat() {
        return this.z;
    }
}
