package com.htdong.core.service;

public interface ShortUrlService {

    String getUrl(String shortPath);

    void addShortUrl(String url);
}