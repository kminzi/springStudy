# springStudy

인프런 '스프링 기반 REST API 개발(백기선)' 강의를 수강하면서 공부한 Spring Project


API Index 생성하기
API의 진입점을 통해 리소스를 제공하고, API 리소스들에 대한 링크를 제공하기 위함.


이벤트 목록 조회 API 구현
- Pageable을 이용한 조회
repository의 findAll에 넘겨주면
- pagedresourcesAssembler 
page에 대한 링크까지 넘겨주게 됨(첫번째 페이지, 이전페이지, 현재 페이지, 이후 페이지 링크)

  

인증 시스템 구현
Spring OAuth2로 구현, Grant Type은 password type사용

