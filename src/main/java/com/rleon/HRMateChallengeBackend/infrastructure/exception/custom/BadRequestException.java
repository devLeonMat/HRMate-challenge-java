package com.rleon.HRMateChallengeBackend.infrastructure.exception.custom;


import com.rleon.HRMateChallengeBackend.infrastructure.exception.BycicleAbstractException;
import org.springframework.http.HttpStatus;

public class BadRequestException extends BycicleAbstractException {

    public BadRequestException(final String message) {

        this.setErrorCode(HttpStatus.BAD_REQUEST.value());
        this.setErrorDescription(message);
    }
}
