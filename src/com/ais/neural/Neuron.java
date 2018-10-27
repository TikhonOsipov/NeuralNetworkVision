package com.ais.neural;

import java.util.Random;

public class Neuron {
    private double[] weight;
    private double out; // y
    private double sum; // net
    private double sigma;
    public static Random random = new Random();
    public double rangeMin = -0.0003;
    public double rangeMax = 0.0003;

    public double getSigma() {
        return sigma;
    }

    public void calcSigma(double desireResponse)
    {
        this.sigma = -(desireResponse - this.out) * derivativeActivationFunc(sum);
    }

    public void calcOut(double[] x)
    {
        this.sum = 0;
        for (int i = 0; i < x.length; i++)
        {
            sum += x[i]*weight[i];
        }
        this.out = activationFunc(sum);
    }

    public Neuron(int weightsCount) {
        this.weight = new double[weightsCount];
        this.out = 0.0;
        randomizeWeights();
    }

    public void randomizeWeights()
    {
        for (int i = 0; i < weight.length; i++)
        {
            weight[i] = rangeMin + (rangeMax - rangeMin) * random.nextDouble();
        }
    }

    private double activationFunc(double val)
    {
        return 1.0 / (1.0 + Math.exp(-val));
    }

    private double derivativeActivationFunc(double val)
    {
        return activationFunc(val) * (1.0 - activationFunc(val));
    }



    public double getOut()
    {
        return out;
    }

    public void correctWeights(double[] deltaWeight)
    {
        for (int i = 0; i < weight.length; i++)
        {
            weight[i] += deltaWeight[i];
        }
    }

    public double[] getWeight()
    {
        return weight;
    }
}
