package sample.Domain;

public class CarValidator {

    public void validate(Car car) {

        String errors = "";
        if (car.getKm() > 0) {
            errors += "Numarul de km trebuie sa fie > 0!\n";
        }

        if (!errors.isEmpty()) {
            throw new RuntimeException(errors);
        }
    }
}
