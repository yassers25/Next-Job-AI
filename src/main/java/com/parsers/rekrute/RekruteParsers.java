package com.parsers.rekrute;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.main.Job;
import com.parsers.Parser;

public class RekruteParsers extends Parser{


  public static void parseAll(List<Job> jobs) {
    parseContractType(jobs);
    parseCity(jobs);
    parseRemoteWork(jobs);
    parseExperience(jobs);
    parseStudyLevel(jobs);
    parseActivitySector(jobs);
    parseFunction(jobs);
    
  }

  /**************** acitivity sector ***************/
  public static void parseActivitySector(List<Job> jobs) {
    for (Job job : jobs) {
      String activitySector = job.getActivitySector().toLowerCase();
      if (VALID_ACTIVITY_SECTORS.contains(activitySector)) {
        job.setActivitySector(activitySector);
      } else {
        activitySector = activitySector.trim();
        activitySector = mapSector(activitySector);
        if (activitySector != null && VALID_ACTIVITY_SECTORS.contains(activitySector)) {
          job.setActivitySector(activitySector);
        } else {
          job.setActivitySector(null);
        }
      }
    }
  }
  /**************** city ***************/
  public static void parseCity(List<Job> jobs) {
    for (Job job : jobs) {
      String city = job.getCity();
      if (city == null) {
        job.setCity(null);
        continue;
      }

      city = city.toLowerCase().trim();

      if (VALID_CITIES.contains(city)) {
        job.setCity(city);
      } else {
        String mappedCity = mapCity(city);

        if (mappedCity != null && VALID_CITIES.contains(mappedCity)) {
          job.setCity(mappedCity);
        } else {
          job.setCity(null);
        }
      }
    }
  }
  /**************** contract type ***************/
  public static void parseContractType(List<Job> jobs) {
    for (Job job : jobs) {
        String contractType = job.getContractType();
        if (contractType == null || contractType.isBlank()) {
            job.setContractType(null);
            continue;
        }
        
        String mappedContract = mapContractType(contractType);

        if(mappedContract != null && VALID_CONTRACT_TYPES.contains(mappedContract)){
            job.setContractType(mappedContract); 
        }
        else{
            job.setContractType(null);
        }
    }

   
}
  /**************** experience ***************/
  private static final Pattern NUMBER_PATTERN = Pattern.compile("\\d+");
  public static String parseExperienceRange(String input) {
        // Extract all numbers from the input

        if(input == null){
          return null;
        }

        List<Integer> numbers = new ArrayList<>();
        Matcher matcher = NUMBER_PATTERN.matcher(input);
        
        while (matcher.find()) {
            numbers.add(Integer.valueOf(matcher.group()));
        }

        // If no numbers found, return fraichement diplômé
        if (numbers.isEmpty()) {
            return "fraichement diplômé";
        }

        // Calculate average
        double average = numbers.stream()
                              .mapToInt(Integer::intValue)
                              .average()
                              .orElse(0.0);
        
        // Round down
        int roundedAverage = (int) Math.floor(average);

        // Map to experience category
        if (roundedAverage < 1) {
            return "fraichement diplômé";
        } else if (roundedAverage <= 3) {
            return "débutant (de 1 à 3 ans)";
        } else if (roundedAverage <= 5) {
            return "junior (de 3 à 5 ans)";
        } else if (roundedAverage <= 10) {
            return "senior (de 5 à 10 ans)";
        } else {
            return "expert (10 ou plus)";
        }
    }

  public static void parseExperience(List<Job> jobs) {
    for (Job job : jobs) {
      job.setRequiredExperience(parseExperienceRange(job.getRequiredExperience()));
    }
  }
  /**************** function ***************/
  public static void parseFunction(List<Job> jobs){
    for(Job job: jobs){
      if(job.getFunction() == null){
        job.setFunction(null);
      }
      else{
        job.setFunction(findClosestFunctionMatch(job.getFunction()));
      }
    }
  }
  /**************** remote ***************/
  public static void parseRemoteWork(List<Job> jobs){
    for(Job job : jobs){
      String remoteWork = job.getRemoteWork();
      if(remoteWork != null){
          if(remoteWork.toLowerCase().contains("oui")){
              job.setRemoteWork("oui");
          }
          else if(remoteWork.toLowerCase().contains("hybride")){
              job.setRemoteWork("hybride");
          }
          else{
              job.setRemoteWork("non");
          }
      }
      else{
          job.setRemoteWork("non");
      }
    }
  }
  /**************** studyLevel ***************/
  

  public static void parseStudyLevel(List<Job> jobs) {
    for (Job job : jobs) {
      
      String studyLevel = job.getStudyLevel();

      if(studyLevel == null || studyLevel.isEmpty()){
        job.setStudyLevel(null);
        continue;
      }
       
      studyLevel = studyLevel.toLowerCase().trim();
      if (VALID_STUDY_LEVELS.contains(studyLevel)) {
        job.setStudyLevel(studyLevel);
      } else {
        String parsedStudyLevel = mapStudyLevel(studyLevel);
        if (parsedStudyLevel != null && VALID_STUDY_LEVELS.contains(parsedStudyLevel)) {
          job.setStudyLevel(parsedStudyLevel);
        } else {
          System.out.println(job.getStudyLevel());
          job.setStudyLevel(null);
        }
      }

    }
  }
}
