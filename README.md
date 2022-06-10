# Clone Project

## Clone Coding Project "당근마켓" Backend

당근 마켓 클론 코딩
<br>


## 진행기간

- 2022.04.16(금) ~ 2022.04.21(목)
  <br>

## ⚙ 주요 기능

#### API설계 [노션](https://www.notion.so/8-00adac21af6d49699787e1bd1b5774c7)

#### WIREFRAME [PIGMA]()

### 1. 회원가입 / 로그인
    - 회원 가입 시 아이디/닉네임 중복검사
    - JWT 인증방식을 통한 로그인 구현
    - 로그인 실패 시 Handler를 통해 에러메세지 전달

### 2. 메인 페이지 (게시글 목록)
    - S3를 이용한 이미지 업로드 및 수정 가능
    - 구매 가능한 상품 목록 조회
    - 페이지네이션 (무한스크롤) 구현
    - 관심 상품 등록 여부 확인
    - 제목에 따른 상품 검색
    - 카테고리별 상품 조회

### 3. 상세 페이지
    - 해당 상품에 관한 정보 조회
    - 게시글 수정, 삭제 가능 (작성자에 한함)
    - 관심 상품 등록 및 삭제

### 4. 마이 페이지
    - 회원가입 시 등록했던 정보 열람
    - 관심 상품 조회
    - 페이지네이션 (무한스크롤) 구현

<br>

## 🔨 개발 툴

<a href="" target="_blank"><img src="https://img.shields.io/badge/React-61DAFB?style=flat-square&logo=React&logoColor=white"/></a>
<a href="" target="_blank"><img src="https://img.shields.io/badge/Redux-764ABC?style=flat-square&logo=Redux&logoColor=white"/></a>
<img src="https://img.shields.io/badge/gradle-02303A?style=for-the-badge&logo=gradle&logoColor=white">
<img src="https://img.shields.io/badge/Springboot-6DB33F?style=for-the-badge&logo=Springboot&logoColor=white">
<img src= "https://img.shields.io/badge/IntelliJIDEA-000000.svg?style=for-the-badge&logo=intellij-idea&logoColor=white" width="110" height="30"/>

<br>


## 👨‍👩‍👧 팀원

|  이름  |                          깃허브 주소                           |       포지션       |
| :----: | :------------------------------------------------------------: | :----------------: |
| 오예령 |      [https://github.com/ohyeryung](https://github.com/ohyeryung)      |    백엔드(스프링)   |
| 이승재 | [https://github.com/langho968](https://github.com/langho968)   |    백엔드(스프링)   |
| 김민성 |   [https://github.com/lincoln-burrows](https://github.com/lincoln-burrows)   |    백엔드(스프링)   |
| 이춘 |  [https://github.com/lee-chun-91](https://github.com/lee-chun-91) |  프론트엔드(리액트) | 
| 노현정 |  [https://github.com/isabel-noh](https://github.com/isabel-noh)  |  프론트엔드(리액트) |


**BacK End** : https://github.com/6thTeam8-daangnMarket/Back/tree/main/clone_backend

**Front End** : https://github.com/6thTeam8-daangnMarket/Front


<br>





<br>

<h3 align="center"><b>📂 Project Directory Structure 📁</b></h3>

<pre>
<code>
/com.sparta.clone_backend
 └──/config
     └── /AmazonS3Config.java
  └──/controller
     ├── /PostController.java
     ├── /PostLikeController.java
     └── /UserController.java
  └──/dto
     ├── /DuplicateChkDto.java
     ├── /IsLoginDto.java
     ├── /KakaoTokenDto.java
     ├── /KakaoUserInfoDto.java
     ├── /PostDetailResponseDto.java
     ├── /PostLikeDto.java
     ├── /PostListDto.java
     ├── /PostRequestDto.java
     ├── /PostResponseDto.java
     ├── /PostsResponseDto.java
     ├── /ResponseDto.java
     ├── /SignupRequestDto.java
     └── /UserPageResponseDto.java
  └──/model
     ├── /Image.java
     ├── /Post.java
     ├── /PostLike.java
     ├── /Timestamped.java
     └── /User.java
  └──/repository
     ├── /ImageRepository.java
     ├── /PostLikeRepository.java
     ├── /PostRepository.java
     └── /UserRepository.java
  └──/security
     └── /filter
         ├── /FormLoginFilter.java
         └── /JwtAuthFilter.java
     └── /jwt
         ├── /HeaderTokenExtractor.java
         ├── /JwtDecoder.java
         ├── /JwtPreProcessingToken.java
         └── /JwtTokenUtils.java
     └── /provider
         ├── /AuthFailureHandler.java
         ├── /FilterSkipMatcher.java
         ├── /FormLoginSuccessHandler.java
         ├── /UserDetailsImpl.java
         ├── /UserDetailsServiceImpl.java
         └── /WebSecurityConfig.java
  └──/service
     ├── /KakaoUserService.java
     ├── /PostLikeService.java
     ├── /PostService.java
     ├── /S3Uploader.java
     └── /UserService.java
  └──/utils
     └── /StatusMessage.java
  └──/validator
     └── /UserInfoValidator.java
  └──/CloneBackendApplication.java
</code>
</pre>


## 🧾Trouble Shooting

 <p> </p>
 <p> </p>
 <p> </p>


<details>
<summary>로컬 환경에서 서버와 통신할 때 CORS 오류 발생</summary>
<div markdown="1">

```java
corsConfiguration.setAllowCredentials(true); 
```
위 문구를 WebSecurityConfig Cors 설정부분에 추가하여 해결
</div>
</details>







<details>
<summary>관심 상품 등록 삭제 시 게시물이 삭제</summary>
<div markdown="1">


</div>
</details>




<details>
<summary>JPA 내장함수로 구현하기 곤란한 검색기능 Query로 구현</summary>
<div markdown="1">

```java
 //검색어를 받아서 최신순으로 정렬한다.
@Query(value = "select * from post p where p.post_title like %:keyword% order by p.modified_at desc", nativeQuery = true)
    List<Post> searchByKeyword(@Param("keyword")String keyword);

//카테고리를 받아서 최신순으로 정렬한다.
@Query(value = "select * from post p where p.category=:category order by p.modified_at desc", nativeQuery = true)
    List<Post> searchByCategory(@Param("category")String category);
    
```
검색기능을 구현할 때는 클라이언트로부터 검색어를 변수로 받아와야 하고, 해당 검색어가 제목 혹은 본문에 포함되어 있는 글을 지역별, 가격순, 최신순 등의 조건으로 가져오게 된다. JPA 함수의 경우에는 변수를 받아오는 것이 어렵고, Or 조건과 And 조건이 섞여 있는 경우도 해결하기 어렵기 때문에 Query를 사용하는 편이 훨씬 간단했다    

</div>
</details>





<details>
<summary>S3 버킷 관련 ACL 관련 에러 </summary>
<div markdown="1">


 <img src="https://user-images.githubusercontent.com/97422693/155449111-a04a7db8-f8ab-4841-bf4e-edc89047e996.PNG" width="600" />


AWS에 들어가서 객체 소유권에 대한  ACL 활성화를 해주었다


</div>
</details>
