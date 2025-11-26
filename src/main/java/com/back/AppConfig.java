package com.back;

import com.back.domain.member.member.entity.Member;
import com.back.domain.member.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;

// 이 클래스는 스프링 컨테이너의 설정 클래스(Configuration Class)이다.
// @Bean이 붙은 메서드는 스프링 컨테이너에 관리되는 객체(빈)를 정의한다.
@Configuration
// Lombok의 @RequiredArgsConstructor 어노테이션은 final 필드에 대한 생성자를 자동 생성한다.
@RequiredArgsConstructor
public class AppConfig {

    // MemberService 타입의 의존성이 생성자 주입 방식으로 주입되는 final 필드이다.
    private final MemberService memberService;

    // 스프링 컨테이너는 AppConfig 자신의 프록시 인스턴스를 이 필드에 주입하도록 선언한다.
    // @Lazy는 순환 참조 방지 및 지연 로딩 목적으로 사용된다.
    @Autowired
    @Lazy
    private AppConfig self;

    // --- 일반 빈 등록 ---

    // 이 메서드가 반환하는 '55'라는 정수 값은 스프링 컨테이너에 빈으로 등록된다.
    // 빈의 이름은 'version'이다.
    @Bean
    int version() {
        return 55;
    }

    // --- ApplicationRunner 빈 등록: 애플리케이션 시작 후 실행 로직 정의 ---

    // 이 메서드는 ApplicationRunner 인터페이스의 구현체를 빈으로 등록한다.
    // ApplicationRunner는 애플리케이션 시작 완료 시점에 실행되는 콜백이다.
    @Bean
    ApplicationRunner baseInitDataApplicationRunner() {
        return args -> {
            // 이 ApplicationRunner가 실행될 때, 주입된 self를 통해 work1()과 work2() 메서드가 순차적으로 호출된다.
            // self를 통해 호출함으로써 @Transactional 프록시가 적용된다.
            self.work1();
            self.work2();
        };
    }

    // --- 초기화 데이터 설정 메서드 ---

    // @Transactional 어노테이션은 이 메서드가 하나의 트랜잭션 내에서 실행되도록 선언한다.
    public void work1() {
        // 회원 수가 0보다 크면 초기화 작업을 건너뛴다.
        if (memberService.count() > 0) return;

        // 새로운 Member 엔티티 5개가 MemberService를 통해 데이터베이스에 저장된다.
        Member memberSystem = memberService.join("system", "1234", "시스템");
        Member memberAdmin = memberService.join("admin", "1234", "관리자");
        Member memberUser1 = memberService.join("user1", "1234", "유저1");
        Member memberUser2 = memberService.join("user2", "1234", "유저2");
        Member memberUser3 = memberService.join("user3", "1234", "유저3");
    }

    // @Transactional 어노테이션은 이 메서드 역시 하나의 트랜잭션 내에서 실행되도록 선언한다.
    public void work2() {
        // 사용자 이름 'user2'로 Member 엔티티를 조회한다.
        Member memberUser2 = memberService.findByUsername("user2").get();

        // 조회된 memberUser2 객체의 닉네임이 '유저2 New'로 변경된다.
        // 이 변경 사항은 트랜잭션 종료 시점에 데이터베이스에 반영된다 (Dirty Checking).
        memberUser2.setNickname("유저2 New");
    }
}