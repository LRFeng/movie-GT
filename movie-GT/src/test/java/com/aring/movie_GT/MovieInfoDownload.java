package com.aring.movie_GT;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import com.aring.bean.Movie;

/**
 * 电影信息爬虫
 * 
 * 针对豆瓣电影的某个电影的详细页面，需要提供URL
 * @author aring
 *
 */
public class MovieInfoDownload {

	public static String downloadPage(String urlStr) throws Exception{
		URL url = new URL(urlStr);
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		InputStream is = conn.getInputStream();
		String result=null;
		InputStreamReader isr = new InputStreamReader(is,"UTF-8");			
		BufferedReader br = new BufferedReader(isr);
		StringBuilder builder = new StringBuilder();
	    String line="";
		while ((line = br.readLine()) != null) {
		   builder.append(line);
		}
		result = builder.toString();
		br.close();
		isr.close();		  
	    return result;
	}

	public static String getName(String source){
		String pattern="<span property=\"v:itemreviewed\">(.*?)</span>";
		Pattern r = Pattern.compile(pattern);
		Matcher m = r.matcher(source);
		String name = null;
		while(m.find()){
			name = m.group(1);
		}
		return name;
	}
	
	
	public static String regexOne(String source,String pattern){
		Pattern r = Pattern.compile(pattern);
		Matcher m = r.matcher(source);
		String s1 = null;
		while(m.find()){
			s1 = m.group(0);
		}
		return s1;
	}
	
	public static String regexGetGroup1(String source,String pattern){
		Pattern r = Pattern.compile(pattern);
		Matcher m = r.matcher(source);
		String s = "";
		while(m.find()){
			if(s=="") {
				s = m.group(1);
			}else{
				s=s+"/"+m.group(1);
			}
		}
		return s;
	}
	
	
	
	public static String getImageName(String source){
		String p1="<div id=\"mainpic\".*?</div>";
		String s1 = regexOne(source, p1);
		String p2="<img src=\"(.*?(p\\d.*?.jpg))\"";
		Pattern r2 = Pattern.compile(p2);
		Matcher m2 = r2.matcher(s1);
		String url = null;
		String imagename = null;
		while(m2.find()){
			url = m2.group(1);
			imagename = m2.group(2);
		}
		
		return "/file/"+imagename;
	}
	
	
	
	
	public static Movie getMovieInfo(String source){
		String p="<div id=\"info\".*?</div>";
		String s1 = regexOne(source, p);
		
		String pattern = "<span class=.pl.>.*?<br/>";
		Pattern r = Pattern.compile(pattern);
		Matcher m = r.matcher(s1);
		List<String> ss = new ArrayList<>();
		while(m.find()){
			ss.add(m.group(0));
		}
		
		Movie movie = new Movie();
		
		//导演
		pattern = "<a.*?>(.*?)</a>";
		String result =	regexGetGroup1(ss.get(0), pattern);
		movie.setDirector(result);
		
		//编剧
		result = regexGetGroup1(ss.get(1), pattern);
		movie.setScriptwriter(result);
		
		//主演
		result = regexGetGroup1(ss.get(2), pattern);
		movie.setStarring(result);
		
		//类型
		pattern = "<span property.*?>(.*?)</span>";
		result = regexGetGroup1(ss.get(3), pattern);
		movie.setType(result);
		
		//制片地区
		pattern = "</span>(.*?)<br/>";
		result = regexGetGroup1(ss.get(4), pattern);
		movie.setFrom(result);
		
		
		//语言
		result = regexGetGroup1(ss.get(5), pattern);
		movie.setLanguage(result);
				
				
		//上映日期
		pattern = "<span property=.*?>(.*?)</span>";
		result = regexGetGroup1(ss.get(6), pattern);
		movie.setReleaseDate(result);
		
		//片长
		result = regexGetGroup1(ss.get(7), pattern);
		movie.setTime(result);
		
		return movie;
	}
	
	
	public static String getIntroduction(String source){
		String pattern = "<div class=\"indent\".*?</div>";
		String s = regexOne(source, pattern);
		pattern = "<span property.*?>\\s*(.*?)\\s*</span>";
		return regexGetGroup1(s, pattern);
	}
	
	
	
	public static void main(String[] args){
		try {
			String url = "https://movie.douban.com/subject/26879060/?from=showing";
			
			String allPage = downloadPage(url);
			String smallPage=null;
			
			String pattern = "<div id=\"content\">.*?<div id=\"related-pic\"";
			Pattern r = Pattern.compile(pattern);
			Matcher m = r.matcher(allPage);
			while(m.find()){
				smallPage = m.group(0);
			}
			
			Movie movie = getMovieInfo(smallPage);
			movie.setName(getName(smallPage));
			movie.setPath(getImageName(smallPage));
			movie.setIntroduction(getIntroduction(smallPage));
			System.out.println(movie);
			
			EntityManagerFactory factory = Persistence.createEntityManagerFactory("Movie");
			EntityManager manager = factory.createEntityManager();
			
			EntityTransaction ts = manager.getTransaction();
			ts.begin();
			movie.setPrice(48.0f);
			movie.setNumber(300);
			manager.persist(movie);
			ts.commit();
			if(ts.isActive()){
				ts.rollback();
			}
			manager.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
