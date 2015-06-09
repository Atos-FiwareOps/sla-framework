/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.synelixis.xifi.fmonitor;

import com.synelixis.xifi.AuthWebClient.Client;
import com.synelixis.xifi.AuthWebClient.IMetricsRetriever;

/**
 *
 * @author panos
 */
public class FMonitor {

    public static void main(String[] args) {

   		String fm_url = "http://193.205.211.69:1026";
   		String token_url = "http://cloud.lab.fi-ware.org:4730/v2.0/tokens";

   		String username = "elena.garrido@atos.net";
		String password = "hola02";
		String secret_token = "767";
		String secret_key = "d8266ec1300898f60025bb08cbd701b40faf3f767ab64c1c6e703315ee42bfe416bbed90168c2c803f91e29a9dcaf06e52924f9fbb69547ce69bf22762022d4e";
		 

        IMetricsRetriever metricRetriever = new Client(fm_url, token_url, username, password, secret_token, secret_key);

        //List All Regions
        System.out.println("GET all federetion Regions");
        System.out.println(metricRetriever.getRegions()+"\n");
        
        //Retrieve a Region
        System.out.println("Retrieve a Region");
        System.out.println(metricRetriever.getRegion("Trento")+"\n");
     
        //Retrieve all hosts in a given region.
        System.out.println("Retrieve all hosts in a given region.");
        System.out.println(metricRetriever.getHosts("Trento")+"\n");
        
        //Retrieve an host
        System.out.println("Retrieve an host");
        System.out.println(metricRetriever.getHost("Trento","node-1", null));
        /*
        String serviceScope = "host|Trento:node-1";

		String []typeAndLocation = serviceScope.split("\\|");
	
		if ("vm".equals(typeAndLocation[0])){
			//metricRetriever.getVM(regionId_, vmId_)	
		}
		if ("host".equals(typeAndLocation[0])){
			String []regionAndNode = typeAndLocation[1].split(":");
		//	JSONObject jsonobject = metricRetriever.getHost(regionAndNode[0], regionAndNode[1]);
	        //System.out.println("1-->"+jsonobject);
		}
		
        
        System.out.println("Retrieve an host");
        String variable = "percDiskUsed";
        JSONObject jsonobjectFromMonitor = metricRetriever.getHost("Trento","node-1", null);
        JSONArray measuresArray = (JSONArray)jsonobjectFromMonitor.get("measures");
        JSONObject  measures = (JSONObject )measuresArray.get(0);
        Date timestamp = null;
        String value =  (String)measures.get("timestamp");
    	timestamp = new Date(Long.valueOf(value));
        JSONObject measure = (JSONObject)measures.get(variable);
        if (measure!=null){
            Object measureValue =  measure.get("value");
            System.out.println(measureValue);
            System.out.println(timestamp);
        }else{
        }

        //Long jsonobject5 = (Long);
        //System.out.println("5-->"+ jsonobject5);*/

        
        
        //Retrieve all VMs on a given region.
        System.out.println("Retrieve all VMs on a given region.");
        System.out.println(metricRetriever.getVMs("Trento")+"\n");
        
        //Retrieve a vm.
        System.out.println("Retrieve a vm");
        System.out.println(metricRetriever.getVM("Trento","ac79158d-8387-4304-a771-319f4f5b22ac", null)+"\n");
        
        //Retrieve all Services running on a given host..
        System.out.println("Retrieve all Services running on a given host.");
        System.out.println(metricRetriever.getHostServices("Trento", "node-1")+"\n");
        
        //Retrieve all Services running on a given host.
        System.out.println("Retrieve all Services running on a given host.");
        System.out.println(metricRetriever.getHostServices("Trento", "node-1")+"\n");  
        
        //List all Services running on a given region
        System.out.println("List all Services running on a given region");
        System.out.println(metricRetriever.getRegionServices("Trento")+"\n");
        
        //List all Services running on a given VM
        System.out.println("List all Services running on a given VM");
        System.out.println(metricRetriever.getVMServices("Trento","ac79158d-8387-4304-a771-319f4f5b22ac")+"\n");
        
        //Retrieve a service belonging to an vm
    //    System.out.println("Retrieve a service belonging to an vm");
    //    System.out.println(mret.getVMService("Trento","ac79158d-8387-4304-a771-319f4f5b22ac",""));
        
        //List all NEs for a given Region on a given Host
        System.out.println("List all NEs for a given Region on a given Host");
        System.out.println(metricRetriever.getRegionNEs("Trento")+"\n");
    
        //Retrieve a network element
    //    System.out.println("List all NEs for a given Region on a given Host");
    //    System.out.println(mret.getRegionNE("Trento",""));
        
        //Retrieve all host2hosts.
        System.out.println("Retrieve all host2hosts");
        System.out.println(metricRetriever.getHost2Hosts()+"\n");    
        
        //Retrieve an host2host.
        System.out.println("Retrieve an host2host");
        System.out.println(metricRetriever.getHost2Host("Waterford-193.1.202.133", "Berlin-193.175.132.203")+"\n");
          
    }


}
