package ch.roester.pdf;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PdfController {

    private final PdfService pdfService;

    public PdfController(PdfService pdfService) {
        this.pdfService = pdfService;
    }

    @Operation(summary = "Generate a PDF with a QR Code",
            description = "Generates a PDF file containing a QR Code based on the provided details of the creditor and debtor.",
            tags = {"PDF Generation"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "PDF generated successfully",
                    content = @Content(mediaType = "application/pdf")),
            @ApiResponse(responseCode = "400", description = "Invalid input provided",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "500", description = "Error while generating PDF",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PostMapping("/generate-pdf-with-qrcode")
    public ResponseEntity<byte[]> generatePdfWithQrCode(
            @Parameter(description = "PDF request details including creditor and debtor information", required = true)
            @Valid @RequestBody
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Details required to generate the PDF with a QR Code",
                    required = true,
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = PdfRequestDTO.class),
                            examples = @ExampleObject(value = "{\n" +
                                    "  \"creditorName\": \"Bob Muster\",\n" +
                                    "  \"creditorStreet\": \"Bobstrasse\",\n" +
                                    "  \"creditorHouseNo\": \"23\",\n" +
                                    "  \"creditorPostalCode\": \"3018\",\n" +
                                    "  \"creditorTown\": \"Bern\",\n" +
                                    "  \"creditorCountryCode\": \"CH\",\n" +
                                    "  \"debtorName\": \"Alice Muster\",\n" +
                                    "  \"debtorStreet\": \"Alicestrasse\",\n" +
                                    "  \"debtorHouseNo\": \"42\",\n" +
                                    "  \"debtorPostalCode\": \"1700\",\n" +
                                    "  \"debtorTown\": \"Fribourg\",\n" +
                                    "  \"debtorCountryCode\": \"CH\",\n" +
                                    "  \"account\": \"CH5604835012345678009\",\n" +
                                    "  \"currency\": \"CHF\",\n" +
                                    "  \"amount\": 1,\n" +
                                    "  \"reference\": \"\",\n" +
                                    "  \"message\": \"\"\n" +
                                    "}")
                    )) PdfRequestDTO pdfRequest) {

        // Delegate to service
        byte[] pdf = pdfService.generatePdfWithQrCode(pdfRequest);

        // Prepare response headers
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=qrbill.pdf");
        headers.add(HttpHeaders.CONTENT_TYPE, "application/pdf");

        // Return generated PDF
        return new ResponseEntity<>(pdf, headers, HttpStatus.OK);
    }
}
