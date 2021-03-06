package rna.perceptron.fruits;

import rna.Example;
import rna.perceptron.Train;

public class ExampleFruit extends Example {

    public ExampleFruit(double[] input, double output, String classType) {
        super(input, output);
        this.classType = classType;
    }

    public ExampleFruit(double[] input) {
        super(input);
    }

    public void getOutputLearned(Train train) {
        double sum = train.getActivationFunction().calculateByExampleAndWeights(this, train.getWeightsTrained());
        output.getLearned(sum, "Fruit");
    }
}
