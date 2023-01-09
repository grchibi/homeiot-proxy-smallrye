package org.zushisa9.tt.router;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

public class TphRegister {
    public Tph tph_register;

    public TphRegister() {}

    public TphRegister(Tph tph) {
        this.tph_register = tph;
    }

    public String jsonToRegister() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.enable(SerializationFeature.INDENT_OUTPUT);

        String jsonStr;

        try {
            jsonStr = mapper.writeValueAsString(this);
        } catch (JsonProcessingException ex) {
            jsonStr = ex.getMessage();
        }

        return jsonStr;
    }
}
