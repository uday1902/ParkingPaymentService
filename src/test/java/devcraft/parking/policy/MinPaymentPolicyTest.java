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
public class MinPaymentPolicyTest {

    @InjectMocks
    MinPaymentPolicy minPaymentPolicy;

    @Mock
    PaymentPolicy paymentPolicy;

    @Test
    public void testCalculatePaymentLessthan1hour(){
        String startTime = "0001-01-01 10:00:00";
        String paymentTime = "0001-01-01 10:59:59";
        int actualPayment = minPaymentPolicy.calculatePayment(TimeUtils.parseTimeToInstant(startTime),TimeUtils.parseTimeToInstant(paymentTime));
        Assert.assertEquals(12,actualPayment);
    }

    @Test
    public void testCalculatePaymentLessthan30min(){
        String startTime = "0001-01-01 10:00:00";
        String paymentTime = "0001-01-01 10:29:59";
        int actualPayment = minPaymentPolicy.calculatePayment(TimeUtils.parseTimeToInstant(startTime),TimeUtils.parseTimeToInstant(paymentTime));
        Assert.assertEquals(12,actualPayment);
    }

    @Test
    public void testCalculatePaymentGreaterthan1hour(){
        String startTime = "0001-01-01 10:00:00";
        String paymentTime = "0001-01-01 11:59:59";
        Mockito.doReturn(24).when(paymentPolicy).calculatePayment(Mockito.any(Instant.class),Mockito.any(Instant.class));
        int actualPayment = minPaymentPolicy.calculatePayment(TimeUtils.parseTimeToInstant(startTime),TimeUtils.parseTimeToInstant(paymentTime));
        Assert.assertEquals(24,actualPayment);
    }
}
