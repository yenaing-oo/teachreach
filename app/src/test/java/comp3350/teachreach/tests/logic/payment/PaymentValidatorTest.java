package comp3350.teachreach.tests.logic.payment;

import org.junit.Test;
import static org.junit.Assert.*;

import comp3350.teachreach.logic.exceptions.payment.*;
import comp3350.teachreach.logic.exceptions.payment.PaymentException;
import comp3350.teachreach.logic.payment.PaymentValidator;


public class PaymentValidatorTest {

    @Test
    public void testPaymentValidator() {
        try {
            boolean flag = PaymentValidator.validatePaymentInfo("0000000000000000", "09/24", "000"); //Functions on valid input
            assertTrue("Valid Card failed", flag);
            assertThrows("InvalidCardNumberException expected", InvalidCardNumberException.class, () ->  {
                PaymentValidator.validatePaymentInfo("000000000000000", "12/99", "000");
            }); // Short length of card number
            assertThrows("InvalidCardNumberException expected", InvalidCardNumberException.class, () ->  {
                PaymentValidator.validatePaymentInfo("00000000000000000", "12/99", "000");
            }); // Long length of card number
            assertThrows("InvalidCardNumberException expected", InvalidCardNumberException.class, () ->  {
                PaymentValidator.validatePaymentInfo("000000000000000a", "12/99", "000");
            }); //Card Number not numeric.
            assertThrows("InvalidCVCException expected", InvalidCVCException.class, () -> {
                PaymentValidator.validatePaymentInfo("0000000000000000", "12/99", "00");
            }); // Short cvc
            assertThrows("InvalidCVCException expected", InvalidCVCException.class, () -> {
                PaymentValidator.validatePaymentInfo("0000000000000000", "12/99", "0000");
            }); // Long cvc
            assertThrows("InvalidCVCException expected", InvalidCVCException.class,
                    () -> {PaymentValidator.validatePaymentInfo("0000000000000000", "12/99", "00a");}); // Non-numeric cvc
            assertThrows("InvalidExpiryDateException expected", InvalidExpiryDateException.class, () -> {
                PaymentValidator.validatePaymentInfo("0000000000000000", "12/9", "000");
                }); // Bad expiry date input
            assertThrows("InvalidExpiryDateException expected", InvalidExpiryDateException.class, ()->{
                PaymentValidator.validatePaymentInfo("0000000000000000", "24/99", "000");
                }); // Bad expiry date input
            assertThrows("InvalidExpiryDateException expected", InvalidExpiryDateException.class, () -> {
                PaymentValidator.validatePaymentInfo("0000000000000000", "1/99", "000");
                }); // Bad expiry date input
            assertThrows("InvalidExpiryDateException expected", InvalidExpiryDateException.class, () -> {
                PaymentValidator.validatePaymentInfo("0000000000000000", "12/9a", "000");
                }); // Bad expiry date input
            assertThrows("ExpiredCardException expected", ExpiredCardException.class, () -> {
                PaymentValidator.validatePaymentInfo("0000000000000000", "02/24", "000");
                }); // Expired Card

        }
        catch(PaymentException p) {
            assertTrue("Some error from payment validator",false);
        }
    }

}
