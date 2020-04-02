package core.planning.core.population;

import core.planning.api.population.FloatActivity;
import org.matsim.core.config.groups.PlanCalcScoreConfigGroup.ActivityParams;

public class FloatActivityImpl implements FloatActivity {

	//Attributes
	private String type;
	private double maximumDuration;
	private final ActivityParams activityParams;

	//Methods
	public FloatActivityImpl(String type, double maximumDuration, ActivityParams activityParams) {
		super();
		this.type = type;
		this.maximumDuration = maximumDuration;
		this.activityParams = activityParams;
	}
	@Override
	public String getType() {
		return type;
	}
	@Override
	public void setType(String type) {
		this.type = type;
	}
	@Override
	public double getMaximumDuration() {
		return maximumDuration;
	}
	@Override
	public void setMaximumDuration(double maximumDuration) {
		this.maximumDuration = maximumDuration;
	}
	@Override
	public ActivityParams getActivityParams() {
		return activityParams;
	}

}
