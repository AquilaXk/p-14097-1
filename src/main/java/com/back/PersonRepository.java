package com.back;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

// 이 클래스는 데이터 접근 계층(Repository layer)의 컴포넌트이다.
// 스프링 컨테이너에 의해 데이터 접근 관련 빈으로 등록된다.
@Repository
// Lombok의 @RequiredArgsConstructor 어노테이션은 final 필드에 대한 생성자를 자동 생성한다.
// 이는 version 빈이 생성자 주입 방식으로 컨테이너로부터 주입된다는 선언이다.
@RequiredArgsConstructor
public class PersonRepository {

    // 스프링 설정 클래스(AppConfig 등)에 등록된 'int' 타입의 'version' 빈이 주입되는 final 필드이다.
    private final int version;

    // 이 메서드는 데이터베이스에 저장된 엔티티의 총 개수를 조회하는 역할을 한다.
    // 실제 데이터베이스 접근 로직이 위치하는 곳이다.
    public long count() {
        // 현재 Repository에 주입된 버전 정보를 포함하는 메시지가 콘솔에 출력된다.
        System.out.println("PersonRepository(v%d).count() 작동".formatted(version));

        // 실제 데이터베이스 쿼리 결과 대신, 임시로 상수 값 3이 반환된다.
        return 3;
    }
}