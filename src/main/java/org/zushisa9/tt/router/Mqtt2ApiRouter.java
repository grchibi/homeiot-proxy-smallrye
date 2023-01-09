package org.zushisa9.tt.router;

import java.util.concurrent.CompletionStage;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.core.Response;

import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;
import org.eclipse.microprofile.reactive.messaging.Incoming;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.quarkus.logging.Log;
import io.smallrye.mutiny.Uni;

@ApplicationScoped
public class Mqtt2ApiRouter {

    /*@Inject
    @Channel("homeiot-api")
    Emitter<String> emitterForJson;*/
    @Inject
    @RestClient
    HomeiotApiService apiService;

    @Incoming("mqtt-broker")
    public void subscribeTphData(byte[] raw) {
        Log.info("subscribed: " + new String(raw));

        Tph tphData = new Tph(raw);
        Log.debug("created the Tph object => " + tphData.toString());

        /*String strToSend = new TphRegister(tphData).jsonToRegister();
        emitterForJson.send(strToSend);*/

        //CompletionStage<Response> respUni = apiService.add(tphData);
        Uni<Response> resp = apiService.add(tphData);
        resp
        .onFailure().invoke(fail -> Log.trace("onFailure(): " + fail) )
        .subscribe().with(
            success -> Log.info("SUCESS: " + success.getStatus()),
            failure -> Log.error("FAILED: " + failure.getLocalizedMessage())
        );
        Log.debug("sent JSON data => " + tphData.jsonToRegister());
    }
}
