package com.ai;

import weka.classifiers.Classifier;
import weka.classifiers.trees.RandomForest;

public class RandomForestPrediction extends Model {
    
    public RandomForestPrediction(int classIndex) {
        super(classIndex);
    }

    @Override
    protected Classifier createClassifier() {
        return new RandomForest();
    }

    @Override
    protected void configureClassifier(Classifier classifier) {
        RandomForest forest = (RandomForest) classifier;
        forest.setNumIterations(100);  // Number of trees in the forest
        forest.setNumFeatures(3); // Number of features to consider for splits
    }

    @Override
    public String getModelInfo() {
        return classifier.toString();
    }
}