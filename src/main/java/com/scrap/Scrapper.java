package com.scrap;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class Scrapper {
  private static final String USER_AGENT = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/124.0.0.0 Safari/537.36";

  public static String getUserAgent(){
    return USER_AGENT;
  }

  public static String extractJobCity(String input, String regex){
    Pattern pattern = Pattern.compile(regex);
    Matcher matcher = pattern.matcher(input);

    if(matcher.find()){
        return matcher.group(1).trim();
    }
    return null;
  }

  public static String extractJobTitle(String input, String regex){
    Pattern pattern = Pattern.compile(regex);
    Matcher matcher = pattern.matcher(input);

    if(matcher.find()){
        return matcher.group().trim();
    }
    return null;
  }

  public static Document createJsoupConnection(String url) throws IOException{
    return Jsoup
          .connect(url)
          .header("Accept-Language", "*")
          .get();
  }





}
