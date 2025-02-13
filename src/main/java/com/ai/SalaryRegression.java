package com.ai;

import com.main.TestJob;

import weka.classifiers.Classifier;
import weka.classifiers.functions.LinearRegression;
import weka.core.Attribute;
import weka.core.DenseInstance;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.SelectedTag;
import weka.filters.Filter;
import weka.filters.unsupervised.attribute.Normalize;

public class SalaryRegression extends Model {
    private Normalize normalizer;
    private double minSalary;
    
    public SalaryRegression() {
        // Call super with -1 as we'll set the class index after adding salary attribute
        super(-1);
        // Add salary as a numeric attribute
        attributes.add(new Attribute("salary"));
        // Create new dataset with updated attributes
        trainingDataset = new Instances("SalaryData", attributes, 0);
        trainingDataset.setClassIndex(attributes.size() - 1);
        
        normalizer = new Normalize();
        minSalary = Double.MAX_VALUE;
    }

    @Override
    protected Classifier createClassifier() {
        return new LinearRegression();
    }

    @Override
    protected void configureClassifier(Classifier classifier) {
        LinearRegression lr = (LinearRegression) classifier;
        try {
            lr.setEliminateColinearAttributes(true);
            lr.setAttributeSelectionMethod(new SelectedTag(LinearRegression.SELECTION_M5, 
                                                         LinearRegression.TAGS_SELECTION));
            lr.setMinimal(true);
        } catch (Exception e) {
            System.err.println("Error configuring Linear Regression: " + e.getMessage());
        }
    }

    @Override
    public void addTrainingData(TestJob job) {
        // Update minimum salary seen in training data
        minSalary = Math.min(minSalary, job.getSalary());
        
        Integer sectorValue = HashMapData.sectorMapName.get(job.getActivitySector().trim().toLowerCase());
        if (sectorValue == null) return;

        Integer experienceValue = HashMapData.experienceMapName.get(job.getRequiredExperience().trim().toLowerCase());
        if (experienceValue == null) return;

        Integer studyValue = HashMapData.studyMapName.get(job.getStudyLevel().trim().toLowerCase());
        if (studyValue == null) return;

        Integer contractValue = HashMapData.contractMapName.get(job.getContractType().trim().toLowerCase());
        if (contractValue == null) return;

        Integer remoteValue = HashMapData.remoteMapName.get(job.getRemoteWork().trim().toLowerCase());
        if (remoteValue == null) return;

        Integer cityValue = HashMapData.cityMapName.get(job.getCity().toLowerCase());
        if (cityValue == null) return;

        Instance instance = new DenseInstance(7);
        instance.setDataset(trainingDataset);

        instance.setValue(attributes.get(0), sectorValue);
        instance.setValue(attributes.get(1), experienceValue);
        instance.setValue(attributes.get(2), studyValue);
        instance.setValue(attributes.get(3), contractValue);
        instance.setValue(attributes.get(4), remoteValue);
        instance.setValue(attributes.get(5), cityValue);
        instance.setValue(attributes.get(6), job.getSalary());

        trainingDataset.add(instance);
    }

    @Override
    public void trainModel() throws Exception {
        // Normalize the data before training
        normalizer.setInputFormat(trainingDataset);
        Instances normalizedData = Filter.useFilter(trainingDataset, normalizer);
        
        classifier.buildClassifier(normalizedData);
    }

    public Double predictSalary(String sector, String experience, String study, 
                              String contractType, String remote, String city) throws Exception {
        Instance testInstance = new DenseInstance(7);
        testInstance.setDataset(trainingDataset);

        testInstance.setValue(attributes.get(0), sector);
        testInstance.setValue(attributes.get(1), experience);
        testInstance.setValue(attributes.get(2), study);
        testInstance.setValue(attributes.get(3), contractType);
        testInstance.setValue(attributes.get(4), remote);
        testInstance.setValue(attributes.get(5), city);

        // Normalize the test instance
        Instances testInstances = new Instances(trainingDataset, 0);
        testInstances.add(testInstance);
        testInstances = Filter.useFilter(testInstances, normalizer);
        
        // Get prediction
        double prediction = classifier.classifyInstance(testInstances.firstInstance());
        
        // Ensure prediction is not negative
        return (Double) Math.max(prediction, minSalary);
    }

    @Override
    public String getModelInfo() {
        return classifier.toString();
    }
}