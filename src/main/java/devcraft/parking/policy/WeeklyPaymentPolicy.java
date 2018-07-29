package devcraft.parking.policy;

import java.time.Instant;
import java.time.LocalTime;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;

import static java.time.ZoneOffset.UTC;

public class WeeklyPaymentPolicy implements PaymentPolicy {

    private PaymentPolicy paymentPolicy;

    public WeeklyPaymentPolicy(PaymentPolicy paymentPolicy){
        this.paymentPolicy = paymentPolicy;
    }

    @Override
    public int calculatePayment(Instant entryTime, Instant paymentTime) {
        ZonedDateTime next6Am = getNext6AmTime(entryTime);
        int payment = 0;
        while (next6Am.toInstant().isBefore(paymentTime)) {
            payment += dailyPayment(entryTime, next6Am.toInstant());
            entryTime = next6Am.toInstant();
            next6Am = next6Am.plus(24,ChronoUnit.HOURS);
        }
        payment += dailyPayment(entryTime,paymentTime);
        return payment;
    }

    private ZonedDateTime getNext6AmTime(Instant entryTime){
        ZonedDateTime entryDate = ZonedDateTime.ofInstant(entryTime, UTC);
        ZonedDateTime next6Am = ZonedDateTime.of(entryDate.toLocalDate(), LocalTime.of(6, 0), UTC);
        if (!next6Am.isAfter(entryDate)) {
            next6Am = next6Am.plus(24, ChronoUnit.HOURS);
        }
        return next6Am;
    }

    public int dailyPayment(Instant entryTime, Instant paymentTime){
        return paymentPolicy.calculatePayment(entryTime,paymentTime);
    }
}
