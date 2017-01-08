package com.aring.bean;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

/**
 * 电影基本信息
 * @author aring
 *
 */

@MappedSuperclass
public class BasicInfo {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	Integer id;
	
	/**电影名*/
	@Basic
	String name;
	
	/**电影海报路径*/
	@Basic
	String path; 

	/**上映日期*/
	@Basic
	@Column(name="release_date")
	String releaseDate;
	
	/**影票价格*/
	@Basic
	Float price;
	
	/**影票数量*/
	@Basic
	Integer number;
	
	public Integer getId() {
		return id;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getReleaseDate() {
		return releaseDate;
	}

	public void setReleaseDate(String releaseDate) {
		this.releaseDate = releaseDate;
	}

	public Float getPrice() {
		return price;
	}

	public void setPrice(Float price) {
		this.price = price;
	}

	public Integer getNumber() {
		return number;
	}

	public void setNumber(Integer number) {
		this.number = number;
	}

	@Override
	public String toString() {
		return "BasicInfo [id=" + id + ", name=" + name + ", path=" + path + ", releaseDate=" + releaseDate + ", price="
				+ price + ", number=" + number + "]";
	}
	
}
