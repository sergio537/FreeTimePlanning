package core.planning.core.mobsim.passivePlanning.definitions;

import core.planning.population.parallelPassivePlanning.PassivePlannerManager;
import org.matsim.api.core.v01.Id;
import org.matsim.api.core.v01.population.Plan;
import org.matsim.core.router.TripRouter;
import org.matsim.facilities.ActivityFacility;

public interface SinglePlannerAgent {

	//Methods
	Plan getPlan();
	int getPlanElementIndex();
	void incrementPlanElementIndex();
	void setRouter(TripRouter tripRouter);
	int planLegActivityLeg(double startTime, PassivePlannerManager.CurrentTime now, Id<ActivityFacility> startFacilityId, double endTime, Id<ActivityFacility> endFacilityId, final PassivePlannerManager.MobsimStatus mobSimEnds);
	void advanceToNextActivity(double now, double penalty);

}
