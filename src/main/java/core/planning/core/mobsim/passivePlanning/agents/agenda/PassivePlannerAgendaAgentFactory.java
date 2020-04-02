/* *********************************************************************** *
 * project: org.matsim.*
 *                                                                         *
 * *********************************************************************** *
 *                                                                         *
 * copyright       : (C) 2008 by the members listed in the COPYING,        *
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

package core.planning.core.mobsim.passivePlanning.agents.agenda;

import java.util.HashSet;
import java.util.Set;

import core.planning.api.population.BasePerson;
import core.planning.core.mobsim.passivePlanning.agents.PassivePlannerDriverAgent;
import core.planning.population.parallelPassivePlanning.PassivePlannerManager;
import org.matsim.api.core.v01.population.Person;
import org.matsim.core.mobsim.framework.MobsimDriverAgent;
import org.matsim.core.mobsim.qsim.agents.AgentFactory;
import org.matsim.core.mobsim.qsim.interfaces.Netsim;

public final class PassivePlannerAgendaAgentFactory implements AgentFactory {

	//Attributes
	private final Netsim simulation;
	private final PassivePlannerManager passivePlannerManager;
	private Set<String> modes;

	//Constructors
	public PassivePlannerAgendaAgentFactory(final Netsim simulation) {
		this(simulation, null);
	}
	public PassivePlannerAgendaAgentFactory(final Netsim simulation, final PassivePlannerManager passivePlannerManager) {
		this.simulation = simulation;
		this.passivePlannerManager = passivePlannerManager;
		modes = new HashSet<String>();
		modes.addAll(simulation.getScenario().getConfig().plansCalcRoute().getNetworkModes());
		modes.addAll(simulation.getScenario().getConfig().plansCalcRoute().getTeleportedModeFreespeedFactors().keySet());
		modes.addAll(simulation.getScenario().getConfig().plansCalcRoute().getTeleportedModeSpeeds().keySet());
		modes.remove("empty");
	}

	//Methods
	@Override
	public MobsimDriverAgent createMobsimAgentFromPerson(final Person person) {
		PassivePlannerDriverAgent agent = new PassivePlannerAgendaAgent((BasePerson)person, simulation, passivePlannerManager);
		return agent;
	}

}
