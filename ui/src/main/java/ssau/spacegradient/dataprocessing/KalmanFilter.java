package ssau.spacegradient.dataprocessing;

import org.ejml.simple.SimpleMatrix;

public class KalmanFilter {
    private SimpleMatrix x_hat = SimpleMatrix.diag(0d,0d,0d);
    private SimpleMatrix P = SimpleMatrix.identity(3);
    private final SimpleMatrix R;
    private final SimpleMatrix Q;
    private int cnt = 0;

    public KalmanFilter(double Rcoeff, double Qcoeff){
        R = SimpleMatrix.diag(Rcoeff,Rcoeff,Rcoeff);
        Q = SimpleMatrix.diag(Qcoeff,Qcoeff,Qcoeff);
    }

    public void doFiltering(double[] z){
        SimpleMatrix Z = new SimpleMatrix(3,1);
        Z.set(0,0,z[0]);
        Z.set(1,0,z[1]);
        Z.set(2,0,z[2]);
        if (cnt ==0){
            x_hat = Z;
            cnt++;
        }else{
            //prediction
            P = P.plus(Q);
            //correction
            SimpleMatrix S = P.plus(R);
            SimpleMatrix K = P.mult(S.invert());
            x_hat = x_hat.plus(K.mult(Z.minus(x_hat)));
        }
    }

    public double[] getX_hat() {
        return new double[]{x_hat.get(0,0),x_hat.get(1,0),x_hat.get(2,0)};
    }
}
