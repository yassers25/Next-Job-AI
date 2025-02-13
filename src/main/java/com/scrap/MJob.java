package com.scrap;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.main.Job;

public class MJob extends Scrapper {

  private static final int THREAD_POOL_SIZE = 4;
  private static final Queue<String> pagesToScrape = new ConcurrentLinkedQueue<>();

  private static String formateDate(String raw) {
    Pattern pattern = Pattern.compile("il y a (\\d+) (heure|heures|mois|jour|jours) avant");
    Matcher matcher = pattern.matcher(raw);

    if (matcher.find()) {
      int amount = Integer.parseInt(matcher.group(1));
      String unit = matcher.group(2);

      LocalDateTime now = LocalDateTime.now();
      LocalDateTime targetDate;

      if (unit.startsWith("heure")) {
        targetDate = now.minusHours(amount);
      } else if (unit.startsWith("mois")) {
        targetDate = now.minusMonths(amount);
      } else if (unit.startsWith("jour")) {
        targetDate = now.minusDays(amount);
      } else {
        return null;
      }

      // Formater en dd/mm/yyyy
      DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
      return targetDate.format(formatter);
    }
    return null;
  }

  public static Double extractAverageSalary(String text) {
    Pattern pattern = Pattern.compile("de (\\d+(?:\\s?\\d+)?)\\s*dh à (\\d+(?:\\s?\\d+)?)\\s*dh");
    Matcher matcher = pattern.matcher(text);

    Double sum = (double) 0;
    int count = 0;

    while (matcher.find()) {
      // Get min and max values, removing spaces
      String minStr = matcher.group(1).replaceAll("\\s", "");
      String maxStr = matcher.group(2).replaceAll("\\s", "");

      // Parse to Double
      Double min = Double.valueOf(minStr);
      Double max = Double.valueOf(maxStr);

      // Calculate average for this range
      sum += (min + max) / 2;
      count++;
    }

    // Return overall average
    return count > 0 ? sum / count : null;
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

  public static Job extractJob(Element jobElement) {

    Job job = new Job();
    @SuppressWarnings("null")
    String pageUrl = jobElement.selectFirst(".offer-heading .offer-title a").absUrl("href");
    try {
      Document jobPage = createJsoupConnection(pageUrl);
      job.setJobTitle(
          extractJobTitle(
              jobPage.select("body section.main-details .offer-title").text(), "(?<=-).+|^.+$"));
      job.setContractType(jobPage.select(".header-details .list-details li:nth-child(2) h3").text());
      job.setCity(jobPage.select(".location span").text());
      job.setActivitySector(jobPage.select(".the-content > h3:contains(Secteur) + div").text());
      job.setSearchedProfile(jobPage.select(".the-content > h3:contains(Profil) + div").text());
      job.setJobDescription(jobPage.select(".the-content > h3:contains(Poste) + div").text());
      job.setFunction(jobPage.select(".the-content > h3:contains(Métier) + div").text());
      job.setRemoteWork("non");
      job.setStudyLevel(jobPage.select(".the-content > h3:contains(études) + div").text());
      job.setRequiredExperience(jobPage.select(".the-content > h3:contains(expériences) + div").text());

      job.setImageUrl(jobPage.select(".header-details div.logo img").attr("src"));
      job.setSiteWeb("m-job");
      job.setJobPageUrl(pageUrl);
      job.setEntreprise(jobPage.select(".header-details span:contains(Société) + h3").text());
      job.setSalary(extractAverageSalary(jobPage.select(".header-details span:contains(Salaire) + h3").text()));
      job.setEntrepriseDescription(jobPage.select(".the-content > h3:contains(recruteur) + div").text());
      job.setLanguage(jobPage.select(".the-content > h3:contains(Langue) + div").text());
      job.setPublicationDate(formateDate(jobPage.select(".bottom-content span").text()));


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

        Elements jobElements = doc.select(".offers-boxes .offer-box");
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

  public static void startScrapping(List<Job> jobs) throws InterruptedException {
    for (int i = 1; i <= 15; i++) {
      pagesToScrape.add("https://www.m-job.ma/recherche?page=" + i);
    }

    scrapJobPage(pagesToScrape.poll(), jobs);
    ExecutorService executorService =
    Executors.newFixedThreadPool(THREAD_POOL_SIZE);

    while (!pagesToScrape.isEmpty()) {
    String url = pagesToScrape.poll();
    if (url == null) continue;
    executorService.submit(() -> scrapJobPage(url, jobs));
    TimeUnit.MILLISECONDS.sleep(1000); // Rate limiting
    }
    executorService.shutdown();
    executorService.awaitTermination(1000, TimeUnit.SECONDS);

  }
}
