package kdr.rest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import kdr.model.PricingParams;
import kdr.model.PricingResult;
import kdr.pricing.Option;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.testng.Assert.*;

/**
 * Full server endpoint test.
 *
 * TODO: Could be enhanced to be more lightweight using MockMvc.
 *
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PricingControllerTest {

    @LocalServerPort
    private int localServerPort;

    @Autowired
    private TestRestTemplate restTemplate;

    private final Logger logger= LoggerFactory.getLogger(PricingControllerTest.class);

    @Test
    public void pricingHelpMessageDisplay() throws Exception {

        String url= "http://localhost:" + localServerPort + "/priceOptionHelp";

        logger.info("Testing url: {}", url);

        String result= restTemplate.getForObject(
                url,
                String.class
        );

        assertThat(result).contains("PUT");

        logger.info("Test success with result: {}", result);
    }

    @Test
    void priceOption() throws JsonProcessingException {
        String url= "http://localhost:" + localServerPort + "/priceOption";

        logger.debug("Testing url: {}", url);

        PricingParams params= new PricingParams(Option.PutCall.CALL, 1.0f, 66.0f, 0.1f, 2.5f, 0.5);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<PricingParams> entity= new HttpEntity<>(params, headers);

        ResponseEntity<String> stringResult= restTemplate.postForEntity(
                url,
                entity,
                String.class
        );

        logger.debug("String Test result: {}", stringResult);

        assertEquals(stringResult.getStatusCode().series(), HttpStatus.Series.SUCCESSFUL);

        logger.debug("Test result headers: {}", stringResult.getHeaders());

        MediaType contentType= stringResult.getHeaders().getContentType();
        logger.debug("Test contentTypes: {}", contentType);

        assertEquals(contentType, MediaType.APPLICATION_JSON);

        // We've covered the basics, now deserialise the message body and ensure the parameters are as passed-in.
        String pricingResultString= stringResult.getBody();
        final ObjectMapper mapper= new ObjectMapper();
        PricingResult pricingResult= mapper.readValue(pricingResultString,PricingResult.class);
        logger.debug("Deserialised pricingResultString: {}", pricingResult);
        assertNotNull(pricingResult);
        assertNotEquals(pricingResult.getMtm(), 0.0);
        assertEquals(pricingResult.getPricingParams(), params);
        logger.debug("Passed test: Deserialised pricingResult contains the parameters we have provided.");
    }
}
