package eu.atos.sla.parser.data.wsag;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "ServiceLevelObjective")
public class ServiceLevelObjective {

	@XmlElement(name = "KPITarget")
	private KPITarget kpitarget;

	public KPITarget getKpitarget() {
		return kpitarget;
	}

	public void setKpitarget(KPITarget kpitarget) {
		this.kpitarget = kpitarget;
	}

}
