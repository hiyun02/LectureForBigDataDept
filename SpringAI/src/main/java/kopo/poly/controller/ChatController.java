package kopo.poly.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.document.Document;
import org.springframework.ai.image.Image;
import org.springframework.ai.image.ImageModel;
import org.springframework.ai.image.ImagePrompt;
import org.springframework.ai.image.ImageResponse;
import org.springframework.ai.openai.OpenAiImageOptions;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@RestController
public class ChatController {

    private final ChatModel chatModel;

    @GetMapping("getMsgFromGPT")
    public String getMsgToGPT(@RequestParam(value = "message", defaultValue = "Tell me a dad joke") String message) {

        log.info(getClass().getName() + "getMsgToGPT 시작");
        log.info("입력받은 메시지 : " + message);

        String answer = chatModel.call(message);

        log.info("GPT 응답 메시지 : " + answer);
        log.info(getClass().getName() + "getMsgToGPT 종료");

        return answer;
    }

}
