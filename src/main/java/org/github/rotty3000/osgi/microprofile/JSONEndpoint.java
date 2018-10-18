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

package org.github.rotty3000.osgi.microprofile;

import java.time.LocalDate;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.bind.JsonbBuilder;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriInfo;

import org.apache.aries.cdi.extra.propertytypes.JSONRequired;
import org.apache.aries.cdi.extra.propertytypes.JaxrsResource;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.github.rotty3000.osgi.microprofile.model.JSONBModel;
import org.github.rotty3000.osgi.microprofile.model.JohnzonModel;
import org.osgi.service.cdi.annotations.Service;
import org.osgi.service.log.Logger;

@ApplicationScoped
@JaxrsResource
@JSONRequired
@Service
public class JSONEndpoint {

	private final Logger logger;
	private String stringProperty;

	@Inject
	public JSONEndpoint(
		Logger logger,
		@ConfigProperty(name="my.string.property")
		String stringProperty) {

		this.logger = logger;
		this.stringProperty = stringProperty;

		logger.info(l -> l.info("my.string.property = {}", stringProperty));
	}

	@GET
	@Path("/jsonb")
	@Produces(MediaType.APPLICATION_JSON)
	public String jsonb(@Context UriInfo uriInfo) {
		logger.debug(l -> l.debug("being called at {}", uriInfo.getAbsolutePath()));

		JSONBModel m = new JSONBModel();
		m.value = stringProperty;
		return JsonbBuilder.create().toJson(m);
	}

	@GET
	@Path("/jsonb_m")
	@Produces(MediaType.APPLICATION_JSON)
	public JSONBModel jsonbModel(@Context UriInfo uriInfo) {
		logger.debug(l -> l.debug("being called at {}", uriInfo.getAbsolutePath()));

		JSONBModel m = new JSONBModel();
		m.value = stringProperty;
		return m;
	}

	@GET
	@Path("/jsonp")
	@Produces(MediaType.APPLICATION_JSON)
	public JsonArray jsonp(@Context UriInfo uriInfo) {
		logger.debug(l -> l.debug("being called at {}", uriInfo.getAbsolutePath()));

		return Json.createArrayBuilder().add(stringProperty).build();
	}

	@GET
	@Path("/jsonp_m")
	@Produces(MediaType.APPLICATION_JSON)
	public JohnzonModel jsonpModel(@Context UriInfo uriInfo) {
		logger.debug(l -> l.debug("being called at {}", uriInfo.getAbsolutePath()));
		JohnzonModel m = new JohnzonModel(LocalDate.now());
		m.value = stringProperty;
		return m;
	}

}
