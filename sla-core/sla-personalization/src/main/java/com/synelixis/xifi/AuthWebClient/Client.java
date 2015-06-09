/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.synelixis.xifi.AuthWebClient;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import org.apache.commons.codec.binary.Base64;

/**
 *
 * @author panos
 */
public class Client implements IMetricsRetriever {

    private String fm_url = null;
    private AToken token = null;
   
    public static void main(String[] args) {
		new Client();
    }

    public Client() {
        init();
    }

    // metthod created by ATOS
    public Client(String fm_url, String token_url, String username, String password, String secret_token, String secret_key) {
        this.token = new AToken(this, token_url, username, password, secret_token, secret_key);
        this.fm_url = fm_url;
    }
    
    
    private void init() {
        this.token = new AToken(this);
        Properties prop = new Properties();
        try {
            //load a properties file
            prop.load(new FileInputStream("client.cfg"));
            this.fm_url = (prop.getProperty("fm_url") == null) ? "" : prop.getProperty("fm_url");            
        } catch (IOException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
            return;
        }
    }
    
   public String postURLWithCredentials(String url_, String data_, String credentials_) {
    	return postURL(url_, data_, credentials_);
    	
    }
    
    public String postURLWithOutCredentials(String url_, String data_) {
    	return postURL(url_, data_, null);
    	
    }
    
