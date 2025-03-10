package kopo.poly.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.image.Image;
import org.springframework.ai.image.ImageModel;
import org.springframework.ai.image.ImagePrompt;
import org.springframework.ai.image.ImageResponse;
import org.springframework.ai.openai.OpenAiImageOptions;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequiredArgsConstructor
@RestController
public class ImgController {

    private final ImageModel imageModel;

    @GetMapping("getMsgToDALLE")
    public Image getMsgToDALLE(@RequestParam(value = "message", defaultValue = "a little cat") String message) {

        log.info(getClass().getName() + "getMsgToDALLE 시작");
        log.info("입력받은 메시지 : " + message);

        // 이미지 생성을 위한 프롬프트 생성
        ImagePrompt imagePrompt = new ImagePrompt(message);
        // 생성된 프롬프트를 전달하여 걸과 이미지 반환
        ImageResponse answer = imageModel.call(imagePrompt);
        log.info("DALLE 응답 데이터 : " + answer);
        log.info(getClass().getName() + "getMsgToDALLE 종료");

        return answer.getResult().getOutput();
    }

    @PostMapping("getImgByDALLE")
    public Image getImgByDALLE(@RequestBody String prompt) {

        log.info(getClass().getName() + "getImgByDALLE 시작");
        log.info("입력받은 프롬프트 : " + prompt);

        // 전달된 프롬프트 값을 이미지 생성 옵션과 함께 Prompt 객체로 만든 후 ImageClient에 전달하여 결과 이미지 반환
        ImageResponse answer = imageModel.call(
                new ImagePrompt(prompt, OpenAiImageOptions.builder()
                        .withWidth(1024).withHeight(1024).build())
        );

        log.info("DALLE 응답 데이터 : " + answer);
        log.info(getClass().getName() + "getImgByDALLE 종료");

        return answer.getResult().getOutput();
    }
}
