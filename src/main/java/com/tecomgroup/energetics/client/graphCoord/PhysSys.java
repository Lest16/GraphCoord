package com.tecomgroup.energetics.client.graphCoord;

public class PhysSys {
    private Models models;

    private int CountIteration = 0;

    public double[] r;

    public double[] v;

    public PhysSys(Models models) {
        this.models = models;
        this.r = rndPoss(this.models.g.length,  200, true);
        this.v = rndPoss(this.models.g.length,  50, false);
    }

    public double[] rndPoss(int n, int mx, boolean sym) {
        double[] r = new double[2*n];
        for(int i = 0; i < 2*n; ++i)
            if(sym)
                r[i] = Math.random() * mx;
            else r[i] = Math.random() * 2 * mx - mx;
        return r;
    }

    public void step() {
        double[] fv = models.ForceFrictionModel(this.r, this.v);
        this.add(this.v, fv);
        this.add(this.r, this.v);
        this.CountIteration++;
        if (this.CountIteration == 500) {
            this.CountIteration = 0;
            return;
        }
        else {
            this.step();
        }
    }

    private void add(double[] x, double[] dx) {
        for(int i = 0; i < x.length; ++i)
            x[i] += dx[i] * 0.001;
    }

    public double[] centralize(double x0, double y0, int deviationX, int deviationY) {
        double xm = 0, ym = 0;
        int n = this.r.length / 2;
        for(int i = 0; i < n; ++i) {
            xm += r[2*i];
            ym += r[2*i+1];
        }

        xm = xm / n;
        ym = ym / n;
        double[] a = new double[r.length];
        System.arraycopy(r, 0, a, 0, a.length);
        for(int j = 0; j < n; ++j) {
            a[2*j] += x0 - xm + deviationX;
            a[2*j+1] += y0 - ym + deviationY;
        }

        return a;
    }
}
