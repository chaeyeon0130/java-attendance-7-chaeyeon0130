# 우테코 최종 코테 - 우아한테크코스 출석

## 📌 기능 목록

### 1. 입력
#### 1. 기능 선택
- [X] 1, 2, 3, 4, Q 중에서 입력받는다.
- [X] **[예외처리]** 기능 선택 항목을 잘못된 형식으로 입력한 경우 예외 처리한다.
#### 2. 각 기능별 입력 사항(+ 입력 관련 예외처리)
- 1. 출석 확인
  - [X] 닉네임을 입력받는다.
  - [X] 등교 시간을 입력받는다.
  - [X] **[예외처리]** 등록되지 않은 닉네임을 입력한 경우 예외 처리한다.
  - [X] **[예외처리]** 날짜 또는 시간을 잘못된 형식으로 입력한 경우 예외 처리한다.
- 2. 출석 수정
  - [ ] 닉네임을 입력받는다.
  - [ ] 수정하려는 날짜를 입력받는다.
  - [ ] 수정하려는 시간을 입력받는다.
  - [ ] **[예외처리]** 등록되지 않은 닉네임을 입력한 경우 예외 처리한다.
  - [ ] **[예외처리]** 날짜 또는 시간을 잘못된 형식으로 입력한 경우 예외 처리한다.
  
- 3. 크루별 출석 기록 확인
  - [ ] 닉네임을 입력받는다.
- 4. 제적 위험자 확인

### 2. 메인 기능(+ 비즈니스 로직 관련 예외 처리)
- 1. 출석 확인
  - 교육 시간은 월요일은 13:00~18:00, 화요일~금요일은 10:00~18:00이다.
  - [X] 해당 요일의 시작 시각으로부터 5분 초과는 지각으로 간주한다.
  - [X] 해당 요일의 시작 시각으로부터 30분 초과는 결석으로 간주한다.
  - [X] 교육 시작 시간 이전 ~ 시작 후 5분 이하까지는 출석으로 간주한다.
  - [X] **[예외처리]** 주말 또는 공휴일에 출석을 확인한 경우 예외 처리한다.
  - [X] **[예외처리]** 캠퍼스 운영 시간은 매일 08:00~23:00이다. 캠퍼스 운영 시간이 아닌 시간에 출석을 하려는 경우 예외 처리한다.
  - [X] **[예외처리]** 이미 출석을 하였는데 다시 출석 확인을 하는 경우 예외 처리한다.
- 2. 출석 수정
  - [ ] 교육 시간을 고려하여 수정하려는 시간을 토대로 출석 결과를 수정한다.
  - [ ] **[예외처리]** 주말 또는 공휴일에 출석을 수정하는 경우 예외 처리한다.
  - [ ] **[예외처리]** 미래 날짜로 출석을 수정하는 경우 예외 처리한다.
  - [ ] **[예외처리]** 캠퍼스 운영 시간이 아닌 시간으로 출석을 수정한 경우 예외 처리한다.
- 3. 크루별 출석 기록 확인
  - [ ] 특정 크루의 전날까지의 출석 기록을 확인할 수 있다.
  - [ ] 특정 크루의 출석, 지각, 결석 각각에 대한 횟수를 출력한다.
  - [ ] 특정 크루가 제적, 경고, 면담 대상자인지 출력한다.
  - [ ] 지각 3회는 결석 1회로 간주한다.
  - [ ] 결석 2회 이상은 경고 대상자, 결석 3회 이상은 면담 대상자, 결석 5회 초과는 제적 대상자로 간주한다.
  - [ ] 등교하지 않아 출석 기록이 없는 날은 결석으로 간주한다.
- 4. 제적 위험자 확인
  - [ ] 전날까지의 크루 출석 기록을 바탕으로 제적 위험자를 파악한다.
  - [ ] 제적 위험자는 제적 대상자, 면담 대상자, 경고 대상자순으로 출력한다.
  - [ ] 대상 항목별 정렬 순서는 지각, 결석 모두 결석으로 간주하여 내림차순한다.
  - [ ] 출석 상태가 같으면 닉네임으로 오름차순 정렬한다.

### 3. 출력
- 출석 관련 기능 수행 결과를 출력한다.
    - 1. 출석 확인
        - [X] `12월 13일 금요일 09:59 (출석)` 형태로 출력한다.
    - 2. 출석 수정
        - [ ] `12월 03일 화요일 10:07 (지각) -> 09:58 (출석) 수정 완료!` 형태로 출력한다.
    - 3. 크루별 출석 기록 확인
        ```angular2html
        닉네임을 입력해 주세요.
        빙티
        
        이번 달 빙티의 출석 기록입니다.
        
        12월 02일 월요일 13:00 (출석)
        12월 03일 화요일 10:07 (지각)
        12월 04일 수요일 10:02 (출석)
        12월 05일 목요일 10:06 (지각)
        12월 06일 금요일 10:01 (출석)
        12월 09일 월요일 --:-- (결석)
        12월 10일 화요일 10:03 (출석)
        12월 11일 수요일 --:-- (결석)
        12월 12일 목요일 --:-- (결석)
        12월 13일 금요일 10:02 (출석)
        
        출석: 3회
        지각: 0회
        결석: 3회
        
        면담 대상자입니다.
        ```
        위와 같은 형태로 출력한다.
    - 4. 제적 위험자 확인
        ```angular2html
        제적 위험자 조회 결과
        - 빙티: 결석 3회, 지각 4회 (면담)
        - 이든: 결석 2회, 지각 5회 (면담)
        - 빙봉: 결석 1회, 지각 6회 (면담)
        - 쿠키: 결석 2회, 지각 3회 (면담)
        - 짱수: 결석 0회, 지각 6회 (경고)
        ```
        위와 같은 형태로 출력한다.

### 4. 예외 처리
- [ ] 사용자가 잘못된 값을 입력할 경우 "[ERROR]"로 시작하는 메시지와 함께 IllegalArgumentException을 발생시킨 후 애플리케이션은 종료되어야 한다.
  - 즉, 프로그램은 사용자가 종료할 때까지 종료되지 않으며, 해당 기능을 수행한 후 초기 화면으로 돌아간다.

### 5. 기타
- [X] 출석 시스템에 등록된 크루와 12월 출석 기록은 제공된 파일(attendances.csv)에서 확인할 수 있다.

## ⚒️ 클래스 다이어그램