package rna.perceptron;

import rna.Example;
import rna.Examples;

import java.util.Random;

public class Train {

    protected static int EPOCH = 0;

    private String FUNCTION_NAME;
    private double LEARNING_RATE;
    private double THRESHOULD;

    protected double activationFunctionResult;
    protected Examples examples;
    protected double[] weights;

    public void config(double threshould, double learningRate, String fuctionName) {
        THRESHOULD = threshould;
        LEARNING_RATE = learningRate;
        FUNCTION_NAME = fuctionName;
    }

    public void execute() {
        System.out.println("Inicia o treinamento");
        initializeWeightsRandom();
        do {
            examples.noExistError();
            adjustTheCorrectWeights();
            EPOCH++;
        } while (examples.hasError());

        showInformations();
    }

    private void adjustTheCorrectWeights() {
        for (Example example : this.examples.get()) {
            activationFunctionResult = activationFunction(example);
            if (!isActivationFunctionResultEqualsOutputExpected(example)) {
                loadNewValueOfWeight(example, LEARNING_RATE);
                examples.existError();
            }
        }
    }

    private double activationFunction(Example example) {
        if("sigmoid".equals(FUNCTION_NAME)){
            return activationFunctionSigmoid(example);
        }
        return activationFunctionSigmoidBipolar(example);
    }

    private double activationFunctionSigmoid(Example example) {
        double u = sumOfProductByExample(example);
        if (u > THRESHOULD) {
            return 1;
        }
        return 0;
    }

    private double activationFunctionSigmoidBipolar(Example example) {
        double u = sumOfProductByExample(example);
        if (u >= THRESHOULD) {
            return 1;
        }
        return -1;
    }

    protected void initializeWeightsRandom() {
        weights = new double[examples.getInputLenght()];
        Random random = new Random(System.currentTimeMillis());
        for (int i = 0; i < weights.length; i++) {
            weights[i] = random.nextDouble();
        }
    }

    protected double sumOfProductByExample(Example example) {
        double sum = 0.0;
        for (int i = 0; i < example.getInputLenght(); i++) {
            sum += example.getInput()[i] * weights[i];
        }
        return sum;
    }

    protected boolean isActivationFunctionResultEqualsOutputExpected(Example example) {
        return activationFunctionResult == example.getOutput();
    }

    protected void loadNewValueOfWeight(Example exemple, double learningRate) {
        for (int i = 0; i < weights.length; i++) {
            weights[i] = weights[i] + learningRate * (exemple.getOutput() - activationFunctionResult) * exemple.getInputX(i);
        }
    }

    protected void showInformations() {
        System.out.println("Numero de épocas necessárias: " + EPOCH);
        System.out.println("Pesos: " + showWeights());
        System.out.println("Finaliza o treinamento");
        System.out.println("############################################################################################");
    }

    private String showWeights() {
        String w = "";
        for(double weight : weights) {
            w += weight + " ";
        }
        return w;
    }

    public double[] getWeights() {
        return weights;
    }
}
