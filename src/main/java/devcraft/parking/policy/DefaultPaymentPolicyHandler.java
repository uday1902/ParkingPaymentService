package devcraft.parking.policy;

import java.time.Instant;

public class DefaultPaymentPolicyHandler implements PaymentPolicy {

    private PaymentPolicy escapePaymentPolicy;

    public DefaultPaymentPolicyHandler(){
        PaymentPolicy dailyPaymentPolicy = new DailyPaymentPolicy();
        PaymentPolicy minPaymentPolicy = new MinPaymentPolicy(dailyPaymentPolicy);
        PaymentPolicy weekendNightPaymentPolicy = new WeekendNightPaymentPolicy(minPaymentPolicy);
        PaymentPolicy maxPaymentPolicy = new MaxPaymentPolicy(weekendNightPaymentPolicy);
        PaymentPolicy weeklyPaymentPolicy = new WeeklyPaymentPolicy(maxPaymentPolicy);
        escapePaymentPolicy = new EscapePaymentPolicy(weeklyPaymentPolicy);
    }

    @Override
    public int calculatePayment(Instant entryTime, Instant paymentTime) {
        return escapePaymentPolicy.calculatePayment(entryTime,paymentTime);
    }
}
