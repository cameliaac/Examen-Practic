import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import sample.Domain.Car;
import sample.Domain.CarValidator;
import sample.Repository.CarRepository;
import sample.Repository.IRepository;
import sample.Domain.IValidator;
import sample.Service.CarService;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/sample.fxml"));
        Parent root = fxmlLoader.load();

        Controller controller =  fxmlLoader.getController();

        IValidator<Car> carValidator = new CarValidator();
        IRepository<Car> carIRepository= new CarRepository<>(carValidator, "car.json", Car[].class);

        CarService carService = new CarService(carIRepository);
        carService.add("1", 100, "1 2 3", "20.01.2019");
        carService.add("2", 200, "e e e", "21.02.2019");
        carService.add("3", 300, "c c c", "22.03.2019");

        controller.setServices(carService);

        primaryStage.setTitle("Cars manager");
        primaryStage.setScene(new Scene(root, 650, 500));

        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
