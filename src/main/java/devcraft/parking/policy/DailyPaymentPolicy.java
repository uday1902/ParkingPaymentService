package devcraft.parking.policy;

import devcraft.parking.util.TimeUtils;

import java.time.Duration;
import java.time.Instant;

public class DailyPaymentPolicy implements PaymentPolicy {

    @Override
    public int calculatePayment(Instant entryTime, Instant paymentTime) {
        Duration dayParkingDuration = Duration.between(entryTime, paymentTime);
        return calculateNoOfIntervals(dayParkingDuration) * 3;
    }

    private int calculateNoOfIntervals(Duration parkingduration) {
        long duration = parkingduration.toMillis();
        return (int) (1 + duration / TimeUtils.minAsMillis(15));
    }

}

