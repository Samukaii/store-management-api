package org.samuel.storemanagement.general.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class FilePayload {
    @NotNull(message = "Arquivo é obrigatório")
    MultipartFile file;
}
