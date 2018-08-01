package devcraft.parking;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import devcraft.parking.jpa.ParkingEntryRepository;
import devcraft.parking.policy.DefaultPaymentPolicyHandler;
import devcraft.parking.policy.PaymentPolicy;
import org.junit.Assert;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

public class Steps {

    private PaymentService parkingService;
    private long code;
    private long now;
    private int amountToPay;

    public Steps(ParkingEntryRepository repository) {
        parkingService = new PaymentService(
                () -> now,
                repository,
                new DefaultPaymentPolicyHandler());
    }

    @Given("^I entered the parking at (.*)$")
    public void i_entered_the_parking_at(String time) throws Throwable {
        now = parseTime(time);
        code = parkingService.enterParking();
    }

    private long parseTime(String time) {
        Instant localDateTime = LocalDateTime.parse(time, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")).toInstant(ZoneOffset.UTC);
        return localDateTime.toEpochMilli();
    }

    @When("^I pay at (.*)$")
    public void i_pay_at(String time) throws Throwable {
        now = parseTime(time);
        amountToPay = parkingService.calcPayment(code);
    }

    @Then("^I should pay (\\d+)$")
    public void i_should_pay(int expectedAmount) throws Throwable {
        Assert.assertEquals(expectedAmount, amountToPay);
    }

}
