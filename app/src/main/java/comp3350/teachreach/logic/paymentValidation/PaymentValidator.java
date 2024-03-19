package comp3350.teachreach.logic.paymentValidation;

import java.time.LocalDate;

import comp3350.teachreach.logic.exceptions.payment.ExpiredCardExcpetion;
import comp3350.teachreach.logic.exceptions.payment.InvalidCVCException;
import comp3350.teachreach.logic.exceptions.payment.InvalidCardNumberException;
import comp3350.teachreach.logic.exceptions.payment.InvalidExpiryDateException;
import comp3350.teachreach.logic.exceptions.payment.PaymentException;

public class PaymentValidator {

    public boolean validatePaymentInfo(String cardNum, String expiryDate, String cvc) throws PaymentException {
        boolean numValid = true;
        boolean cvcValid = true;
        boolean expiryValid = true;
        boolean notExpired = true;
        try {
            int card = Integer.parseInt(cardNum);
        } catch (NumberFormatException nfe) {
            numValid = false;
        }
        numValid &= cardNum.length() == 16;

        try {
            int cvcNum = Integer.parseInt(cvc);
        } catch (NumberFormatException nfe) {
            cvcValid = false;
        }
        cvcValid &= cvc.length() == 3;

        expiryValid &= expiryDate.length() == 5;
        String formattedDate = "";

        try {
            formattedDate += "20" + expiryDate.substring(3);
            formattedDate += "-" + expiryDate.substring(0, 2);
            formattedDate += "-01";
            LocalDate expDate = LocalDate.parse(formattedDate);

            notExpired = !expDate.isBefore(LocalDate.now());
        } catch (Exception e) {
            expiryValid = false;
        }

        if(!numValid) {
            throw(new InvalidCardNumberException("Invalid Card Number"));
        }
        else if(!cvcValid) {
            throw (new InvalidCVCException("Invalid CVC Number"));
        }
        else if(!expiryValid) {
            throw (new InvalidExpiryDateException("Invalid Expiry Date"));
        }
        else if(!notExpired) {
            throw (new ExpiredCardExcpetion("Card Is Expired"));
        }

        return numValid && cvcValid && expiryValid && notExpired;
    }
}
