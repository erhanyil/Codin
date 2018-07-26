package com.codin.Api.Responses;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class ReturnResponse {

    public LinkedHashMap<String, Object> response;
    protected String lastMethodName;

    public ReturnResponse() {
        clearResponse();
    }

    public ReturnResponse(LinkedHashMap<String, Object> response) {
        this.response = response;
    }

    public ReturnResponse response() {
        return new ReturnResponse(response);
    }

    public void addResponse(String to, Object list) {
        checkLastMethodName();
        LinkedHashMap<String, Object> sub = new LinkedHashMap<String, Object>();
        sub.put(to, list);
        this.response.putAll(sub);
    }

    public void addResponse(String to, String name, Object list) {
        checkLastMethodName();
        for (Map.Entry<String, Object> entry : response.entrySet()) {
            ArrayList value = (ArrayList) entry.getValue();
            String key = entry.getKey();
            if (key.equals(to)) {
                value.add(new HashMap() {{
                    put(name, list);
                }});
                System.out.println("addResponse --- Name->" + name + " added TO-> " + to);
                break;
            } else {
                findSubItem(to, name, list, value, 0);
            }
        }
    }

    protected void findSubItem(String to, String name, Object list, ArrayList value, Integer count) {
        for (Object item : value) {
            HashMap newHash = (HashMap) item;
            if (newHash.containsKey(to)) {
                ((ArrayList) newHash.get(to)).add(new HashMap() {{
                    put(name, list);
                }});
                System.out.println("findSubItem --- Name->" + name + " added TO-> " + to);
                break;
            } else {
                try {
                    findSubItem(to, name, list, (ArrayList) ((HashMap) item).values(), count);
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            }
        }
    }

    public Object getResponse(String name) {
        return this.response.get(name);
    }

    public Object getResponse() {
        return this.response;
    }

    public void clearResponse() {
        this.response = new LinkedHashMap<String, Object>();
        this.lastMethodName = "";
    }

    protected void checkLastMethodName() {
        if (!this.lastMethodName.equals(new Exception().getStackTrace()[2].getMethodName())) {
            clearResponse();
            this.lastMethodName = new Exception().getStackTrace()[2].getMethodName();
        }
    }
}
