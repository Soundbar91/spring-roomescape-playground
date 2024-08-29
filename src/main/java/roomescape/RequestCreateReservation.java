package roomescape;

public record RequestCreateReservation(
        String name,
        String date,
        String time
) {
    public Reservation toReservation(Long id) {
        return new Reservation(
                id,
                this.name,
                this.date,
                this.time
        );
    }
}
