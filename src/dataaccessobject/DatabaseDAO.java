package dataaccessobject;

import java.sql.ResultSet;

public interface DatabaseDAO {

    boolean create();
    ResultSet read();
    boolean update();
    boolean delete();

}
