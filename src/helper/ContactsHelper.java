package helper;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Contact;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public abstract class ContactsHelper {

    public static ResultSet read() throws SQLException {
        String sql = "SELECT * FROM contacts";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();

        return rs;
    }

    public static ObservableList<Contact> contactList() throws SQLException {
        ResultSet rs = read();
        ObservableList<Contact> contactsList = FXCollections.observableArrayList();

        while (rs.next()) {
            int id = rs.getInt("Contact_ID");
            String name = rs.getString("Contact_Name");

            Contact contact = new Contact(id, name);
            contactsList.add(contact);
        }
        return contactsList;
    }
}
