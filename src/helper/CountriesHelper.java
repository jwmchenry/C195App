package helper;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * This class is a helper that interacts with the countries database.
 */
public abstract class CountriesHelper {

    /**
     * This method returns the countries from the database for use.
     * @return
     * @throws SQLException
     */
    public static ResultSet read() throws SQLException {
        String sql = "SELECT * FROM countries";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();

        return rs;
    }

}
