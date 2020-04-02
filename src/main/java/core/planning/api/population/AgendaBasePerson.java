package core.planning.api.population;

import core.planning.core.population.decisionMakers.AgendaDecisionMaker;

public interface AgendaBasePerson extends BasePerson {

	//Methods
	AgendaDecisionMaker getAgendaDecisionMaker();

}
