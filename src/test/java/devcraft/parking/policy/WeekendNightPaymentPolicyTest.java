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
public class WeekendNightPaymentPolicyTest {

    @InjectMocks
    WeekendNightPaymentPolicy weekendNightPaymentPolicy;

    @Mock
    PaymentPolicy paymentPolicy;

    @Test
    public void testCalculatePaymentLessthan10minDay(){
        String startTime = "0001-01-01 10:00:00";
        String paymentTime = "0001-01-01 10:09:59";
        Mockito.doReturn(12).when(paymentPolicy).calculatePayment(Mockito.any(Instant.class),Mockito.any(Instant.class));
        int actualPayment = weekendNightPaymentPolicy.calculatePayment(TimeUtils.parseTimeToInstant(startTime),TimeUtils.parseTimeToInstant(paymentTime));
        Assert.assertEquals(12,actualPayment);
    }

    @Test
    public void testCalculatePaymentLessthan10minWeekendNight(){
        String startTime = "2018-07-27 22:00:00";
        String paymentTime = "2018-07-27 22:09:59";
        Mockito.doReturn(0).when(paymentPolicy).calculatePayment(Mockito.any(Instant.class),Mockito.any(Instant.class));
        int actualPayment = weekendNightPaymentPolicy.calculatePayment(TimeUtils.parseTimeToInstant(startTime),TimeUtils.parseTimeToInstant(paymentTime));
        Assert.assertEquals(40,actualPayment);
    }

    @Test
    public void testCalculatePaymentLessthan10minWeekdayNight(){
        String startTime = "2018-07-30 22:00:00";
        String paymentTime = "2018-07-30 22:09:59";
        Mockito.doReturn(12).when(paymentPolicy).calculatePayment(Mockito.any(Instant.class),Mockito.any(Instant.class));
        int actualPayment = weekendNightPaymentPolicy.calculatePayment(TimeUtils.parseTimeToInstant(startTime),TimeUtils.parseTimeToInstant(paymentTime));
        Assert.assertEquals(12,actualPayment);
    }

    @Test
    public void testCalculatePayment1minDay10minWeekendNight(){
        String startTime = "2018-07-27 21:59:00";
        String paymentTime = "2018-07-27 22:09:59";
        Mockito.doReturn(12).when(paymentPolicy).calculatePayment(Mockito.any(Instant.class),Mockito.any(Instant.class));
        int actualPayment = weekendNightPaymentPolicy.calculatePayment(TimeUtils.parseTimeToInstant(startTime),TimeUtils.parseTimeToInstant(paymentTime));
        Assert.assertEquals(52,actualPayment);
    }

}
