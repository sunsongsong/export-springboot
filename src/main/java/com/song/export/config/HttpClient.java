package com.song.export.config;

import org.springframework.web.client.RestTemplate;

import java.util.Map;

public class HttpClient {
    /**
     * 默认返回Map结果
     * @param url
     * @param params
     * @return
     */
    public Map post(String url, Map<String, String> params){
        RestTemplate restTemplate = new RestTemplate();
        Map result = restTemplate.postForObject(url, params, Map.class);
        return result;
    }

    /**
     * 默认返回Map结果
     * @param url
     * @return
     */
    public Map get(String url){
        RestTemplate restTemplate = new RestTemplate();
        Map result = restTemplate.getForObject(url, Map.class);
        return result;
    }

    /**
     * 支持自定义返回结果
     * @param url
     * @param params
     * @param responseType
     * @return
     */
    public Object post(String url,Map<String, String> params,Class responseType){
        RestTemplate restTemplate = new RestTemplate();
        Object object = restTemplate.postForObject(url, params, responseType);
        return object;
    }

    /**
     * 支持自定义返回结果
     * @param url
     * @param responseType
     * @return
     */
    public Object get(String url,Class responseType){
        RestTemplate restTemplate = new RestTemplate();
        Object object = restTemplate.getForObject(url, responseType);
        return object;
    }
}
