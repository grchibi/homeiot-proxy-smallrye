package org.zushisa9.tt.router;

import java.util.concurrent.CompletionStage;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;
import org.jboss.resteasy.reactive.PartType;
import org.jboss.resteasy.reactive.RestForm;

import com.fasterxml.jackson.databind.JsonNode;

import io.smallrye.mutiny.Uni;


@RegisterRestClient(configKey = "homeiot-api")
public interface HomeiotApiService {

    @POST
    @Path("/tph_register")
    //CompletionStage<Response> add(@PartType(MediaType.APPLICATION_JSON) Tph tph_register);
    Uni<Response> add(@PartType(MediaType.APPLICATION_JSON) Tph tph_register);

}
