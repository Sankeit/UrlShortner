package com.sankeit.urlshortner.service;

import org.springframework.stereotype.Service;

import com.sankeit.urlshortner.dao.UrlDao;
import com.sankeit.urlshortner.model.Url;

@Service
public class UrlShortnerServiceImpl implements UrlShortnerService {

	private UrlDao dao;
	
	public UrlShortnerServiceImpl(UrlDao dao) {
		this.dao = dao;
	}

	@Override
	public Url shorten(String longUrl) {
		return shorten(longUrl, null);
	}

	@Override
	public Url shorten(String longUrl, String customKey) {
		 
		Url url = new Url();
		url.setUrl(longUrl);
		url.setCustomKey(customKey);
		
		url = dao.save(url);
		
		return url;
	}

	@Override
	public Url findByKey(String key) {
		return dao.findById(key).orElse(dao.findByCustomKey(key));
	}

	@Override
	public Url findByLongUrl(String longUrl) {
		return dao.findByUrl(longUrl);
	}
}
