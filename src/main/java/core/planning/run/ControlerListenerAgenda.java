/* *********************************************************************** *
 * project: org.matsim.*
 * Controler.java
 *                                                                         *
 * *********************************************************************** *
 *                                                                         *
 * copyright       : (C) 2007 by the members listed in the COPYING,        *
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

package core.planning.run;

import com.google.inject.Provider;

import core.planning.core.mobsim.passivePlanning.PassivePlanningActivityEngine;
import core.planning.core.mobsim.passivePlanning.PlanningEngine;
import core.planning.core.mobsim.passivePlanning.agents.agenda.PassivePlannerAgendaAgentFactory;
import core.planning.core.mobsim.passivePlanning.agents.agenda.PassivePlannerTransitAgendaAgentFactory;
import core.planning.core.population.AgendaBasePersonImpl;
import core.planning.core.population.socialNetwork.SocialNetworkReader;
import core.planning.core.scenario.ScenarioSocialNetwork;
import core.planning.population.parallelPassivePlanning.PassivePlannerManager;
import org.matsim.api.core.v01.Id;
import org.matsim.api.core.v01.Scenario;
import org.matsim.api.core.v01.TransportMode;
import org.matsim.api.core.v01.population.Person;
import org.matsim.core.config.ConfigUtils;
import org.matsim.core.config.groups.QSimConfigGroup;
import org.matsim.core.controler.AbstractModule;
import org.matsim.core.controler.Controler;
import org.matsim.core.controler.OutputDirectoryHierarchy;
import org.matsim.core.controler.events.StartupEvent;
import org.matsim.core.controler.listener.StartupListener;
import org.matsim.core.mobsim.framework.Mobsim;
import org.matsim.core.mobsim.qsim.QSim;
import org.matsim.core.mobsim.qsim.TeleportationEngine;
import org.matsim.core.mobsim.qsim.agents.AgentFactory;
import org.matsim.core.mobsim.qsim.agents.PopulationAgentSource;
import org.matsim.core.mobsim.qsim.pt.ComplexTransitStopHandlerFactory;
import org.matsim.core.mobsim.qsim.pt.TransitQSimEngine;
import org.matsim.core.mobsim.qsim.qnetsimengine.QNetsimEngineModule;
import org.matsim.core.network.NetworkImpl;
import org.matsim.core.network.algorithms.TransportModeNetworkFilter;
import org.matsim.core.population.PersonImpl;
import org.matsim.core.scenario.ScenarioUtils;
import org.matsim.facilities.ActivityFacility;
import org.matsim.facilities.ActivityFacilityImpl;

import java.util.*;


/**
 * This is currently only a substitute to the full Controler. 
 *
 * @author sergioo
 */
public class ControlerListenerAgenda implements StartupListener {

