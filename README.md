# todo-max

### 💻 [배포 주소](ec2-43-201-104-148.ap-northeast-2.compute.amazonaws.com)

## 팀원 소개

| <img src="https://avatars.githubusercontent.com/u/33227831?v=4" width="180" height="180"/> | <img src="https://avatars.githubusercontent.com/u/57559288?v=4" width="180" height="180"/> | <img src="https://avatars.githubusercontent.com/u/107015624?v=4" width="180" height="180"/> | <img src="https://avatars.githubusercontent.com/u/103398897?v=4" width="180" height="180"/> | <img src="https://avatars.githubusercontent.com/u/79886384?v=4" width="180" height="180"/> | <img src="https://avatars.githubusercontent.com/u/76121068?v=4" width="180" height="180"/> |
|:------------------------------------------------------------------------------------------:|:------------------------------------------------------------------------------------------:|:-------------------------------------------------------------------------------------------:|:-------------------------------------------------------------------------------------------:|:------------------------------------------------------------------------------------------:|:------------------------------------------------------------------------------------------:|
|                      [ 네모네모(BE) ](https://github.com/yonghwankim-dev)                      |                          [이안 (BE)](https://github.com/othertkfka)                          |                            [지구 (BE)](https://github.com/Ojeegu)                             |                             [감귤 (BE)](https://github.com/swinb)                             |                      [Kakamotobi (FE)](https://github.com/Kakamotobi)                      |                          [퓨즈 (FE)](https://github.com/silvertae)                           |

<br/>

## 그라운드룰

### 스크럼 방식

`시간`: 10:00 - 10:30

**스크럼** **내용:** 어제 했던 일 / 오늘 할 일 / 컨디션(10점 만점)

**스크럼 마스터:** 스크럼 진행 및 내용 작성

|  월  |   화   |  수  |  목   |  금   |
|:---:|:-----:|:---:|:----:|:----:|
|  x  |  지구   | 감귤  | 네모네모 |  이안  |
|  월  |   화   |  수  |  목   |  금   |
| 퓨즈  | 카카모토비 | 지구  |  퓨즈  | 네모네모 |

<details>
<summary><b style="color: gray">Details</b></summary>
<h2>Process</h2>
<dl>
<dt>어제 무엇을 했는지 간단하게 공유.</dt>
<dd>
<blockquote>
<b>ex</b></br>
어제 계획했던대로, 검색창과 서버를 연결해서 자동완성 기능을 구현했다.<br>
어제 계획했던 사이드바의 메인메뉴와 서브메뉴간의 이동을 ㅇㅇ문제 때문에 아직 구현하지 못했다.
</blockquote>
</dd>
</dl>
<dl>
<dt>작고 구체적인 오늘의 목표/계획 공유.</dt>
<dd>
점심시간 전까지 Promise에 대해서 공부하고 내용을 기록하기.
코어타임 마무리 전까지 사이드바의 메인메뉴와 서브메뉴간의 이동을 구현하고 커밋 올리기.
1시간 동안 딤처리 로직을 리팩토링 하기.
</dd>
</dl>

<dl>
<dt>기타 공유</dt>
<dd>
<blockquote>
<b>ex</b></br>
이부분이 도무지 이해가 안가고 해결이 안되고 있는데 도와주실 분 있나요?
</blockquote>
</dd>
</dl>

<dl>
<dt>Rules</dt>
<dd>
공유자의 공유에 따른 가벼운 멘트 가능.<br/>
<blockquote>
<b>ex</b></br>
저도 같은 고민이 있었어요. 조금 이따가 같이 의논해 볼까요?<br>
</blockquote>
공유자의 고민, 문제점에 대한 깊은 대화는 위 과정이 끝나고 잡담 시간 혹은 개인학습/미션해결 시간에 하기.
</dd>
</dl>

<dl>
<dt>Scrum Master</dt>
<dd>
위 과정과 규칙이 원활하게 따르게 되도록 스크럼 진행하기.<br/>
스크럼 마무리할 때 내일의 스크럼마스터 지정.
</dd>
</dl>
</details>

### Communication

- Github Wiki에 trouble shooting 과정 등을 기록해서 공유한다.
- 클래스 별 수정사항 등은 Slack으로 공유, 확인했으면 답글이나 이모지 달아주기
- 회의 방식
    - 공통 회의와 클래스 별 회의를 나눠서 진행한다.
    - 회의 진행 전에 슬랙을 통해 회의 안건을 정리해서 올려놓는다.
    - 회의 진행은 그날의 스크럼 마스터가 진행한다.
    - 만약 회의가 길어지는 경우에는 1시간 진행하고 10분 씩 휴식

<br/>

## 협업 전략 (브랜치 구조와 분업하기, 의존적인 작업하기)

### 코딩 컨벤션

- 클래스 별 논의

### 브랜치 전략

`main`

`release`

`fe`, `be` - 각자 작업한 feature 브랜치들을 fe, be 브랜치로 PR, merge한 feature 브랜치는 삭제

`feature#1` - 로컬에서 작업한 내용들을 feature 브랜치로 push

### PR 컨벤션

**PR 제목**

ex) [BE] PR 제목

**PR 메세지 템플릿**

```tsx
##
What
is
this
PR ? 👓

##
Key
changes 🔑

##
To
reviewers 👋

##
Issues
Closes #{이슈번호}
Closes #{이슈번호}

```

- What is this PR? : PR에 대한 간단한 설명
- Key changes : 주요 변경 사항
- To reviewers : 중점적으로 봐야 하는 부분 및 고민 사항
- Issues: PR과 관련된 이슈번호

**PR 머지 전략**

- 작업자 외 나머지 팀원들이 리뷰 후 마지막 리뷰어가 머지 후 브랜치 삭제

<br/>

## 커밋 템플릿, 이슈 템플릿 등

### 커밋 컨벤션

**커밋 메시지 템플릿**

```bash
#{이슈번호} {커밋타입}: {커멧 제목}

- {커밋 본문}
- {커밋 본문}
```

```bash
#1 feat: add new feature

- 새로운 기능을 추가하였습니다.
```

- 커밋 본문은 선택적으로 작성합니다.

**커밋 타입 종류**

- `✨feat` : 새로운 기능 추가
- `🐛fix` : 버그 수정
- `♻️refactor` : 코드 리팩토링
- `✅test` : 테스트 코드
- `📝docs` : 문서 수정
- `🎨style` :코드 포맷팅, 세미콜론 누락, 코드 변경이 없는 경우
- `💄design` : CSS 등 사용자 UI 디자인 변경
- `🔧chore` : 빌드 업무 수정, 패키지 매니저 수정 → 패키지 설치, 개발 환경 세팅
- `🔀merge` : merge
- `🚚rename`:  디렉토리 및 파일명 변경
- `🌱comment`: 주석 추가 혹은 오타 수정
- `➕add`: 의존성 추가

### 이슈 템플릿

```
## Feature

## Tasks
- [ ] 1
- [ ] 2
- [ ] 3
```
