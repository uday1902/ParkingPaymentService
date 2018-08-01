package devcraft.parking.policy;

import java.time.Duration;
import java.time.Instant;

public class EscapePaymentPolicy implements PaymentPolicy {
    PaymentPolicy paymentPolicy;

    public EscapePaymentPolicy(PaymentPolicy paymentPolicy){
        this.paymentPolicy = paymentPolicy;
    }

    @Override
    public int calculatePayment(Instant entryTime, Instant paymentTime) {
        Duration duration = Duration.between(entryTime,paymentTime);
        return isLessThan10Min(duration)? 0:paymentPolicy.calculatePayment(entryTime,paymentTime);
    }

    private boolean isLessThan10Min(Duration timeInParking) {
        return timeInParking.toMillis() < Duration.ofMinutes(10).toMillis();
    }
}
