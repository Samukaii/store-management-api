package org.samuel.storemanagement.general.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BaseOption {
    Integer id;
    String name;
}
