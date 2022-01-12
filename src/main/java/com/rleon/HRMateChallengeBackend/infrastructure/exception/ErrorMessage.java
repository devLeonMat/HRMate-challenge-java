package com.rleon.HRMateChallengeBackend.infrastructure.exception;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ErrorMessage {
    private int errorCode;
    private String errorDescription;


}
