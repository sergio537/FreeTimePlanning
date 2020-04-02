package core.planning.core.mobsim.passivePlanning.agents.agenda;

import java.util.List;

import core.planning.api.population.AgendaBasePerson;
import core.planning.core.mobsim.passivePlanning.agents.PassivePlannerDriverAgent;
import core.planning.core.mobsim.passivePlanning.agents.SinglePlannerAgentImpl;
import core.planning.core.population.PlacesSharer;
import core.planning.core.population.decisionMakers.AgendaDecisionMaker;
import core.planning.core.population.decisionMakers.types.DecisionMaker;
import core.planning.population.parallelPassivePlanning.PassivePlannerManager;
import org.matsim.api.core.v01.Id;
import org.matsim.api.core.v01.population.Activity;
import org.matsim.api.core.v01.population.PlanElement;
import org.matsim.facilities.ActivityFacility;

public class SinglePlannerAgendaAgent extends SinglePlannerAgentImpl {

	public SinglePlannerAgendaAgent(PassivePlannerDriverAgent agent) {
		super(new DecisionMaker[]{((AgendaBasePerson)agent.getBasePerson()).getAgendaDecisionMaker()}, agent);
	}
	
	@Override
	protected List<? extends PlanElement> getLegActivityLeg(double startTime, PassivePlannerManager.CurrentTime now,
			Id<ActivityFacility> startFacilityId, double endTime, Id<ActivityFacility> endFacilityId, final PassivePlannerManager.MobsimStatus mobsimStatus) {
		((AgendaDecisionMaker)decisionMakers[0]).prepareScheduling((Activity) agent.getCurrentPlan().getPlanElements().get(currentElementIndex.get()-1), agent.getCurrentPlan(), now);
		((AgendaDecisionMaker)decisionMakers[0]).setMobsimEnds(mobsimStatus);
		return ((AgendaDecisionMaker)decisionMakers[0]).decideRoute(startTime, startFacilityId, endFacilityId, null, tripRouter);
	}
	public void addKnownPerson(PlacesSharer sharer) {
		((PlacesSharer)decisionMakers[0]).addKnownPerson(sharer);
	}
	public void shareKnownPlace(Id<ActivityFacility> facilityId, double startTime, String type) {
		PlacesSharer sharer = ((PlacesSharer)decisionMakers[0]);
		if(!type.equals("home"))
			sharer.shareKnownPlace(facilityId, startTime, type);
	}
	public PlacesSharer getPlaceSharer() {
		return (PlacesSharer)decisionMakers[0];
	}

	public void shareKnownTravelTime(Id<ActivityFacility> prevFacilityId, Id<ActivityFacility> facilityId, String mode, double startTime, double travelTime) {
		((PlacesSharer)decisionMakers[0]).shareKnownTravelTime(prevFacilityId, facilityId, mode, startTime, travelTime);
	}

}
