package com.utils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.main.Job;

public class JsonHandler {
  public static void saveJobsToJson(List<Job> jobs, String filePath) {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT); // Format JSON lisible

        try {
            File file = new File(filePath);
            List<Job> allJobs = new ArrayList<>();

            if(file.exists()){
                try{
                    allJobs = objectMapper.readValue(file, objectMapper.getTypeFactory().constructCollectionType(List.class, Job.class));
                }
                catch(IOException e){
                    System.out.println("Oops! Error reading existing jobs from JSON file: "+e.getMessage());
                }
            }
            allJobs.addAll(jobs);
            objectMapper.writeValue(new File(filePath), allJobs);
            System.out.println("Jobs have been saved successfully." + filePath);
        } catch (IOException e) {
            System.err.println("Oops! something went wrong while writing jobs to json. Error: " + e.getMessage());
        }
    }

    public static List<Job> mergeJsonFiles(){
        ObjectMapper mapper = new ObjectMapper();
        try{
            List<Job> rekruteJobs = mapper.readValue(new File("./src/main/resources/jobs.json"), mapper.getTypeFactory().constructCollectionType(List.class, Job.class));
            List<Job> emploiJobs = mapper.readValue(new File("./src/main/resources/EmploiJobs.json"), mapper.getTypeFactory().constructCollectionType(List.class, Job.class));
            List<Job> mJobs = mapper.readValue(new File("./src/main/resources/mjobs.json"), mapper.getTypeFactory().constructCollectionType(List.class, Job.class));
            List<Job> allJobs = new ArrayList<>();
            allJobs.addAll(rekruteJobs);
            allJobs.addAll(emploiJobs);
            allJobs.addAll(mJobs);
            mapper.writeValue(new File("./src/main/resources/allJobs.json"), allJobs);
            System.out.println("Data has been successfully saved to the allJobs.json file in the resources folder.");
            return allJobs;


        }
        catch(IOException e){
            System.out.println("Oops! something went wrong while mergin json files");
        }
        return null;
    }

    public static List<Job> getAllJobs(String fileName){
      ObjectMapper mapper = new ObjectMapper();
      try {
        List<Job> allJobs = mapper.readValue(new File("./src/main/resources/"+fileName), mapper.getTypeFactory().constructCollectionType(List.class, Job.class));
        return allJobs;
          
      } catch (IOException e) {
        System.out.println("Oops! something went wrong while fetching data");
      }
      return null;
    }
}
