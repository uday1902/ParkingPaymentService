package devcraft.parking.policy;

import devcraft.parking.util.TimeUtils;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class DailyPaymentPolicyTest {

    @InjectMocks
    DailyPaymentPolicy dailyPaymentPolicy;

    @Test
    public void testCalculatePaymentLessthan10min(){
        String startTime = "0001-01-01 10:00:00";
        String paymentTime = "0001-01-01 10:09:59";
        int actualPayment = dailyPaymentPolicy.calculatePayment(TimeUtils.parseTimeToInstant(startTime),TimeUtils.parseTimeToInstant(paymentTime));
        Assert.assertEquals(3,actualPayment);
    }

    @Test
    public void testCalculatePaymentLessThan8hours(){
        String startTime = "0001-01-01 10:00:00";
        String paymentTime = "0001-01-01 17:59:59";
        int actualPayment = dailyPaymentPolicy.calculatePayment(TimeUtils.parseTimeToInstant(startTime),TimeUtils.parseTimeToInstant(paymentTime));
        Assert.assertEquals(96,actualPayment);
    }

    @Test
    public void testCalculatePayment8hours(){
        String startTime = "0001-01-01 10:00:00";
        String paymentTime = "0001-01-01 18:00:00";
        int actualPayment = dailyPaymentPolicy.calculatePayment(TimeUtils.parseTimeToInstant(startTime),TimeUtils.parseTimeToInstant(paymentTime));
        Assert.assertEquals(99,actualPayment);
    }

    @Test
    public void testCalculatePaymentLessthan4hours(){
        String startTime = "0001-01-01 10:00:00";
        String paymentTime = "0001-01-01 13:59:59";
        int actualPayment = dailyPaymentPolicy.calculatePayment(TimeUtils.parseTimeToInstant(startTime),TimeUtils.parseTimeToInstant(paymentTime));
        Assert.assertEquals(48,actualPayment);
    }

    @Test
    public void testCalculatePayment4hours(){
        String startTime = "0001-01-01 10:00:00";
        String paymentTime = "0001-01-01 14:00:00";
        int actualPayment = dailyPaymentPolicy.calculatePayment(TimeUtils.parseTimeToInstant(startTime),TimeUtils.parseTimeToInstant(paymentTime));
        Assert.assertEquals(51,actualPayment);
    }
}
