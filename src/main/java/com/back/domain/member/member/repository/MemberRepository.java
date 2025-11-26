package com.back.domain.member.member.repository;

import com.back.domain.member.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

// 스프링 데이터 JPA의 레포지토리 인터페이스
// 스프링 컨테이너에 의해 실행 시점에 구현체가 자동으로 생성된다
public interface MemberRepository extends JpaRepository<Member, Integer> {

    // JpaRepository를 상속받음으로써 기본적인 CRUD(Create, Read, Update, Delete) 메서드들이 제공된다.
    // 여기서 Member는 대상 엔티티 타입이며, Integer는 해당 엔티티의 기본 키(ID) 타입이다.

    /**
     * 특정 username을 기준으로 Member 엔티티를 조회하는 역할을 한다.
     * 이는 스프링 데이터 JPA의 쿼리 메서드 기능에 의해 자동으로 구현된다.
     * 조회 결과가 없을 경우를 대비하여 Optional<Member> 타입으로 반환된다.
     * @param username 조회할 회원의 사용자 이름
     * @return 조회된 Member 엔티티를 포함하는 Optional 객체
     */
    Optional<Member> findByUsername(String username);
}