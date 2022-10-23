package properties;

import java.util.ResourceBundle;

public class PropertiesSQL {
    public static String getProperties(String SQL){
        String sql;
        ResourceBundle resourceBundle = ResourceBundle.getBundle("sql");
        sql = resourceBundle.getString(SQL);
        return sql;
    }
}
