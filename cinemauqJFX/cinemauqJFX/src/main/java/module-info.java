module co.edu.uniquindio.poo.cinemauqjfx {
    requires javafx.controls;
    requires javafx.fxml;

    opens co.edu.uniquindio.poo.cinemauqjfx to javafx.fxml;
    opens co.edu.uniquindio.poo.cinemauqjfx.viewController to javafx.fxml;

    exports co.edu.uniquindio.poo.cinemauqjfx;
    exports co.edu.uniquindio.poo.cinemauqjfx.viewController;
}
