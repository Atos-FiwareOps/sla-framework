package eu.atos.xifi.sla.monitoring;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Value;

import com.synelixis.xifi.AuthWebClient.Client;

import eu.atos.sla.datamodel.IGuaranteeTerm;
import eu.atos.sla.monitoring.IMetricsRetrieverV2;
import eu.atos.sla.monitoring.IMonitoringMetric;

public class OrionMetricsRetriever implements IMetricsRetrieverV2 {
	private static Logger logger = LoggerFactory.getLogger(OrionMetricsRetriever.class);

	//Prefix of the Guarantee Terms of the agreements
	private static String PREFIX_GT = "GT_";
	
	@Value("ORION{fm_url}")
	private String fm_url = null;
	@Value("ORION{token_url}")
	private String token_url = null;
	@Value("ORION{username}")
	private String username = null;
	@Value("ORION{password}")
	private String password = null;
	
	static com.synelixis.xifi.AuthWebClient.IMetricsRetriever metricRetriever = null;
	
	public void init(){
		if (metricRetriever==null){
			logger.info("Configuring client with [fm_url: "+fm_url+", token_url: "+token_url+", username: "+username+", password: "+password+"]");
			metricRetriever = new Client(fm_url, token_url, username, password);
		}

	}
	

	@Override
	public List<IMonitoringMetric> getMetrics(String agreementId,
			String serviceScope, String variable, Date begin, Date end,
			int maxResults) {
		logger.info("Not used" );
		return null;
	}

	
	public class OrionMonitoringMetric implements IMonitoringMetric {
		private String metricKey;
		private String metricValue;
		private Date date;

		public OrionMonitoringMetric(String metricKey, String metricValue) {
			this(metricKey, metricValue, null); 
		}

		public OrionMonitoringMetric(String metricKey, String metricValue, Date date) {
			this.metricKey = metricKey;
			this.metricValue = metricValue;
			if (date == null) this.date = new Date();
			else this.date = date;
		}

		@Override
		public String getMetricKey() {
	
			return metricKey;
		}
	
		@Override
		public String getMetricValue() {
	
			return metricValue;
		}
	
		@Override
		public Date getDate() {
			return date;
		}
	}


	@Override
	public Map<IGuaranteeTerm, List<IMonitoringMetric>> getMetrics(
			String agreementId, List<RetrievalItem> retrievalItems,
			int maxResults) {
		HashMap<IGuaranteeTerm, List<IMonitoringMetric>> result = new HashMap<IGuaranteeTerm, List<IMonitoringMetric>>();
		logger.info("StartOf getMetrics -  agreementId:"+agreementId + " maxResults:"+maxResults );
		Hashtable<String, JSONObject> hastableWithJsonObjects = getListOfJSONObjectWithMeasures(retrievalItems);

		for (RetrievalItem ri: retrievalItems){
			
			String metricVariable;
			//Normalize the metric name in case that this include the prefix
			if (ri.getVariable().indexOf(PREFIX_GT) == -1) {
				metricVariable = ri.getVariable();
			}else{
				metricVariable = ri.getVariable().substring(PREFIX_GT.length());
			}
			
			logger.info("Will retrieve for variable:"+metricVariable+ " serviceName:"+ri.getGuaranteeTerm().getServiceName());
			JSONObject jsonobject = hastableWithJsonObjects.get(ri.getGuaranteeTerm().getServiceName());
			OrionMonitoringMetric orionMonitoringMetric = parseOrionMetric(jsonobject, metricVariable);
			if (orionMonitoringMetric!=null){
				List<IMonitoringMetric> list = result.get(ri.getGuaranteeTerm());
				if (list==null){
					list = new ArrayList<IMonitoringMetric>();
					result.put(ri.getGuaranteeTerm(), list);
				}
				list.add(orionMonitoringMetric);
			}else{
				logger.info("No metric retrieved for variable:"+metricVariable+ " serviceName:"+ri.getGuaranteeTerm().getServiceName());
			}
		}
		
		logger.info("EndOf getMetrics -  agreementId:"+agreementId + " maxResults:"+maxResults );
		return result;
	}

	private JSONObject getMeasuresFromOrion(String serviceName){ 
		init();
		JSONObject jsonobjectFromMonitor = null;
		logger.info("StartOf getMeasuresFromOrion - serviceName:"+serviceName );
	
		String []typeAndLocation = serviceName.split("\\|");
		if (typeAndLocation.length==2){
			logger.info("Requesting monitoring information for type: "+typeAndLocation[0]+ " and location:"+typeAndLocation[1]);
			
			if ("vm".equals(typeAndLocation[0])){
				//metricRetriever.getVM(regionId_, vmId_)	
			}
			if ("host".equals(typeAndLocation[0])){
				String []regionAndNode = typeAndLocation[1].split(":");
		        jsonobjectFromMonitor = metricRetriever.getHost(regionAndNode[0],regionAndNode[1], null);
				logger.info("Received object : "+jsonobjectFromMonitor);
			}
		}else{
			logger.info("The service serviceName should have a format like typeOfResource|nameOfResource and what we're receiving is:"+serviceName+". Nothing can be done with this." );
		}
		return jsonobjectFromMonitor;
	}

	private Hashtable<String, JSONObject> getListOfJSONObjectWithMeasures(List<RetrievalItem> retrievalItems){
		HashSet<String> tmp = new HashSet<String>();
		// retrieve a list with the different serviceNames
		for (RetrievalItem ri:retrievalItems){
			String serviceName = ri.getGuaranteeTerm().getServiceName();
			tmp.add(serviceName);
		}
		
		Hashtable<String, JSONObject> result = new Hashtable<String, JSONObject>();
		Iterator<String> it = tmp.iterator(); 
		while(it.hasNext()){
			String serviceName = it.next();
			JSONObject object = getMeasuresFromOrion(serviceName);
			if (object!=null)
				result.put(serviceName, object);
		}
		return result;
	}
	
	
	// al metrics must have the same format!!!!
	private OrionMonitoringMetric parseOrionMetric(JSONObject jsonobjectFromMonitor, String variable){
		OrionMonitoringMetric result = null;
		if (jsonobjectFromMonitor!=null){
	        JSONArray measuresArray = (JSONArray)jsonobjectFromMonitor.get("measures");
	        JSONObject  measures = (JSONObject )measuresArray.get(0);
	        Date timestamp = null;
            String value =  (String)measures.get("timestamp");
        	timestamp = new Date(Long.valueOf(value));
            JSONObject measure = (JSONObject)measures.get(variable);
            if (measure!=null){
	            Object measureValue =  measure.get("value");
    			logger.info("measure "+variable+" with value: "+measureValue+ " will be added" );
	            result = new OrionMonitoringMetric(variable, measureValue.toString(), timestamp);
            }else{
            	logger.info("measure "+variable+" is null" );
            }
		}
		return result;
	}
}

