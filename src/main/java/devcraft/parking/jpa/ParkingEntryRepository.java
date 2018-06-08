package devcraft.parking.jpa;

import org.springframework.data.repository.CrudRepository;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

public interface ParkingEntryRepository extends CrudRepository<ParkingEntryRepository.ParkingEntry, Long> {

    @Entity(name = "parking_entry")
    public static class ParkingEntry {

        @Id
        @GeneratedValue
        Long code;
        long time;

        public Long getCode() {
            return code;
        }

        public void setCode(Long code) {
            this.code = code;
        }

        public long getTime() {
            return time;
        }

        public void setTime(long time) {
            this.time = time;
        }
    }

}