	//Methods
	//@Override
	@Override
	public void notifyStartup(StartupEvent event) {
		Controler controler = event.getControler();
        TransportModeNetworkFilter filter = new TransportModeNetworkFilter(controler.getScenario().getNetwork());
		NetworkImpl net = NetworkImpl.createNetwork();
		HashSet<String> carMode = new HashSet<String>();
		carMode.add(TransportMode.car);
		filter.filter(net, carMode);
		for(ActivityFacility facility:controler.getScenario().getActivityFacilities().getFacilities().values())
			((ActivityFacilityImpl)facility).setLinkId(net.getNearestLinkExactly(facility.getCoord()).getId());
        Map<Id<Person>, ? extends Person> persons = event.getControler().getScenario().getPopulation().getPersons();
		Collection<Person> toBeAdded = new ArrayList<Person>();
		Set<String> modes = new HashSet<String>();
		modes.addAll(controler.getConfig().plansCalcRoute().getNetworkModes());
		if(controler.getConfig().scenario().isUseTransit())
			modes.add("pt");
        for(Person person: controler.getScenario().getPopulation().getPersons().values())
			toBeAdded.add(AgendaBasePersonImpl.convertToAgendaBasePerson((PersonImpl) person, controler.getScenario().getActivityFacilities(), new HashSet<String>(controler.getConfig().qsim().getMainModes()), modes, controler.getConfig().qsim().getEndTime()));
		for(Person person:toBeAdded) {
            controler.getScenario().getPopulation().getPersons().remove(person.getId());
            controler.getScenario().getPopulation().addPerson(person);
		}
	}
	//Main
	public static void main(String[] args) {
		final Scenario scenario = new ScenarioSocialNetwork(ConfigUtils.loadConfig(args.length>0 ? args[0] : null));
		ScenarioUtils.loadScenario(scenario);
		new SocialNetworkReader(scenario).parse(args.length>1 ? args[1] : null);
		final Controler controler = new Controler(scenario);
		controler.getConfig().plansCalcRoute().getTeleportedModeFreespeedFactors().put("empty", 0.0);
		controler.getConfig().controler().setOverwriteFileSetting(
				true ?
						OutputDirectoryHierarchy.OverwriteFileSetting.overwriteExistingFiles :
							OutputDirectoryHierarchy.OverwriteFileSetting.failIfDirectoryExists );
		/*WaitTimeCalculator waitTimeCalculator = new WaitTimeCalculator(controler.getScenario().getTransitSchedule(), controler.getConfig().travelTimeCalculator().getTraveltimeBinSize(), (int) (controler.getConfig().qsim().getEndTime()-controler.getConfig().qsim().getStartTime()));
		controler.getEvents().addHandler(waitTimeCalculator);
		StopStopTimeCalculator stopStopTimeCalculator = new StopStopTimeCalculator(controler.getScenario().getTransitSchedule(), controler.getConfig().travelTimeCalculator().getTraveltimeBinSize(), (int) (controler.getConfig().qsim().getEndTime()-controler.getConfig().qsim().getStartTime()));
		controler.getEvents().addHandler(stopStopTimeCalculator);
		controler.setTransitRouterFactory(new TransitRouterWSImplFactory(controler.getScenario(), waitTimeCalculator.getWaitTimes(), stopStopTimeCalculator.getStopStopTimes()));*/
		//controler.setScoringFunctionFactory(new CharyparNagelOpenTimesScoringFunctionFactory(controler.getConfig().planCalcScore(), controler.getScenario()));
		controler.addControlerListener(new ControlerListenerAgenda());
		final PassivePlannerManager passivePlannerManager = new PassivePlannerManager(controler.getConfig().global().getNumberOfThreads()-controler.getConfig().qsim().getNumberOfThreads());
		controler.addControlerListener(passivePlannerManager);
		controler.addOverridingModule(new AbstractModule() {
			@Override
			public void install() {
				bindMobsim().toProvider(new Provider<Mobsim>() {
					@Override
					public Mobsim get() {
						QSimConfigGroup conf = scenario.getConfig().qsim();
				        if (conf == null) {
				            throw new NullPointerException("There is no configuration set for the QSim. Please add the module 'qsim' to your config file.");
				        }

						QSim qSim = new QSim(scenario, controler.getEvents());
						PassivePlanningActivityEngine activityEngine = new PassivePlanningActivityEngine();
						qSim.addMobsimEngine(activityEngine);
						qSim.addActivityHandler(activityEngine);
						PlanningEngine planningEngine = new PlanningEngine(qSim);
						passivePlannerManager.setPlanningEngine(planningEngine);
						qSim.addMobsimEngine(planningEngine);
						qSim.addDepartureHandler(planningEngine);
				        QNetsimEngineModule.configure(qSim);
						TeleportationEngine teleportationEngine = new TeleportationEngine(scenario, controler.getEvents());
						qSim.addMobsimEngine(teleportationEngine);
						AgentFactory agentFactory;
						if(scenario.getConfig().scenario().isUseTransit()) {
							agentFactory = new PassivePlannerTransitAgendaAgentFactory(qSim, passivePlannerManager);
							TransitQSimEngine transitEngine = new TransitQSimEngine(qSim);
							transitEngine.setTransitStopHandlerFactory(new ComplexTransitStopHandlerFactory());
							qSim.addDepartureHandler(transitEngine);
							qSim.addAgentSource(transitEngine);
							qSim.addMobsimEngine(transitEngine);
						}
						else
							agentFactory = new PassivePlannerAgendaAgentFactory(qSim, passivePlannerManager);
						PopulationAgentSource agentSource = new PopulationAgentSource(scenario.getPopulation(), agentFactory, qSim);
						qSim.addAgentSource(agentSource);
						return qSim;
					}
				});
			}
		});

		controler.run();
	}

}