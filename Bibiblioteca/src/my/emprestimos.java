package my;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Larissa Guder
 */
public class emprestimos {

    boolean emprestimo;

    public boolean isEmprestimo() {
        return emprestimo;
    }

    public void setEmprestimo(boolean emprestimo) {
        this.emprestimo = emprestimo;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getCod_usuar() {
        return cod_usuar;
    }

    public void setCod_usuar(String cod_usuar) {
        this.cod_usuar = cod_usuar;
    }

    public String getObra() {
        return obra;
    }

    public void setObra(String obra) {
        this.obra = obra;
    }

    public String getCod_obra() {
        return cod_obra;
    }

    public void setCod_obra(String cod_obra) {
        this.cod_obra = cod_obra;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getDataDev() {
        return dataDev;
    }

    public void setDataDev(String dataDev) {
        this.dataDev = dataDev;
    }
    String usuario;
    String cod_usuar;
    String obra;
    String cod_obra;
    String data;
    String dataDev;

    public void devolver(String a1, int a2) {
        Conec_BD in = new Conec_BD();
        try {
            Class.forName(in.driver);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(emprestimos.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            Connection con = DriverManager.getConnection(in.url, in.nome, in.senha);
            PreparedStatement banco_emprestimos = con.prepareStatement("UPDATE emprestimo  SET emprestimo ='false' WHERE emprestimo = " + true + " and cod_obr like '%" + a1 + "%'");
            banco_emprestimos.executeUpdate();

            banco_emprestimos = con.prepareStatement("UPDATE acervo  SET status ='DISPONÍVEL' WHERE cod_obra = " + a2 + "");

            banco_emprestimos.executeUpdate();

            banco_emprestimos = con.prepareStatement("UPDATE usuarios SET emprestimo = 'false' WHERE cod_usuar = " + a2 + "");
            banco_emprestimos.executeUpdate();

            JOptionPane.showMessageDialog(null, "Item devolvido: " + a2);

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "ERRO");
            Logger.getLogger(emprestimos.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void renovar(String data, String cod) {
        Conec_BD in = new Conec_BD();
        try {
            Class.forName(in.driver);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(emprestimos.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            Connection con = DriverManager.getConnection(in.url, in.nome, in.senha);
            PreparedStatement banco_emprestimos = con.prepareStatement("UPDATE emprestimo  SET data_dev ='" + data + "' WHERE cod_obr like '%" + cod + "%'");;

            banco_emprestimos.executeUpdate();
            JOptionPane.showMessageDialog(null, "Data de Devolução: " + data);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "ERRO");
            Logger.getLogger(emprestimos.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void emprestar(String a1, String a2, String a3, String a4, String a5, String a6) {

        Conec_BD in = new Conec_BD();
        try {
            Class.forName(in.driver);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(emprestimos.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            Connection con = DriverManager.getConnection(in.url, in.nome, in.senha);
            PreparedStatement banco_emprestimos = con.prepareStatement("insert into emprestimo values (?, ?, ?, ?, ?, ?, ?) ");

            //banco_emprestimos1.executeUpdate();
            banco_emprestimos.setString(1, a1);
            banco_emprestimos.setString(2, a2);
            banco_emprestimos.setString(3, a3);
            banco_emprestimos.setString(4, a4);
            banco_emprestimos.setString(5, a5);
            banco_emprestimos.setString(6, a6);
            banco_emprestimos.setBoolean(7, true);
            banco_emprestimos.executeUpdate();

            banco_emprestimos = con.prepareStatement("UPDATE acervo  SET status ='EMPRESTADO' WHERE status = 'DISPONÍVEL' and cod_obra = " + a6 + "");
            banco_emprestimos.executeUpdate();
            // PARA ATUALIZAR NA OUTRA TABELA
            banco_emprestimos = con.prepareStatement("UPDATE usuarios SET emprestimo = 'true' WHERE cod_usuar = " + a5 + "");
            banco_emprestimos.executeUpdate();

            JOptionPane.showMessageDialog(null, "Item: " + a2 + " | Usuário: " + a1 + " | Data de devolução: " + datadevolucao() + "");

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "ERRO");
            Logger.getLogger(emprestimos.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public String dataAtual() {

        Date d = new Date();
        SimpleDateFormat s = new SimpleDateFormat("dd/MM/yyyy");

        String dataAtual = s.format(d.getTime());

        return dataAtual;

    }

    public String datadevolucao() {
        Date d = new Date();
        SimpleDateFormat s = new SimpleDateFormat("dd/MM/yyyy");

        String dataAtual = s.format(d.getTime());
        Calendar c = Calendar.getInstance();
        c.setTime(d);

        c.set(Calendar.DAY_OF_MONTH, c.get(Calendar.DAY_OF_MONTH) + 7);
        String dataDevolucao = s.format(c.getTime());

        return dataDevolucao;
        // System.out.println(new SimpleDateFormat("dd/MM/yyyy").format(c.getTime()));
    }

    public void excluiObra(String titulo) {
        Conec_BD in = new Conec_BD();
        try {
            Class.forName(in.driver);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(emprestimos.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            Connection con = DriverManager.getConnection(in.url, in.nome, in.senha);
            PreparedStatement banco_emprestimos = con.prepareStatement("DELETE FROM acervo WHERE titulo ='" + titulo + "' and status = 'DISPONÍVEL'");
            banco_emprestimos.executeUpdate();

            JOptionPane.showMessageDialog(null, "Item: " + obra + " excluido");

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "ERRO");
            Logger.getLogger(emprestimos.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public ArrayList<emprestimos> emprestimos_abertos(String sql) {
        Conec_BD par = new Conec_BD();

        ArrayList<emprestimos> lista = new ArrayList<>();

        try {
            Connection con = DriverManager.getConnection(par.url, par.nome, par.senha);
            Statement parametro = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet busca = parametro.executeQuery(sql);

            while (busca.next()) {

                emprestimos a2 = new emprestimos();

                a2.usuario = busca.getString("usuario");
                a2.obra = busca.getString("obra");
                //  a2.data = busca.getString("data_emp");
                a2.dataDev = busca.getString("data_dev");
                //   a2.cod_usuar = busca.getString("cod_usu");
                a2.cod_obra = busca.getString("cod_obr");
                lista.add(a2);

            }
            return lista;

        } catch (SQLException ex) {
            Logger.getLogger(emprestimos.class.getName()).log(Level.SEVERE, null, ex);
        }

        return new ArrayList<>();

    }

    public DefaultTableModel monta_tabela(String par1, String par2) {

        DefaultTableModel tabelaDev = new DefaultTableModel();

        tabelaDev.addColumn("Usuário");
        tabelaDev.addColumn("Obra");
        tabelaDev.addColumn("Data de Devolução");
        tabelaDev.addColumn("Código do Item");

        emprestimos emprestimo = new emprestimos();

        ArrayList<emprestimos> dados = emprestimo.emprestimos_abertos("select * from emprestimo where cod_obr like '%" + par1 + "%' and emprestimo =" + true + "");

        for (emprestimos base : dados) {

            tabelaDev.addRow(new Object[]{base.usuario, base.obra, base.dataDev, base.cod_obra});

        }

        return tabelaDev;

    }

    public void editarObra(String a1, String a2, String a3, String a4, String a5, String a6, String a7) {
        Conec_BD in = new Conec_BD();
        try {
            Class.forName(in.driver);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(emprestimos.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            Connection con = DriverManager.getConnection(in.url, in.nome, in.senha);
            System.out.println(a1 + "..." + a2 + "..." + a3 + "..." + a4 + "..." + a5 + "..." + a6 + "..." + a7);                                   // SEPARAR POR VIRGULA                                                                  
            PreparedStatement banco_acervo = con.prepareStatement("UPDATE acervo  SET titulo  ='" + a1 + "', genero = '" + a2 + "', tipo_obra = '" + a3 + "', autor = '" + a4 + "', issn = '" + a5 + "', ano = '" + a6 + "' WHERE cod_obra = '" + a7 + "'");

            banco_acervo.executeUpdate();

            JOptionPane.showMessageDialog(null, "Obra atualizada");

//                this.dispose();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "ERRO");
            Logger.getLogger(emprestimos.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    void data() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
