package com.back.domain.wiseSaying.wiseSaying.controller;

import com.back.domain.wiseSaying.wiseSaying.entity.WiseSaying;
import com.back.domain.wiseSaying.wiseSaying.service.WiseSayingService;
import com.back.standard.util.markdown.service.MarkdownService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import java.util.stream.Collectors;


/**
 * 명언(WiseSaying) 엔티티에 대한 HTTP 요청 처리 컨트롤러.
 * MarkdownService를 사용하여 명언 내용을 HTML로 변환하는 기능 통합.
 */
@Controller
@RequiredArgsConstructor // final 필드에 대한 생성자 기반 의존성 주입 명시.
public class WiseSayingController {

    // 비즈니스 로직 처리 Service 빈 의존성 주입.
    private final WiseSayingService wiseSayingService;
    // Markdown 텍스트를 HTML로 변환하는 유틸리티 Service 빈 의존성 주입.
    private final MarkdownService markdownService;


    /**
     * 명언 생성 및 저장 엔드포인트.
     * HTTP GET '/wiseSayings/write' 경로 처리.
     * @param content 명언 내용 (쿼리 파라미터, 필수).
     * @param author 작가 이름 (쿼리 파라미터, 필수).
     * @return 생성된 명언 ID를 포함한 성공 메시지 문자열 반환.
     */
    @GetMapping("/wiseSayings/write")
    @ResponseBody
    public String write(
            @RequestParam(defaultValue = "내용") String content,
            @RequestParam(defaultValue = "작가") String author
    ) {
        // 입력 값 유효성 검증.
        if (content.isBlank()) {
            throw new IllegalArgumentException("Content cannot be null or blank");
        }

        if (author.isBlank()) {
            throw new IllegalArgumentException("Author cannot be null or blank");
        }

        // Service 계층에 명언 생성 로직 위임.
        WiseSaying wiseSaying = wiseSayingService.write(content, author);

        return "%d번 명언이 생성되었습니다.".formatted(wiseSaying.getId());
    }


    /**
     * 전체 명언 목록 조회 엔드포인트.
     * HTTP GET '/wiseSayings' 경로 처리.
     * @return 모든 명언 리스트를 HTML <ul> 태그 형식의 문자열로 반환.
     */
    @GetMapping("/wiseSayings")
    @ResponseBody
    public String list() {
        // Service를 통한 전체 명언 조회 및 HTML 목록 형식 변환 처리.
        return "<ul>"
                + wiseSayingService.findAll()
                .stream()
                .map(wiseSaying ->
                        "<li>%d / %s / %s</li>".formatted(wiseSaying.getId(), wiseSaying.getAuthor(), wiseSaying.getContent())
                )
                .collect(Collectors.joining(""))
                + "</ul>";
    }


    /**
     * 특정 ID 명언 상세 조회 엔드포인트.
     * HTTP GET '/wiseSayings/{id}' 경로 처리.
     * @param id 조회할 명언의 고유 ID (경로 변수).
     * @return 명언 상세 정보 및 Markdown이 HTML로 변환된 내용을 포함하는 문자열 반환.
     */
    @GetMapping("/wiseSayings/{id}")
    @ResponseBody
    public String detail(@PathVariable int id) {
        // ID 기반 명언 조회.
        WiseSaying wiseSaying = wiseSayingService.findById(id).get();

        // 조회된 명언 내용을 MarkdownService를 통해 HTML로 변환.
        String html = markdownService.toHtml(wiseSaying.getContent());

        // HTML 형식으로 응답 구성 및 반환.
        return """
                <div>번호 : %d</div>
                <div>작가 : %s</div>
                <div>%s</div>
                """.formatted(wiseSaying.getId(), wiseSaying.getAuthor(), html);
    }


    /**
     * 특정 ID 명언 삭제 엔드포인트.
     * HTTP GET '/wiseSayings/{id}/delete' 경로 처리.
     * @param id 삭제할 명언의 고유 ID (경로 변수).
     * @return 삭제된 명언 ID를 포함한 성공 메시지 문자열 반환.
     */
    @GetMapping("/wiseSayings/{id}/delete")
    @ResponseBody
    public String delete(
            @PathVariable int id
    ) {
        // ID 기반 명언 조회. 존재하지 않을 시 예외 발생.
        WiseSaying wiseSaying = wiseSayingService.findById(id)
                .orElseThrow(
                        () -> new IllegalArgumentException("%d번 명언은 존재하지 않습니다.".formatted(id))
                );

        // Service에 명언 삭제 로직 위임.
        wiseSayingService.delete(wiseSaying);

        return "%d번 명언이 삭제되었습니다.".formatted(id);
    }


    /**
     * 특정 ID 명언 수정 엔드포인트.
     * HTTP GET '/wiseSayings/{id}/modify' 경로 처리.
     * @param id 수정할 명언의 고유 ID (경로 변수).
     * @param content 수정할 명언 내용 (쿼리 파라미터, 필수).
     * @param author 수정할 작가 이름 (쿼리 파라미터, 필수).
     * @return 수정된 명언 ID를 포함한 성공 메시지 문자열 반환.
     */
    @GetMapping("/wiseSayings/{id}/modify")
    @ResponseBody
    public String modify(
            @PathVariable int id,
            @RequestParam(defaultValue = "") String content,
            @RequestParam(defaultValue = "") String author
    ) {
        // 수정 내용 유효성 검증.
        if (content.isBlank()) {
            throw new IllegalArgumentException("Content cannot be null or blank");
        }

        if (author.isBlank()) {
            throw new IllegalArgumentException("Author cannot be null or blank");
        }

        // ID 기반 명언 조회. 존재하지 않을 시 예외 발생.
        WiseSaying wiseSaying = wiseSayingService.findById(id)
                .orElseThrow(
                        () -> new IllegalArgumentException("%d번 명언은 존재하지 않습니다.".formatted(id))
                );

        // Service에 명언 수정 로직 위임.
        wiseSayingService.modify(wiseSaying, content, author);

        return "%d번 명언이 수정되었습니다.".formatted(id);
    }
}