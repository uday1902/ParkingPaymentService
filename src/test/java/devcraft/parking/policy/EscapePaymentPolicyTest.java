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
public class EscapePaymentPolicyTest {

    @InjectMocks
    EscapePaymentPolicy escapePaymentPolicy;

    @Mock
    PaymentPolicy paymentPolicy;

    @Test
    public void testCalculatePaymentLessthan10min(){
        String startTime = "0001-01-01 10:00:00";
        String paymentTime = "0001-01-01 10:09:59";
        Mockito.doReturn(12).when(paymentPolicy).calculatePayment(Mockito.any(Instant.class),Mockito.any(Instant.class));
        int actualPayment = escapePaymentPolicy.calculatePayment(TimeUtils.parseTimeToInstant(startTime),TimeUtils.parseTimeToInstant(paymentTime));
        Assert.assertEquals(0,actualPayment);
    }

    @Test
    public void testCalculatePayment10min(){
        String startTime = "0001-01-01 10:00:00";
        String paymentTime = "0001-01-01 10:10:00";
        Mockito.doReturn(12).when(paymentPolicy).calculatePayment(Mockito.any(Instant.class),Mockito.any(Instant.class));
        int actualPayment = escapePaymentPolicy.calculatePayment(TimeUtils.parseTimeToInstant(startTime),TimeUtils.parseTimeToInstant(paymentTime));
        Assert.assertEquals(12,actualPayment);
    }

    @Test
    public void testCalculatePayment11min(){
        String startTime = "0001-01-01 10:00:00";
        String paymentTime = "0001-01-01 10:11:00";
        Mockito.doReturn(12).when(paymentPolicy).calculatePayment(Mockito.any(Instant.class),Mockito.any(Instant.class));
        int actualPayment = escapePaymentPolicy.calculatePayment(TimeUtils.parseTimeToInstant(startTime),TimeUtils.parseTimeToInstant(paymentTime));
        Assert.assertEquals(12,actualPayment);
    }
}
