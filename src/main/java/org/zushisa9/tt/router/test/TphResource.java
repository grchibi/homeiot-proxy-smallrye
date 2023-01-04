package org.zushisa9.tt.router.test;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Set;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;

@Path("/api/v1/tph_register")
@ApplicationScoped
public class TphResource {

    private Set<Tph> tphs = Collections.newSetFromMap(Collections.synchronizedMap(new LinkedHashMap<>()));

    @POST
    public void add(Tph tph) {
        tphs.add(tph);

        System.out.println("POSTed TPH Data => " + tph);
    }

    @GET
    public String retrieve() {
        String result = "";
        
        for (Tph tph: tphs) {
            result += tph.toString();
        }

        return result;
    }
}

/** FOR quarkus-resteasy-reactive-jackson
@Path("/tph_register")
@ApplicationScoped
public class TphResource {

    private Set<Tph> tphs = Collections.newSetFromMap(Collections.synchronizedMap(new LinkedHashMap<>()));

    @POST
    public Set<Tph> add(Tph tph) {
        tphs.add(tph);

        System.out.println("POSTed TPH Data => " + tph);

        return tphs;
    }
}
**/