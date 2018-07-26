package com.codin.Oauth.Web;

import com.codin.Oauth.Models.LinkedInProfile;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.json.JSONObject;

import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class LinkedInConnection {

    public static String client_id = "77niymxy0tbuh4";
    public static String redirect_url = "http://localhost:8003/auth/linkedIn";
    public static String client_secret = "UmdtLTjoJ0VX7e8E";

    private static LinkedInProfile sendGet(String access_token) throws Exception {

        LinkedInProfile linkedInProfile = new LinkedInProfile();

        //String url = "https://api.linkedin.com/v1/people/~:(id,first-name,email-address,last-name,headline,picture-url,industry,summary,specialties,positions:(id,title,summary,start-date,end-date,is-current,company:(id,name,type,size,industry,ticker)),educations:(id,school-name,field-of-study,start-date,end-date,degree,activities,notes),associations,interests,num-recommenders,date-of-birth,publications:(id,title,publisher:(name),authors:(id,name),date,url,summary),patents:(id,title,summary,number,status:(id,name),office:(name),inventors:(id,name),date,url),languages:(id,language:(name),proficiency:(level,name)),skills:(id,skill:(name)),certifications:(id,name,authority:(name),number,start-date,end-date),courses:(id,name,number),recommendations-received:(id,recommendation-type,recommendation-text,recommender),honors-awards,three-current-positions,three-past-positions,volunteer)?format=json";
        String url = "https://api.linkedin.com/v1/people/~:(picture-urls::(original),email-address,public-profile-url,id,summary,specialties,num-connections-capped,num-connections,current-share,phonetic-first-name,formatted-name,first-name,last-name,maiden-name,headline,picture-url,industry,positions,educations,last-modified-timestamp,proposal-comments,associations,interests,publications,patents,languages,skills,certifications,courses,volunteer,three-current-positions,three-past-positions,num-recommenders,recommendations-received,following,job-bookmarks,suggestions,date-of-birth,member-url-resources,related-profile-views,honors-awards,location)?format=json";
        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();

        // optional default is GET
        con.setRequestMethod("GET");

        //add request header
        con.setRequestProperty("Host", "api.linkedin.com");
        con.setRequestProperty("Connection", "Keep-Alive");
        con.setRequestProperty("Authorization", "Bearer " + access_token);

        int responseCode = con.getResponseCode();
        System.out.println("\nSending 'GET' request to URL : " + url);
        System.out.println("Response Code : " + responseCode);

        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        //print result
        System.out.println(response.toString());

        JSONObject jsonObj = new JSONObject(response.toString());
        Gson gson = new GsonBuilder().create();

        linkedInProfile = gson.fromJson(String.valueOf(jsonObj), LinkedInProfile.class);
        //linkedInProfile.setFirstName(jsonObj.getString("firstName"));

        System.out.println(access_token);

        return linkedInProfile;
    }

    // HTTP POST request
    public LinkedInProfile sendPost(String code) throws Exception {

        String url = "https://www.linkedin.com/oauth/v2/accessToken";
        URL obj = new URL(url);
        HttpsURLConnection con = (HttpsURLConnection) obj.openConnection();

        //add reuqest header
        con.setRequestMethod("POST");
        con.setRequestProperty("Host", "www.linkedin.com");
        con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

        String urlParameters = "grant_type=authorization_code&code=" + code + "&redirect_uri=" + redirect_url + "&client_id=" + client_id + "&client_secret=" + client_secret + "";

        // Send post request
        con.setDoOutput(true);
        DataOutputStream wr = new DataOutputStream(con.getOutputStream());
        wr.writeBytes(urlParameters);
        wr.flush();
        wr.close();

        int responseCode = con.getResponseCode();
        System.out.println("\nSending 'POST' request to URL : " + url);
        System.out.println("Post parameters : " + urlParameters);
        System.out.println("Response Code : " + responseCode);

        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        //print result
        System.out.println(response.toString());

        JSONObject jsonObj = new JSONObject(response.toString());
        String access_token = jsonObj.getString("access_token");
        System.out.println(access_token);

        LinkedInProfile linkedInProfile = new LinkedInProfile();
        linkedInProfile = sendGet(access_token);

        return linkedInProfile;

    }


}