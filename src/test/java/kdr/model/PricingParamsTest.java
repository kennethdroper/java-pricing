package kdr.model;

import com.fasterxml.jackson.databind.ObjectMapper;
import kdr.pricing.Option;
import org.testng.annotations.Test;

import java.io.IOException;
import java.io.StringWriter;

import static org.testng.Assert.*;

public class PricingParamsTest {

    /**
     * This test illustrates how the Jackson ObjectMapper serialises the REST interface POJO into JSON.
     * @throws IOException
     */
    @Test
    public void testJacksonObjectMapper() throws IOException {
        PricingParams params= new PricingParams(Option.PutCall.CALL, 1.0f, 66.0f,
                0.1f, 2.5f, 0.5);

        String expectedJSON = "{\"putCall\":\"CALL\"," +
                              "\"impliedVolatility\":1.0," +
                              "\"strikePrice\":66.0," +
                              "\"underlyingPrice\":0.1," +
                              "\"yearsToMaturity\":2.5," +
                              "\"riskFreeRate\":0.5" +
                              "}";

        final ObjectMapper mapper= new ObjectMapper();
        StringWriter stringWriter= new StringWriter();
        mapper.writeValue(stringWriter, params);
        assertEquals(stringWriter.toString(), expectedJSON);

    }
}