package com.sankeit.urlshortner.model;

import java.util.Date;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
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

}
