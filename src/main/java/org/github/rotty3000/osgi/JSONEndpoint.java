/**
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.github.rotty3000.osgi;

import java.time.LocalDate;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.bind.JsonbBuilder;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriInfo;

import org.apache.aries.cdi.extra.propertytypes.JSONRequired;
import org.apache.aries.cdi.extra.propertytypes.JaxrsResource;
import org.github.rotty3000.osgi.config.Config;
import org.github.rotty3000.osgi.model.JSONBModel;
import org.github.rotty3000.osgi.model.JohnzonModel;
import org.osgi.service.cdi.annotations.ComponentProperties;
import org.osgi.service.cdi.annotations.Service;
import org.osgi.service.log.Logger;

@ApplicationScoped
@JaxrsResource
@JSONRequired
@Service
public class JSONEndpoint {

	@Inject
	private Logger logger;

	@ComponentProperties
	@Inject
	private Config config;

	@PostConstruct
	void postConstruct() {
		logger.debug("my.string.property = {}", config.my_string_property());
	}

	@GET
	@Path("/jsonb")
	@Produces(MediaType.APPLICATION_JSON)
	public String jsonb(@Context UriInfo uriInfo) {
		logger.debug("being called at {}", uriInfo.getAbsolutePath());

		JSONBModel m = new JSONBModel();
		m.date = LocalDate.now();
		m.value = config.my_string_property();

		return JsonbBuilder.create().toJson(m);
	}

	@GET
	@Path("/jsonb_m")
	@Produces(MediaType.APPLICATION_JSON)
	public JSONBModel jsonbModel(@Context UriInfo uriInfo) {
		logger.debug("being called at {}", uriInfo.getAbsolutePath());

		JSONBModel m = new JSONBModel();
		m.date = LocalDate.now();
		m.value = config.my_string_property();

		return m;
	}

	@GET
	@Path("/jsonp")
	@Produces(MediaType.APPLICATION_JSON)
	public JsonObject jsonp(@Context UriInfo uriInfo) {
		logger.debug("being called at {}", uriInfo.getAbsolutePath());

		JohnzonModel model = new JohnzonModel();
		model.date = LocalDate.now();
		model.value = config.my_string_property();

		return Json.createObjectBuilder().add("my.string.property", model.value).build();
	}

	@GET
	@Path("/jsonp_m")
	@Produces(MediaType.APPLICATION_JSON)
	public JohnzonModel jsonpModel(@Context UriInfo uriInfo) {
		logger.debug("being called at {}", uriInfo.getAbsolutePath());

		JohnzonModel model = new JohnzonModel();
		model.date = LocalDate.now();
		model.value = config.my_string_property();

		return model;
	}

}
