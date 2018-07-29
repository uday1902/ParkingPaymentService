package devcraft.parking.policy;

import java.time.Instant;

public interface PaymentPolicy {
    int calculatePayment(Instant entryTime, Instant paymentTime);
}
