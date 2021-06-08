module edu.agile {
    requires javafx.controls;
    requires javafx.fxml;
    requires lombok;
    requires mongo.java.driver;
    opens edu.agile.Controllers to javafx.fxml;
    opens edu.agile.Models to javafx.base;
    exports edu.agile;
}
