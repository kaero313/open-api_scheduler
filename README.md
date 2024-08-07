<h1>quartz-scheduler를 이용한 open-api 데이터 수집</h1>

<h3>1. scheduler란?</h3>

- 특정 시간에 등록한 특정 job을 자동으로 실행시키는 것

<br/>

<h3>2. spring의 scheduler 종류</h3>

- 대표적으로 spring-scheduler와 quartz-scheduler가 있다

<br/>

<h4>1. spring-scheduler의 특징</h4>

- spring 프레임워크의 기본 기능이므로 추가적인 설정이 필요 없음
- 어노테이션을 통하여 구현이 간단함
- 기본적으로는 동기 방식 처리(설정을 통한 비동기 처리 가능)

<br/>

<h4>2. quartz-scheduler의 특징</h4>

- 다중 서버간의 클러스터링, 로드밸런싱 기능 제공
- 작업 실패를 대비한 후 처리 가능
- 메인 스레드를 막지 않고 비동기 방식 처리 용이

> 추후 확장성까지 고려하여 quartz-scheduler를 채택

<br/>

<h3>3. quartz-scheduler</h3>

![image](https://github.com/user-attachments/assets/1725dde7-d947-4b05-ae1e-235348b9b297)

> quartz-scheduler architecture
- scheduler, trigger, job 기본 기능 위주로 구현 예정

<br/>

<h3>4. open-api 세팅</h3>
<h5>1. 공공데이터포털 접속 및 가입</h5>
<h5>2. 해당 api 선택 후 활용 신청(날씨 관련 api)</h5>
<h5>3. 서비스 키 발급 확인, 공식 문서 확인하여 개발 진행</h5>

<br/>

<h3>5. quartz-scheduler 개발</h3>
<h5>1. maven으로 관련 라이브러리 다운</h5>
<h5>2. 스케줄러 설정 파일 추가<br/>
- 실행할 job 생성 > trigger에 jobdetail 등록, 실행주기 설정 > scheduler에 실행할 trigger 리스트 등록</h5>
<h5>3. job 인터페이스 구현<br/>
- api 요청 메세지 양식에 맞게 파라미터 설정하여 url 호출 > 받아온 데이터(JSON) 파싱하여 활용</h5>

<br/>

<h3>6. 개발 완료</h3>

![image](https://github.com/user-attachments/assets/65f3704d-af1d-43a9-b75d-68863a1a43f3)
> 30초마다 설정한 지역의 단기 날씨 예보를 수집하는 scheduler<br/>
(실제 사용시에는 수집 주기 설정과 수집한 데이터를 저장 및 활용하는 로직도 필요함)
