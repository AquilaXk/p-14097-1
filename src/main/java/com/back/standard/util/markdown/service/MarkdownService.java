package com.back.standard.util.markdown.service;

import lombok.RequiredArgsConstructor;
import org.commonmark.node.Node;
import org.commonmark.parser.Parser;
import org.commonmark.renderer.html.HtmlRenderer;
import org.springframework.stereotype.Service;

/**
 * Markdown 형식 텍스트를 HTML 형식으로 변환하는 서비스 계층 클래스.
 * 외부 라이브러리(CommonMark)의 파싱 및 렌더링 기능을 캡슐화하고 제공.
 */
@Service // 해당 클래스가 Spring의 서비스 빈임을 선언.
@RequiredArgsConstructor // final 필드에 대한 생성자 기반 의존성 주입 명시 (현재는 없음, 구조 유지).
public class MarkdownService {

    // CommonMark 라이브러리: Markdown AST(추상 구문 트리)를 HTML 문자열로 렌더링하는 객체.
    private final HtmlRenderer htmlRender = HtmlRenderer.builder().build();

    // CommonMark 라이브러리: Markdown 텍스트를 파싱하여 AST(Node) 객체로 변환하는 객체.
    private final Parser parser = Parser.builder().build();


    /**
     * 입력된 Markdown 문자열을 HTML 문자열로 변환하는 메서드.
     * @param markdown 변환할 Markdown 형식의 입력 문자열.
     * @return Markdown이 HTML로 변환된 결과 문자열.
     */
    public String toHtml(String markdown) {
        // 1. Markdown 문자열을 파서(parser)를 통해 AST(Node 객체)로 변환.
        Node document = parser.parse(markdown);

        // 2. 생성된 AST(Node)를 HTML 렌더러(htmlRender)를 통해 HTML 문자열로 변환하여 반환.
        return htmlRender.render(document);
    }
}