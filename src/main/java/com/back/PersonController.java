package com.back;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

// 이 클래스는 클라이언트의 HTTP 요청을 처리하는 스프링 MVC의 컨트롤러이다.
@Controller
// Lombok의 @RequiredArgsConstructor : final 필드에 대한 생성자를 자동 생성한다.
// 이는 PersonService 의존성이 생성자 주입 방식으로 컨테이너로부터 주입된다는 선언이다.
@RequiredArgsConstructor
public class PersonController {

    // PersonService 타입의 의존성이 주입되는 final 필드(필드 의존성 주입)
    // 사람(Person) 엔티티와 관련된 비즈니스 로직 및 데이터 접근을 담당
    private final PersonService personService;

    // HTTP GET 요청을 "/people" 경로와 매핑한다.
    @GetMapping("/people")
    // @ResponseBody 어노테이션은 메서드가 반환하는 문자열이
    // View 이름이 아닌 HTTP 응답 본문(Body)으로 직접 전송된다는 선언이
    @ResponseBody
    public String people() {

        long count = personService.count();

        // 조회된 사람 수를 포함하는 포맷된 문자열이 HTTP 응답 본문으로 반환
        return "people // 사람 수 : %d명".formatted(count);
    }
}