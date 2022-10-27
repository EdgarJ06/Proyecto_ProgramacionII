package MODELO;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.FileOutputStream;
import java.time.LocalDate;
import java.util.List;
import javax.swing.JOptionPane;


public class Page_pdf{
    ProductoFunciones pf = new ProductoFunciones();
    Document documento;
    FileOutputStream archivo;
    Paragraph titulo;
    List<Producto> lista = pf.listar();
    
    LocalDate fecha = LocalDate.now();
    
    public void GenerarPdf(){
        try {
            documento = new Document();
            archivo = new FileOutputStream("INVENTARIO "+fecha+".pdf"); 
            PdfWriter.getInstance(documento, archivo);
            documento.open();
            
            Image image = null;
            try {
                image = Image.getInstance("G:\\Mi unidad\\NetBeansProjects\\PROYECTO_PROGRAII_MAVEN\\src\\main\\java\\IMAGENES\\ComercialLogo.png");//Image.getInstance("ComercialImage.jpg");
                image.scaleAbsolute(150, 100);
                image.setAbsolutePosition(410, 730);                
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "No es posible encontrar la Imagen: "+e.getMessage());
            }
            documento.add(image);
            documento.add(Chunk.NEWLINE);
            
            titulo = new Paragraph("INVENTARIO DE PRODUCTOS"); 
            titulo.setAlignment(1);
            documento.add(titulo);
      
            documento.add(Chunk.NEWLINE);
            documento.add(new Paragraph("INVENTARIO GENERADO EL: "+fecha));
            documento.add(Chunk.NEWLINE);
            
            PdfPTable tabla = new PdfPTable(6);
            tabla.setWidthPercentage(100);
            
            PdfPCell Codigo = new PdfPCell(new Phrase("CODIGO")); 
            Codigo.setBackgroundColor(BaseColor.LIGHT_GRAY);
            Codigo.getBottom(4);
            
            PdfPCell Nombre = new PdfPCell(new Phrase("NOMBRE"));
            Nombre.setBackgroundColor(BaseColor.LIGHT_GRAY);
            Nombre.getBottom(4);
            
            PdfPCell Marca = new PdfPCell(new Phrase("MARCA"));
            Marca.setBackgroundColor(BaseColor.LIGHT_GRAY);
            Marca.getBottom(4);
            
            PdfPCell Modelo = new PdfPCell(new Phrase("MODELO"));
            Modelo.setBackgroundColor(BaseColor.LIGHT_GRAY);
            Modelo.getBottom(4);
            
            PdfPCell Precio = new PdfPCell(new Phrase("PRECIO"));
            Precio.setBackgroundColor(BaseColor.LIGHT_GRAY);
            Precio.getBottom(4);
            
            PdfPCell Stock = new PdfPCell(new Phrase("STOCK"));
            Stock.setBackgroundColor(BaseColor.LIGHT_GRAY);
            Stock.getBottom(4);
            tabla.addCell(Codigo);
            tabla.addCell(Nombre);
            tabla.addCell(Marca);
            tabla.addCell(Modelo);
            tabla.addCell(Precio);
            tabla.addCell(Stock);
            
            for(Producto p: this.lista){
                tabla.addCell(p.getCodigo());                
                tabla.addCell(p.getNombre());
                tabla.addCell(p.getMarca());
                tabla.addCell(p.getModelo());
                tabla.addCell("Q "+Integer.toString(p.getPrecio()));
                tabla.addCell(Integer.toString(p.getStock()));
            }
            
            documento.add(tabla);
            
            documento.close();
           JOptionPane.showMessageDialog(null, "DocumentoMGenerado Exitosamente!");
        } catch (Exception e) {
                JOptionPane.showMessageDialog(null,"Error al generar pdf " +e);
            }
    }
    public void VisualizarPdf(){
        try{
            String url = "G:\\Mi unidad\\NetBeansProjects\\PROYECTO_PROGRAII_MAVEN\\INVENTARIO "+fecha+".pdf";
            ProcessBuilder p = new ProcessBuilder();
            p.command("cmd.exe","/c",url);
            p.start();
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, "Error al abrir archivo; "+e.getMessage());
        }
    }
}

