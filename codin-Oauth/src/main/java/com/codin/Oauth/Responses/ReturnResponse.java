package com.codin.Oauth.Responses;

import java.util.HashMap;

public class ReturnResponse {

    public HashMap<String, Object> response;

    public ReturnResponse() {
        response = new HashMap<String, Object>();
    }

    public void add(String name, Object object) {
        HashMap<String, Object> sub = new HashMap<String, Object>();
        sub.put(name, object);
        response.putAll(sub);
    }

    public Object get(String name) {
        return response.get(name);
    }
}
