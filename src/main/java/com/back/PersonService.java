package com.back;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

// @Service : 이 클래스는 비즈니스 로직을 담당하는 서비스 계층의 컴포넌트이다.
// 스프링 컨테이너에 의해 서비스 빈으로 등록된다.
@Service
// Lombok의 @RequiredArgsConstructor 어노테이션은 final 필드에 대한 생성자를 자동 생성한다.
// 이는 PersonRepository 의존성이 생성자 주입 방식으로 컨테이너로부터 주입된다는 선언이다.
@RequiredArgsConstructor
public class PersonService {

    // @Service에 의해 PersonRepository 타입의 의존성이 주입되는 final 필드이다.
    // 이 서비스는 데이터 접근 계층(Repository)에 대한 접근 권한을 가진다.
    private final PersonRepository personRepository;

    // 이 메서드는 Repository를 통해 저장된 사람(Person)의 총 개수를 조회하는 역할을 한다.
    // 서비스 계층은 Repository의 메서드를 호출하여 로직을 수행한다.
    public long count() {
        // 주입된 personRepository의 count() 메서드 호출 결과가 그대로 반환된다.
        return personRepository.count();
    }
}