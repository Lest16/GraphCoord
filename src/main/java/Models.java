public class Models
{
    public int[][] g;
    private Params params;

    public Models(int[][] g, Params params) {
        this.g = g;
        this.params = params;
    }

    private double LogSpring(double dl) {
        return this.params.spring * (dl * Math.log(dl/this.params.meanSpringLength) - dl);
    }

    private double Coulomb(double dl) {
        return this.params.q2 / dl;
    }

    private double edge(double dl) {
        return params.q2 / (dl*dl) - params.spring * Math.log(dl / params.meanSpringLength);
    }

    private double noEdge(double dl) {
        return params.q2 / (dl*dl);
    }

    private double[] EdgeNodeForce(double[] r) {
        double[] f = new double[2 * this.g.length];
        int i;
        for(i = 0; i < this.g.length; ++i)
            f[2*i] = f[2*i+1] = 0;
        for(i = 0; i < this.g.length; ++i)
            for(int j = i + 1; j < this.g.length; ++j) {
                double dx = r[2*i] - r[2*j];
                double dy = r[2*i+1] - r[2*j+1];
                double dl = Math.sqrt(dx*dx + dy*dy);
                if(dl == 0) continue;
                double fs = (g[i][j] == 1) ? this.edge(dl) : this.noEdge(dl);
                double fx = fs * dx / dl;
                double fy = fs * dy / dl;
                f[2*i] += fx;
                f[2*j] -= fx;
                f[2*i+1] += fy;
                f[2*j+1] -= fy;
            }

        return f;

    }

    private double friction(double v) {
        return params.gamma * v;
    }

    public double[] ForceFrictionModel(double[] r, double[] v) {
        int n = r.length / 2;
        double[] f = EdgeNodeForce(r);
        for(int i = 0; i < n; ++i) {
            double dv = Math.sqrt(v[2*i]*v[2*i] + v[2*i+1]*v[2*i+1]);
            if(dv == 0) continue;
            f[2*i] -= friction(dv) * v[2*i] / dv;
            f[2*i+1] -= friction(dv) * v[2*i+1] / dv;
        }

        return f;
    }

    public double SpringChargeEnergy(double[] r) {
        double E = 0;
        for(int i = 0; i < this.g.length; ++i)
            for(int j = i + 1; j < this.g.length; ++j) {

                double dx = r[2*i] - r[2*j];
                double dy = r[2*i+1] - r[2*j+1];
                double dl = Math.sqrt(dx*dx + dy*dy);
                E += (g[i][j] == 1) ? Coulomb(dl) + LogSpring(dl) : Coulomb(dl);
            }
        return E;

    }
}

