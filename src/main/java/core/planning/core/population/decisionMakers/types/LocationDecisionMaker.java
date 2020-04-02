package core.planning.core.population.decisionMakers.types;

import org.matsim.api.core.v01.Coord;

public interface LocationDecisionMaker extends DecisionMaker {

	//Methods
	Coord decideLocation(double time);

}
