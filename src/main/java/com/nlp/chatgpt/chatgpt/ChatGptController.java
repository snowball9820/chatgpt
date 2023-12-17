package com.nlp.chatgpt.chatgpt;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nlp.chatgpt.FormInputDTO;
import com.nlp.chatgpt.OpenAiApiClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
import java.util.Map;

@Controller
public class ChatGptController
{

    private static final String MAIN_PAGE = "index";

    @Autowired private ObjectMapper jsonMapper;
    @Autowired private OpenAiApiClient client;

    private String chatWithGpt3(String message) throws Exception {
        var completion = CompletionRequest.defaultWith(message);
        var postBodyJson = jsonMapper.writeValueAsString(completion);
        var responseBody = client.postToOpenAiApi(postBodyJson, OpenAiApiClient.OpenAiService.GPT_3);
        var completionResponse = jsonMapper.readValue(responseBody, CompletionResponse.class);
        return completionResponse.firstAnswer().orElseThrow();
    }
    // DALL-E API 요청 본문을 생성하는 메소드
    private String createDallERequestBody(String prompt) throws JsonProcessingException {
        var requestBody = Map.of(
                "model", "dall-e-3",
                "prompt", prompt,
                "n", 1,
                "size", "1024x1024"
        );
        return jsonMapper.writeValueAsString(requestBody);
    }

    // DALL-E API 응답으로부터 이미지 URL을 추출하는 메소드
    private String extractImageUrlFromResponse(String responseBody) throws JsonProcessingException {
        var responseJson = jsonMapper.readValue(responseBody, Map.class);
        var images = (List<Map<String, Object>>) responseJson.get("data");
        if (images != null && !images.isEmpty()) {
            return (String) images.get(0).get("url");
        }
        throw new RuntimeException("No image URL found in response");
    }

    // DALL-E API를 호출하는 메소드 추가
    private String generateImage(String prompt) throws Exception {
        // JSON 요청 본문 생성 (여기에 적절한 DALL-E API 요청 형식을 사용)
        String requestBodyJson = createDallERequestBody(prompt);

        // DALL-E API 호출
        String responseBody = client.postToOpenAiApi(requestBodyJson, OpenAiApiClient.OpenAiService.DALL_E);

        // 응답 처리 (여기에 적절한 로직을 추가하여 이미지 URL 추출)
        return extractImageUrlFromResponse(responseBody);
    }

    @GetMapping(path = "/")
    public String index() {
        return MAIN_PAGE;
    }

    // Chat 메소드 수정
    @PostMapping(path = "/")
    public String chat(Model model, @ModelAttribute FormInputDTO dto) {
        try {
            String scenario = chatWithGpt3(dto.prompt());
            System.out.println("Scenario: " + scenario); // 로그 출력
            model.addAttribute("scenario", scenario);

            try {
                String imageUri = generateImage(dto.prompt());
                model.addAttribute("imageUri", imageUri);
            } catch (JsonProcessingException e) {
                model.addAttribute("error", "Error processing JSON for image generation.");
            }
        } catch (Exception e) {
            model.addAttribute("error", "Error in communication with OpenAI ChatGPT API.");
        }
        return MAIN_PAGE;
    }
}
