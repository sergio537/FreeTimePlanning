package core.planning.core.population.decisionMakers.types;

import org.matsim.api.core.v01.Id;
import org.matsim.facilities.ActivityFacility;

public interface EndTimeDecisionMaker extends DecisionMaker {

	//Methods
	double decideEndTime(double startTime, double maximumEndTime, String typeOfActivity, Id<ActivityFacility> facilityId);
	
}
