package devcraft.parking.policy;

import java.time.Duration;
import java.time.Instant;

public class DailyPaymentPolicy implements PaymentPolicy {

    @Override
    public int calculatePayment(Instant entryTime, Instant paymentTime) {
        Duration dayParkingDuration = Duration.between(entryTime, paymentTime);
        return calculateNoOfIntervals(dayParkingDuration) * 3;
    }

    private int calculateNoOfIntervals(Duration parkingduration) {
        Long duration = parkingduration.toMillis();
        return 1 + duration.intValue() / minAsMillis(15);
    }

    private int minAsMillis(int min) {
        return min * 60 * 1000;
    }

}

