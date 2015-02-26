package eu.atos.sla.util;

import eu.atos.sla.datamodel.IAgreement;
import eu.atos.sla.datamodel.ICompensation.IPenalty;
import eu.atos.sla.datamodel.IEnforcementJob;
import eu.atos.sla.datamodel.IProvider;
import eu.atos.sla.datamodel.ITemplate;
import eu.atos.sla.datamodel.IViolation;
import eu.atos.sla.parser.data.EnforcementJob;
import eu.atos.sla.parser.data.Penalty;
import eu.atos.sla.parser.data.Provider;
import eu.atos.sla.parser.data.Violation;
import eu.atos.sla.parser.data.wsag.Agreement;
import eu.atos.sla.parser.data.wsag.Context;
import eu.atos.sla.parser.data.wsag.Template;

public interface IModelConverter {

	public IAgreement getAgreementFromAgreementXML(Agreement agreementXML, String payload) throws ModelConversionException;

	public ITemplate getTemplateFromTemplateXML(Template templateXML, String payload) throws ModelConversionException;

	public IEnforcementJob getEnforcementJobFromEnforcementJobXML(EnforcementJob enforcementJobXML) throws ModelConversionException;

	public Context getContextFromAgreement(IAgreement agreement) throws ModelConversionException;
	
	public IProvider getProviderFromProviderXML(Provider providerXML);

	public EnforcementJob getEnforcementJobXML(IEnforcementJob enforcementJob);

	public Provider getProviderXML(IProvider provider);
	
	public Violation getViolationXML(IViolation violation);
	
	public Penalty getPenaltyXML(IPenalty penalty);

}