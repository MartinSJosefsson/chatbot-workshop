package se.lexicon.chatbotworkshop;

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

}