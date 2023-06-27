import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.swing.JOptionPane;

public class conectaDAO {

    public Connection connectDB() {
        try {
            Connection conn = DriverManager.getConnection(
                    "jdbc:mysql://localhost/uc11", // linha de conexao
                    "Admin", // usuario do mysql
                    "Rps@32admin"// senha do mysql
            );
            return conn;

        } catch (SQLException erro) {
            JOptionPane.showMessageDialog(null, "Erro ao conectar" + erro.getMessage());
            return null;
        }
    }
}
