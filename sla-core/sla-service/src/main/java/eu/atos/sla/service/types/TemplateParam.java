package eu.atos.sla.service.types;

import eu.atos.sla.parser.data.wsag.Template;


/**
 * 
 * @author Elena Garrido
 */

public class TemplateParam {
	Template template;
	String originalSerialzedTemplate;
	
	public Template getTemplate() {
		return template;
	}
	public void setTemplate(Template template) {
		this.template= template;
	}
	public String getOriginalSerialzedTemplate() {
		return originalSerialzedTemplate;
	}
	public void setOriginalSerialzedTemplate(String originalSerialzedTemplate) {
		this.originalSerialzedTemplate = originalSerialzedTemplate;
	}
}
