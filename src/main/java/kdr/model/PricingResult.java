package kdr.model;

/**
 * Result of a pricing request, including the input parameters.
 *
 * Immutable implementation, threadsafe.
 */
public final class PricingResult {

    private final PricingParams params;
    private final double mtm;

    public PricingResult(PricingParams params, double mtm) {
        this.params = params;
        this.mtm= mtm;
    }

    public double getMtm() {
        return mtm;
    }

    public PricingParams getPricingParams() {
        return params;
    }
}
