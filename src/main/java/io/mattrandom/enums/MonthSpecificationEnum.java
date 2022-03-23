package io.mattrandom.enums;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum MonthSpecificationEnum {

    JANUARY("01", "31"),
    FEBRUARY("01", "28"),
    MARCH("03", "31"),
    APRIL("04", "30"),
    MAY("05", "31"),
    JUNE("06", "30"),
    JULY("07", "31"),
    AUGUST("08", "31"),
    SEPTEMBER("09", "30"),
    OCTOBER("10", "31"),
    NOVEMBER("11", "30"),
    DECEMBER("12", "31");

    private final String monthNumber;
    private final String maxNumberOfDays;

    public String getFirstDayOfGivenMonthAndYear(String year) {
        return year + "-" + this.monthNumber + "-01";
    }

    public String getLastDayOfGivenMonthAndYear(String year) {
        return year + "-" + this.monthNumber + "-" + this.maxNumberOfDays;
    }
}
