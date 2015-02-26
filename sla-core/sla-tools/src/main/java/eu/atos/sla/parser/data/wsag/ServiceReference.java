package eu.atos.sla.parser.data.wsag;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "ServiceReference")
public class ServiceReference {

	@XmlAttribute(name = "Name", required = true)
	private String name;

	@XmlAttribute(name = "ServiceName", required = true)
	private String serviceName;

	public ServiceReference() {
	}

	/**
	 * Gets the value of the name property.
	 */
	public String getName() {
		return name;
	}

	/**
	 * Sets the value of the name property.
	 */
	public void setName(String value) {
		this.name = value;
	}

	/**
	 * Gets the value of the serviceName property.
	 * 
	 */
	public String getServiceName() {
		return serviceName;
	}

	/**
	 * Sets the value of the serviceName property.
	 */
	public void setServiceName(String value) {
		this.serviceName = value;
	}

}
