package CONTROLADOR;

import MODELO.Page_pdf;
import MODELO.Producto;
import MODELO.ProductoFunciones;
import VISTA.VistaPrincipal;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

public class Controlador implements ActionListener {

    ProductoFunciones pf = new ProductoFunciones();
    Producto p = new Producto();
    VistaPrincipal vista = new VistaPrincipal();
    Page_pdf pdf = new Page_pdf();
    DefaultTableModel modelo = new DefaultTableModel();
    
    public Controlador(VistaPrincipal v) {
        this.vista = v;
        this.vista.btnListar.addActionListener(this);
        this.vista.btnAgregar.addActionListener(this);
        this.vista.btnEditar.addActionListener(this);
        this.vista.btnEliminar.addActionListener(this);
        this.vista.btnActualizar.addActionListener(this);
        this.vista.btnNuevo.addActionListener(this);
        this.vista.btnGenerarPdf.addActionListener(this);
        this.vista.btnVisualizarPdf.addActionListener(this);
        this.vista.btnSalir.addActionListener(this);
        
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == vista.btnListar) {
            limpiarTabla();
            listar(vista.TablaRegistros);
            Limpiar();
        }
        if (e.getSource() == vista.btnAgregar) {
            Agregar();
            listar(vista.TablaRegistros);
            Limpiar();
        }
        if (e.getSource() == vista.btnEditar) {
            int fila = vista.TablaRegistros.getSelectedRow();
            if (fila == -1) {
                JOptionPane.showMessageDialog(vista, "Seleccione una fila para poder Editar");
            } else {
                int id = Integer.parseInt((String) vista.TablaRegistros.getValueAt(fila, 0).toString());
                String Codigo = (String) vista.TablaRegistros.getValueAt(fila, 1);
                String Nombre = (String) vista.TablaRegistros.getValueAt(fila, 2);
                String Marca = (String) vista.TablaRegistros.getValueAt(fila, 3);
                String Modelo = (String) vista.TablaRegistros.getValueAt(fila, 4);
                int Precio = (int) vista.TablaRegistros.getValueAt(fila, 5);
                int Stock = (int) vista.TablaRegistros.getValueAt(fila, 6);
                vista.txtId.setText("" + id);
                vista.txtCodigo.setText(Codigo);
                vista.txtNombre.setText(Nombre);
                vista.txtMarca.setText(Marca);
                vista.txtModelo.setText(Modelo);
                vista.txtPrecio.setText(Integer.toString(Precio));
                vista.txtStock.setText(Integer.toString(Stock));
            }
        }
        if (e.getSource() == vista.btnActualizar) {
            Actualizar();
            listar(vista.TablaRegistros);
            Limpiar();

        }
        if (e.getSource() == vista.btnEliminar) {
            Eliminar();
            listar(vista.TablaRegistros);
            Limpiar();
        }
        if (e.getSource() == vista.btnNuevo) {
            Limpiar();
        }
        if (e.getSource() == vista.btnGenerarPdf) {
            limpiarTabla();
            listar(vista.TablaRegistros);
            pdf.GenerarPdf();
            Limpiar();
        }
        if (e.getSource() == vista.btnVisualizarPdf) {
            pdf.VisualizarPdf();
            Limpiar();
        }
        if (e.getSource() == vista.btnSalir) {
            System.exit(0);
        }

    }
    
    void Limpiar() {
        vista.txtId.setText(null);
        vista.txtCodigo.setText(null);
        vista.txtMarca.setText(null);
        vista.txtNombre.setText(null);
        vista.txtModelo.setText(null);
        vista.txtPrecio.setText(null);
        vista.txtStock.setText(null);
    }

    public void Eliminar() {
        int fila = vista.TablaRegistros.getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(vista, "Selecione una fila para Eliminar");
        } else {
            int id = Integer.parseInt((String) vista.TablaRegistros.getValueAt(fila, 0).toString());
            pf.Eliminar(id);
            JOptionPane.showMessageDialog(vista, "Registro Eliminado...!!!");
        }
        limpiarTabla();
    }

    public void Agregar() {
        if (vista.txtCodigo.getText().equals("")) {
            JOptionPane.showMessageDialog(vista, "Campos vacios, ingresar datos!!");
        } else {
        String Codigo = vista.txtCodigo.getText();
        String Nombre = vista.txtNombre.getText();
        String Marca = vista.txtMarca.getText();
        String Modelo = vista.txtModelo.getText();
        String Precio = vista.txtPrecio.getText();
        String Stock = vista.txtStock.getText();
        p.setCodigo(Codigo);
        p.setNombre(Nombre);
        p.setMarca(Marca);
        p.setModelo(Modelo);
        p.setPrecio(Integer.parseInt(Precio));
        p.setStock(Integer.parseInt(Stock));
        int r = pf.agregar(p);
        if (r == 1) {
            JOptionPane.showMessageDialog(vista, "Producto Agregado con Exito.");
        } else {
        }
        limpiarTabla();
    }
    }

    public void Actualizar() {
        if (vista.txtId.getText().equals("")) {
            JOptionPane.showMessageDialog(vista, "No se Identifica el Id debe selecionar la opcion Editar");
        } else {
            int id = Integer.parseInt(vista.txtId.getText());
            String Codigo = vista.txtCodigo.getText();
            String Nombre = vista.txtNombre.getText();
            String Marca = vista.txtMarca.getText();
            String Modelo = vista.txtModelo.getText();
            String Precio = vista.txtPrecio.getText();
            String Stock = vista.txtStock.getText();
            p.setId(id);
            p.setCodigo(Codigo);
            p.setNombre(Nombre);
            p.setMarca(Marca);
            p.setModelo(Modelo);
            p.setPrecio(Integer.parseInt(Precio));
            p.setStock(Integer.parseInt(Stock));
            int r = pf.Actualizar(p);
            if (r == 1) {
                JOptionPane.showMessageDialog(vista, "Prodiucto Actualizado con Exito.");
            } else {
            }
        }
        limpiarTabla();
    }

    public void listar(JTable tabla) {
        centrarCeldas(tabla);
        modelo = (DefaultTableModel) tabla.getModel();
        tabla.setModel(modelo);
        List<Producto> lista = pf.listar();
        Object[] objeto = new Object[7];
        for (int i = 0; i < lista.size(); i++) {
            objeto[0] = lista.get(i).getId();
            objeto[1] = lista.get(i).getCodigo();
            objeto[2] = lista.get(i).getNombre();
            objeto[3] = lista.get(i).getMarca();
            objeto[4] = lista.get(i).getModelo();
            objeto[5] = lista.get(i).getPrecio();
            objeto[6] = lista.get(i).getStock();
            modelo.addRow(objeto);
        }
        tabla.setRowHeight(35);
        tabla.setRowMargin(20);

    }

    void centrarCeldas(JTable tabla) {
        DefaultTableCellRenderer tcr = new DefaultTableCellRenderer();
        tcr.setHorizontalAlignment(SwingConstants.CENTER);
        for (int i = 0; i < vista.TablaRegistros.getColumnCount(); i++) {
            tabla.getColumnModel().getColumn(i).setCellRenderer(tcr);
        }
    }

    void limpiarTabla() {
        for (int i = 0; i < vista.TablaRegistros.getRowCount(); i++) {
            modelo.removeRow(i);
            i = i - 1;
        }
    }
}
