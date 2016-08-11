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
 * @author lg0116088
 */
public class CadastroAcervoClass {

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public String getISSN() {
        return ISSN;
    }

    public void setISSN(String ISSN) {
        this.ISSN = ISSN;
    }

    public String getAno() {
        return ano;
    }

    public void setAno(String ano) {
        this.ano = ano;
    }

    public String getTipoObra() {
        return tipoObra;
    }

    public void setTipoObra(String tipoObra) {
        this.tipoObra = tipoObra;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public String getGeracodigo() {
        return geracodigo;
    }

    public void setGeracodigo(String geracodigo) {
        this.geracodigo = geracodigo;
    }

    String titulo;
    String codigo;
    String status;
    String autor;
    String ISSN;
    String ano;
    String tipoObra;
    String genero;
    String geracodigo;

    public void inserir_obra(String a1, String a2, String a3, String a4, String a5, String a6) {

        String codigo = null;
        Statement stmt = null;
        Conec_BD in = new Conec_BD();
        try {
            Class.forName(in.driver);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(cadastroAcervo.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            Connection con = DriverManager.getConnection(in.url, in.nome, in.senha);
            PreparedStatement banco_acervo = con.prepareStatement("insert into acervo values (?, ?, ?, ?, ?, ?, ?) ");
            banco_acervo.setString(1, a1);
            banco_acervo.setString(2, a2);
            banco_acervo.setString(3, a3);
            banco_acervo.setString(4, a4);
            banco_acervo.setString(5, a5);
            banco_acervo.setString(6, a6);
            banco_acervo.setString(7, "DISPONÍVEL");
            // banco_acervo.setString(8, a7);

            banco_acervo.executeUpdate();

            stmt = con.createStatement();

            ResultSet rs = stmt.executeQuery("select * from acervo where titulo like '%" + a1 + "%'");
            while (rs.next()) {
                codigo = rs.getString("cod_obra");
            }

            JOptionPane.showMessageDialog(null, "Obra cadastrada. Código da obra: " + codigo);

//                this.dispose();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "ERRO");
            Logger.getLogger(cadastroAcervo.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public ArrayList<CadastroAcervoClass> listar_livros(String sql) {
        Conec_BD par = new Conec_BD();

        ArrayList<CadastroAcervoClass> lista = new ArrayList<>();

        try {
            Connection con = DriverManager.getConnection(par.url, par.nome, par.senha);
            Statement parametro = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet busca = parametro.executeQuery(sql);

            while (busca.next()) {

                CadastroAcervoClass a1 = new CadastroAcervoClass();

                a1.titulo = busca.getString("titulo");
                a1.codigo = busca.getString("cod_obra");
                a1.status = busca.getString("status");
                a1.ISSN = busca.getString("issn");
                a1.ano = busca.getString("ano");
                a1.autor = busca.getString("autor");
                a1.tipoObra = busca.getString("tipo_obra");
                a1.genero = busca.getString("genero");
                lista.add(a1);

            }
            return lista;

        } catch (SQLException ex) {
            Logger.getLogger(CadastroAcervoClass.class.getName()).log(Level.SEVERE, null, ex);
        }

        return new ArrayList<>();

    }

    public DefaultTableModel monta_tabela(String par1, String par2) {

        DefaultTableModel tabelaAcervo = new DefaultTableModel();

        tabelaAcervo.addColumn("Título");
        tabelaAcervo.addColumn("Código");
        tabelaAcervo.addColumn("Gênero");
        tabelaAcervo.addColumn("Tipo de Obra");
        tabelaAcervo.addColumn("Status");
        tabelaAcervo.addColumn("Autor");
        tabelaAcervo.addColumn("Ano");
        tabelaAcervo.addColumn("ISSN");
        CadastroAcervoClass acervo = new CadastroAcervoClass();

        ArrayList<CadastroAcervoClass> dados = acervo.listar_livros("select * from acervo where titulo like '%" + par1 + "%' and codigo like '%" + par2 + "%'");

        for (CadastroAcervoClass base : dados) {

            tabelaAcervo.addRow(new Object[]{base.titulo, base.codigo, base.status, base.ISSN, base.ano, base.autor, base.genero, base.tipoObra});

        }

        return tabelaAcervo;

    }

}
