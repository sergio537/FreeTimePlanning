package core.planning.core.population.decisionMakers.types;

import org.matsim.api.core.v01.Id;
import org.matsim.facilities.ActivityFacility;

public interface StartTimeDecisionMaker extends DecisionMaker {

	//Methods
	double decideStartTime(double minimumStartTime, Id<ActivityFacility> facilityId);
	
}
