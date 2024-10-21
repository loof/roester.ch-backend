package ch.roester.pdf;

import net.codecrete.qrbill.generator.*;
import org.apache.batik.transcoder.Transcoder;
import org.apache.batik.transcoder.TranscoderInput;
import org.apache.batik.transcoder.TranscoderOutput;
import org.apache.fop.svg.PDFTranscoder;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

@Service
public class PdfService {

    public byte[] generatePdfWithQrCode(PdfRequestDTO pdfRequest) {
        // Setup bill
        Bill bill = new Bill();
        bill.setAccount(pdfRequest.getAccount());
        bill.setAmountFromDouble(pdfRequest.getAmount());
        bill.setCurrency(pdfRequest.getCurrency());

        // Set creditor
        Address creditor = new Address();
        creditor.setName(pdfRequest.getCreditorName());
        creditor.setStreet(pdfRequest.getCreditorStreet());
        creditor.setHouseNo(pdfRequest.getCreditorHouseNo());
        creditor.setPostalCode(pdfRequest.getCreditorPostalCode());
        creditor.setTown(pdfRequest.getCreditorTown());
        creditor.setCountryCode(pdfRequest.getCreditorCountryCode());
        bill.setCreditor(creditor);

        // More bill data
        bill.setReference(pdfRequest.getReference());
        bill.setUnstructuredMessage(pdfRequest.getMessage());

        // Set debtor
        Address debtor = new Address();
        debtor.setName(pdfRequest.getDebtorName());
        debtor.setStreet(pdfRequest.getDebtorStreet());
        debtor.setHouseNo(pdfRequest.getDebtorHouseNo());
        debtor.setPostalCode(pdfRequest.getDebtorPostalCode());
        debtor.setTown(pdfRequest.getDebtorTown());
        debtor.setCountryCode(pdfRequest.getDebtorCountryCode());
        bill.setDebtor(debtor);

        // Set output format
        BillFormat format = new BillFormat();
        format.setGraphicsFormat(GraphicsFormat.SVG);
        format.setOutputSize(OutputSize.QR_BILL_ONLY);
        format.setLanguage(Language.DE);
        bill.setFormat(format);

        // Generate QR bill as SVG
        byte[] svg = QRBill.generate(bill);

        // Transcode SVG to PDF
        ByteArrayOutputStream pdfOutputStream = new ByteArrayOutputStream();
        try {
            // Create a PDF transcoder
            Transcoder transcoder = new PDFTranscoder();
            TranscoderInput transcoderInput = new TranscoderInput(new ByteArrayInputStream(svg));
            TranscoderOutput transcoderOutput = new TranscoderOutput(pdfOutputStream);

            // Transcode SVG to PDF
            transcoder.transcode(transcoderInput, transcoderOutput);
        } catch (Exception e) {
            throw new RuntimeException("Error while transcoding SVG to PDF", e);
        }

        return pdfOutputStream.toByteArray();
    }
}
