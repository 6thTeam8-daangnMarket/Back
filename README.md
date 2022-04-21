# mini22

## Webmini project2 "개모임" Backend

만들고 싶은 프로젝트가 있을 때 함께 만들어갈 동료를 구하는 공간
<br>
만들고 싶은 서비스는 많은데 팀원을 어떻게 구해야할지 모르겠고, 취업 포트폴리오는 만들어야하는데 아이디어는 떠오르지 않아 막막했던적 없으신가요? ‘개발자들의 모임’ <개.모.임>은 이러한 고민을 갖고 있는 개발자들을 위한 Social Recruiting Network Service 입니다. 취업준비는 하고 싶은데 포트폴리오가 없어 난항을 겪는 개발자들,또는 아이디어는 넘쳐나는데 함께할 팀원이 없어 낙심하는 개발자들을 많이 보았습니다. 그렇다고 네트워크를 만들기 위해 학원이나 부트캠프를 다니기엔 부담스럽기도 하죠. 이러한 고민을 갖고 있는 개발자들의 크고 작은 도전에 한 발짝 보탬이 되고자 서비스를 만들어 보았습니다.
<br>

## 진행기간

- 2022.04.08(금) ~ 2022.04.14(목)
  <br>

## ⚙ 주요 기능

#### API설계 [노션](https://cyber-biology-9fd.notion.site/c303f02b79fc4506a025145a7adc4701)

#### WIREFRAME [PIGMA](https://www.figma.com/file/B6WEXes2RDdsOecbfTQKqP/%EB%AF%B8%EB%8B%88%ED%94%84%EB%A1%9C%EC%A0%9D%ED%8A%B8?node-id=0%3A1)

### 1. 회원가입 / 로그인
    - 회원 가입 시 아이디/닉네임 중복검사
    - JWT 인증방식을 통한 로그인 구현
    - 로그인 실패 시 Handler를 통해 에러메세지 전달
### 2. 메인 페이지 (게시글 목록)
    - CRUD
    - 사용자가 모집 현황을 알 수 있도록 표시
    - 페이지네이션

### 3. 상세 페이지
    - 유저 스택에 따른 참여 카운트
    - 참여버튼 다시 선택 시 참여 취소.
    - 인원이 다 찼을 경우 더이상 참여 불가.
    - 코멘트 CRUD

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
| 지수민 |      [https://github.com/sumye](https://github.com/sumye)      |    백엔드(스프링)   |
| 오예령 | [https://github.com/ohyeryung](https://github.com/ohyeryung)   |    백엔드(스프링)   |
| 김빛찬 |   [https://github.com/blingbc9](https://github.com/blingbc9)   |    백엔드(스프링)   |
| 엄성훈 |  [https://github.com/umsounghun](https://github.com/umsounghun)|    백엔드(스프링)   |
| 변기원 |  [https://github.com/kiwonbyun](https://github.com/kiwonbyun)  |  프론트엔드(리액트) |
| 서유나 |   [https://github.com/rriverr](https://github.com/rriverr)     | 프론트엔드(리액트)  |

**BacK End** : https://github.com/ohyeryung/mini22.git

**Front End** : https://github.com/bkw9603/hanghae99-gaemoim-FE


<br>





<br>

<h3 align="center"><b>📂 Project Directory Structure 📁</b></h3>

<pre>
<code>
/com.sparta.Webmini2
  └──/controller
     ├── /BackController.java
     ├── /CommentController.java
     ├── /FrontController.java
     ├── /PostController.java
     └── /UserController.java
  └──/dto
     ├── /BackRequestDto.java
     ├── /BackResponseDto.java
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
