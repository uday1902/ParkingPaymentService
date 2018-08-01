package devcraft.parking;


import devcraft.parking.jpa.ParkingEntryRepository;
import devcraft.parking.policy.*;

import java.time.*;
import java.time.temporal.ChronoUnit;
import java.time.temporal.Temporal;

import static java.time.Duration.between;
import static java.time.ZoneOffset.UTC;

public class PaymentService {

    private ParkingEntryRepository repository;
    private Clock clock;
    private PaymentPolicy paymentPolicy;

    public interface Clock {
        long now();
    }

    public PaymentService(Clock clock, ParkingEntryRepository repository, PaymentPolicy paymentPolicy) {
        this.clock = clock;
        this.repository = repository;
        this.paymentPolicy = paymentPolicy;
    }

    public long enterParking() {
        ParkingEntryRepository.ParkingEntry entry = new ParkingEntryRepository.ParkingEntry();
        long entryTime = clock.now();
        entry.setTime(entryTime);
        repository.save(entry);
        return entry.getCode();
    }

    public int calcPayment(long code) {
        long paymentTime = clock.now();
        ParkingEntryRepository.ParkingEntry entry = repository.findOne(code);
        long entryTime = entry.getTime();
        return paymentPolicy.calculatePayment(Instant.ofEpochMilli(entryTime), Instant.ofEpochMilli(paymentTime));
    }
}