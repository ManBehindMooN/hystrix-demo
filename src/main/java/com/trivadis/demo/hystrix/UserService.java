package com.trivadis.demo.hystrix;

import java.text.SimpleDateFormat;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Application;

import com.netflix.config.ConfigurationManager;

@ApplicationPath("/")
@Path("/")
public class UserService extends Application {

	static private Configuration serviceConfig = new Configuration();

	@GET
	@Path("/users/{userid}")
	@Produces("application/json")
	public Person lookup(@PathParam("userid") String userid) throws Exception {
		Person person;
		try {
			if (serviceConfig.serviceProperties.useHystrix) {
				person = new LookupCommand(userid, serviceConfig).execute();
			} else {
				person = UserClient.loadPerson(userid, serviceConfig.serviceProperties.worktime);
			}
			logPerson(person);
			return person;
		} catch (Exception e) {
			System.out.println(e.getMessage());
			throw e;
		}
	}

	@POST
	@Path("/config")
	@Consumes("application/json")
	public void setServiceConfig(Configuration config) {
		serviceConfig.serviceProperties.online = config.serviceProperties.online;
		System.out.println("Set Service Property 'online' to value: " + serviceConfig.serviceProperties.online);
		serviceConfig.serviceProperties.worktime = config.serviceProperties.worktime;
		System.out.println("Set Service Property 'worktime' to value: " + serviceConfig.serviceProperties.worktime);
		serviceConfig.serviceProperties.useHystrix = config.serviceProperties.useHystrix;
		System.out.println("Set Service Property 'useHystrix' to value: " + serviceConfig.serviceProperties.useHystrix);

		for (HystrixProperty property : config.globalProperies) {
			if (property != null) {
				// https://github.com/Netflix/Hystrix/wiki/Configuration
				ConfigurationManager.getConfigInstance().setProperty(property.key, property.value);
				System.out.println("Set Property: " + property.key + " to value: " + property.value);
				serviceConfig.globalProperies = config.globalProperies;
			}
		}

		for (HystrixProperty property : config.instanceProperies) {
			if (property != null) {
				// https://github.com/Netflix/Hystrix/wiki/Configuration
				ConfigurationManager.getConfigInstance().setProperty(property.key, property.value);
				System.out.println("Set Property: " + property.key + " to value: " + property.value);
				serviceConfig.instanceProperies = config.instanceProperies;
			}
		}
	}

	@GET
	@Path("/config")
	@Produces("application/json")
	public Configuration getServiceConfig() {
		return serviceConfig;
	}

	private void logPerson(Person person) {
		SimpleDateFormat formatter = new SimpleDateFormat("d.m.Y H:M:s.S");
		System.out.println(
				"Person: " + person.userid + " " + formatter.format(person.readtime) + " (" + person.state + ")");
	}

}
