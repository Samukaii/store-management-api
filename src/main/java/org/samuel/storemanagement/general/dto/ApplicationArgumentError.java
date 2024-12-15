package org.samuel.storemanagement.general.dto;

import lombok.Data;
import org.samuel.storemanagement.general.enumerations.ApplicationErrorType;

import java.util.Map;

@Data
public class ApplicationArgumentError implements ApplicationError{
    Integer type = ApplicationErrorType.ARGUMENT.ordinal();
    Map<String, String> messages;
}
