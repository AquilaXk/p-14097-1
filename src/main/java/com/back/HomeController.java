package com.back;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

// 이 클래스는 클라이언트의 HTTP 요청을 처리하는 스프링 MVC의 컨트롤러이다.
@Controller
// Lombok의 @RequiredArgsConstructor 어노테이션은 final 필드를 인자로 받는 생성자를 자동 생성한다.
// 이는 필드 주입(Field Injection) 대신 생성자 주입(Constructor Injection)을 사용한다는 선언이다.
@RequiredArgsConstructor
public class HomeController {

    // PersonService 타입의 의존성이 주입되는 final 필드이다.
    // 이 서비스는 데이터베이스 관련 로직을 담당한다.
    private final PersonService personService;

    // 이 메서드는 HTTP GET 요청을 루트 경로("/")와 매핑한다.
    @GetMapping("/")
    // @ResponseBody 어노테이션은 메서드가 반환하는 값이 View 이름이 아닌,
    // HTTP 응답 본문(Body)으로 직접 전송된다는 선언이다.
    @ResponseBody
    public String main() {

        // PersonService를 통해 데이터베이스에 저장된 사람의 총 개수(count)를 조회한다.
        long count = personService.count();

        // 조회된 사람 수를 포함하는 문자열이 HTTP 응답 본문으로 반환된다.
        return "main // 사람 수 : %d명".formatted(count);
    }
}