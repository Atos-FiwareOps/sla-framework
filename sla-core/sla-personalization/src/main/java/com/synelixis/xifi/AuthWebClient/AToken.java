/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.synelixis.xifi.AuthWebClient;


import java.io.FileInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Properties;
import java.util.TimeZone;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 *
 * @author panos
 */
public class AToken {

    protected String resp = "";
    protected String token_expires = null;
    protected String token_id = null;
    protected User user = null;
    protected Client clnt = null;
    protected String fm_url = null;
    protected String token_url = null;
    protected String username = null;
    protected String password = null;
    
    public static void main(String[] args) {
        new AToken();

    }

    public AToken() {
    }
    
    public AToken(Client clnt_) {
        this.clnt = clnt_;
        Properties prop = new Properties();
        try {
            //load a properties file
            prop.load(new FileInputStream("client.cfg"));
            this.token_url = (prop.getProperty("token_url") == null) ? "" : prop.getProperty("token_url");
            this.username = (prop.getProperty("username") == null) ? "" : prop.getProperty("username");
            this.password = (prop.getProperty("password") == null) ? "" : prop.getProperty("password");
            
        } catch (IOException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
            return;
        }
    }

    // metthod created by ATOS    
    public AToken(Client clnt_, String token_url, String username, String password) {
        this.clnt = clnt_;
        
        this.token_url = (token_url == null) ? "" : token_url;
        this.username = (username == null) ? "" : username;
        this.password = (password == null) ? "" : password;
    }
    
    public String getToken() {
        if (!isTokenValid()) {
            updateToken();
        }
        return this.token_id;
    }

    private boolean isTokenValid() {
        try {
            if (this.token_id == null) {
                return false;
            }
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
            Date tokenExp = formatter.parse(this.token_expires);
            Date curDate = this.getGMTime();
            Long diff = tokenExp.getTime() - curDate.getTime();
            if (diff / 3600000 < 1) {
                return false;
            } else {
                return true;
            }
        } catch (java.text.ParseException ex) {
            Logger.getLogger(AToken.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    private void updateToken() {
        
        String resp = this.clnt.postURL(this.token_url, "{\"auth\": {\"passwordCredentials\": {\"username\":\""+this.username+"\", \"password\":\""+this.password+"\"}}}");
        
        JSONObject json = null;
        try {
            json = (JSONObject) new JSONParser().parse(resp);
        } catch (ParseException ex) {
            Logger.getLogger(AToken.class.getName()).log(Level.SEVERE, null, ex);
        }
        JSONObject access = (JSONObject) json.get("access");
        if (access != null) {
            JSONObject token = (JSONObject) access.get("token");
            if (token != null) {
                this.token_id = token.get("id").toString();
                this.token_expires = token.get("expires").toString();
            }
            JSONObject user = (JSONObject) access.get("user");
            if (user != null) {
                this.user = new User(user.get("name").toString(), user.get("id").toString(), user.get("username").toString());
            }
            
        }
        return;
    }

    private Date getGMTime() {
        Calendar c = Calendar.getInstance();
        System.out.println("current: " + c.getTime());

        TimeZone z = c.getTimeZone();
        int offset = z.getRawOffset();
        if (z.inDaylightTime(new Date())) {
            offset = offset + z.getDSTSavings();
        }
        int offsetHrs = offset / 1000 / 60 / 60;
        int offsetMins = offset / 1000 / 60 % 60;
        c.add(Calendar.HOUR_OF_DAY, (-offsetHrs));
        c.add(Calendar.MINUTE, (-offsetMins));
        return c.getTime();

    }

}

class User {

    String user_username = null;
    String user_id = null;
    String user_name = null;

    public User(String username_, String id_, String name_) {
        this.user_username = username_;
        this.user_id = id_;
        this.user_name = name_;
    }

    public String getUser_username() {
        return user_username;
    }

    public String getUser_id() {
        return user_id;
    }

    public String getUser_name() {
        return user_name;
    }

}
