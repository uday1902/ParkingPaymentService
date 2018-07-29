package devcraft.parking.policy;

import java.time.*;

import static java.time.ZoneOffset.UTC;

public class WeekendNightPaymentPolicy implements PaymentPolicy {

    private PaymentPolicy paymentPolicy;

    public WeekendNightPaymentPolicy(PaymentPolicy paymentPolicy){
        this.paymentPolicy= paymentPolicy;
    }

    @Override
    public int calculatePayment(Instant entryTime, Instant paymentTime) {
        if(!hasWeekendNightTime(entryTime,paymentTime)){
            return paymentPolicy.calculatePayment(entryTime, paymentTime);
        }
        ZonedDateTime nightStart = getNightStart(entryTime);
        return paymentPolicy.calculatePayment(entryTime, nightStart.toInstant()) + 40;
    }

    private boolean hasWeekendNightTime(Instant entryTime, Instant paymentTime){
        ZonedDateTime zonedEntryTime = ZonedDateTime.ofInstant(entryTime, UTC);
        ZonedDateTime zonedPaymentTime = ZonedDateTime.ofInstant(paymentTime, UTC);

        boolean isWeekend = zonedEntryTime.getDayOfWeek() == DayOfWeek.THURSDAY ||
                zonedEntryTime.getDayOfWeek() == DayOfWeek.FRIDAY ||
                zonedEntryTime.getDayOfWeek() == DayOfWeek.SATURDAY;
        ZonedDateTime nightStart = getNightStart(entryTime);
            return isWeekend && zonedPaymentTime.isAfter(nightStart);
    }

    private ZonedDateTime getNightStart(Instant entryTime){
        ZonedDateTime zonedEntryTime = ZonedDateTime.ofInstant(entryTime, UTC);
        return ZonedDateTime.of(zonedEntryTime.toLocalDate(), LocalTime.of(22, 0), UTC);
    }
}
