package com.nlp.chatgpt.chatgpt;

public record CompletionRequest(String model, String prompt,
                                double temperature, int max_tokens) {

    public static CompletionRequest defaultWith(String prompt) {
        return new CompletionRequest("gpt-4", prompt, 0.7, 100);
    }

}