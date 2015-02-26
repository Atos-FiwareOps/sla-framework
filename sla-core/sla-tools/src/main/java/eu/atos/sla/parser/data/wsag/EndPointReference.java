package eu.atos.sla.parser.data.wsag;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * A POJO Object that stores all the information from a Agreement
 * 
 * @author Pedro Rey - Atos
 */


@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "EndPointReference")
public class EndPointReference {

	@XmlElement(name = "Address")
	private String address;
	@XmlElement(name = "ServiceName")
	private String serviceName;

	public EndPointReference() {
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getServiceName() {
		return serviceName;
	}

	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}

}
