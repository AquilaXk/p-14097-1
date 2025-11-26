package com.back.domain.member.member.service;

import com.back.domain.member.member.entity.Member;
import com.back.domain.member.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

// 회원(Member) 관련 비즈니스 로직을 담당하는 서비스 컴포넌트
// @Service : 스프링 컨테이너에 의해 서비스 빈으로 등록된다.
@Service
// Lombok의 @RequiredArgsConstructor : final 필드에 대한 생성자를 자동 생성한다.
// 이는 MemberRepository 의존성이 생성자 주입 방식으로 컨테이너로부터 주입된다는 선언이다.
@RequiredArgsConstructor
public class MemberService {

    // MemberRepository 타입의 의존성이 주입되는 final 필드이다.
    // 이 서비스는 Repository를 통해 데이터 접근을 수행하는 q역할이다.
    private final MemberRepository memberRepository;

    /**
     * 저장된 회원의 총 개수를 조회하는 역할을 한다.
     * @return DB에 저장된 Member 엔티티의 개수
     */
    public long count() {
        // 주입된 memberRepository의 count() 메서드 호출 결과가 반환된다.
        return memberRepository.count();
    }

    /**
     * 새로운 회원을 데이터베이스에 저장(가입)하는 역할을 한다.
     * @param username, password, nickname 회원 정보
     * @return 저장 후 데이터베이스에서 반환된 Member 엔티티
     */
    public Member join(String username, String password, String nickname) {
        // 새로운 Member 엔티티 객체를 생성하고 Repository를 통해 저장 요청한다.
        return memberRepository.save(
                new Member(username, password, nickname)
        );
    }

    /**
     * 특정 username을 기준으로 회원을 조회하는 역할을 한다.
     * @param username 조회할 회원의 사용자 이름
     * @return 조회된 Member 엔티티를 포함하는 Optional 객체
     */
    public Optional<Member> findByUsername(String username) {
        // 주입된 memberRepository의 findByUsername() 쿼리 메서드가 호출된다.
        return memberRepository.findByUsername(username);
    }
}