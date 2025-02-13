package com.parsers.mjobs;

import java.util.List;
import java.util.Map;

import com.main.Job;
import com.parsers.Parser;

public class MjobParsers extends Parser {

  public static void parseAll(List<Job> jobs) {
    parseContractType(jobs);
    parseCity(jobs);
    parseRemote(jobs);
    parseExperience(jobs);
    parseStudyLevel(jobs);
    parseLanguage(jobs);
    parseActivitySector(jobs);
    parseFunction(jobs);
  }

  /************* Contract type ***************/
  public static void parseContractType(List<Job> jobs) {
    for (Job job : jobs) {
      String contractType = job.getContractType();
      if (contractType == null || contractType.isBlank()) {
        job.setContractType(null);
        continue;
      }

      String mappedContract = mapContractType(contractType);

      if (mappedContract != null && VALID_CONTRACT_TYPES.contains(mappedContract)) {
        job.setContractType(mappedContract);
      } else {
        job.setContractType(null);
      }
    }

  }

  /************* remote work Sector ***************/

  public static void parseRemote(List<Job> jobs) {
    for (Job job : jobs) {
      job.setRemoteWork("non");
    }
  }

  /************* Acitivty Sector ***************/
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

  /**************** City ****************/

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

  /**************** Experience ****************/

  private static final Map<String, String> EXPERIENCE_MAPPING = Map.of(
      "fraichement diplômé", "fraichement diplômé",
      "débutant (de 1 à 3 ans)", "débutant (de 1 à 3 ans)",
      "junior (de 3 à 5 ans)", "junior (de 3 à 5 ans)",
      "senior (de 5 à 7 ans)", "senior (de 5 à 10 ans)",
      "confirmé (de 7 à 10 ans)", "senior (de 5 à 10 ans)",
      "+ 10 ans", "expert (10 ou plus)");

  public static String getMinimumExperience(String experienceLine) {
    if (experienceLine == null || experienceLine.trim().isEmpty()) {
      return null;
    }
    if (!experienceLine.contains("/")) {
      return EXPERIENCE_MAPPING.getOrDefault(experienceLine, experienceLine);
    }

    // Split the input string by " / "

    String[] experiences = experienceLine.split(" / ");

    return EXPERIENCE_MAPPING.getOrDefault(experiences[0], experiences[0]);
  }

  public static void parseExperience(List<Job> jobs) {
    for (Job job : jobs) {
      job.setRequiredExperience(getMinimumExperience(job.getRequiredExperience().toLowerCase()));
    }
  }

  /**************** function ****************/

  public static void parseFunction(List<Job> jobs) {
    for (Job job : jobs) {
      job.setFunction(findClosestFunctionMatch(job.getFunction()));
    }
  }

  /**************** Studylevel ****************/
  private static final Map<String, String> STUDY_LEVEL_MAPPING = Map.of(
      "bac", "bac",
      "niv bac et moins", "bac",
      "bac+1", "bac +1",
      "bac+2", "bac +2",
      "bac+3", "bac +3",
      "bac+4", "bac +4",
      "bac+5 et plus", "bac +5 et plus");

  public static String getMinimumStudyLevel(String studyLevel) {
    if (studyLevel == null || studyLevel.trim().isEmpty()) {
      return null;
    }
    if (!studyLevel.contains("/")) {
      return STUDY_LEVEL_MAPPING.getOrDefault(studyLevel, studyLevel);

    }

    // Split the input string by " / "

    String[] experiences = studyLevel.split(" / ");

    return STUDY_LEVEL_MAPPING.getOrDefault(experiences[0], experiences[0]);
  }

  public static void parseStudyLevel(List<Job> jobs) {
    for (Job job : jobs) {
      job.setStudyLevel(getMinimumStudyLevel(job.getStudyLevel().toLowerCase()));
    }
  }

  /******** language parser *********/
  public static String getLanguage(String language) {
    if (language == null || language.trim().isEmpty()) {
      return null;
    }
    if (!language.contains("/")) {
      return mapLanguage(language);

    }

    // Split the input string by " / "

    String[] experiences = language.split(" / ");

    return mapLanguage(experiences[0]);
  }

  public static void parseLanguage(List<Job> jobs) {
    for (Job job : jobs) {
      job.setLanguage(getLanguage(job.getLanguage()));
    }
  }

}
