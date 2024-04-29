# ssarylog
### 목적
스프링 공부를 함에 있어 실전적으로 임함으로써 무엇을 공부해야하는지 코드가 어떻게 작동하는지 알고 스스로 결과물을 만들어감에 따라 성취감을 갖고자 한다. {24.01.24}

# 목차
- [개발 환경](#개발-환경)
- [사용 기술](#사용-기술)

## 개발 환경
- Intellij
- Github

## 사용 기술
### 백엔드
#### 주요 프레임워크 / 라이브러리
- Java 21 openjdk
- JAVA 21
- SpringBoot 3.1.7
- SpringBoot Security
- Spring Data JPA
- Lombok

### 서버
- apache tomcat
- naver cloud

#### Build tool
- Gradle 8.6

#### Database
- H2
- Mysql

### 프론트엔드
- Javascript
- Typescript
- Html/Css
- Bootstrap5
- Vue.js

group = 'com.ssarylog'
version = '0.0.1-SNAPSHOT'


#### 비밀번호 암호화
1. 해시
2. 해시 방식
    - SHA1
    - SHA256
    - MD5
    - 왜 이런걸로 비번 암호화 하면 안되는지 (salt 값이 없다.)
3. BCrypt SCrypt, Argon2
    - salt 값 (어떻게 넣는냐에 따라 달라짐)
