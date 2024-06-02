package com.sankeit.urlshortner.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sankeit.urlshortner.model.Url;

@Repository
public interface UrlDao extends JpaRepository<Url, String>{
	Url findByUrl(String longUrl);
	Url findByCustomKey(String customKey);
}
