package com.ais.neural;

public class Vector
{
    private double[] x;
    private double[] desireOutputs;

    public Vector(double[] x, double[] desireOutputs) {
        this.x = new double[x.length +1];
        this.x[0] = 1;
        for (int i = 1; i < x.length; i++)
        {
            this.x[i] = x[i - 1];
        }
        this.desireOutputs = desireOutputs;
    }

    public double[] getX() {
        return x;
    }

    public double[] getDesireOutputs() {
        return desireOutputs;
    }
}
