package com.nlp.chatgpt.chatgpt;

public record CompletionRequest(String model, String prompt,
                                double temperature, int max_tokens) {

    public static CompletionRequest defaultWith(String prompt) {
        return new CompletionRequest("gpt-3.5-turbo-instruct", prompt, 0.7, 500);
    }

}