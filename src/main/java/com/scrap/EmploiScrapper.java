package com.scrap;

import java.io.IOException;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.main.Job;

public class EmploiScrapper extends Scrapper {

  private static final int THREAD_POOL_SIZE = 4;
  private static final Queue<String> pagesToScrape = new ConcurrentLinkedQueue<>();


  public static Double extractAverageSalary(String salaryString) {
 
        if(salaryString == null || salaryString.length() ==0){
          return null;
        }
        String cleaned = salaryString.replace("DH", "").trim();
        
        // Split by dash
        String[] parts = cleaned.split("-");
        if (parts.length != 2) {
            throw new IllegalArgumentException("Invalid salary range format");
        }
        
        // Extract min and max values by removing spaces
        double minSalary = Double.parseDouble(parts[0].replaceAll("\\s+", "").trim());
        double maxSalary = Double.parseDouble(parts[1].replaceAll("\\s+", "").trim());
        
        // Calculate and return average
        return (minSalary + maxSalary) / 2.0;
    
}

  public static Job extractJob(Element jobElement) {

    Job job = new Job();
    String pageUrl = jobElement.select(".card-job-detail h3:first-child a").attr("href");

    try {
      Document jobPage = createJsoupConnection("https://www.emploi.ma"+pageUrl);
      job.setJobTitle(extractJobTitle(jobElement.select("h3 a").text(), "^(.*?)\\-"));
      job.setActivitySector(jobPage.select(".arrow-list strong:contains(Secteur) + span").text());
      job.setFunction(jobPage.select(".arrow-list strong:contains(Métier) + span").text());
      job.setRemoteWork(null);
      job.setStudyLevel(jobPage.select(".arrow-list strong:contains(études) + span").text());
      job.setRequiredExperience(jobPage.select(".arrow-list strong:contains(expérience) + span").text());
      job.setContractType(jobPage.select(".arrow-list strong:contains(Type de contrat) + span").text());
      job.setCity(jobPage.select(".arrow-list strong:contains(Ville) + span").text());
      job.setSearchedProfile(jobPage.select("article.page-application-content div.job-qualifications ul").text());
      job.setJobDescription(jobPage.select("article.page-application-content div.job-description ul").text());

      job.setPublicationDate(jobElement.select(".card-job-detail time").text().replace(".", "/"));
      job.setHardSkills(
          jobElement.select(".card-job-detail ul li:contains(Compétences clés) strong").text().replace(" - ", ", "));
      job.setImageUrl(jobPage.select(".card-block-company img").attr("src"));
      job.setEntreprise(jobPage.select(".card-block-company a:first-child").text());
      job.setSiteWeb("emploi.ma");
      job.setLanguage(jobPage.select(".arrow-list strong:contains(Langues) + span").text());
      job.setSalary(extractAverageSalary(jobPage.select(".arrow-list strong:contains(Salaire) + span").text()));

      try {
        String url = jobPage.select(".company-description .truncated-text a").attr("href");
        Document entreprisePage = createJsoupConnection("https://www.emploi.ma"+url);
        job.setEntrepriseDescription(entreprisePage.select(".page-content .card-block-company-description p").text());

      } catch (IOException e) {
      }

      return job;
    } catch (IOException e) {
      System.out.println("Oops! something went wrong! Please try again later.");
      return null;
    }

  }

  public static void scrapJobPage(String url, List<Job> jobs) {
    try {
      Document doc = createJsoupConnection(url);

      if (doc != null) {
        Elements jobElements = doc.select("div.card.card-job");

        for (Element jobElement : jobElements) {
          Job job = extractJob(jobElement);
          System.out.println(job.toString());
          jobs.add(job);
        }
      } else {
        System.out.println("Jsoup connection returned null for: " + url);
      }

    } catch (IOException e) {
      System.out.println("Error scrapping page: " + url + " - " + e.getMessage());
    }

  }

  public static Document createJsoupConnection(String url) throws IOException {
    int retries = 3;
    while (retries > 0) {
      try {
        return Jsoup.connect(url)
            .userAgent(Scrapper.getUserAgent())
            .header("Accept-Language", "*")
            .referrer("https://google.com")
            .timeout(60000) // 60 seconds timeout
            .get();
      } catch (IOException e) {
        retries--;
        if (retries == 0) {
          throw e;
        }
        System.out.println("Retrying connection... Attempts left: " + retries);
      }
    }
    return null; // Unreachable, but required for compilation
  }

  public static void startScrapping(List<Job> jobs) throws InterruptedException {
    // pagesToScrape.add("https://www.emploi.ma/recherche-jobs-maroc");
    // scrapJobPage(pagesToScrape.poll(), jobs);

    for (int i = 1; i < 32; i++) {
      pagesToScrape.add("https://www.emploi.ma/recherche-jobs-maroc?page=" + i);
    }

    ExecutorService executorService = Executors.newFixedThreadPool(THREAD_POOL_SIZE);

    while (!pagesToScrape.isEmpty()) {
      String url = pagesToScrape.poll();
      if (url == null) continue;
      System.out.println("Current page -> "+url);
      executorService.submit(() -> scrapJobPage(url, jobs));
      TimeUnit.MILLISECONDS.sleep(2000); // Rate limiting
    }
    executorService.shutdown();
    executorService.awaitTermination(1000, TimeUnit.SECONDS);


  }
}
