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
public class WeeklyPaymentPolicyTest {

    @InjectMocks
    WeeklyPaymentPolicy weeklyPaymentPolicy;

    @Mock
    PaymentPolicy paymentPolicy;


    @Test
    public void testCalculatePaymentLessthan1day(){
        String startTime = "0001-01-01 06:00:00";
        String paymentTime = "0001-01-02 05:10:00";
        Mockito.doReturn(24).when(paymentPolicy).calculatePayment(Mockito.any(Instant.class),Mockito.any(Instant.class));
        int actualPayment = weeklyPaymentPolicy.calculatePayment(TimeUtils.parseTimeToInstant(startTime),TimeUtils.parseTimeToInstant(paymentTime));
        Assert.assertEquals(24,actualPayment);
    }

    @Test
    public void testCalculatePayment1day(){
        String startTime = "0001-01-01 06:00:00";
        String paymentTime = "0001-01-02 06:00:00";
        Mockito.doReturn(24).when(paymentPolicy).calculatePayment(Mockito.any(Instant.class),Mockito.any(Instant.class));
        int actualPayment = weeklyPaymentPolicy.calculatePayment(TimeUtils.parseTimeToInstant(startTime),TimeUtils.parseTimeToInstant(paymentTime));
        Assert.assertEquals(24,actualPayment);
    }

    @Test
    public void testCalculatePayment2days(){
        String startTime = "0001-01-01 06:00:00";
        String paymentTime = "0001-01-02 06:00:01";
        Mockito.doReturn(96).when(paymentPolicy).calculatePayment(Mockito.any(Instant.class),Mockito.any(Instant.class));
        int actualPayment = weeklyPaymentPolicy.calculatePayment(TimeUtils.parseTimeToInstant(startTime),TimeUtils.parseTimeToInstant(paymentTime));
        Assert.assertEquals(96*2,actualPayment);
    }
}
