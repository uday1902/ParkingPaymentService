package devcraft.parking.policy;

import devcraft.parking.util.TimeUtils;

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
        return TimeUtils.isLessThan10Min(duration)? 0:paymentPolicy.calculatePayment(entryTime,paymentTime);
    }
}
