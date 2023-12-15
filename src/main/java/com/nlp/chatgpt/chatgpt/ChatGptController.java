package com.nlp.chatgpt.chatgpt;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nlp.chatgpt.FormInputDTO;
import com.nlp.chatgpt.OpenAiApiClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class ChatGptController
{

    private static final String MAIN_PAGE = "index";

    @Autowired private ObjectMapper jsonMapper;
    @Autowired private OpenAiApiClient client;

    private String chatWithGpt4(String message) throws Exception {
        var completion = CompletionRequest.defaultWith(message);
        var postBodyJson = jsonMapper.writeValueAsString(completion);
        var responseBody = client.postToOpenAiApi(postBodyJson, OpenAiApiClient.OpenAiService.GPT_4);
        var completionResponse = jsonMapper.readValue(responseBody, CompletionResponse.class);
        return completionResponse.firstAnswer().orElseThrow();
    }

    @GetMapping(path = "/")
    public String index() {
        return MAIN_PAGE;
    }

    @PostMapping(path = "/")
    public String chat(Model model, @ModelAttribute FormInputDTO dto) {
        try {
            model.addAttribute("request", dto.prompt());
            model.addAttribute("response", chatWithGpt4(dto.prompt()));
        } catch (Exception e) {
            model.addAttribute("response", "Error in communication with OpenAI ChatGPT API.");
        }
        return MAIN_PAGE;
    }
}