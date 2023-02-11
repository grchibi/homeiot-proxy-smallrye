package org.zushisa9.tt.router;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.core.Response;

import org.eclipse.microprofile.config.ConfigProvider;
import org.eclipse.microprofile.reactive.messaging.Incoming;
import org.eclipse.microprofile.rest.client.RestClientBuilder;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import io.quarkus.logging.Log;
import io.smallrye.mutiny.Uni;

@ApplicationScoped
public class Mqtt2ApiRouter {

    @Inject
    @RestClient
    HomeiotApiService apiService;

    Map<String, HomeiotDynamicApiService> dynApiServices = new HashMap<>();

    public Mqtt2ApiRouter() {
        String dests = ConfigProvider.getConfig().getValue("tt.rest-client.keys", String.class);
        String[] destsAry = dests.split(",");
        for (String dest: destsAry) {
            try {
                String urlProperty = ConfigProvider.getConfig().getValue("tt.rest-client." + dest + ".url", String.class);
                Log.debug("URI => " + urlProperty);

                HomeiotDynamicApiService dynApiService = RestClientBuilder.newBuilder()
                    .baseUri(URI.create(urlProperty))
                    .build(HomeiotDynamicApiService.class);
                
                dynApiServices.put(urlProperty, dynApiService);
                Log.info("API Servie created: " + urlProperty);

            } catch (Exception ex) {
                Log.warn("WARN in Mqtt2ApiRouter(): " + ex.getLocalizedMessage());
            }
        }
    }
    
    @Incoming("mqtt-broker")
    public void subscribeTphData(byte[] raw) {
        Log.info("subscribed: " + new String(raw));

        Tph tphData = new Tph(raw);
        Log.debug("created the Tph object => " + tphData.toString());

        for (Map.Entry<String, HomeiotDynamicApiService> dynApiServiceEntry: dynApiServices.entrySet()) {
            try {
                //Uni<Response> resp = dynApiServiceEntry.getValue().add(tphData);
                Uni<Response> resp = dynApiServiceEntry.getValue().add(new TphRegister(tphData));
                resp
                .onFailure().invoke(fail -> Log.trace("onFailure(): " + dynApiServiceEntry.getKey() + " => " + fail) )
                .subscribe().with(
                    success -> Log.info("SUCESS: " + dynApiServiceEntry.getKey() + " => " + success.getStatus()),
                    failure -> Log.error("FAILED: " + dynApiServiceEntry.getKey() + " => " + failure.getLocalizedMessage())
                );
                Log.debug("sent JSON data => " + dynApiServiceEntry.getKey() + " => " + tphData.jsonToRegister());

            } catch (Exception ex) {
                Log.error("FAILED: " + dynApiServiceEntry.getKey() + " => " + ex.getLocalizedMessage());
            }
        }
    }
}
