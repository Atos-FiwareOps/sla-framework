package eu.atos.sla.service.types;

import eu.atos.sla.parser.data.wsag.Agreement;


/**
 * 
 * @author Elena Garrido
 */

public class AgreementParam {
	Agreement agreement;
	String originalSerialzedAgreement;
	public Agreement getAgreement() {
		return agreement;
	}
	public void setAgreement(Agreement agreement) {
		this.agreement = agreement;
	}
	public String getOriginalSerialzedAgreement() {
		return originalSerialzedAgreement;
	}
	public void setOriginalSerialzedAgreement(String originalSerialzedAgreement) {
		this.originalSerialzedAgreement = originalSerialzedAgreement;
	}
}
