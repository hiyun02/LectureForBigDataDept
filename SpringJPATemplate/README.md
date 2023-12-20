# SpringJPATemplate
Spring JPA 구현을 위한 탬플릿 코드

MariaDB 기반 Spring Boot Frameworks 프로젝트(수업 탬플릿)으로 공지사항만 구현됨

* 2023.04.04 기준 JPA 버그가 있어 Spring Boot 버전은 2.7.7로 고정바람
* 테스트 결과 : 2.7.8~ 2.7.10은 오류 발생
* 발생오류 : Update, Delete 쿼리를 NativeQuery로 구현하면, Null Pointer 에러와 함께 빌딩 안됨
* 관련글 : https://github.com/spring-projects/spring-boot/issues/34363 / https://github.com/spring-projects/spring-data-jpa/issues/2812


* 작성자 : 한국폴리텍대학 서울강서캠퍼스 빅데이터과 이협건 교수
* 이메일 : hglee67@kopo.ac.kr
* 빅데이터학과 입학 상담 오픈채팅방 : https://open.kakao.com/o/gEd0JIad
