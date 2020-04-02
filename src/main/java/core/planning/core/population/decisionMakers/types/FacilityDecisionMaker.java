package core.planning.core.population.decisionMakers.types;

import org.matsim.api.core.v01.Coord;
import org.matsim.facilities.ActivityFacility;

public interface FacilityDecisionMaker extends DecisionMaker {

	//Methods
	ActivityFacility decideFacility(double time, Coord location, String typeOfActivity);

}
