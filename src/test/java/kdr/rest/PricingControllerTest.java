package kdr.rest;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;

import static org.assertj.core.api.Assertions.assertThat;

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
}
