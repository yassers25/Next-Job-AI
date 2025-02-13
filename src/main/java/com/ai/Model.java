// Prediction.java
package com.ai;

import java.util.ArrayList;
import java.util.Random;

import com.main.TestJob;

import weka.classifiers.Classifier;
import weka.classifiers.Evaluation;
import weka.core.Attribute;
import weka.core.DenseInstance;
import weka.core.Instance;
import weka.core.Instances;

public abstract class Model {
    protected Classifier classifier;
    protected Instances trainingDataset;
    protected ArrayList<Attribute> attributes;

    public Model(int classIndex) {
        // Initialize fields using private final methods
        this.attributes = initializeAttributes();
        this.trainingDataset = initializeDataset(classIndex);
        this.classifier = initializeClassifier();
    }

    protected abstract Classifier createClassifier();

    private  ArrayList<Attribute> initializeAttributes() {
        ArrayList<Attribute> attrs = new ArrayList<>();

        ArrayList<String> sectorValues = new ArrayList<>();
        for (int i = 0; i < 50; i++) {
            sectorValues.add(String.valueOf(i));
        }

        ArrayList<String> experienceValues = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            experienceValues.add(String.valueOf(i));
        }
        
        ArrayList<String> studyValues = new ArrayList<>();
        for (int i = 0; i < 9; i++) {
            studyValues.add(String.valueOf(i));
        }

        ArrayList<String> contractValues = new ArrayList<>();
        for (int i = 0; i < 9; i++) {
            contractValues.add(String.valueOf(i));
        }

