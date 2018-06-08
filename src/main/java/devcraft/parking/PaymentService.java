package devcraft.parking;


import devcraft.parking.jpa.ParkingEntryRepository;

import java.time.*;
import java.time.temporal.ChronoUnit;
import java.time.temporal.Temporal;
import java.time.temporal.TemporalUnit;

import static java.time.Duration.between;
import static java.time.ZoneOffset.UTC;

public class PaymentService {

    private ParkingEntryRepository repository;
    private Clock clock;

    public interface Clock {
        long now();
    }

    public PaymentService(Clock clock, ParkingEntryRepository repository) {
        this.clock = clock;
        this.repository = repository;
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
        return calcPayment(Instant.ofEpochMilli(entryTime), Instant.ofEpochMilli(paymentTime));
    }

    private int calcPayment(Instant entryTime, Instant paymentTime) {

        ZonedDateTime entryDate = ZonedDateTime.ofInstant(entryTime, UTC);
        ZonedDateTime next6Am = ZonedDateTime.of(entryDate.toLocalDate(), LocalTime.of(6, 0), UTC);
        if (!next6Am.isAfter(entryDate)) {
            next6Am = next6Am.plus(24, ChronoUnit.HOURS);
        }

        int payment = 0;
        while (next6Am.toInstant().isBefore(paymentTime)) {
            payment += dailyPayment(entryTime, next6Am.toInstant());
            entryTime = next6Am.toInstant();
            next6Am = next6Am.plus(24,ChronoUnit.HOURS);
        }
        payment += dailyPayment(entryTime,paymentTime);
        return payment;
    }

    private int dailyPayment(Instant entryTime, Instant paymentTime) {
        Duration timeInParking = between(entryTime, paymentTime);
        if (isLessThan10Min(timeInParking))
            return 0;
        ZonedDateTime zonedEntryTime = ZonedDateTime.ofInstant(entryTime, UTC);
        boolean b = zonedEntryTime.getDayOfWeek() == DayOfWeek.THURSDAY || zonedEntryTime.getDayOfWeek() == DayOfWeek.FRIDAY || zonedEntryTime.getDayOfWeek() == DayOfWeek.SATURDAY;
        if (b) {
            return calcWeekendPayment(entryTime, paymentTime);
        }

        int amountToPay = 12;
        if (!isLessThanAnHour(timeInParking)) {
            long intervalsToPay = calcIntervalsToPay(timeInParking.toMillis());
            amountToPay += (intervalsToPay * 3);
        }
        return Math.min(amountToPay, 96);
    }

    private int calcWeekendPayment(Instant entryTime, Instant paymentTime) {
        ZonedDateTime entryDay = ZonedDateTime.ofInstant(entryTime, UTC);
        ZonedDateTime nightStart = ZonedDateTime.of(entryDay.toLocalDate(), LocalTime.of(22, 0), UTC);


        Duration durationInDayParking = Duration.between(entryTime, min(nightStart.toInstant(),paymentTime));
        Duration durationInNigntParking = Duration.between(max(entryTime,nightStart.toInstant()), paymentTime);

        int amountToPay = 0;
        if (durationInDayParking.toMillis() > 0) {
            amountToPay += 12;
            if (!isLessThanAnHour(durationInDayParking)) {
                long intervalsToPay = calcIntervalsToPay(durationInDayParking.toMillis());
                amountToPay += (intervalsToPay * 3);
            }
        }
        if (durationInNigntParking.toMillis() > 0) {
            amountToPay += 40;
        }
        return Math.min(amountToPay, 96);
    }

    private Temporal max(Instant t1, Instant t2) {
        if (t1.isAfter(t2))
            return t1;
        return t2;
    }

    private Temporal min(Instant t1, Instant t2) {
        if (t1.isBefore(t2))
            return t1;
        return t2;
    }

    private boolean isLessThanAnHour(Duration timeInParking) {
        return timeInParking.toMillis() < Duration.ofMinutes(60).toMillis();
    }

    private boolean isLessThan10Min(Duration timeInParking) {
        return timeInParking.toMillis() < Duration.ofMinutes(10).toMillis();
    }

    private long calcIntervalsToPay(long timeInParking) {
        timeInParking -= minAsMillis(60);

        return 1 + timeInParking / minAsMillis(15);
    }

    private long minAsMillis(int min) {
        return min * 60 * 1000L;
    }

}