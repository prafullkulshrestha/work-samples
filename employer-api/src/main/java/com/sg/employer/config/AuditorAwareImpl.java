package com.sg.employer.config;

import java.util.Optional;

import org.springframework.data.domain.AuditorAware;

import com.sg.employer.utils.EmployerApiApplicationConstants;

public class AuditorAwareImpl implements AuditorAware<String> {

    @Override
    public Optional<String> getCurrentAuditor(){
    	//This should be extended to accept the value of the logged in user in future
        return Optional.of(EmployerApiApplicationConstants.CURRENT_AUDITOR);
    }
}