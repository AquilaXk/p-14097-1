package com.back.domain.member.member.entity;

import com.back.global.jpa.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

// 이 클래스는 데이터베이스 테이블과 매핑되는 JPA 엔티티이다.
// 클래스명과 동일한 이름의 테이블(Member)이 기본적으로 매핑된다.
@Entity
// Lombok의 @Getter : 모든 필드에 대한 getter 메서드를 자동으로 생성
@Getter
// Lombok의 @Setter : 모든 필드에 대한 setter 메서드를 자동으로 생성 (주의: 엔티티 수정 시 사용)
@Setter
// Lombok의 @ToString 어노테이션은 객체의 내용을 출력하는 toString() 메서드를 자동으로 생성
@ToString
// JPA 사용을 위해 인자 없는 () 기본 생성자(Default Constructor)를 자동으로 생성
@NoArgsConstructor
// BaseEntity를 상속받아 id, createDate, modifyDate 필드를 포함한다.
public class Member extends BaseEntity {

    // 이 필드는 데이터베이스 테이블의 컬럼과 매핑된다.
    // unique = true 설정은 이 컬럼의 값이 테이블 내에서 중복될 수 없음을 선언한다. (예: 사용자 ID)
    @Column(unique = true)
    private String username;

    // 이 필드는 회원의 비밀번호를 저장한다.
    private String password;

    // 이 필드는 회원의 닉네임을 저장한다.
    private String nickname;

    // --- 생성자 ---

    // username, password, nickname 필드를 초기화하는 생성자이다.
    // 주로 초기 데이터 생성 시 사용된다.
    public Member(String username, String password, String nickname) {
        this.username = username;
        this.password = password;
        this.nickname = nickname;
    }
}