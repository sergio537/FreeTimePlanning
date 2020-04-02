package core.planning.api.population;

import org.matsim.api.core.v01.population.Person;
import org.matsim.api.core.v01.population.Plan;

public interface BasePerson extends Person {

	//Methods
	Plan getBasePlan();

}
