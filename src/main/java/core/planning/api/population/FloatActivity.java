package core.planning.api.population;

import org.matsim.api.core.v01.population.PlanElement;
import org.matsim.core.config.groups.PlanCalcScoreConfigGroup.ActivityParams;

public interface FloatActivity extends PlanElement {

	//Methods
	String getType();
	void setType(final String type);
	double getMaximumDuration();
	void setMaximumDuration(double seconds);
	ActivityParams getActivityParams();

}
