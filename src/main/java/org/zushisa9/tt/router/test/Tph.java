package org.zushisa9.tt.router.test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Tph {
    public String dsrc;
    public String dt;
    public double t, p, h;

    public Tph() {}

    public Tph(String dsrc, String dt, double t, double p, double h) {
        this.dsrc = dsrc;
        this.dt = dt;
        this.t = t;
        this.p = p;
        this.h = h;
    }

    public Tph(byte[] rawJson) {
        String jsonStr = new String(rawJson);
        ObjectMapper mapper = new ObjectMapper();

        try {
            JsonNode jNode = mapper.readTree(jsonStr);
            this.dsrc = jNode.get("dsrc").asText();
            this.dt = jNode.get("dt").asText();
            this.t = jNode.get("t").asDouble();
            this.p = jNode.get("p").asDouble();
            this.h = jNode.get("h").asDouble();

        } catch (JsonProcessingException jpex) {
            jpex.printStackTrace();
        }
    }

    public String toString() {
        return String.format("From dsrc(%s) at dt(%s): temp[%f], press[%f], humid[%f]", dsrc, dt, t, p, h);
    }
}
