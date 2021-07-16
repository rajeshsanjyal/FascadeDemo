package com.asterisk.facade;

import org.jsoup.HttpStatusException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class FascadeDemo {
    public static void main(String[] args) throws IOException {

        System.out.println("Enter a keyword to search");
        Scanner scanner=new Scanner(System.in);
        String jobName=scanner.nextLine();
        int i=1;
        boolean exists=true;
        try {
            while (exists) {

                Document doc = Jsoup.connect("https://merojob.com" + "/search/?q=" + jobName + "&page=" + (i++)).get();
                Elements elements = doc.select("div.hover-shadow");
//        System.out.println(elements);
                List<JobDetail> jobDetails = new ArrayList<>();

                for (Element element : elements) {
                    Elements jobTitle = element.select("h1.text-primary").select("a");
                    Elements image = element.select("img.border");
                    Elements companyName = element.select("h3");
                    Elements companyAddress = element.select("span.text-muted > span");
                    Elements keySkills = element.select("span.badge");
                    Elements deadLine = element.select("div.card-footer > p > span");
                    Elements views = element.select("div.dropdown > span.text-primary");

                    JobDetail jobDetail = new JobDetail();
                    jobDetail.setTitle(jobTitle.text());
                    jobDetail.setCompanyImageUrl("https://merojob.com" + image.attr("src"));
                    jobDetail.setCompany(companyName.text());
                    jobDetail.setCompanyAddress(companyAddress.text());

                    List<String> skills = new ArrayList<>();

                    for (Element skill : keySkills) {
                        Elements s = skill.select("s");
                        skills.add(skill.text());
                    }
                    jobDetail.setKeySkills(skills);
                    jobDetail.setDeadLines(deadLine.text());
                    jobDetail.setViews(views.text());
                    System.out.println(jobDetail);
                }

            }
        }catch(HttpStatusException e){
            System.out.println("The Program terminated");
        }

    }
}
