package com.dpzz.lib_base.http;

import org.json.JSONObject;

import java.util.HashMap;

public class RequestParams {

    private String requestUrl;
    private HttpMethod httpMethod = HttpMethod.GET;
    private Class<?> aClass;
    private HashMap<String, Object> paramsMap = new HashMap<>();
    private HashMap<String, Object> headerMap = new HashMap<>();

    public RequestParams(String requestUrl) {
        this.requestUrl = requestUrl;
    }

    public RequestParams(String requestUrl, HttpMethod httpMethod) {
        this.requestUrl = requestUrl;
        this.httpMethod = httpMethod;
    }

    public RequestParams putParam(String key, Object value) {
        paramsMap.put(key, value);
        return this;
    }

    public RequestParams addHeader(String key, Object value) {
        headerMap.put(key, value);
        return this;
    }

    public <T> RequestParams setParseClass(Class<T> aclass) {
        this.aClass = aclass;
        return this;
    }

    public Class<?> getParseClass() {
        return aClass;
    }

    public String getRequestUrl() {
        return requestUrl;
    }

    public void setRequestUrl(String requestUrl) {
        this.requestUrl = requestUrl;
    }

    public void setHttpMethod(HttpMethod httpMethod) {
        this.httpMethod = httpMethod;
    }

    public HttpMethod getHttpMethod() {
        return httpMethod;
    }

    public HashMap<String, Object> getParamsMap() {
        return paramsMap;
    }

    public void setParamsMap(HashMap<String, Object> paramsMap) {
        this.paramsMap = paramsMap;
    }

    public HashMap<String, Object> getHeaderMap() {
        return headerMap;
    }

    public void setHeaderMap(HashMap<String, Object> headerMap) {
        this.headerMap = headerMap;
    }

    public String getParamsJson() {
        return new JSONObject(paramsMap).toString();
    }
}
