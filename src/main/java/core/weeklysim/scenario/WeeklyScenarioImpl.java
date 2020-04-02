/* *********************************************************************** *
 * project: org.matsim.*
 * StatelessScenarioImpl
 *                                                                         *
 * *********************************************************************** *
 *                                                                         *
 * copyright       : (C) 2009 by the members listed in the COPYING,        *
 *                   LICENSE and WARRANTY file.                            *
 * email           : info at matsim dot org                                *
 *                                                                         *
 * *********************************************************************** *
 *                                                                         *
 *   This program is free software; you can redistribute it and/or modify  *
 *   it under the terms of the GNU General Public License as published by  *
 *   the Free Software Foundation; either version 2 of the License, or     *
 *   (at your option) any later version.                                   *
 *   See also COPYING, LICENSE and WARRANTY file                           *
 *                                                                         *
 * *********************************************************************** */
package core.weeklysim.scenario;

import core.planning.core.scenario.ScenarioSocialNetwork;
import org.matsim.core.config.Config;


/**
 * @author dgrether
 * @author mrieser
 */
public class WeeklyScenarioImpl extends ScenarioSocialNetwork {

	protected WeeklyScenarioImpl(Config config) {
		super(config);
	}

}
