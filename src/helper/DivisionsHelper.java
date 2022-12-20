package helper;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * This class is a helper that interacts with the divisions database.
 */
public abstract class DivisionsHelper {

    /**
     * This method returns the first level divisions from the database for use.
     * @return
     * @throws SQLException
     */
    public static ResultSet read() throws SQLException {
        String sql = "SELECT * FROM first_level_divisions";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();

        return rs;
    }

}
