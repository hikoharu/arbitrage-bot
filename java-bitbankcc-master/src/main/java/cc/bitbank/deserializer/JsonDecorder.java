package cc.bitbank.deserializer;

import cc.bitbank.entity.response.Response;
import cc.bitbank.exception.BitbankException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

/**
 * Created by tanaka on 2017/04/11.
 */
public class JsonDecorder {

    public <T extends Response> T decode(String json, Class<T> clazz) throws BitbankException {
        ObjectMapper mapper = new ObjectMapper();
        try {
            T result = mapper.readValue(json, clazz);
            return result;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
