package devcraft.parking.policy;

import java.time.Duration;
import java.time.Instant;

public class MaxPaymentPolicy implements PaymentPolicy {

    PaymentPolicy paymentPolicy;

    public MaxPaymentPolicy(PaymentPolicy paymentPolicy){
        this.paymentPolicy=paymentPolicy;
    }

    @Override
    public int calculatePayment(Instant entryTime, Instant paymentTime) {
        int dailyPayment = paymentPolicy.calculatePayment(entryTime,paymentTime);
        if(dailyPayment > 96){
            dailyPayment = 96;
        }
        return dailyPayment;
    }
}
