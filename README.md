# 🍽️ 위치 기반 맛집 추천 서비스 `이맛집(EatMatJib)`

## 목차

- [개요](#개요)
- [개발 환경](#skils)
- [Related Repository](#Related-Repository)
- [Data Pipeline](#Data-Pipeline)
- [구현과정](#구현과정)
- [TIL 및 회고](#til-및-회고)
- [Authors](#authors)

## 개요

`이맛집(EatMatJib)`의 Raw Data를 관리하는 데이터 서버입니다.

[서울시 일반음식점 인허가 정보](https://data.seoul.go.kr/dataList/OA-16094/S/1/datasetView.do) 데이터를 활용하여 맛집 추천 서비스를 제공하고 있습니다.

본 데이터 서버에서는 `OpenAPI`를 통해 매일 데이터를 갱신합니다.  
받아온 데이터는 원본 그대로 저장되며, 데이터가 갱신되면 전처리 과정을 거쳐 실제 API 서버에서 사용될 DB에 전달됩니다.

## 개발 환경

<img src="https://img.shields.io/badge/Language-%23121011?style=for-the-badge"><img src="https://img.shields.io/badge/java-007396?style=for-the-badge&logo=OpenJDK&logoColor=white"><img src="https://img.shields.io/badge/17-515151?style=for-the-badge">

<img src="https://img.shields.io/badge/Framework-%23121011?style=for-the-badge"><img src="https://img.shields.io/badge/springboot-6DB33F?style=for-the-badge&logo=springboot&logoColor=white"><img src="https://img.shields.io/badge/3.3.2-515151?style=for-the-badge">

<img src="https://img.shields.io/badge/Database-%23121011?style=for-the-badge"><img src="https://img.shields.io/badge/MySQL-4479A1?style=for-the-badge&logo=MySQL&logoColor=white"> <img src="https://img.shields.io/badge/MyBatis-d97b53?style=for-the-badge">

<img src="https://img.shields.io/badge/Build-%23121011?style=for-the-badge"><img src="https://img.shields.io/badge/Gradle-02303A?style=for-the-badge&logo=Gradle&logoColor=white"><img src="https://img.shields.io/badge/8.8-515151?style=for-the-badge">

<img src="https://img.shields.io/badge/Deployment-%23121011?style=for-the-badge"><img src="https://img.shields.io/badge/aws%20EC2-FF9900?style=for-the-badge&logo=Amazon%20EC2&logoColor=white"> <img src="https://img.shields.io/badge/flyway-CC0200?style=for-the-badge&logo=flyway&logoColor=white"> <img src="https://img.shields.io/badge/aws rds-527FFF?style=for-the-badge&logo=amazonrds&logoColor=white">

<img src="https://img.shields.io/badge/version control-%23121011?style=for-the-badge"><img src="https://img.shields.io/badge/git-F05032?style=for-the-badge&logo=git&logoColor=white"> <img src="https://img.shields.io/badge/github-181717?style=for-the-badge&logo=github&logoColor=white">

## Related Repository

- API Server:
  [EatMatJib-Server](https://github.com/team-TEN-TEN/EatMatJib-Server)

## Data Pipeline

<details>
<summary>데이터 수집 및 저장</summary>

> 맛집 정보 업데이트를 위한 초기 데이터를 세팅한다.

- OpenAPI를 통해 서울시 음식점 데이터를 가져온다. 데이터는 원본 그대로 저장하며, 필드명 모두 원본과 동일하다.

- 이때 데이터를 가져올 때마다 저장하는 것이 아니라, 한번에 모아둔 다음 Batch Insert를 하여 처리 속도를 향상시킨다.
</details>

<details>
<summary>일정 주기로 데이터 갱신</summary>

> 서울시 음식점 데이터가 매일 갱신됨에 따라 Raw Data Table도 갱신한다.

- Spring Scheduler를 이용해 매일 정해진 시각 데이터 갱신을 진행하도록 설정한다.

- Unique 키인 `관리번호`를 통해 이미 저장된 데이터인지 확인한다. 저장된 테이터라면 `최종수정일자` 필드를 저장된 값과 비교하여, 수정된 데이터일 경우 갱신한다.  
저장되지 않은 데이터라면 새로 삽입한다.
</details>

<details>
<summary>데이터 전처리 후 DB 저장</summary>

- raw_restaurant에 저장된 가공되지 않은 원본 데이터를 조회하여 사용할 필드 별로 데이터 검증 및 전처리 후 실제 api 서버에서 사용될 restaurant 테이블에 저장한다
</details>

## 구현과정

<details>
<summary>ERD 모델링</summary>

- 원본 데이터의 모든 필드를 그대로 저장
- `is_updated`필드로 갱신 여부 구분

<img src="https://github.com/user-attachments/assets/3412c08e-8ea3-4222-b7c1-9d4918433c93" height="1000">

</details>

<details>
<summary>디렉토리 구조</summary>

```
📂server
 ┣ 📂common
 ┃ ┣ 📂config
 ┃ ┣ 📂exception
 ┃ ┗ 📂util
 ┣ 📂pipeline
 ┃ ┣ 📂datamapper
 ┃ ┣ 📂devmapper
 ┃ ┣ 📂dto
 ┗ ┗ 📂service
```

</details>

## TIL 및 회고

<details>
<summary>JPA 대신 MyBatis 활용</summary>

> 평소 개발을 할 때 편리하게 DB에 접근하기 위해 ORM 기술을 제공하는 JPA를 활용하곤 했다.  
> 그러나 본 DB 서버는 대량의 데이터를 저장하는 용도이며, 객체 또는 테이블 간의 연관관계가 별도로 존재하지 않는다. 또한 대량의 데이터를 한 번에 삽입하는 벌크 인서트 작업가 이루어진다. 이러한 작업들은 JPA를 사용하기에는 불리한 조건들이다.  
> 따라서 본 DB 서버에서는 MyBatis를 도입하기로 결정하였으며, 대량의 데이터를 보다 효율적으로 처리할 수 있게 되었다.

</details>

<details>
<summary>다중 DB 연결</summary>

> 서울시 공공 데이터의 원본 데이터를 저장할 DB와 api 서버에서 필요한 가공된 데이터를 저장할 DB 두개를 나눠서 사용하기로 결정되어 다중 DB 연결을 처음 시도했는데 DB의 DataSource를 설정해주는 과정에서 application.yml에 설정해둔 DB 정보가 바인딩되지 않았고 local 환경에서는 잘 되었지만 CI 빌드 중에 에러가 생겨서 다른 팀원의 도움을 받아 해결되었다. 이와 관련된 내용을 더 찾아보고 공부할 필요를 느꼈다

</details>

## Author

| <img src="https://avatars.githubusercontent.com/u/65033360?v=4" width="150" height="150"/> | <img src="https://avatars.githubusercontent.com/u/148259495?v=4" width="150" height="150"/> |
| :----------------------------------------------------------------------------------------: | :-----------------------------------------------------------------------------------------: |
|                     배기연<br/>[@GiYeons](https://github.com/GiYeons)                      |                     최유림<br/>[@Yuurim98](https://github.com/Yuurim98)                     |
|                      **데이터 수집 및 저장**<br/>**데이터 갱신**<br/>                      |                                   **데이터 전처리**<br/>                                    |
