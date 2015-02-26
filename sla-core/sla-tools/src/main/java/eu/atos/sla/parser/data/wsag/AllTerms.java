package eu.atos.sla.parser.data.wsag;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "All")
public class AllTerms {

	@XmlElement(name = "ServiceDescriptionTerm")
	private ServiceDescriptionTerm serviceDescriptionTerm;
	@XmlElement(name = "ServiceProperties")
	private List<ServiceProperties> serviceProperties;
	@XmlElement(name = "GuaranteeTerm")
	private List<GuaranteeTerm> guaranteeTerms;

	public AllTerms() {
	}

	public ServiceDescriptionTerm getServiceDescriptionTerm() {
		return serviceDescriptionTerm;
	}

	public void setServiceDescriptionTerm(
			ServiceDescriptionTerm serviceDescriptionTerm) {
		this.serviceDescriptionTerm = serviceDescriptionTerm;
	}

	public List<ServiceProperties> getServiceProperties() {
		return serviceProperties;
	}

	public void setServiceProperties(List<ServiceProperties> serviceProperties) {
		this.serviceProperties = serviceProperties;
	}

	public List<GuaranteeTerm> getGuaranteeTerms() {
		return guaranteeTerms;
	}

	public void setGuaranteeTerms(List<GuaranteeTerm> guaranteeTerms) {
		this.guaranteeTerms = guaranteeTerms;
	}

	

}
