package com.back.global.jpa.entity;

import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

import static jakarta.persistence.GenerationType.IDENTITY;

// 이 클래스가 상속받는 자식 엔티티의 매핑 정보만 포함하는 매핑 슈퍼클래스이다.
// 이 클래스 자체는 데이터베이스 테이블과 매핑되지 않는다.
@MappedSuperclass
// 엔티티의 생명주기 이벤트를 감시하는 리스너를 지정한다.
// AuditingEntityListener는 @CreatedDate와 @LastModifiedDate 필드의 값 자동 설정을 담당하는 역할이다.
@EntityListeners(AuditingEntityListener.class)
// Lombok의 @Getter 어노테이션은 모든 필드에 대한 getter 메서드를 자동으로 생성하는 역할이다.
@Getter
public abstract class BaseEntity {

    // 기본 키(Primary Key) 필드임을 선언한다.
    @Id
    // 기본 키의 생성 전략이 데이터베이스에 의존하는 자동 증가(IDENTITY) 방식임을 선언한다.
    @GeneratedValue(strategy = IDENTITY)
    private int id;

    // 엔티티가 처음 생성될 때 그 시각을 자동으로 기록하는 필드이다.
    // AuditingEntityListener에 의해 값이 자동으로 채워진다.
    @CreatedDate
    private LocalDateTime createDate;

    // 엔티티의 값이 마지막으로 수정된 시각을 자동으로 기록하는 필드이다.
    // AuditingEntityListener에 의해 값이 자동으로 갱신된다.
    @LastModifiedDate
    private LocalDateTime modifyDate;
}