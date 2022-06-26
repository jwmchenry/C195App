package helper;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public abstract class JoinHelper {

    public static ResultSet customersDivisionCountryRead() throws SQLException {
        String sql = "SELECT customers.Customer_ID, customers.Customer_Name, " +
                "customers.Address, countries.Country_ID, customers.Postal_Code, customers.Phone, " +
                "first_level_divisions.Division, countries.Country\n" +
                "FROM customers\n" +
                "JOIN first_level_divisions ON customers.Division_ID = first_level_divisions.Division_ID\n" +
                "JOIN countries ON countries.Country_ID = first_level_divisions.Country_ID;\n";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();

        return rs;
    }

    public static ResultSet divisionsCountriesRead() throws SQLException {
        String sql = "SELECT countries.Country_ID, first_level_divisions.Division, countries.Country, " +
                "first_level_divisions.Division_ID\n" +
                "FROM first_level_divisions\n" +
                "JOIN countries ON first_level_divisions.Country_ID = countries.Country_ID;";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();

        return rs;
    }
}
