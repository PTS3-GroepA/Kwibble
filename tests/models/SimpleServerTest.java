package models;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Created by dennisvermeulen on 17-04-17.
 */
class SimpleServerTest {


    @Test
    void testQueryToMap() {
        //& splits different parts of the query, = assigns the value: paramater1=value1&parameter2=value2


        String urlString = "proftaak=leuk";
        Map<String, String> mss = SimpleServer.queryToMap(urlString);

        List<String> strings = new ArrayList<>();

        for (String key : mss.keySet()) {
            strings.add(key);
        }
        for (String value : mss.values()) {
            strings.add(value);
        }

        assertEquals(strings.get(0), "proftaak");
        assertEquals(strings.get(1), "leuk");


    }


}