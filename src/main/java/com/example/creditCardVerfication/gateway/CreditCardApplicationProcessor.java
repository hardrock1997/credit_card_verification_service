package com.example.creditCardVerfication.gateway;

import com.example.creditCardVerfication.service.CreditCardVerificationService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.example.events.*;

import java.util.function.Function;

@Configuration
@AllArgsConstructor
@Slf4j
public class CreditCardApplicationProcessor {
    private CreditCardVerificationService creditCardVerificationService;

    @Bean
    //Input is NewCreditCardEvent :its being consumed and output is VerifyCreditCardEvent event which will be published.
    public Function<NewCreditCardEvent, VerifyCreditCardEvent> verifyCreditCardApplication(){

        return newCreditCardEvent -> {

            VerifyCreditCardEvent verifyCreditCardEvent = creditCardVerificationService
                    .verifyCreditCardApplication(newCreditCardEvent);

            log.info("**** Publishing credit card applications verification status : {} **** "
                    ,verifyCreditCardEvent.getCreditCardVerificationStatus().size());

            //if none of the creditCard applications got approved
            return (verifyCreditCardEvent.getCreditCardVerificationStatus().isEmpty())? null : verifyCreditCardEvent;
        };
    }
}
