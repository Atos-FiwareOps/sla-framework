package eu.atos.sla.parser.data.wsag;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "VariableSet")
public class VariableSet {

	@XmlElement(name = "Variable")
	private List<Variable> variables;

	public List<Variable> getVariables() {
		return variables;
	}

	public void setVariable(List<Variable> variables) {
		this.variables = variables;
	}
	

}
