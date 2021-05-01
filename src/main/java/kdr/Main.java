package kdr;

import kdr.pricing.BlackScholesPricer;
import kdr.pricing.Option;


/**
 * Main function used for lightweight testing
 */
public class Main {

    public static void main(String[] args) {
	// write your code here
        System.out.println("Hello World");

        float k=100.0f, s=101.1f;
        double v=0.2, r = 0.05;
        float t = 1.5f;


        System.out.printf("k = %f, s = %f, v = %f, t = %f, r = %f \n", k, s, v, t, r);

        double mtm = BlackScholesPricer.priceOption(Option.PutCall.PUT, v, k, s, t, r);

        System.out.printf("\nmtm: %f", mtm);
    }
}
