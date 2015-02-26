package eu.atos.sla.parser.data.wsag;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;


/**
 * A POJO Object that stores all the information from a Agreement
 * 
 * @author Pedro Rey - Atos
 */

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "Agreement")
public class Agreement {

	@XmlAttribute(name = "AgreementId")
	private String agreementId;
	@XmlElement(name = "Name")
	private String name;
	@XmlElement(name = "Context")
	private Context context;
	@XmlElement(name = "Terms")
	private Terms Terms;

	public String getAgreementId() {
		return agreementId;
	}

	public void setAgreementId(String agreementId) {
		this.agreementId = agreementId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Context getContext() {
		return context;
	}

	public void setContext(Context context) {
		this.context = context;
	}

	public Terms getTerms() {
		return Terms;
	}

	public void setTerms(Terms terms) {
		Terms = terms;
	}

	@Override
	public String toString() {

		return "Agreement[id='" + agreementId + "']";
	}
}