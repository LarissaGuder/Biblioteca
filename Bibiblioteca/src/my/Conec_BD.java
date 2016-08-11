/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package my;

import java.sql.Connection;
import java.sql.PreparedStatement;

/**
 *
 * @author Larissa Guder
 */
public class Conec_BD {
    // Cadastro de Usuarios

    public String nome = "postgres";
    public String senha = "lerigou";
    public String url = "jdbc:postgresql://localhost/gerBib";
    public Connection con;
    public PreparedStatement stm;
    public String driver = "org.postgresql.Driver";

    public static void main(String[] args) {
        new Conec_BD();
    }
    /*   public  Conec_BD(){
   
     try {
     Class.forName(driver);
     con = DriverManager.getConnection(url, nome, senha);
     String sql = "insert into usuarios values(?,?,?)";
     stm = con.prepareStatement(sql);
     stm.setString(1, "Larissa Guder");
     stm.setString(2, "27656734");
     stm.setString(3, "234");
   
   
     stm.executeUpdate();
     con.close();
   
   
     } catch(ClassNotFoundException | SQLException e){
     System.out.println("Erro: " + e);
   
     }
     }*/

}
