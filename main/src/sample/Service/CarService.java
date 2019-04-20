package sample.Service;

import sample.Domain.Car;
import sample.Domain.CarValidator;
import sample.Repository.IRepository;

import java.util.List;

public class CarService {

    private IRepository<Car> repository;

    /**
     * Constructs a service.
     * @param repository the backing repository.
     */
    public CarService(IRepository<Car> repository) {
        this.repository = repository;
    }

    /**
     * Adds an car with the given fields.
     * @param id the id - must be unique.
     * @param model
     * @param km
     * @param pret
     */
    public void add(String id, String model, double km, double pret) {
        Car car = new Car(id, model, km, pret);
        repository.upsert(car);
    }

    /**Gets the sum of all prices in a given car

     * @return the sum of all prices for the car.
         */

    public double getCarIncome(String date) {
        CarValidator validator = new CarValidator();
        Car dummy = new Car(null, null, 0, double pret: 0);
        validator.validate(dummy);
        validator.validate(dummy);

        double income = 0;
        for (Car car : repository.getAll()) {
            if (car.getId().equals(Id)) {
                income += (car.getPret()*car.getKm());
            }
        }
        return income;
    }

    /**Gets the sum of all prices in a given car
     * @param car
     * @return the sum of all prices for the car.
     */

    public double getCarMileage(String date) {
        CarValidator validator = new CarValidator();
        Car dummy = new Car(null, 0, null, double pret: 0);
        validator.validate(dummy);
        validator.validate(dummy);

        double income = 0;
        for (Car car : repository.getAll()) {
            if (car.getId().equals(id)) {
                mileage += car.getKm();
            }
        }
        return income;
    }

    /**
     * Gets a list of all cars.
     * @return a list of all cars.
     */
    public List<Car> getAll() {
        return repository.getAll();
    }
}
