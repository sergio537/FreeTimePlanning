package core.planning.core.population.decisionMakers.types;

import org.matsim.api.core.v01.Id;
import org.matsim.facilities.ActivityFacility;

public interface ModeDecisionMaker extends DecisionMaker {

	//Methods
	String decideMode(double time, Id<ActivityFacility> startFacilityId, Id<ActivityFacility> endFacilityId);
	
}
