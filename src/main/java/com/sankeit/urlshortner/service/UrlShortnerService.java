package com.sankeit.urlshortner.service;

import com.sankeit.urlshortner.model.Url;

public interface UrlShortnerService {
	Url shorten(String longUrl);
	Url findByKey(String key);
}
