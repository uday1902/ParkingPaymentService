package devcraft.parking.policy;

import devcraft.parking.util.TimeUtils;

import java.time.Duration;
import java.time.Instant;

public class MinPaymentPolicy implements PaymentPolicy {

    PaymentPolicy paymentPolicy;

    public MinPaymentPolicy(PaymentPolicy paymentPolicy){
        this.paymentPolicy = paymentPolicy;
    }

    @Override
    public int calculatePayment(Instant entryTime, Instant paymentTime) {
        if(!entryTime.isBefore(paymentTime)){
            return 0;
        }
        Duration parkingDuration = Duration.between(entryTime, paymentTime);
        if(TimeUtils.isLessThanAnHour(parkingDuration)){
            return 12;
        }
        return paymentPolicy.calculatePayment(entryTime,paymentTime);
    }
}
