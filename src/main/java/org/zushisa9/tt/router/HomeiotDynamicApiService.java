package org.zushisa9.tt.router;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.jboss.resteasy.reactive.PartType;

import io.smallrye.mutiny.Uni;

public interface HomeiotDynamicApiService {

    @POST
    @Path("/tph_register")
    Uni<Response> add(@PartType(MediaType.APPLICATION_JSON) Tph tph_register);
    
    @POST
    @Path("/tph_register")
    Uni<Response> add(@PartType(MediaType.APPLICATION_JSON) TphRegister tph_register);
}