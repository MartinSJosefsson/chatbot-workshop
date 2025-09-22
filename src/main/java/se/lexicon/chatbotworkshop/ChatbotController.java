package se.lexicon.chatbotworkshop;

import jakarta.validation.Valid;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.messages.SystemMessage;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.openai.OpenAiChatOptions;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/chatbot")
public class ChatbotController {

    private final ChatModel chatModel;

    public ChatbotController(ChatModel chatModel) {
        this.chatModel = chatModel;
    }

    @PostMapping("/query")
    public ResponseEntity<String> generateResponse(@RequestBody @Valid QueryRequest request) {
        Message systemMessage = new SystemMessage(
                "You are a helpful Math tutor. " +
                        "Tailor your responses to the user's expertise level: " +
                        "for beginner, use simple explanations and basic terminology; " +
                        "for intermediate, include practical use cases and best practices; " +
                        "for advanced, discuss deeper concepts, trade-offs, and optimizations. " +
                        "Keep responses concise and focused on the question."
        );

        Message userMessage = new UserMessage(
                "Expertise level: " + request.getExpertiseLevel() + "\n" +
                        "Question: " + request.getQuestion()
        );

        // Build OpenAI-specific options, overriding defaults if provided
        OpenAiChatOptions.Builder optionsBuilder = OpenAiChatOptions.builder();
        if (request.getMaxCompletionTokens() != null) {
            optionsBuilder.maxCompletionTokens(request.getMaxCompletionTokens());
        }
        if (request.getTemperature() != null) {
            optionsBuilder.temperature(request.getTemperature());
        }
        OpenAiChatOptions options = optionsBuilder.build();

        Prompt prompt = new Prompt(List.of(systemMessage, userMessage), options);

        ChatResponse aiResponse = chatModel.call(prompt);

        return ResponseEntity.ok(aiResponse.getResult().getOutput().getText());
    }

}