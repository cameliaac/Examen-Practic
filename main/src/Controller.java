import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.util.converter.DoubleStringConverter;
import sample.Domain.Car;
import sample.Service.CarService;
import sun.security.pkcs11.wrapper.CK_ATTRIBUTE;

public class Controller {
    public TableView tableViewCars;
    public TableColumn tableColumnId;
    public TableColumn tableColumnModel;
    public TableColumn tableColumnKm;
    public TableColumn tableColumnPret;
    public TextField txtId;
    public TextField txtModel;
    public TextField txtKm;
    public TextField txtPret;
    public Button btnAdd;
    public TextField txtCarIncome;
    public TextField txtCarMileage;
    public Button btnSumForDay;

    private CarService carService;
    private ObservableList<Car> cars = FXCollections.observableArrayList();

    public void btnAddClick(ActionEvent actionEvent) {
        try {
            String id = txtId.getText();
            double income = Double.parseDouble(txtCarIncome.getText());
            String Model = txtModel.getText();
            String km = txtKm.getText();
            carService.add(id, model, km, pret);

            txtId.clear();
            txtSum.clear();
            txtDescription.clear();
            txtDate.clear();

            cars.clear();
            cars.addAll(carService.getAll());

        } catch (CarDateFormatException idfe) {
            Common.showValidationError(idfe.getMessage());
        } catch (RuntimeException rex) {
            Common.showValidationError(rex.getMessage());
        }
    }

    public void setServices(CarService carService) {
        this.carService = carService;
    }

    @FXML
    private void initialize() {

        Platform.runLater(() -> {
            cars.addAll(carService.getAll());
            tableViewCars.setItems(cars);
        });
    }

    public void btnSumForDayClick(ActionEvent actionEvent) {
        try {
            String date = txtCarIncome.getText();
            double sum = carService.getCarIncome(date);
            txtSumResult.setText(String.valueOf(sum));
            Common.showValidationError(idfe.getMessage());
        }
    }
}
