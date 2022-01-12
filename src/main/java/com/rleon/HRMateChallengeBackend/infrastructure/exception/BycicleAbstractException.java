package com.rleon.HRMateChallengeBackend.infrastructure.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class BycicleAbstractException extends RuntimeException{
    private String errorDescription;
    private int errorCode;
}
