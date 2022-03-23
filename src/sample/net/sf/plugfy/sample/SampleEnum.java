package net.sf.plugfy.sample;

import java.math.BigDecimal;



public class SampleEnum {

    private static final long serialVersionUID = 1L;

    public enum SampleEnumType {

        WEEKLY(1) {
            protected double convertTypeHoursToMonthly(double workingHours) {
                return WEEK_TO_MONTH * workingHours;
            }
        },
        MONTHLY(2);


        final double WEEK_TO_MONTH = 4.348125;

        private final Long hiskey;

        private SampleEnumType(long hiskey) {
            this.hiskey = Long.valueOf(hiskey);
        }
    }

    private Long hiskeyId;

}
