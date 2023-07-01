
import java.sql.PreparedStatement;
import java.sql.Connection;
import javax.swing.JOptionPane;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ProdutosDAO {

    private conectaDAO conexao;
    private Connection conn;

    public ProdutosDAO() {
        this.conexao = new conectaDAO();
        this.conn = this.conexao.connectDB();
    }

    public void cadastrarProduto(ProdutosDTO produto) {

        // Inicia a transação
        try {
            conn.setAutoCommit(false);

            // Insere o produto no banco de dados
            String sql = "INSERT INTO produtos (nome, valor, status) VALUES (?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, produto.getNome());
            stmt.setDouble(2, produto.getValor());
            stmt.setString(3, produto.getStatus());
            stmt.executeUpdate();

            // Confirma a transação
            conn.commit();
            JOptionPane.showMessageDialog(null, "Produto cadastrado com sucesso!");
        } catch (SQLException e) {
            // Desfaz a transação em caso de erro
            try {
                conn.rollback();
            } catch (SQLException rollbackEx) {
                JOptionPane.showMessageDialog(null, "Erro ao desfazer transação: " + rollbackEx.getMessage());
            }
            JOptionPane.showMessageDialog(null, "Erro ao inserir produto: " + e.getMessage());
        } finally {
            // Restaura o modo de commit automático
            try {
                conn.setAutoCommit(true);
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "Erro ao restaurar o modo de commit automático: " + e.getMessage());
            }
        }
    }

    public List<ProdutosDTO> listarProdutos() {

        List<ProdutosDTO> produtos = new ArrayList<>();
        try {
            String sql = "SELECT * FROM produtos";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                ProdutosDTO produto = new ProdutosDTO();
                produto.setId(rs.getInt("id"));
                produto.setNome(rs.getString("nome"));
                produto.setValor(rs.getInt("valor"));
                produto.setStatus(rs.getString("status"));
                produtos.add(produto);
            }

            rs.close();
            stmt.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao recuperar produtos: " + e.getMessage());
        }

        return produtos;
    }

    public void venderProduto(int id) {

        try {
            // Inicia a transação
            conn.setAutoCommit(false);

            // Atualiza o status do produto para "vendido" no banco de dados
            String sql = "UPDATE produtos SET status = ? WHERE id = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, "Vendido");
            stmt.setInt(2, id);
            stmt.executeUpdate();

            // Confirma a transação
            conn.commit();
            JOptionPane.showMessageDialog(null, "Produto vendido com sucesso!");
        } catch (SQLException e) {
            // Desfaz a transação em caso de erro
            try {
                conn.rollback();
            } catch (SQLException rollbackEx) {
                JOptionPane.showMessageDialog(null, "Erro ao desfazer transação: " + rollbackEx.getMessage());
            }
            JOptionPane.showMessageDialog(null, "Erro ao vender produto: " + e.getMessage());
        } finally {
            // Restaura o modo de commit automático
            try {
                conn.setAutoCommit(true);
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "Erro ao restaurar o modo de commit automático: " + e.getMessage());
            }
        }
    }

    public List<ProdutosDTO> listarProdutosVendidos() {

        List<ProdutosDTO> produtos = new ArrayList<>();
        try {
            String sql = "SELECT * FROM produtos WHERE status = 'Vendido'";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                ProdutosDTO produto = new ProdutosDTO();
                produto.setId(rs.getInt("id"));
                produto.setNome(rs.getString("nome"));
                produto.setValor(rs.getInt("valor"));
                produto.setStatus(rs.getString("status"));
                produtos.add(produto);
            }

            rs.close();
            stmt.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao recuperar produtos: " + e.getMessage());
        }

        return produtos;
    }

}
