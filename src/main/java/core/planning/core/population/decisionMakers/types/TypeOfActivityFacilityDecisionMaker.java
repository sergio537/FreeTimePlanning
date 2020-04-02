package core.planning.core.population.decisionMakers.types;

import org.matsim.api.core.v01.Id;
import org.matsim.core.utils.collections.Tuple;
import org.matsim.facilities.ActivityFacility;

public interface TypeOfActivityFacilityDecisionMaker extends DecisionMaker {

	//Methods
	Tuple<String, Id<ActivityFacility>> decideTypeOfActivityFacility(double time, Id<ActivityFacility> startFacilityId);

}
