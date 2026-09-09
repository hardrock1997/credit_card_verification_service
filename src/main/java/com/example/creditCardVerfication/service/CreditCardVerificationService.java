package com.example.creditCardVerfication.service;

import com.example.creditCardVerfication.entity.CreditCardVerification;
import com.example.events.*;
import com.example.creditCardVerfication.repo.CreditCardVerificationRepo;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Slf4j
public class CreditCardVerificationService {
    private CreditCardVerificationRepo creditCardVerificationRepo;

    public VerifyCreditCardEvent verifyCreditCardApplication(NewCreditCardEvent newCreditCardEvent) {

        System.out.println("HERE");
        //getting the creditCardApplications that have been consumed by this service
        List<ApplicationDetail>creditCardApplications= newCreditCardEvent.getCreditCardApplications();

        List<CreditCardVerificationStatus> creditCardVerificationStatusList = creditCardApplications.stream()
                .map(creditCardApplication -> {
            //converting each ApplicationDetail object to CreditCardVerificationStatus (event type)
                CreditCardVerificationStatus creditCardVerificationStatus = CreditCardVerificationStatus.builder().build();
                BeanUtils.copyProperties(creditCardApplication,creditCardVerificationStatus);

                //logic for approving or rejecting the creditCardApplications
                if(creditCardApplication.getAnnualIncome() > 4000){
                    creditCardVerificationStatus.setStatus(verificationStatus.APPROVED);
                }else{
                    creditCardVerificationStatus.setStatus(verificationStatus.REJECTED);
                }

            return creditCardVerificationStatus;
        }).toList();

        //preparing the event type which will be published by verify-credit-card-application-service
        var verifyCreditCardEvent = VerifyCreditCardEvent.builder()
                .creditCardVerificationStatus(creditCardVerificationStatusList)
                .build();

        //saving the verification data for all the credit card applications
        saveCreditCardVerificationStatus(creditCardVerificationStatusList);

        return verifyCreditCardEvent;
    }

    private void saveCreditCardVerificationStatus(List<CreditCardVerificationStatus> creditCardVerificationStatusList) {

        var creditCardVerifications =  creditCardVerificationStatusList.stream()
                .map(creditCardVerificationStatus -> {
                    //converting creditCardVerificationStatus to CreditCardVerification (entity) for saving back into DB
                    CreditCardVerification creditCardVerification = new CreditCardVerification();
                    BeanUtils.copyProperties(creditCardVerificationStatus,creditCardVerification);
                    creditCardVerification.setStatus(creditCardVerificationStatus.getStatus().name());
                    return creditCardVerification;
        }).toList();

        log.info("**** Saving credit card application status ******");
        creditCardVerificationRepo.saveAll(creditCardVerifications);
    }
}
