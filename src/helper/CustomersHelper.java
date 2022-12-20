package helper;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * This class is a helper that interacts with the customers database.
 */
public abstract class CustomersHelper {

    /**
     * This method creates a customer for inserting into the database.
     * @param customerID
     * @param name
     * @param address
     * @param postalCode
     * @param phone
     * @param divisionID
     * @throws SQLException
     */
    public static void create(int customerID, String name, String address, String postalCode, String phone, int divisionID) throws SQLException {

        String sql = "INSERT INTO customers (Customer_ID, Customer_Name, Address, Postal_Code, Phone, Division_ID)" +
                " VALUES(?, ?, ?, ?, ?, ?)";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setInt(1, customerID);
        ps.setString(2, name);
        ps.setString(3, address);
        ps.setString(4, postalCode);
        ps.setString(5, phone);
        ps.setInt(6, divisionID);

        if (ps.executeUpdate() > 0) {
        }
    }

    /**
     * This method reads the customers from the database for use.
     * @return
     * @throws SQLException
     */
    public static ResultSet read() throws SQLException {
        String sql = "SELECT * FROM customers";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();

        return rs;
    }

    /**
     * This method updates a customer already existing in the database.
     * @param name
     * @param address
     * @param postalCode
     * @param phone
     * @param divisionID
     * @param customerID
     * @return
     * @throws SQLException
     */
    public static boolean update(String name, String address, String postalCode, String phone, int divisionID, int customerID) throws SQLException {

        String sql = "UPDATE customers SET Customer_Name = ?, Address = ?, Postal_Code = ?, Phone = ?, Division_ID = ?" +
                " WHERE Customer_ID = ?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);

        ps.setString(1, name);
        ps.setString(2, address);
        ps.setString(3, postalCode);
        ps.setString(4, phone);
        ps.setInt(5, divisionID);
        ps.setInt(6, customerID);

        if (ps.executeUpdate() > 0) {
            return true;
        }
        return false;
    }

    /**
     * This method deletes a customer from the database.
     * @param customerID
     * @throws SQLException
     */
    public static void delete(int customerID) throws SQLException {
        String sql = "DELETE FROM customers WHERE Customer_ID = ?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setInt(1, customerID);

        if (ps.executeUpdate() > 0) {
        }
    }
}
