package ssau.spacegradient.dataprocessing;

import org.ejml.simple.SimpleMatrix;

public class KalmanFilter extends Filter{
    private SimpleMatrix x_hat;
    private SimpleMatrix P = SimpleMatrix.identity(9);
    private final SimpleMatrix R;
    private final SimpleMatrix Q;
    private int cnt = 0;

    public KalmanFilter(double Rcoeff, double Qcoeff) {
        this.x_hat = new SimpleMatrix(9, 1);
        R = SimpleMatrix.diag(Rcoeff, Rcoeff, Rcoeff, Rcoeff, Rcoeff, Rcoeff, Rcoeff, Rcoeff, Rcoeff);
        Q = SimpleMatrix.diag(Qcoeff, Qcoeff, Qcoeff, Qcoeff, Qcoeff, Qcoeff, Qcoeff, Qcoeff, Qcoeff);
    }

    public void doFiltering(double[] z) {
        SimpleMatrix Z = new SimpleMatrix(9, 1);
        for (int i = 0; i < 9; i++) {
            Z.set(i, 0, z[i]);
        }
        if (cnt == 0) {
            x_hat = Z;
            cnt++;
        } else {
            //prediction
            P = P.plus(Q);
            //correction
            SimpleMatrix S = P.plus(R);
            SimpleMatrix K = P.mult(S.invert());
            x_hat = x_hat.plus(K.mult(Z.minus(x_hat)));
        }
    }

    public double[] getX_hat() {
        double[] ans = new double[9];
        for(int i = 0; i<9;i++){
            ans[i] = x_hat.get(i, 0);
        }
        return ans;
    }
}
