package org.samuel.storemanagement.general.dto;

import lombok.Data;
import org.samuel.storemanagement.general.enumerations.ApplicationErrorType;

@Data
public class ApplicationBasicError implements ApplicationError {
    Integer type = ApplicationErrorType.BASIC.ordinal();
    String message;
}
