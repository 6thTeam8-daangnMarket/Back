# Clone Project

## Clone Coding Project "당근마켓" Backend

당근마켓 클론코딩
<br>


## 진행기간

- 2022.04.16(금) ~ 2022.04.22(목)
  <br>

## ⚙ 주요 기능

#### API설계 [노션](https://www.notion.so/8-00adac21af6d49699787e1bd1b5774c7)

#### WIREFRAME [PIGMA](https://www.figma.com/file/B6WEXes2RDdsOecbfTQKqP/%EB%AF%B8%EB%8B%88%ED%94%84%EB%A1%9C%EC%A0%9D%ED%8A%B8?node-id=0%3A1)

### 1. 회원가입 / 로그인
    - 회원 가입 시 아이디/닉네임 중복검사
    - JWT 인증방식을 통한 로그인 구현
    - 로그인 실패 시 Handler를 통해 에러메세지 전달

### 2. 메인 페이지
    - 상품 목록 조회
    - 사용자가 상품 관심 등록 여부 확인
    - 제목에 따른 키워드별 검색
    - 카테고리별 상품 조회
    - S3를 활용한 이미지 업로드 및 수정 가능

### 3. 상세 페이지
    - 상품에 대한 정보 조히
    - 관심 상품 등록 및 삭제 가능
    - 게시글 수정 및 삭제 (작성자 본인에 한함)

### 4. 마이 페이지
    - 회원가입 시 등록했던 개인 정보 열람
    - 관심 상품 목록 조히
    
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
     └── /AmazonS3.java
  └──/controller
     ├── /PostController.java
     ├── /PostLikeController.java
     └── /UserController.java
  └──/dto
     ├── /DuplicateChkDto.java
     ├── /IsLoginDto.java
     ├── /CommentRequestDto.java
     ├── /CommentResponseDto.java
     ├── /FrontRequestDto.java
     ├── /FrontResponseDto.java
     ├── /PostRequestDto.java
     ├── /PostResponseDto.java
     ├── /ResponseDto.java
     └── /SignupRequestDto.java
  └──/model
     ├── /Back.java
     ├── /Comment.java
     ├── /Front.java
     ├── /Post.java
     ├── /Timestamped.java
     └── /User.java
  └──/repository
     ├── /BackRepository.java
     ├── /CommentRepository.java
     ├── /FrontRepository.java
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
     ├── /BackService.java
     ├── /CommentService.java
     ├── /FrontService.java
     ├── /PostService.java
     └── /UserService.java
  └──/validator
     └── /UserInfoValidator.java
  └──/Mini22Application.java
</code>
</pre>


## 🧾Trouble Shooting

 <p> </p>
 <p> </p>
 <p> </p>


<details>
<summary>MultipartFile과 JSON으로 보낼때 프론트측에 제대로 데이터가 안가는 문제</summary>
<div markdown="1">

```java
@PostMapping("/post")
    public ResponseEntity<String> writePost(@RequestPart("file") MultipartFile multipartFile, @RequestPart("post") PostsRequestDto requestDto,
                                            @AuthenticationPrincipal UserDetailsImpl userDetails) throws IOException {
        System.out.println(requestDto.getContent());

        // String image = s3Uploader.upload(multipartFile, "postImage");
        String image = s3Uploader.upload(multipartFile,"postImage");
        requestDto.setImage(image);
        postService.writePost(requestDto, userDetails.getUser());
        return ResponseEntity.ok()
                .body("작성되었습니다 true");
    }
```
@RequestPart로 둘다 multipart/form-data 형태로 전송하는 것으로 해결

</div>
</details>







<details>
<summary>게시물 삭제시 Likes와의 연관 관계로 인해 좋아요를 누르면 삭제 안되는 문제</summary>
<div markdown="1">


```java
 @JsonIgnore
    @OneToMany(cascade = CascadeType.REMOVE, mappedBy = "post")
    private List<Likes> LikesList  = new ArrayList<>();
```
cascade = CascadeType.REMOVE 를 이용하여 해결

</div>
</details>




<details>
<summary>거래를 한 상대에게 무한정으로 후기를 남길수 있는 문제</summary>
<div markdown="1">

```java
public RatedDto addRate(RateDto rateDto){ //유저 평가하기

        RatedDto ratedDto = new RatedDto();
        Post post = postRepository.findById(rateDto.getPostId()).get();
        post.setRated(true);


        int rate1 = rateDto.getRate();
        User user = userRepository.findById(rateDto.getId()).get();  //평가 점수 더하는 로직
        int currentRate = user.getRate();
        user.setRate(currentRate + rate1);
        User user2 = userRepository.save(user);


        rateDto.setRate(user2.getRate());
        ratedDto.setRated(true);
        return ratedDto;
    }
```
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
