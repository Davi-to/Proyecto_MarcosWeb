package pe.edu.utp.korvia.service;

import com.lowagie.text.*;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import pe.edu.utp.korvia.model.Boleta;
import pe.edu.utp.korvia.model.ItemBoleta;
import org.springframework.stereotype.Service;

import java.awt.Color;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

@Service
public class BoletaPdfService {

    public ByteArrayInputStream generarPdf(Boleta boleta) {
        Document document = new Document(PageSize.A4, 40, 40, 60, 40);
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        try {
            PdfWriter.getInstance(document, out);
            document.open();

            // ============ TÍTULO ============
            Font tituloFont = new Font(Font.HELVETICA, 20, Font.BOLD, new Color(13, 110, 253));
            Paragraph titulo = new Paragraph("KORVIA", tituloFont);
            titulo.setAlignment(Element.ALIGN_CENTER);
            document.add(titulo);

            Font subtituloFont = new Font(Font.HELVETICA, 14, Font.BOLD, Color.DARK_GRAY);
            Paragraph subtitulo = new Paragraph("BOLETA ELECTRÓNICA", subtituloFont);
            subtitulo.setAlignment(Element.ALIGN_CENTER);
            subtitulo.setSpacingAfter(20);
            document.add(subtitulo);

            // ============ DATOS DE LA EMPRESA ============
            Font smallFont = new Font(Font.HELVETICA, 10, Font.NORMAL, Color.GRAY);
            Paragraph empresa = new Paragraph(
                    "Av. Garcilaso de la Vega 1348, Lima\n" +
                            "RUC: 20123456789\n" +
                            "ventas@korvia.pe | +51 987 654 321",
                    smallFont);
            empresa.setAlignment(Element.ALIGN_CENTER);
            empresa.setSpacingAfter(20);
            document.add(empresa);

            // ============ DATOS DE LA BOLETA ============
            Font boldFont = new Font(Font.HELVETICA, 11, Font.BOLD);
            Font normalFont = new Font(Font.HELVETICA, 11, Font.NORMAL);

            PdfPTable infoTable = new PdfPTable(2);
            infoTable.setWidthPercentage(100);
            infoTable.setSpacingAfter(15);

            infoTable.addCell(cell("N° Boleta:", boldFont, false));
            infoTable.addCell(cell(boleta.getNumero(), normalFont, false));
            infoTable.addCell(cell("Fecha:", boldFont, false));
            infoTable.addCell(cell(boleta.getFechaFormateada(), normalFont, false));
            infoTable.addCell(cell("Cliente:", boldFont, false));
            infoTable.addCell(cell(boleta.getClienteNombre(), normalFont, false));
            infoTable.addCell(cell("Email:", boldFont, false));
            infoTable.addCell(cell(boleta.getClienteEmail(), normalFont, false));
            infoTable.addCell(cell("Método de pago:", boldFont, false));
            infoTable.addCell(cell(boleta.getMetodoPago(), normalFont, false));

            document.add(infoTable);

            // ============ TABLA DE ITEMS ============
            PdfPTable tabla = new PdfPTable(new float[] { 4, 1.5f, 2, 2 });
            tabla.setWidthPercentage(100);
            tabla.setSpacingBefore(10);
            tabla.setSpacingAfter(20);

            // Encabezados
            Font headerFont = new Font(Font.HELVETICA, 11, Font.BOLD, Color.WHITE);
            Color headerColor = new Color(33, 37, 41);

            tabla.addCell(headerCell("Producto", headerFont, headerColor));
            tabla.addCell(headerCell("Cantidad", headerFont, headerColor));
            tabla.addCell(headerCell("Precio Unit.", headerFont, headerColor));
            tabla.addCell(headerCell("Subtotal", headerFont, headerColor));

            // Filas
            for (ItemBoleta item : boleta.getItems()) {
                tabla.addCell(cell(item.getNombreProducto(), normalFont, true));
                tabla.addCell(cellCenter(String.valueOf(item.getCantidad()), normalFont));
                tabla.addCell(cellRight("S/ " + item.getPrecioUnitario(), normalFont));
                tabla.addCell(cellRight("S/ " + item.getSubtotal(), normalFont));
            }

            document.add(tabla);

            // ============ TOTALES ============
            PdfPTable totales = new PdfPTable(new float[] { 3, 1.5f });
            totales.setWidthPercentage(60);
            totales.setHorizontalAlignment(Element.ALIGN_RIGHT);

            totales.addCell(cellRight("Subtotal:", normalFont));
            totales.addCell(cellRight("S/ " + boleta.getSubtotal(), normalFont));
            totales.addCell(cellRight("IGV (18%):", normalFont));
            totales.addCell(cellRight("S/ " + boleta.getIgv(), normalFont));

            Font totalFont = new Font(Font.HELVETICA, 13, Font.BOLD, new Color(13, 110, 253));
            totales.addCell(cellRight("TOTAL:", totalFont));
            totales.addCell(cellRight("S/ " + boleta.getTotal(), totalFont));

            document.add(totales);

            // ============ PIE ============
            Paragraph pie = new Paragraph(
                    "\n\nGracias por tu compra. Esta boleta es una simulación con fines académicos.",
                    smallFont);
            pie.setAlignment(Element.ALIGN_CENTER);
            document.add(pie);

            document.close();

        } catch (DocumentException e) {
            e.printStackTrace();
        }

        return new ByteArrayInputStream(out.toByteArray());
    }

    // ============ HELPERS ============

    private PdfPCell cell(String texto, Font font, boolean border) {
        PdfPCell cell = new PdfPCell(new Phrase(texto, font));
        cell.setPadding(6);
        if (!border)
            cell.setBorder(Rectangle.NO_BORDER);
        return cell;
    }

    private PdfPCell headerCell(String texto, Font font, Color bg) {
        PdfPCell cell = new PdfPCell(new Phrase(texto, font));
        cell.setBackgroundColor(bg);
        cell.setPadding(8);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        return cell;
    }

    private PdfPCell cellCenter(String texto, Font font) {
        PdfPCell cell = new PdfPCell(new Phrase(texto, font));
        cell.setPadding(6);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        return cell;
    }

    private PdfPCell cellRight(String texto, Font font) {
        PdfPCell cell = new PdfPCell(new Phrase(texto, font));
        cell.setPadding(6);
        cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
        cell.setBorder(Rectangle.NO_BORDER);
        return cell;
    }
}