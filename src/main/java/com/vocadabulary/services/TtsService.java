package com.vocadabulary.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

// import org.slf4j.Logger;
// import org.slf4j.LoggerFactory;

@Service
public class TtsService {

    // private static final Logger log = LoggerFactory.getLogger(TtsService.class);

    // private String preview(String text) {
    //     if (text == null) return "";
    //     return text.length() <= 20 ? text : text.substring(0, 20) + "...";
    // }

    @Value("${OPENAI_API_KEY}")
    private String apiKey;

    private static final String OPENAI_TTS_URL = "https://api.openai.com/v1/audio/speech";

    public byte[] generateAudio(String text) {
        // log.info("🎤 TTS generateAudio called (len={}): preview='{}'",
        //         text == null ? 0 : text.length(),
        //         preview(text));
        RestTemplate restTemplate = new RestTemplate();

        Map<String, Object> body = Map.of(
                "model", "gpt-4o-mini-tts",
                "voice", "nova",
                "input", text
        );

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(apiKey);
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(List.of(MediaType.APPLICATION_OCTET_STREAM));

        HttpEntity<Map<String, Object>> request = new HttpEntity<>(body, headers);

        return restTemplate.postForObject(OPENAI_TTS_URL, request, byte[].class);
    }

}