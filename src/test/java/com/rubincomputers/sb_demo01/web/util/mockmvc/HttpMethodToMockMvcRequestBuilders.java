package com.rubincomputers.sb_demo01.web.util.mockmvc;

import org.springframework.http.HttpMethod;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.EnumMap;
import java.util.Map;


public class HttpMethodToMockMvcRequestBuilders {

    private static final Map<HttpMethod, MockMvcRequest> METHOD_MAPPING = new EnumMap<>(HttpMethod.class);

    static {
        METHOD_MAPPING.put(HttpMethod.GET, (u) -> MockMvcRequestBuilders.get(u));
        METHOD_MAPPING.put(HttpMethod.POST, (u) -> MockMvcRequestBuilders.post(u));
        METHOD_MAPPING.put(HttpMethod.DELETE, (u) -> MockMvcRequestBuilders.delete(u));
        METHOD_MAPPING.put(HttpMethod.PUT, (u) -> MockMvcRequestBuilders.put(u));
    }

    public static MockMvcRequest convert(HttpMethod httpMethod) {
        return METHOD_MAPPING.computeIfAbsent(httpMethod, (key) -> {
            throw new IllegalArgumentException("Unsupported HTTP method: " + httpMethod);
        });
    }

}
