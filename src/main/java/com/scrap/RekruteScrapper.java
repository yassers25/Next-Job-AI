package com.scrap;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.main.Job;

public class RekruteScrapper extends Scrapper {
    private static final String REKRUTE_DOMAIN_NAME = "https://www.rekrute.com";
    private static final int THREAD_POOL_SIZE = 4;
    private static final Set<String> pagesDiscovered = Collections.synchronizedSet(new HashSet<>());
    private static final Queue<String> pagesToScrape = new ConcurrentLinkedQueue<>();
    // private static final List<Job> jobs = Collections.synchronizedList(new
    // ArrayList<>());

    public static String extractRemoteWork(String fullText) {
        String key = "Télétravail :";
        int index = fullText.indexOf(key);
        return index != -1 ? fullText.substring(index + key.length()).trim() : null;
    }

    public static void setPaginationElements(Document doc) {
        Elements paginationElements = doc.select("span.jobs select option");
        for (Element page : paginationElements) {
            String pageUrl = REKRUTE_DOMAIN_NAME + page.val();
            if (pagesDiscovered.add(pageUrl)) {
                pagesToScrape.offer(pageUrl);
            }
        }
    }

    @SuppressWarnings("null")
    public static Job extractJob(Element jobElement) {
        try {
            Job job = new Job();
            job.setSalary(null);
            job.setSiteWeb("rekrute");
            job.setJobTitle(extractJobTitle(jobElement.selectFirst(".section h2 a").text(), "^(.*?)\\|"));
            job.setCity(extractJobCity(jobElement.selectFirst(".section h2 a").text(), "\\|\\s*([^,(]+)"));
            job.setActivitySector(jobElement.select(".holder ul li:first-child a").text());
            job.setFunction(jobElement.select(".holder ul li:nth-child(2) a").text());
            job.setRequiredExperience(jobElement.select(".holder ul li:nth-child(3) a").text());
            job.setStudyLevel(jobElement.select(".holder ul li:nth-child(4) a").text());
            job.setContractType(jobElement.select(".holder ul li:last-child a").text());
            job.setRemoteWork(extractRemoteWork(jobElement.select(".holder ul li:last-child").text()));
            String jobUrl = jobElement.selectFirst(".section h2 a").absUrl("href");
            job.setJobPageUrl(jobUrl);
            Document jobPage = createJsoupConnection(jobUrl);
            job.setJobDescription(jobPage.select(".contentbloc .col-md-12.blc:has(h2:contains(Post))").text());
            job.setSearchedProfile(jobPage.select(".contentbloc .col-md-12.blc:has(h2:contains(Profil))").text());
            job.setEntrepriseAddress(jobPage.select("#address").text());
            Element entreprise = jobPage.selectFirst("#recruiterDescription strong:first-child");
            job.setEntreprise(entreprise == null ? "" : entreprise.text());
            job.setPublicationDate(jobElement.select("em.date span:nth-child(2)").text());
            job.setApplyBefore(jobElement.select("em.date span:nth-child(3)").text());
            job.setEntrepriseDescription(jobPage.select("#recruiterDescription").text());
            job.setImageUrl(jobPage.select("img.photo").attr("src"));
            job.setLanguage(null);
            ArrayList<String> skills = new ArrayList<>();
            Elements skillsTags = jobPage.select(".jobdetail span.tagSkills");
            for (Element skill : skillsTags) {
                skills.add(skill.text());
            }
            String joinedSkills = String.join(", ", skills);
            job.setSoftSkills(joinedSkills);

            return job;
        } catch (IOException e) {
            System.err.println("Error extracting job: " + e.getMessage());
            return null;
        }
    }

    public static void scrapJobPage(String url, List<Job> jobs) {

        try {
            Document doc = createJsoupConnection(url);
            Elements jobsElements = doc.select("li.post-id");
            for (Element jobElement : jobsElements) {
                Job job = extractJob(jobElement);
                jobs.add(job);
            }
            setPaginationElements(doc);
        } catch (IOException e) {
            System.out.println("Error scraping page: " + url + " - " + e.getMessage());
        }
    }

    public static void startScrapping(List<Job> jobs) throws InterruptedException {
        pagesToScrape.add("https://www.rekrute.com/offres-emploi-maroc.html?s=3");
        scrapJobPage(pagesToScrape.poll(), jobs);
        ExecutorService executorService = Executors.newFixedThreadPool(THREAD_POOL_SIZE);

        while (!pagesToScrape.isEmpty()) {
        String url = pagesToScrape.poll();
        if (url == null) continue;
        executorService.submit(() -> scrapJobPage(url, jobs));
        TimeUnit.MILLISECONDS.sleep(800); // Rate limiting
        }
        executorService.shutdown();
        executorService.awaitTermination(800, TimeUnit.SECONDS);
    }

}
