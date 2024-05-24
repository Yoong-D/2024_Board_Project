# 게시판 만들기  프로젝트
##### 프로젝트 진행 기간 : 2024.05.23 ~ 2024.05.24 (단독) 
##### IDE : Intellij Professional
##### Famework : Spring boot 
##### dependencies : Spring Boot DevTools, Lombok, Spring Web, Spring Data JDBC, Thymeleaf
##### Language : Java 21, Html 5, CSS
##### Database : Mysql 8.4.0

## 📑 사용 설명서  
   ### Board 테이블 구조 
   - 데이터 베이스 명 : boardproject
```
CREATE TABLE board (
id BIGINT AUTO_INCREMENT PRIMARY KEY,
name VARCHAR(100) NOT NULL,
title VARCHAR(255) NOT NULL,
password VARCHAR(255) NOT NULL, -- 암호는 해싱하여 저장하는 것이 좋습니다
content TEXT NOT NULL,
created_at DATETIME DEFAULT CURRENT_TIMESTAMP, -- 등록일
updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP -- 수정일
);
```
### 글 목록 
- 최신 글부터 보여짐
- ID, 제목, 이름, 등록일(YYYY/MM/DD)형식으로 목록 나열
- 페이징 처리
  
### 글 등록 
- 이름, 제목 암호, 본문을 입력
- 등록일, ID는 자동 저장

### 글 상세 페이지
- 암호는 비공개
- 글 등록일 형식 : YYYY/MM/DD hh24:mi

### 글 수정
- 이름, 제목, 본문을 수정 가능
- 암호는 글 등록시 입력했던 암호와 동일해야 수정 가능
- 수정일은 자동으로 저장
### 글 삭제
 - 암호는 글 등록시 입력했던 암호와 동일해야 삭제 가능
## 💡 구현 모습
   ### 기본 페이지(/)
   ![image](https://github.com/Yoong-D/2024_Board_Project/assets/52689951/82911e99-21f9-4293-943f-cf58868d486d)


   ### 목록 페이지(/list?page=페이지번호)
   - 게시글 목록을 페이지별로 나열
   - page 파라미터가 없으면 기본적으로 1페이지를 보여준다.
   - 각 페이지는 최신 글 부터 보여지며, 페이징 처리가 적용되어 있다.
![image](https://github.com/Yoong-D/2024_Board_Project/assets/52689951/7fa4b2e8-0fe5-4ce6-98c4-b2cbca4ef926)



   ### 게시글 상세 조회 페이지(/view?id=아이디)
   - 목록 페이지에서 보고싶은 게시글의 제목을 클릭하면 들어오는 화면
   - 삭제와 수정 링크를 제공하여, 해당 기능을 수행할 수 있는 페이지로  이동 가능
   ![image](https://github.com/Yoong-D/2024_Board_Project/assets/52689951/b30b318f-9bfe-42c1-b804-4d440403b98d)


   ### 게시글 등록 페이지(/writeform)
   - 특정 게시글을 쓰기 위한 폼을 제공
   - 사용자는 이름, 제목, 내용, 암호를 입력하고, 확인 버튼을 통해 등록을 요청
   - 모든 내용이 잘 입력되었을 시 /write로 요청을 보내 등록 처리후 /list로 리다이렉트

![image](https://github.com/Yoong-D/2024_Board_Project/assets/52689951/aebe8a1a-26e3-4048-a4e2-0e4529aceba1)

   ### 게시글 삭제 페이지(/deleteform?id=아이디)
   - 사용자는 암호를 입력하고, 확인 버튼을 클릭하여 삭제 요청
   - 올바른 암호시 /delete로 요청을 보내 삭제 후 /list로 다이렉트
     
![image](https://github.com/Yoong-D/2024_Board_Project/assets/52689951/ca14c225-a068-4303-9eb7-8135fdc86f1d)
[ 삭제 페이지 ]       
![image](https://github.com/Yoong-D/2024_Board_Project/assets/52689951/9ed7396b-b6cb-4ac2-86a4-f3fc64e4d6a2)
[ 비밀번호가 올바르지 않을 시 "비밀번호가 일치하지 않습니다" 경고 문구 출력 ]    
![image](https://github.com/Yoong-D/2024_Board_Project/assets/52689951/752b3c5e-ff38-44b9-9ad3-e8f10e06206a)
[ 비밀번호가 올바를 시 해당 게시글이 삭제된 모습 ] 



   ### 게시글 수정 페이지(/updateform?id=아이디)
   ![image](https://github.com/Yoong-D/2024_Board_Project/assets/52689951/6893d2ec-5780-49bc-b54f-fd50a950c8df)
   [ 수정 하기 전 게시 글  ]  
   ![image](https://github.com/Yoong-D/2024_Board_Project/assets/52689951/08817a08-c047-4098-8d30-d09742ef7910)
   [ 비밀번호가 올바르지 않을 시 "비밀번호가 일치하지 않습니다" 경고 문구 출력 ]   
   ![image](https://github.com/Yoong-D/2024_Board_Project/assets/52689951/cee4a14f-3f01-42f2-8bb3-54a8fef8a655)
   [ 비밀번호가 올바를 시 해당 게시글이 수정된 모습  - 상세 페이지 ]   
   ![image](https://github.com/Yoong-D/2024_Board_Project/assets/52689951/67a51846-d940-4824-8905-4b0f46d2c49f)
   [ 비밀번호가 올바를 시 해당 게시글이 삭제된 모습 - 목록 페이지 ] 


## 📢 프로젝트 소감 & 개선할 점
### ❤ 소감
첫 스프링 부트 프로젝트를 무사히 완성해서 정말 뿌듯하다. 많은 에노테이션과 용어들 덕분에 처음에는 스프링이 조금 어려웠지만, 그래도 최소한의 목표를 이룰 수 있어서 다행이다. 프로젝트를 진행하면서 타임 리프 문법에서 많은 오류가 발생했는데, 이를 해결하면서 동적으로 페이지가 변경되고 바뀌는 과정, 그리고 MVC의 구조에 대해 더 깊게 이해할 수 있었다. 예전에는 페이징을 구현하는 것이 정말 어려웠는데, 이번에는 스프링 부트를 활용하여 매우 간단하게 구현할 수 있다는 방법을 배우게 되어 뿌듯하다.

### ❤ 개선 및 추가할 점 
1. 비밀번호에 제약 조건 두기
2. 삭제 폼과 수정폼 ui 수정
3. 카테고리 만들기
4. 댓글 기능
5. 비밀 댓글
6. 로그인 기능
7. 마이페이지
8. 내가 쓴 글 보기 
 









