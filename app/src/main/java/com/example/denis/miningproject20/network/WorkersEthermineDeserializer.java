package com.example.denis.miningproject20.network;

import com.example.denis.miningproject20.models.ethermine.WorkerEthermine;
import com.example.denis.miningproject20.models.ethermine.WorkersEthermine;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Created by denis on 06.07.17.
 */

class WorkersEthermineDeserializer implements JsonDeserializer<WorkersEthermine> {
    @Override
    public WorkersEthermine deserialize(JsonElement json,
                                             Type typeOfT,
                                             JsonDeserializationContext context) throws JsonParseException {

        final JsonObject jsonObject = json.getAsJsonObject();
        List<WorkerEthermine> list = new LinkedList<>();

        for (Map.Entry<String, JsonElement> element : jsonObject.entrySet()){
            list.add(context.deserialize(element.getValue(), WorkerEthermine.class));
        }

        WorkersEthermine w =new WorkersEthermine();
        w.setWorkersEthermine(list);
        return w;
    }
}
