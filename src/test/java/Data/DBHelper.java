package Data;

import lombok.SneakyThrows;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class DBHelper {
    private static final String url = System.getProperty("db.url");
    private static final String user = System.getProperty("db.username");
    private static final String password = System.getProperty("db.password");

    public DBHelper() {
    }

    @SneakyThrows
    public static String getPaymentStatusDebitCard() {
        String statusBD = "SELECT status FROM payment_entity ORDER BY created DESC LIMIT 1";
        Connection connection = DriverManager.getConnection(url, user, password);
        Statement payStatus = connection.createStatement();
        ResultSet rs = payStatus.executeQuery(statusBD);

        String status = null;
        if (rs.next()) {
            status = rs.getString(1);
        }
        return status;
    }

    @SneakyThrows
    public static String getPaymentStatusCreditCard() {
        String statusBD = "SELECT status FROM credit_request_entity ORDER BY created DESC LIMIT 1";
        Connection connection = DriverManager.getConnection(url, user, password);
        Statement payStatus = connection.createStatement();
        ResultSet rs = payStatus.executeQuery(statusBD);

        String status = null;
        if (rs.next()) {
            status = rs.getString(1);
        }
        return status;
    }
}
