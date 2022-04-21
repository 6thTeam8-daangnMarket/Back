# Clone Project

## Clone Coding Project "당근마켓" Backend

당근 마켓 클론 코딩
<br>


## 진행기간

- 2022.04.16(금) ~ 2022.04.22(목)
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
    - 관심 상품 등록

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


```java
 @JsonIgnore
    @OneToMany(cascade = CascadeType.REMOVE, mappedBy = "post")
    private List<Likes> LikesList  = new ArrayList<>();
```
**cascade =** CascadeType.REMOVE 를 이용하여 해결

</div>
</details>




<details>
<summary>거래를 한 상대에게 무한정으로 후기를 남길수 있는 문제</summary>
<div markdown="1">

프론트측에 유저의 아이디만 받고 평점을 남기는 식으로 하였는데 이번에 post아이디도 함께 받아서 포스트에 boolean rated = false;//평가여부 항목을 추가하여 post 아이디를 받아서
해당 게시물을 찾고 true로 바꿔서 거래를 한 게시물을 알려주었습니다.

</div>
</details>






<details>
<summary>S3 버킷 관련 ACL 관련 에러 </summary>
<div markdown="1">


 <img src="https://user-images.githubusercontent.com/97422693/155449111-a04a7db8-f8ab-4841-bf4e-edc89047e996.PNG" width="600" />


AWS에 들어가서 객체 소유권에 대한  ACL 활성화를 해주었다


</div>
</details>
