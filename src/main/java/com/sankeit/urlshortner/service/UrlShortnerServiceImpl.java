package com.sankeit.urlshortner.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sankeit.urlshortner.dao.UrlDao;
import com.sankeit.urlshortner.model.Url;

@Service
public class UrlShortnerServiceImpl implements UrlShortnerService {

	private UrlDao dao;
	
	@Autowired
	public UrlShortnerServiceImpl(UrlDao dao) {
		this.dao = dao;
	}

	@Override
	public Url shorten(String longUrl) {
		 
		Url url = new Url();
		url.setUrl(longUrl);
		
		dao.save(url);
		
		return findByKey(url.getKey());
	}

	@Override
	public Url findByKey(String key) {
		return dao.findById(key).orElse(null);
	}

}
