package com.ais.neural;

import java.util.Random;

public class NeuralNet {
    private int inputVectorSize;
    private Neuron[] layer;
    private int epochNumber;
    private boolean complete;
    private double[] error;
    private double errorNet;
    private double eta = 0.00000001;
    private double epsThreshold = 0.000000001;

    public double getErrorNet() {
        return errorNet;
    }

    public NeuralNet(int inputVectorSize, int outputNeuronsCount) {
        this.inputVectorSize = inputVectorSize;
        layer = new Neuron[outputNeuronsCount];
        for (int j = 0; j < outputNeuronsCount; j++)
        {
            layer[j] = new Neuron(inputVectorSize);
        }
        error = new double[layer.length];
    }

    public double[] getError() {
        return error;
    }

    public int getEpochNumber() {
        return epochNumber;
    }

    public boolean isComplete() {
        return complete;
    }

    public void setComplete(boolean complete) {
        this.complete = complete;
    }

    public void train(Vector[] vectorSet) throws InterruptedException
    {
        // эпоха обучения равна нулю
        epochNumber = 0;
        double[][] deltaWeight = new double[layer.length][];
        for (int j = 0; j < layer.length; j++)
        {
            // создаем пустой массив для каждого
            //синаптического веса wij каждого j-го нейрона в слое
            deltaWeight[j] = new double[layer[j].getWeight().length];
        }
        // массив для хранения ошибки каждого нейрона
        error = new double[layer.length];
        Random random = new Random();

            while (true)
            {
                // Шаг 3 и 4. Берем случайным образом обучающий вектор
                int m = random.nextInt(vectorSet.length);
                // Шаг 5
                for (int j = 0; j < layer.length; j++) //перебор нейронов
                {
                    layer[j].calcOut(vectorSet[m].getX());
                }

                errorNet = 0.0;
                // Шаг 6
                for (int j = 0; j < layer.length; j++)
                { // считаем ошибка каждого j-го нейрона
                    error[j] =
                            (vectorSet[m].getDesireOutputs()[j] -
                            layer[j].getOut()) *
                            (vectorSet[m].getDesireOutputs()[j] -
                            layer[j].getOut());
                    errorNet += 0.5 * error[j];
                }
                // Шаг 7
                if (errorNet < epsThreshold) // ошибка < ошибки порог.
                    break; // прерываем внешний цикл
                // критерий останова обучения

                // Шаг 8. Цикл коррекции синаптических весов
                for (int j = 0; j < layer.length; j++)
                {
                    layer[j].calcSigma(vectorSet[m].getDesireOutputs()[j]);
                    int n = layer[j].getWeight().length; // кол-во синаптических весов j-го нейрона
                    for (int i = 0; i < n; i++)
                    {
                        deltaWeight[j][i] = - eta * layer[j].getSigma() *
                                vectorSet[m].getX()[i];
                    }
                    layer[j].correctWeights(deltaWeight[j]);
                }
                epochNumber++;
            }


        complete = true;
    }

    public double[] test(double[] vector)
    {
        double[] outVector = new double[layer.length];
        for (int j = 0; j < layer.length; j++)
        {
            layer[j].calcOut(vector);
            outVector[j] = Math.round(layer[j].getOut() * 100.0) / 100.0;
        }
        return outVector;
    }
}

