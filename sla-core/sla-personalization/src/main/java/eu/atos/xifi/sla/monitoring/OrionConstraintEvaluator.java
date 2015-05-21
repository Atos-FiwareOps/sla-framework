package eu.atos.xifi.sla.monitoring;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import eu.atos.sla.evaluation.constraint.IConstraintEvaluator;
import eu.atos.sla.evaluation.constraint.simple.SimpleConstraintEvaluator;
import eu.atos.sla.evaluation.constraint.simple.SimpleConstraintParser;
import eu.atos.sla.evaluation.constraint.simple.SimpleConstraintParser.SimpleConstraintElements;
import eu.atos.sla.monitoring.IMonitoringMetric;

/* egarrido 
 * it is possible to configure the file in order to use eu.atos.sla.evaluation.constraint.simple.SimpleConstraintEvaluator
 * directly. We are preparing this just to test and decide if we need it.
 * */

public class OrionConstraintEvaluator implements IConstraintEvaluator {
	private static Logger logger = LoggerFactory.getLogger(OrionConstraintEvaluator.class);
	static SimpleConstraintParser parser = new SimpleConstraintParser();
	static SimpleConstraintEvaluator evaluator = new SimpleConstraintEvaluator();
	@Override
	public List<IMonitoringMetric> evaluate(String kpiName, String constraint,
			List<IMonitoringMetric> metrics) {
		logger.info("StartOf evaluate -  kpiName:"+kpiName+" - constraint:"+constraint+ "- metrics:"+metrics);
		evaluator.evaluate(kpiName, constraint, metrics);
		List<IMonitoringMetric> result = evaluator.evaluate(kpiName, constraint, metrics);
		logger.info("EndOf evaluate");
		return result;
	}

	@Override
	public String getConstraintVariable(String constraint) {
		String result = null;
		synchronized (parser){
			SimpleConstraintElements elems = parser.parse(constraint);
			result = elems.getLeft();
		}
		return result;
	}

}
