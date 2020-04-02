package core.planning.core.population.decisionMakers;

import core.planning.core.population.decisionMakers.types.TypeOfActivityDecisionMaker;

import java.util.HashMap;
import java.util.Map;

public class NeedsDecisionMaker implements TypeOfActivityDecisionMaker {

	//Attributes
	private final Map<NeedType, Double> needs = new HashMap<NeedType, Double>();
	
	//Methods
	public void putNeed(NeedType needType, Double value) {
		needs.put(needType, value);
	}
	public Map<NeedType, Double> getNeeds() {
		return needs;
	}
	@Override
	public String decideTypeOfActivity(double time) {
		return null;
	}
	
}
