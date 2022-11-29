package aed.scriptsql;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Properties;

public class ConexionDB {

    private static Connection conn;
    private static Properties prop = new Properties();

    public void conectarBD(File properties)
            throws FileNotFoundException, IOException, ClassNotFoundException, SQLException {

        prop.load(new FileReader(properties));

        Class.forName(ConexionDB.prop.getProperty("driver"));

        conn = DriverManager.getConnection(ConexionDB.prop.getProperty("url"), ConexionDB.prop.getProperty("user"),
                ConexionDB.prop.getProperty("password"));

    }

    public void desconetarBD() throws SQLException {
        conn.close();
    }

    public Boolean ejecutar(String comando) throws SQLException {

        return conn.createStatement().execute(comando);

    }

    public ArrayList<String> getResultado(String comando) throws SQLException {

        ResultSet resultado = conn.createStatement().executeQuery(comando);
        ResultSetMetaData metadatos = resultado.getMetaData();

        int numeroColumnas = metadatos.getColumnCount();
        String[] nombresColumnas = new String[numeroColumnas];

        for (int i = 1; i <= numeroColumnas; i++) {
            nombresColumnas[i - 1] = metadatos.getColumnName(i);
        }

        ArrayList<String> consulta = new ArrayList<>();

        while (resultado.next()) {

            consulta.add(String.format("%s: %s \t | | %s: %s", nombresColumnas[0], resultado
                    .getString(nombresColumnas[0]), nombresColumnas[1], resultado.getString(nombresColumnas[1])));

            consulta.add("------------------------------------------");

        }

        return consulta; // TODO Cambiar return

    }
}
