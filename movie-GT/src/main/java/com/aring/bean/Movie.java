package com.aring.bean;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 电影信息
 * @author aring
 *
 */

@Entity	
@Table(name="movie",schema="movie_GT")
public class Movie extends BasicInfo{
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	
	@Basic
	private String director;
	@Basic
	private String scriptwriter;
	@Basic
	private String starring;
	@Basic
	private String type;
	@Basic
	private String from;
	@Basic
	private String language;
	@Basic
	private String time;
	@Basic
	private String introduction;
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	
	public String getDirector() {
		return director;
	}
	public void setDirector(String director) {
		this.director = director;
	}
	public String getScriptwriter() {
		return scriptwriter;
	}
	public void setScriptwriter(String scriptwriter) {
		this.scriptwriter = scriptwriter;
	}
	public String getStarring() {
		return starring;
	}
	public void setStarring(String starring) {
		this.starring = starring;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getFrom() {
		return from;
	}
	public void setFrom(String from) {
		this.from = from;
	}
	public String getLanguage() {
		return language;
	}
	public void setLanguage(String language) {
		this.language = language;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public String getIntroduction() {
		return introduction;
	}
	public void setIntroduction(String introduction) {
		this.introduction = introduction;
	}

	@Override
	public String toString() {
		return "Movie [id=" + id + ", director=" + director + ", scriptwriter=" + scriptwriter + ", starring="
				+ starring + ", type=" + type + ", from=" + from + ", language=" + language + ", time=" + time
				+ ", introduction=" + introduction + "]";
	}
	
	
	
}
