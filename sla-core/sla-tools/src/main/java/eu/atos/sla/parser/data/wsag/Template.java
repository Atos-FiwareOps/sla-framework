package eu.atos.sla.parser.data.wsag;


import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;


/**
 * A POJO Object that stores all the information from a Template
 * 
 * @author Pedro Rey - Atos
 */

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "Template")
public class Template {

	@XmlAttribute(name = "TemplateId")
	private String templateId;
	@XmlElement(name = "Name")
	private String Name;
	@XmlElement(name = "Context")
	private Context context;
	@XmlElement(name = "Terms")
	private Terms Terms;

	public String getTemplateId() {
		return templateId;
	}

	public void setTemplateId(String templateId) {
		this.templateId = templateId;
	}

	public String getName() {
		return Name;
	}

	public void setName(String name) {
		Name = name;
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

		return "Template[id='" + templateId + "']";
	}	
}