package core.planning.core.mobsim.passivePlanning.agents.agenda;

import core.planning.api.population.BasePerson;
import core.planning.core.mobsim.passivePlanning.agents.PassivePlannerDriverAgent;
import core.planning.core.population.PlacesSharer;
import core.planning.population.parallelPassivePlanning.PassivePlannerManager;
import org.matsim.api.core.v01.population.Activity;
import org.matsim.core.mobsim.qsim.interfaces.Netsim;

public class PassivePlannerAgendaAgent extends PassivePlannerDriverAgent {

	//Constructors
	public PassivePlannerAgendaAgent(final BasePerson basePerson, final Netsim simulation, final PassivePlannerManager passivePlannerManager) {
		super(basePerson, simulation, passivePlannerManager);
		planner = new SinglePlannerAgendaAgent(this);
	}
	
	//Methods
	@Override
	public void endActivityAndComputeNextState(double now) {
		Activity prevAct = (Activity)getCurrentPlanElement();
		((SinglePlannerAgendaAgent)planner).shareKnownPlace(prevAct.getFacilityId(), prevAct.getStartTime(), prevAct.getType());
		super.endActivityAndComputeNextState(now);
	}
	public void addKnownPerson(PlacesSharer sharer) {
		((SinglePlannerAgendaAgent)planner).addKnownPerson(sharer);
	}
	public PlacesSharer getPlaceSharer() {
		return ((SinglePlannerAgendaAgent)planner).getPlaceSharer();
	}
}
