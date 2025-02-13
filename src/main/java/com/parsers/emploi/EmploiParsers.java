package com.parsers.emploi;

import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import com.main.Job;
import com.parsers.Parser;

public class EmploiParsers extends Parser {

  public static void parseAll(List<Job> jobs) {
    parseContractType(jobs);
    parseCity(jobs);
    parseRemote(jobs);
    parseRequiredExperience(jobs);
    parseStudyLevel(jobs);
    parseLanguage(jobs);
  }

  /******* Contract type *******/
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

  /******* Remote work *******/

  public static void parseRemote(List<Job> jobs) {
    for (Job job : jobs) {
      job.setRemoteWork("non");
    }
  }

  /******* Experience *********/

  private static final Map<Pattern, String> EXPERIENCE_MAPPER = Map.ofEntries(
      Map.entry(Pattern.compile("etudiant|jeune diplômé|jeune"), "fraichement diplômé"),
      Map.entry(Pattern.compile("débutant < 2 ans"), "débutant (de 1 à 3 ans)"),
      Map.entry(Pattern.compile("expérience entre 2 ans et 5 ans"), "junior (de 3 à 5 ans)"),
      Map.entry(Pattern.compile("expérience entre 5 ans et 10 ans"), "senior (de 5 à 10 ans)"),
      Map.entry(Pattern.compile("expérience > 10 ans"), "expert (10 ou plus)"));

  public static String getMinimumRequiredExperience(String experience) {
    if (experience == null || experience.trim().isEmpty()) {
      return null;
    }
    if (!experience.contains(" - ")) {
      return experience;
    }

    // Split the input string by " - "

    String[] experiences = experience.split(" - ");

    return experiences[0];
  }

  public static String mapExperience(String experience) {
    for (Map.Entry<Pattern, String> entry : EXPERIENCE_MAPPER.entrySet()) {
      if (entry.getKey().matcher(experience).find()) {
        return entry.getValue();
      }
    }
    return experience;
  }

  public static void parseRequiredExperience(List<Job> jobs) {
    for (Job job : jobs) {
      String experience = mapExperience(getMinimumRequiredExperience(job.getRequiredExperience().toLowerCase()));

      job.setRequiredExperience(experience);
    }
  }

  /***************** Study level *****************/

  private static final Map<String, String> STUDY_LEVEL_MAPPING = Map.of(
      "bac", "bac",
      "niv bac et moins", "bac",
      "bac+1", "bac +1",
      "bac+2", "bac +2",
      "bac+3", "bac +3",
      "bac+4", "bac +4",
      "bac+5 et plus", "bac +5 et plus",
      "qualification avant bac", "qualification avant bac");

  public static String getMinimumStudyLevel(String studyLevel) {
    if (studyLevel == null || studyLevel.trim().isEmpty()) {
      return null;
    }
    if (!studyLevel.contains(" - ")) {
      return STUDY_LEVEL_MAPPING.getOrDefault(studyLevel, studyLevel);

    }

    // Split the input string by " - "

    String[] experiences = studyLevel.split(" - ");

    return STUDY_LEVEL_MAPPING.getOrDefault(experiences[0], experiences[0]);
  }

  public static void parseStudyLevel(List<Job> jobs) {
    for (Job job : jobs) {
      job.setStudyLevel(getMinimumStudyLevel(job.getStudyLevel().toLowerCase()));
    }
  }

  /******************* city ****************/

  public static void parseCity(List<Job> jobs) {
    for (Job job : jobs) {
      String city = job.getCity();
      if (job.getCity() == null)
        continue;
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

  /******** language parser *********/
  public static String getLanguage(String language, boolean isLanguage) {
    if (language == null || language.trim().isEmpty()) {
      return null;
    }
    if (!language.contains(">")) {
      return mapLanguage(language);
    }

    // Split the input string by " > "
    String[] experiences = language.split(" > ");

    return isLanguage == true ? mapLanguage(experiences[0]) : experiences[1];
  }

  public static void parseLanguage(List<Job> jobs) {
    for (Job job : jobs) {
      job.setLanguageLevel(getLanguage(job.getLanguage(), false));
      job.setLanguage(getLanguage(job.getLanguage().toLowerCase().trim(), true));
    }
  }
}