        ArrayList<String> remoteValues = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            remoteValues.add(String.valueOf(i));
        }

        ArrayList<String> cityValues = new ArrayList<>();
        for (int i = 0; i < 43; i++) {
            cityValues.add(String.valueOf(i));
        }

        // Add all attributes
        attrs.add(new Attribute("activitySector", sectorValues));
        attrs.add(new Attribute("requiredExperience", experienceValues));
        attrs.add(new Attribute("studyLevel", studyValues));
        attrs.add(new Attribute("contractType", contractValues));
        attrs.add(new Attribute("remoteWork", remoteValues));
        attrs.add(new Attribute("city", cityValues));

        return attrs;
    }

    private  Instances initializeDataset(int classIndex) {
        // Create empty training dataset
        Instances dataset = new Instances("JobData", attributes, 0);
        dataset.setClassIndex(classIndex);
        return dataset;
    }

    private  Classifier initializeClassifier() {
        Classifier clf = createClassifier();
        configureClassifier(clf);
        return clf;
    }

    protected void configureClassifier(Classifier classifier) {
        // Default implementation does nothing
        // Subclasses can override this to add their specific configuration
    }


    public void addTrainingData(TestJob job) {
        // Check if any value in the required fields is null
        Integer sectorValue = HashMapData.sectorMapName.get(job.getActivitySector().trim().toLowerCase());
        if (sectorValue == null)
            return;

        Integer experienceValue = HashMapData.experienceMapName.get(job.getRequiredExperience().trim().toLowerCase());
        if (experienceValue == null)
            return;

        Integer studyValue = HashMapData.studyMapName.get(job.getStudyLevel().trim().toLowerCase());
        if (studyValue == null)
            return;

        Integer contractValue = HashMapData.contractMapName.get(job.getContractType().trim().toLowerCase());
        if (contractValue == null)
            return;

        Integer remoteValue = HashMapData.remoteMapName.get(job.getRemoteWork().trim().toLowerCase());
        if (remoteValue == null)
            return;

        Integer cityValue = HashMapData.cityMapName.get(job.getCity().toLowerCase());
        if (cityValue == null)
            return;

        Instance instance = new DenseInstance(6);
        instance.setDataset(trainingDataset);

        instance.setValue(attributes.get(0), sectorValue);
        instance.setValue(attributes.get(1), experienceValue);
        instance.setValue(attributes.get(2), studyValue);
        instance.setValue(attributes.get(3), contractValue);
        instance.setValue(attributes.get(4), remoteValue);
        instance.setValue(attributes.get(5), cityValue);

        trainingDataset.add(instance);
    }

    public void trainModel() throws Exception {
        classifier.buildClassifier(trainingDataset);
    }

    protected String predict(Instance testInstance) throws Exception {
        double prediction = classifier.classifyInstance(testInstance);
        return trainingDataset.classAttribute().value((int) prediction);
    }

    protected Instance createTestInstance() {
        Instance testInstance = new DenseInstance(6);
        testInstance.setDataset(trainingDataset);
        return testInstance;
    }

    public String predictActivitySector(String experience, String study, String remote, String contractType,
            String city) throws Exception {
        Instance testInstance = createTestInstance();
        testInstance.setValue(attributes.get(1), experience);
        testInstance.setValue(attributes.get(2), study);
        testInstance.setValue(attributes.get(3), contractType);
        testInstance.setValue(attributes.get(4), remote);
        testInstance.setValue(attributes.get(5), city);
        return predict(testInstance);
    }

    // Similar implementation for other predict methods
    public String predictExperience(String sector, String study, String remote, String contractType, String city)
            throws Exception {
        Instance testInstance = createTestInstance();
        testInstance.setValue(attributes.get(0), sector);
        testInstance.setValue(attributes.get(2), study);
        testInstance.setValue(attributes.get(3), contractType);
        testInstance.setValue(attributes.get(4), remote);
        testInstance.setValue(attributes.get(5), city);
        return predict(testInstance);
    }

    public String predictStudyLevel(String sector,
            String experience, String remote, String contractType, String city) throws Exception {
        // Create test instance
        Instance testInstance = createTestInstance();

        // Set known values
        testInstance.setValue(attributes.get(0), sector);
        testInstance.setValue(attributes.get(1), experience);
        testInstance.setValue(attributes.get(3), contractType);

        testInstance.setValue(attributes.get(4), remote);
        testInstance.setValue(attributes.get(5), city);

        // Make prediction
        return predict(testInstance);
    }

    public String predictContractType(String sector, String experience,
            String study, String remote, String city) throws Exception {
        // Create test instance
        Instance testInstance = createTestInstance();

        // Set known values
        testInstance.setValue(attributes.get(0), sector);
        testInstance.setValue(attributes.get(1), experience);
        testInstance.setValue(attributes.get(2), study);
        // Contract type is what we're predicting (index 3)
        testInstance.setValue(attributes.get(4), remote);
        testInstance.setValue(attributes.get(5), city);

        // Make prediction
        return predict(testInstance);
    }

    public String predictRemoteWork(String sector, String experience, String study, String contractType, String city)
            throws Exception {
                Instance testInstance = createTestInstance();

        testInstance.setValue(attributes.get(0), sector);
        testInstance.setValue(attributes.get(1), experience);
        testInstance.setValue(attributes.get(2), study);
        // Contract type is what we're predicting (index 4)
        testInstance.setValue(attributes.get(3), contractType);
        testInstance.setValue(attributes.get(5), city);
        return predict(testInstance);

    }

    public String predictCity(String sector, String experience, String study, String contractType, String remoteWork)
            throws Exception {
                Instance testInstance = createTestInstance();

        testInstance.setValue(attributes.get(0), sector);
        testInstance.setValue(attributes.get(1), experience);
        testInstance.setValue(attributes.get(2), study);
        // Contract type is what we're predicting (index 4)
        testInstance.setValue(attributes.get(3), contractType);
        testInstance.setValue(attributes.get(4), remoteWork);
        return predict(testInstance);

    }

    public Double predictSalary(String sector, String experience, String study, 
                              String contractType, String remote, String city) throws Exception {
                                return null;
                              }

    public abstract String getModelInfo();

    public String evaluateModel(int numFolds, int seed) throws Exception {
        if (trainingDataset.isEmpty()) {
            return "No training data available for evaluation";
        }

        // Create evaluator
        Evaluation evaluation = new Evaluation(trainingDataset);
        
        // Perform cross-validation
        evaluation.crossValidateModel(classifier, trainingDataset, numFolds, new Random(seed));
        
        // Build summary string
        StringBuilder summary = new StringBuilder();
        summary.append("=== Model Evaluation Summary ===\n");
        summary.append("Model type: ").append(classifier.getClass().getSimpleName()).append("\n\n");
        
        // Add general statistics
        summary.append("Correctly Classified Instances: ")
              .append(String.format("%.2f%%\n", evaluation.pctCorrect()));
        summary.append("Incorrectly Classified Instances: ")
              .append(String.format("%.2f%%\n", evaluation.pctIncorrect()));
        summary.append("Kappa statistic: ")
              .append(String.format("%.4f\n", evaluation.kappa()));
        summary.append("Mean absolute error: ")
              .append(String.format("%.4f\n", evaluation.meanAbsoluteError()));
        summary.append("Root mean squared error: ")
              .append(String.format("%.4f\n", evaluation.rootMeanSquaredError()));
        
        // Add detailed accuracy by class
        summary.append("\nDetailed Accuracy By Class:\n");
        summary.append(evaluation.toClassDetailsString());
        
        // Add confusion matrix
        summary.append("\nConfusion Matrix:\n");
        summary.append(evaluation.toMatrixString());
        
        return summary.toString();
    }

    public String evaluateModel() throws Exception {
        return evaluateModel(10, 1);  
    }
}




