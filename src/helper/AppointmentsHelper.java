package helper;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

public abstract class AppointmentsHelper {

    public static boolean create(int apptID, String title, String description, String location,
                                 String type, ZonedDateTime startDateTime, ZonedDateTime endDateTime,
                                 int customerID, int userID, int contactID) throws SQLException {
        System.out.println(startDateTime.toString());
        String sql = "INSERT INTO appointments (Appointment_ID, Title, Description, Location, Type, Start, End," +
                " Customer_ID, User_ID, Contact_ID) VALUES(?, ?, ?, ?, ?, ?, ?, ? ,? ,?)";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setInt(1, apptID);
        ps.setString(2, title);
        ps.setString(3, description);
        ps.setString(4, location);
        ps.setString(5, type);

        ZonedDateTime startUTCTime = startDateTime.withZoneSameInstant(ZoneId.of("UTC"));
        ps.setTimestamp(6, Timestamp.valueOf(startUTCTime.toLocalDateTime()));

        ZonedDateTime endUTCTime = endDateTime.withZoneSameInstant(ZoneId.of("UTC"));
        ps.setTimestamp(7, Timestamp.valueOf(endUTCTime.toLocalDateTime()));

        ps.setInt(8, customerID);
        ps.setInt(9, userID);
        ps.setInt(10, contactID);
        return ps.executeUpdate() > 0;
    }

    public static ResultSet read() throws SQLException {
        String sql = "SELECT * FROM appointments";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);

        return ps.executeQuery();
    }

    public static ResultSet read(int apptID) throws SQLException {
        String sql = "SELECT * FROM appointments WHERE Appointment_ID = " + apptID;
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);

        return ps.executeQuery();
    }

    public static boolean update(int apptID, String title, String description, String location,
                                   String type, LocalDateTime startDateTime, LocalDateTime endDateTime,
                                   int customerID, int userID, int contactID) throws SQLException {
        String sql = "UPDATE appointments SET Title = ?, Description = ?, Location = ?, Type = ?, Start = ?, End = ?," +
                " Customer_ID = ?, User_ID = ?, Contact_ID = ? WHERE Appointment_ID = ?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setString(1, title);
        ps.setString(2, description);
        ps.setString(3, location);
        ps.setString(4, type);
        ps.setTimestamp(5, java.sql.Timestamp.valueOf(startDateTime));
        ps.setTimestamp(6, java.sql.Timestamp.valueOf(endDateTime));
        ps.setInt(7, customerID);
        ps.setInt(8, userID);
        ps.setInt(9, contactID);
        ps.setInt(10, apptID);


        if (ps.executeUpdate() > 0) {
            return true;
        }
        return false;

    }

    public static boolean delete(int apptID) throws SQLException {
        String sql = "DELETE FROM appointments WHERE Appointment_ID = ?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setInt(1, apptID);

        if (ps.executeUpdate() > 0) {
            return true;
        }
        return false;
    }
}
