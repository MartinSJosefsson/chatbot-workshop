package se.lexicon.chatbotworkshop.dto;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class QueryRequest {

    @NotBlank(message = "Question is required")
    private String question;

    @NotBlank(message = "Expertise level is required")
    @Pattern(regexp = "(?i)beginner|intermediate|advanced", message = "Expertise level must be beginner, intermediate, or advanced")
    private String expertiseLevel;

    @Min(value = 1, message = "Max completion tokens must be at least 1")
    @Max(value = 4096, message = "Max completion tokens must not exceed 4096 for gpt-4o-mini")
    private Integer maxCompletionTokens;

    @DecimalMin(value = "0.0", message = "Temperature must be at least 0.0")
    @DecimalMax(value = "2.0", message = "Temperature must not exceed 2.0")
    private Double temperature;

}