package MODELO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;


public class ProductoFunciones {
    PreparedStatement ps;
    ResultSet rs;
    Connection con;
    ConexionBd conectar = new ConexionBd();
    
    public List listar() {
        List<Producto> datos = new ArrayList<>();
        try {
            con = conectar.Conectar();
            ps = con.prepareStatement("select * from producto");
            rs = ps.executeQuery();
            while (rs.next()) {
                Producto p = new Producto();
                p.setId(rs.getInt(1));
                p.setCodigo(rs.getString(2));
                p.setNombre(rs.getString(3));
                p.setMarca(rs.getString(4));
                p.setModelo(rs.getString(5));
                p.setPrecio(rs.getInt(6));
                p.setStock(rs.getInt(7));
                datos.add(p);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al encontrar datos! "+e.getMessage());
        }
        return datos;
    }
    
    public int agregar(Producto pro) {  
        int r=0;
        String sql="insert into producto(Codigo, Nombre, Marca, Modelo, Precio, Stock)values(?, ?, ?, ?, ?, ?)";
        try {
            con = conectar.Conectar();
            ps = con.prepareStatement(sql);            
            ps.setString(1, pro.getCodigo());
            ps.setString(2, pro.getNombre());
            ps.setString(3, pro.getMarca());
            ps.setString(4, pro.getModelo());
            ps.setInt(5, pro.getPrecio());
            ps.setInt(6, pro.getStock());
            r=ps.executeUpdate();    
            if(r==1){
                return 1;
            }
            else{
                return 0;
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al guardar: "+e.getMessage());
        }  
        return r;
    }
    public int Actualizar(Producto pro) {  
        int r=0;
        String sql="update producto set Codigo=?, Nombre=?, Marca=?, Modelo=?, Precio=?, Stock=? where IdProducto=?";        
        try {
            con = conectar.Conectar();
            ps = con.prepareStatement(sql);            
            ps.setString(1, pro.getCodigo());
            ps.setString(2, pro.getNombre());
            ps.setString(3, pro.getMarca());
            ps.setString(4, pro.getModelo());
            ps.setInt(5, pro.getPrecio());
            ps.setInt(6, pro.getStock());
            ps.setInt(7, pro.getId());
            r=ps.executeUpdate();    
            if(r==1){
               return 1;
            }
            else{
                return 0;
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al actualizar datos: "+e.getMessage());
        }  
        return r;
    }
    public int Delete(int id){
        int r=0;
        String sql="delete from producto where IdProducto="+id;
        try {
            con=conectar.Conectar();
            ps=con.prepareStatement(sql);
            r= ps.executeUpdate();
        } catch (Exception e) {
        }
        return r;
    }
}