    private String postURL(String url_, String data_, String credentials_) {
        String urlString = url_;
        String JSON = data_;
        String credentials = credentials_;
        
        try {
            URL u = new URL(urlString);
            HttpURLConnection c = (HttpURLConnection) u.openConnection();
            c.setDoOutput(true);
            c.setRequestMethod("POST");
            if ((credentials != null) && !credentials.equals("")){
            	c.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            	c.setRequestProperty("Authorization", "Basic "+credentials);
            }else{
            	c.setRequestProperty("Content-Type", "application/json");
                c.setRequestProperty("Accept", "application/json");
            }
            c.setUseCaches(false);
            c.setAllowUserInteraction(false);
            OutputStreamWriter out = new OutputStreamWriter(c.getOutputStream());
            out.write(JSON);
            out.flush();
            out.close();
            System.out.println("url:"+url_+" -- data:"+data_+ " -- response"+c.getResponseCode());
            switch (c.getResponseCode()) {
                case 200:
                case 201:
                    BufferedReader br = new BufferedReader(new InputStreamReader(c.getInputStream()));
                    StringBuilder sb = new StringBuilder();
                    String line;
                    while ((line = br.readLine()) != null) {
                        sb.append(line + "\n");
                    }
                    br.close();
                    String result = sb.toString();
                    return result;
            }
        } catch (MalformedURLException ex) {
            Logger.getLogger(com.synelixis.xifi.AuthWebClient.Client.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(com.synelixis.xifi.AuthWebClient.Client.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(com.synelixis.xifi.AuthWebClient.Client.class.getName()).log(Level.SEVERE,  "Unexpected exception when call the remote server ", ex);
        }
        return null;
    }

    public String getURL(String url_, String token_) {
        URL obj;
        try {
            obj = new URL(url_);
            HttpURLConnection c = (HttpURLConnection) obj.openConnection();

            // optional default is GET
            c.setRequestMethod("GET");

            //add request header
            //c.setRequestProperty("User-Agent", "Mozilla/5.0");
            //c.setRequestProperty("Content-Type", "application/json");
            //c.setRequestProperty("X-Auth-Token", token_);
            c.setRequestProperty("Authorization", "Bearer " + new String(Base64.encodeBase64(token_.getBytes())));
            c.setRequestProperty("accept", "application/json");
            c.setConnectTimeout(50000);
            int responseCode = c.getResponseCode();

            System.out.println("Response Code : " + responseCode);
            switch (c.getResponseCode()) {
                case 200:
                case 201:
                    BufferedReader in = new BufferedReader(
                            new InputStreamReader(c.getInputStream()));
                    String inputLine;
                    StringBuffer response = new StringBuffer();

                    while ((inputLine = in.readLine()) != null) {
                        response.append(inputLine);
                    }
                    in.close();
                    return response.toString();
            }
        } catch (MalformedURLException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        } catch (IOException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
        return null;
    }

    private JSONObject parseJson(String resp_) {
        JSONObject json = null;
        if (resp_==null) return null;
        try {
            json = (JSONObject) new JSONParser().parse(resp_);
        } catch (ParseException ex) {
            Logger.getLogger(AToken.class.getName()).log(Level.SEVERE, null, ex);
        }
        return json;
    }

    public JSONObject getRegions() {
        return parseJson(this.getURL(this.fm_url+"/monitoring/regions", this.token.getToken()));
    }

    public JSONObject getRegion(String regionId_) {
        return parseJson(this.getURL(this.fm_url+"/monitoring/regions/" + regionId_, this.token.getToken()));
    }

    public JSONObject getHosts(String regionId_) {
        return parseJson(this.getURL(this.fm_url+"/monitoring/regions/" + regionId_ + "/hosts", this.token.getToken()));
    }

    public JSONObject getHost(String regionId_, String hostId_, Date date) {
    	SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:MM");
    	String url = this.fm_url+"/monitoring/regions/" + regionId_ + "/hosts/" + hostId_;
    	if (date!=null) url += "?since="+dateFormat.format(date);
        return parseJson(this.getURL(url, this.token.getToken()));
    }

    public JSONObject getVMs(String regionId_) {
        return parseJson(this.getURL(this.fm_url+"/monitoring/regions/" + regionId_ + "/vms", this.token.getToken()));
    }

   public JSONObject getVM(String regionId_, String vmId_, Date date) {
    	SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:MM");
    	String url = this.fm_url+"/monitoring/regions/" + regionId_ + "/vms/" + vmId_;
    	if (date!=null) url += "?since="+dateFormat.format(date);
        return parseJson(this.getURL(url, this.token.getToken()));
    }

    public JSONObject getHostServices(String regionId_, String hostId_) {
        return parseJson(this.getURL(this.fm_url+"/monitoring/regions/" + regionId_ + "/hosts/" + hostId_ + "/services", this.token.getToken()));
    }

    public JSONObject getHostService(String regionId_, String hostId_, String serviceId_) {
        return parseJson(this.getURL(this.fm_url+"/monitoring/regions/" + regionId_ + "/hosts/" + hostId_ + "/services/" + serviceId_, this.token.getToken()));
    }

    public JSONObject getVMServices(String regionId_, String vmId_) {
        return parseJson(this.getURL(this.fm_url+"/monitoring/regions/" + regionId_ + "/vms/" + vmId_ + "/services", this.token.getToken()));
    }

    public JSONObject getVMService(String regionId_, String vmId_, String serviceId_) {
        return parseJson(this.getURL(this.fm_url+"/monitoring/regions/" + regionId_ + "/vms/" + vmId_ + "/services/" + serviceId_, this.token.getToken()));
    }

    public JSONObject getRegionServices(String regionId_) {
        return parseJson(this.getURL(this.fm_url+"/monitoring/regions/" + regionId_ + "/services", this.token.getToken()));
    }

    public JSONObject getRegionNEs(String regionId_) {
        return parseJson(this.getURL(this.fm_url+"/monitoring/regions/" + regionId_ + "/nes", this.token.getToken()));
    }

    public JSONObject getRegionNE(String regionId_, String neId_) {
        return parseJson(this.getURL(this.fm_url+"/monitoring/regions/" + regionId_ + "/nes/" + neId_, this.token.getToken()));
    }

    public JSONObject getHost2Hosts() {
        return parseJson(this.getURL(this.fm_url+"/monitoring/host2hosts", this.token.getToken()));
    }

    public JSONObject getHost2Host(String src_, String dest_) {
        return parseJson(this.getURL(this.fm_url+"/monitoring/host2hosts/" + src_ + ";" + dest_, this.token.getToken()));
    }

}
