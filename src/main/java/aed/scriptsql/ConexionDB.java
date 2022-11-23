package aed.scriptsql;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConexionDB {

    private static Connection conn;
    private static Properties prop = new Properties();
    
    public void conectarBD(File properties) throws FileNotFoundException, IOException, ClassNotFoundException, SQLException {

        prop.load(new FileReader(properties));

        Class.forName(ConexionDB.prop.getProperty("driver"));

        conn = DriverManager.getConnection(ConexionDB.prop.getProperty("url"), ConexionDB.prop.getProperty("user"),
                ConexionDB.prop.getProperty("password"));

    }

    public void desconetarBD() throws SQLException {
        conn.close();
    }

    public void ejecutar(String comando) throws SQLException {

        conn.createStatement().execute(comando);

    }
}
