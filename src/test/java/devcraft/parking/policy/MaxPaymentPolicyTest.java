package devcraft.parking.policy;

import devcraft.parking.util.TimeUtils;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import java.time.Instant;

@RunWith(MockitoJUnitRunner.class)
public class MaxPaymentPolicyTest {
    @InjectMocks
    MaxPaymentPolicy maxPaymentPolicy;

    @Mock
    PaymentPolicy paymentPolicy;

    @Test
    public void testCalculatePayment2hours(){
        String startTime = "0001-01-01 10:00:00";
        String paymentTime = "0001-01-01 12:00:00";
        Mockito.doReturn(24).when(paymentPolicy).calculatePayment(Mockito.any(Instant.class),Mockito.any(Instant.class));
        int actualPayment = maxPaymentPolicy.calculatePayment(TimeUtils.parseTimeToInstant(startTime),TimeUtils.parseTimeToInstant(paymentTime));
        Assert.assertEquals(24,actualPayment);
    }

    @Test
    public void testCalculatePayment12hours(){
        String startTime = "0001-01-01 10:00:00";
        String paymentTime = "0001-01-01 12:00:00";
        Mockito.doReturn(144).when(paymentPolicy).calculatePayment(Mockito.any(Instant.class),Mockito.any(Instant.class));
        int actualPayment = maxPaymentPolicy.calculatePayment(TimeUtils.parseTimeToInstant(startTime),TimeUtils.parseTimeToInstant(paymentTime));
        Assert.assertEquals(96,actualPayment);
    }
}
