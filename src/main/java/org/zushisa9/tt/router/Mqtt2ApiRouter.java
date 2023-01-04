package org.zushisa9.tt.router;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;
import org.eclipse.microprofile.reactive.messaging.Incoming;

@ApplicationScoped
public class Mqtt2ApiRouter {

    @Inject
    @Channel("homeiot-api")
    Emitter<String> emitterForJson;

    @Incoming("mqtt-broker")
    public void subscribeTphData(byte[] raw) {
        System.out.println(" subscribed: " + new String(raw));

        Tph tphData = new Tph(raw);
        System.out.println(" Tph to String => " + tphData.toString());

        emitterForJson.send(new String(raw));
    }
}
