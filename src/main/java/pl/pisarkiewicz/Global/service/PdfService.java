package pl.pisarkiewicz.Global.service;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import org.springframework.stereotype.Service;
import pl.pisarkiewicz.User.entity.User;
import pl.pisarkiewicz.Visit.entity.Visit;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;

@Service
public class PdfService {
    public void generateInvoice(Visit visit, HttpServletResponse response) {
        try {
            User user = visit.getPatient();
            OutputStream o = response.getOutputStream();
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/pdf");
            response.setHeader("Content-Disposition", "inline; filename=" + user.getEmail() + ".pdf");
            Document pdf = new Document();
            PdfWriter.getInstance(pdf, o);
            pdf.open();
            Paragraph title = new Paragraph("Invoice", new Font(Font.FontFamily.TIMES_ROMAN, 30f, Font.BOLD));
            title.setAlignment(Paragraph.ALIGN_CENTER);
            pdf.add(title);
            pdf.add(new Paragraph(Chunk.NEWLINE));
            pdf.add(new Paragraph(Chunk.NEWLINE));

            Paragraph clinicName = new Paragraph("Nazwa kliniki medycznej");
            clinicName.setAlignment(Paragraph.ALIGN_RIGHT);
            pdf.add(clinicName);
            Paragraph clinicAddress = new Paragraph("Adres kliniki medyczbej");
            clinicAddress.setAlignment(Paragraph.ALIGN_RIGHT);
            pdf.add(clinicAddress);

            Paragraph patient = new Paragraph(user.getFirstName() + " " + user.getLastName());
            pdf.add(patient);
            Paragraph patientPesel = new Paragraph("PESEL: " +user.getPesel());
            pdf.add(patientPesel);
            Paragraph patientEmail = new Paragraph("Email: " +user.getEmail());
            pdf.add(patientEmail);
            Paragraph patientPhone = new Paragraph("Phone number: " +user.getTelephone());
            pdf.add(patientPhone);

            pdf.add(new Paragraph(Chunk.NEWLINE));
            pdf.add(new Paragraph(Chunk.NEWLINE));
            pdf.add(new Paragraph(Chunk.NEWLINE));

            PdfPTable visitTable = new PdfPTable(2);
            visitTable.addCell("Date");
            visitTable.addCell(visit.getVisitHours().getStartDate().plusMinutes((visit.getNumberInQueue() - 1) * visit.getVisitHours().getVisitLength()).toString());
            visitTable.addCell("Service");
            visitTable.addCell(visit.getVisitHours().getDescription());
            visitTable.addCell("Doctor");
            visitTable.addCell(visit.getVisitHours().getDoctor().getFirstName() + " " + visit.getVisitHours().getDoctor().getLastName());
            visitTable.addCell("Fees");
            visitTable.addCell(visit.getVisitHours().getVisitCost() + "zl");
            pdf.add(visitTable);
            pdf.close();
            o.close();
        } catch (DocumentException | IOException e) {
            e.printStackTrace();
        }
    }
}
