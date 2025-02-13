package com.ai;

import weka.classifiers.Classifier;
import weka.classifiers.functions.SMO;
import weka.core.SelectedTag;

public class SVMPrediction extends Model {
    
    public SVMPrediction(int classIndex) {
        super(classIndex);
    }

    @Override
    protected Classifier createClassifier() {
        return new SMO();
    }

    @Override
    protected void configureClassifier(Classifier classifier) {
        SMO smo = (SMO) classifier;
        
        // Configure SVM parameters
        try {
            // Use RBF (Radial Basis Function) kernel
            smo.setKernel(new weka.classifiers.functions.supportVector.RBFKernel());
            
            // Set complexity parameter (C)
            smo.setC(1.0);
            
            // Set tolerance parameter
            smo.setToleranceParameter(0.001);
            
            // Enable normalization of attributes
            smo.setFilterType(new SelectedTag(SMO.FILTER_NORMALIZE, SMO.TAGS_FILTER));
        } catch (Exception e) {
            System.err.println("Error configuring SMO: " + e.getMessage());
        }
    }

    @Override
    public String getModelInfo() {
        return classifier.toString();
    }
}