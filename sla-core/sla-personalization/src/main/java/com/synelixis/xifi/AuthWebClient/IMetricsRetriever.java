/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.synelixis.xifi.AuthWebClient;

import java.util.Date;

import org.json.simple.JSONObject;

/**
 *
 * @author panos
 */
public interface IMetricsRetriever {
    
    
    public JSONObject getRegions();
    
    public JSONObject getRegion(String regionId_);
    
    public JSONObject getHosts(String regionId_);
    
    //public JSONObject getHost(String regionId_, String hostId_);
    public JSONObject getHost(String regionId_, String hostId_, Date date);
    
    public JSONObject getVMs(String regionId_);
    
    //public JSONObject getVM(String regionId_, String vmId_);
    public JSONObject getVM(String regionId_, String vmId_,  Date date);
    
    public JSONObject getHostServices(String regionId_, String hostId_);    
    
    public JSONObject getHostService(String regionId_, String hostId_, String serviceId_);
    
    public JSONObject getVMServices(String regionId_, String vmId_);
    
    public JSONObject getVMService(String regionId_, String vmId_, String serviceId_);
    
    public JSONObject getRegionServices(String regionId_);
    
    public JSONObject getRegionNEs(String regionId_);
    
    public JSONObject getRegionNE(String regionId_, String neId);
    
    public JSONObject getHost2Hosts();
    
    public JSONObject getHost2Host(String src_, String dest_);

}
