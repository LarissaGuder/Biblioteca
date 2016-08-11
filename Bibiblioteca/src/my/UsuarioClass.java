/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package my;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Larissa Guder
 */
public class UsuarioClass {

    String nome;
    String cpf;
    String codigo;

    public void inserir_usuario(String a1, String a2) {
        String codigo = null;
        Statement stmt = null;
        Conec_BD in = new Conec_BD();
        try {
            Class.forName(in.driver);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(UsuarioClass.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            Connection con = DriverManager.getConnection(in.url, in.nome, in.senha);
            PreparedStatement banco_usuario = con.prepareStatement("insert into usuarios values (?, ?) ");
            banco_usuario.setString(1, a1);
            banco_usuario.setString(2, a2);
            // banco_usuario.setString(3, a3);

            banco_usuario.executeUpdate();

            stmt = con.createStatement();

            ResultSet rs = stmt.executeQuery("select * from usuarios where nome_usur like '%" + a1 + "%'");
            while (rs.next()) {
                codigo = rs.getString("cod_usuar");
            }

            JOptionPane.showMessageDialog(null, "Código do usuário: " + codigo);

            //  JOptionPane.showMessageDialog(null, a3);
            // this.dispose();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "ERRO");
            Logger.getLogger(UsuarioClass.class.getName()).log(Level.SEVERE, null, ex);
        }
        //  tabela
    }

    public ArrayList<UsuarioClass> listar_usuarios(String sql) {
        Conec_BD par = new Conec_BD();

        ArrayList<UsuarioClass> lista = new ArrayList<>();

        try {
            Connection con = DriverManager.getConnection(par.url, par.nome, par.senha);
            Statement parametro = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet busca = parametro.executeQuery(sql);

            while (busca.next()) {

                UsuarioClass a1 = new UsuarioClass();

                a1.nome = busca.getString("nome_usur");
                a1.cpf = busca.getString("cpf_usur");
                a1.codigo = busca.getString("cod_usuar");

                lista.add(a1);

            }

            return lista;

        } catch (SQLException ex) {
            Logger.getLogger(CadastroAcervoClass.class.getName()).log(Level.SEVERE, null, ex);
        }

        return new ArrayList<>();

    }

    public DefaultTableModel monta_tabela(String par1, String par2, String par3) {

        DefaultTableModel tabela_Usuario = new DefaultTableModel();

        tabela_Usuario.addColumn("Nome");
        tabela_Usuario.addColumn("CPF");
        tabela_Usuario.addColumn("Código");

        UsuarioClass usuario = new UsuarioClass();

        ArrayList<UsuarioClass> dados = usuario.listar_usuarios("select * from usuarios where nome_usur like '%" + par1 + "%' or cpf_usur like '%" + par2 + "%' or cod_usuar = " + par3 + "");

        for (UsuarioClass base : dados) {

            tabela_Usuario.addRow(new Object[]{base.nome, base.cpf, base.codigo});

        }

        return tabela_Usuario;

    }

    public void edita(String a1, String a2, String a3) {
        Conec_BD in = new Conec_BD();

        try {
            Class.forName(in.driver);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(UsuarioClass.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            Connection con = DriverManager.getConnection(in.url, in.nome, in.senha);
            PreparedStatement usuarios_update = con.prepareStatement("UPDATE usuarios  SET nome_usur  ='" + a1 + "', cpf_usur = '" + a2 + "' WHERE cod_usuar = '" + a3 + "'");
            usuarios_update.executeUpdate();
            JOptionPane.showMessageDialog(null, "Usuário |" + a1 + "| atualizado");

        } catch (SQLException ex) {
            Logger.getLogger(UsuarioClass.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void excluir(String a1) {

        Conec_BD in = new Conec_BD();

        try {
            Class.forName(in.driver);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(UsuarioClass.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            Connection con = DriverManager.getConnection(in.url, in.nome, in.senha);
            PreparedStatement excluir = con.prepareStatement("DELETE FROM usuarios WHERE cod_usuar = " + a1 + " and emprestimo = 'false'");
            excluir.executeUpdate();
            JOptionPane.showMessageDialog(null, "Usuário excluido");

        } catch (SQLException ex) {
            Logger.getLogger(UsuarioClass.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}
