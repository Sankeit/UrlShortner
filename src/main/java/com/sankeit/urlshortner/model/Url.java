package com.sankeit.urlshortner.model;

import java.util.Date;
import java.util.Objects;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "URL")
public class Url {

	@Id
	@Column(name = "URL_KEY", unique = true)
	@GeneratedValue(generator = "triggerIdGenerator")
	@GenericGenerator(
			name = "triggerIdGenerator",
			type = com.sankeit.urlshortner.dao.UrlKeyGenerator.class)
	private String key;

	@Column(name = "CUSTOM_KEY")
	private String customKey;
	
	@Column(name = "URL")
	private String url;

	@CreationTimestamp
	@Column(name = "CREATED_ON", updatable = false)
	private Date createdOn;

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getCustomKey() {
		return customKey;
	}

	public void setCustomKey(String customKey) {
		this.customKey = customKey;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Date getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}

	@Override
	public int hashCode() {
		return Objects.hash(createdOn, customKey, key, url);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Url other = (Url) obj;
		return Objects.equals(createdOn, other.createdOn) && Objects.equals(customKey, other.customKey)
				&& Objects.equals(key, other.key) && Objects.equals(url, other.url);
	}

	@Override
	public String toString() {
		return "Url [key=" + key + ", customKey=" + customKey + ", url=" + url + ", createdOn=" + createdOn + "]";
	}
	
	

}
