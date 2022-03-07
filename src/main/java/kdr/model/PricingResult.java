package kdr.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Objects;

/**
 * Result of a pricing request, including the input parameters.
 *
 * Immutable implementation, threadsafe.
 */
public final class PricingResult {

    private final PricingParams params;
    private final double mtm;

    public PricingResult(@JsonProperty("pricingParams") PricingParams params,
                         @JsonProperty("mtm") double mtm) {
        this.params = params;
        this.mtm= mtm;
    }

    public double getMtm() {
        return mtm;
    }

    public PricingParams getPricingParams() {
        return params;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PricingResult that = (PricingResult) o;
        return Double.compare(that.mtm, mtm) == 0 &&
               Objects.equals(params, that.params);
    }

    @Override
    public int hashCode() {
        return Objects.hash(params, mtm);
    }

    @Override
    public String toString() {
        return "PricingResult{" +
               "params=" + params +
               ", mtm=" + mtm +
               '}';
    }
}
