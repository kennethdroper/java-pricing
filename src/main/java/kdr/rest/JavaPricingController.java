package kdr.rest;

import kdr.pricing.BlackScholesPricer;
import kdr.pricing.Option;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class JavaPricingController {

    private final static Logger logger= LoggerFactory.getLogger(JavaPricingController.class);

    /**
     * Rest controller, handles HTTP Gets such as:
     *
     * http://localhost:8080/priceOption?strike=100&s=10&v=5&r=0.01&t=2
     *
     * @param k @see kdr.pricing.BlackScholesPricer
     * @param s @see kdr.pricing.BlackScholesPricer
     * @param v @see kdr.pricing.BlackScholesPricer
     * @param r @see kdr.pricing.BlackScholesPricer
     * @param t @see kdr.pricing.BlackScholesPricer
     * @return A message with the MTM
     */
    @GetMapping("/priceOption")
    public String priceOption(
            @RequestParam(value = "putOrCall") String putOrCall,
            @RequestParam(value = "k") float k,
            @RequestParam(value = "s") float s,
            @RequestParam(value = "v") float v,
            @RequestParam(value = "r") float r,
            @RequestParam(value = "t") float t
    ) {

        logger.info("Received request putOrCall = {}, k = {}, s = {}, v = {}, t = {}, r = {}", putOrCall, k, s, v, t, r);

        Option.PutCall pc;
        if ("PUT".equalsIgnoreCase(putOrCall) )
            pc= Option.PutCall.PUT ;
        else if ("CALL".equalsIgnoreCase(putOrCall))
            pc = Option.PutCall.CALL;
        else
            return "putOrCall must be PUT or CALL, not {}" + putOrCall;

        String result;
        try {
            double mtm = BlackScholesPricer.priceOption(pc, v, k, s, t, r);
            result= String.format("Priced option: pc=%s, k=%f, s=%f, v=%f, r=%f, t=%f, mtm=%f",
                    pc, k, s, v, r, t, mtm);

        } catch (Throwable e) {
            logger.error(e.toString());
            result=e.getMessage() + "\nSee /priceOptionHelp.";
        }

        logger.info("Response: {}", result);

        return result;
    }


    @GetMapping("/priceOptionHelp")
    public String priceOptionHelp() {
        return "Expected parameters:<BR>" +
                 "putOrCall the option type, PUT or CALL<BR>"+
                 "v         the implied volatility, sigma<BR>" +
                 "k         the strike price<BR>" +
                 "s         the underlying spot stock price<BR>" +
                 "t         the time to maturity of the European option, in years<BR>" +
                 "rate      the risk free rate<BR>" +
                 "result is the MTM of the option according to the standard Black Scholes formula.<BR>"+
                 "for example: " +
                "<a href=\"http://localhost:8080/priceOption?putOrCall=CALL&k=22&s=100&v=5&r=0.01&t=2\">" +
                          "http://localhost:8080/priceOption?putOrCall=CALL&k=22&s=100&v=5&r=0.01&t=2" +
                "</a>";
    }
}
