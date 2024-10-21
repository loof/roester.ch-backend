package ch.roester.pdf;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PdfRequestDTO {

    @NotBlank(message = "Creditor name cannot be blank")
    private String creditorName;

    @NotBlank(message = "Creditor street cannot be blank")
    private String creditorStreet;

    @NotBlank(message = "Creditor house number cannot be blank")
    private String creditorHouseNo;

    @NotBlank(message = "Creditor postal code cannot be blank")
    private String creditorPostalCode;

    @NotBlank(message = "Creditor town cannot be blank")
    private String creditorTown;

    @NotBlank(message = "Creditor country code cannot be blank")
    @Size(min = 2, max = 2, message = "Creditor country code must be exactly 2 characters")
    private String creditorCountryCode;

    @NotBlank(message = "Debtor name cannot be blank")
    private String debtorName;

    @NotBlank(message = "Debtor street cannot be blank")
    private String debtorStreet;

    @NotBlank(message = "Debtor house number cannot be blank")
    private String debtorHouseNo;

    @NotBlank(message = "Debtor postal code cannot be blank")
    private String debtorPostalCode;

    @NotBlank(message = "Debtor town cannot be blank")
    private String debtorTown;

    @NotBlank(message = "Debtor country code cannot be blank")
    @Size(min = 2, max = 2, message = "Debtor country code must be exactly 2 characters")
    private String debtorCountryCode;

    @NotBlank(message = "Account cannot be blank")
    private String account;

    @NotBlank(message = "Currency cannot be blank")
    @Size(min = 3, max = 3, message = "Currency must be exactly 3 characters")
    private String currency; // New field for currency

    private double amount; // Amount can be zero or negative, consider using @Positive or @PositiveOrZero if needed

    //@Pattern(regexp = "^\\d{27}$", message = "Reference must contain exactly 27 digits")
    private String reference;

    private String message;
}
