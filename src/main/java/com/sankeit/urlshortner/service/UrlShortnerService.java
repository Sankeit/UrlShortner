package com.sankeit.urlshortner.service;

import com.sankeit.urlshortner.model.Url;

public interface UrlShortnerService {
	Url shorten(String longUrl);
	Url shorten(String longUrl, String customKey);
	Url findByKey(String key);
	Url findByLongUrl(String longUrl);
}
