package devcraft.parking.policy;

import java.time.Instant;

public class DefaultPaymentPolicyHandler implements PaymentPolicy {
    @Override
    public int calculatePayment(Instant entryTime, Instant paymentTime) {
        PaymentPolicy dailyPaymentPolicy = new DailyPaymentPolicy();
        PaymentPolicy minPaymentPolicy = new MinPaymentPolicy(dailyPaymentPolicy);
        PaymentPolicy weekendNightPaymentPolicy = new WeekendNightPaymentPolicy(minPaymentPolicy);
        PaymentPolicy maxPaymentPolicy = new MaxPaymentPolicy(weekendNightPaymentPolicy);
        PaymentPolicy weeklyPaymentPolicy = new WeeklyPaymentPolicy(maxPaymentPolicy);
        EscapePaymentPolicy escapePaymentPolicy = new EscapePaymentPolicy(weeklyPaymentPolicy);
        return escapePaymentPolicy.calculatePayment(entryTime,paymentTime);
    }
}
