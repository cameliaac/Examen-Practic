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
    public TableColumn tableColumnSum;
    public TableColumn tableColumnDescription;
    public TableColumn tableColumnDate;
    public TextField txtId;
    public TextField txtSum;
    public TextField txtDescription;
    public TextField txtDate;
    public Button btnAdd;
    public TextField txtSumDay;
    public TextField txtSumResult;
    public Button btnSumForDay;

    private CarService invoiceService;
    private ObservableList<Car> invoices = FXCollections.observableArrayList();

    public void btnAddClick(ActionEvent actionEvent) {
        try {
            String id = txtId.getText();
            double sum = Double.parseDouble(txtSum.getText());
            String description = txtDescription.getText();
            String date = txtDate.getText();
            invoiceService.add(id, sum, description, date);

            txtId.clear();
            txtSum.clear();
            txtDescription.clear();
            txtDate.clear();

            invoices.clear();
            invoices.addAll(invoiceService.getAll());

        } catch (CarDateFormatException idfe) {
            Common.showValidationError(idfe.getMessage());
        } catch (RuntimeException rex) {
            Common.showValidationError(rex.getMessage());
        }
    }

    public void setServices(CarService invoiceService) {
        this.invoiceService = invoiceService;
    }

    @FXML
    private void initialize() {

        Platform.runLater(() -> {
            invoices.addAll(invoiceService.getAll());
            tableViewCars.setItems(invoices);
        });
    }

    public void btnSumForDayClick(ActionEvent actionEvent) {
        try {
            String date = txtSumDay.getText();
            double sum = invoiceService.getDaySum(date);
            txtSumResult.setText(String.valueOf(sum));
        } catch (CarDateFormatException idfe) {
            Common.showValidationError(idfe.getMessage());
        }
    }
}
