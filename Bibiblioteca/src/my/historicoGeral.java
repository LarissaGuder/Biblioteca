/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package my;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Larissa Guder
 */
public class historicoGeral {

    public ArrayList<historicoGeral> emprestimos_abertos(String sql) {
        Conec_BD par = new Conec_BD();

        ArrayList<historicoGeral> lista = new ArrayList<>();

        try {
            Connection con = DriverManager.getConnection(par.url, par.nome, par.senha);
            Statement parametro = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet busca = parametro.executeQuery(sql);

            while (busca.next()) {

                historicoGeral a2 = new historicoGeral();

                a2.usuario = busca.getString("usuario");
                a2.obra = busca.getString("obra");
                //  a2.data = busca.getString("data_emp");
                a2.dataDev = busca.getString("data_dev");
                //   a2.cod_usuar = busca.getString("cod_usu");
                a2.data = busca.getString("data_emp");
                a2.devo = busca.getBoolean("emprestimo");
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
        tabelaDev.addColumn("Data de Emprestimo");
        tabelaDev.addColumn("Iten ausente");

        historicoGeral emprestimo = new historicoGeral();

        ArrayList<historicoGeral> dados = emprestimo.emprestimos_abertos("select * from emprestimo");

        for (historicoGeral base : dados) {

            tabelaDev.addRow(new Object[]{base.usuario, base.obra, base.dataDev, base.data, base.devo});

        }

        return tabelaDev;

    }

    String usuario;
    String obra;
    String dataDev;
    String data;
    boolean devo;
}
