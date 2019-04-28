public class GregorianDate extends Date {

    private static final int[] MONTH_LENGTHS = {
        31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31
    };

    public GregorianDate(int year, int month, int dayOfMonth) {
        super(year, month, dayOfMonth);
    }

    @Override
    public int dayOfYear() {
        int precedingMonthDays = 0;
        for (int m = 1; m < month; m += 1) {
            precedingMonthDays += getMonthLength(m);
        }
        return precedingMonthDays + dayOfMonth;
    }

    private static int getMonthLength(int m) {
        return MONTH_LENGTHS[m - 1];
    }

    public Date nextDate() {
        int yr = year;
        int mth = month;
        int nextdate = dayOfMonth;

        if (month == 4 || month ==  6 || month == 9 || month == 11) {
            if (dayOfMonth == 30) {
                nextdate = 1;
                mth = month + 1;
            } else {
                nextdate = dayOfMonth + 1;
            }
        }

        else if (month == 1 || month == 3 || month == 5 || month == 7 || month == 8 || month == 10) {
            if (dayOfMonth == 31) {
                nextdate = 1;
                mth = month + 1;
            } else {
                nextdate = dayOfMonth + 1;
            }
        }

        else if (month == 12) {
            if (dayOfMonth == 31) {
                nextdate = 1;
                mth = 1;
                yr = year + 1;
            } else {
                nextdate = dayOfMonth + 1;
            }
        }

        else {
            if (dayOfMonth == 28) {
                nextdate = 1;
                mth = month + 1;
            } else {
                nextdate = dayOfMonth + 1;
            }
        }

        return new GregorianDate(yr, mth, nextdate);
    }
}
