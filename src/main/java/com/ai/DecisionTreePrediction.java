package com.ai;

import weka.classifiers.Classifier;
import weka.classifiers.trees.J48;

public class DecisionTreePrediction extends Model {
    
    public DecisionTreePrediction(int classIndex) {
        super(classIndex);
    }

    @Override
    protected Classifier createClassifier() {
        return new J48();
    }

    @Override
    public String getModelInfo() {
        return ((J48) classifier).toString();
    }
}