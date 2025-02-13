package com.ai;

import weka.classifiers.Classifier;
import weka.classifiers.bayes.NaiveBayes;

public class NaiveBayesPrediction extends Model {
    
    public NaiveBayesPrediction(int classIndex) {
        super(classIndex);
    }

    @Override
    protected Classifier createClassifier() {
        return new NaiveBayes();
    }

    @Override
    protected void configureClassifier(Classifier classifier) {
        NaiveBayes nb = (NaiveBayes) classifier;
        
        // Configure Naive Bayes parameters
        try {
            // Use normal density estimator rather than normal distribution
            nb.setUseKernelEstimator(false);
            
            // Set whether to use supervised discretization to convert numeric attributes to nominal ones
            nb.setUseSupervisedDiscretization(true);
            
            // Display model in old format
            nb.setDisplayModelInOldFormat(false);
        } catch (Exception e) {
            System.err.println("Error configuring Naive Bayes: " + e.getMessage());
        }
    }

    @Override
    public String getModelInfo() {
        return classifier.toString();
    }
}