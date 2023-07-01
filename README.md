# SimplePractice_Redis

### 1. Redis란?
Redis 는 Key-Value 형태로 데이터를 관리하는 오픈 소스
Redis 는 빠른 속도와 간편한 사용법으로 인해 캐시, 인증 토큰, 세션 관리 등등 여러 용도로 사용
데이터베이스, 캐시, 메세지 브로커로 사용되며 인메모리 데이터 구조를 가진 저장소

 - In-Memory Data Strucutre Store
 - Key - Value 형태로 데이터 저장
 - 여러 가지 Value 타입 저장 가능 (String, Set, Hash, List 등등..)
 - Single Thread
 - 데이터 만료 시간 지정 가능

### 2. 캐시 서버(Cache Server)
데이터 베이스가 있는데도 Redis라는 인메모리 데이터 구조 저장소를 사용하는 이유
 - 데이터 베이스는 데이터를 물리 디스크에 직접 쓰기 때문에 서버에 문제가 발생하여 다운되더라도 데이터가 손실되지 않음. 하지만 매번 디스크에 접근해야 하기 때문에 사용자가 많아질수록 부하가 많아져서 느려질 수 있음.
 - 일반적으로 서비스 운영 초반이거나 규모가 작은, 사용자가 많지 않은 서비스의 경우에는 WEB - WAS - DB 의 구조로도 데이터 베이스에 무리가 가지 않음
 - 하지만 사용자가 늘어난다면 데이터 베이스가 과부하 될 수 있기 때문에 이때 캐시 서버를 도입하여 사용
  
 - 그리고 이 캐시 서버로 이용할 수 있는 것이 바로 Redis 입니다.
 > 캐시는 한번 읽어온 데이터를 임의의 공간에 저장하여 다음에 읽을 때는 빠르게 결괏값을 받을 수 있도록 도와주는 공간  
 > 같은 요청이 여러 번 들어오는 경우 매번 데이터 베이스를 거치는 것이 아니라 캐시 서버에서 첫 번째 요청 이후 저장된 결괏값을 바로 내려주기 때문에 DB의 부하를 줄이고 서비스의 속도도 느려지지 않는 장점
 

##### 캐시 서버는 Look aside cache 패턴과 Write Back 패턴이 존재
- Look aside cache
1. 클라이언트가 데이터를 요청
2. 웹서버는 데이터가 존재하는지 Cache 서버에 먼저 확인
3. Cache 서버에 데이터가 있으면 DB에 데이터를 조회하지 않고 Cache 서버에 있는 결과값을 클라이언트에게 바로 반환 (Cache Hit)
4. Cache 서버에 데이터가 없으면 DB에 데이터를 조회하여 Cache 서버에 저장하고 결과값을 클라이언트에게 반환 (Cache Miss)
  
- Write Back 
1. 웹서버는 모든 데이터를 Cache 서버에 저장
2. Cache 서버에 특정 시간 동안 데이터가 저장됨
3. Cache 서버에 있는 데이터를 DB에 저장
4. DB에 저장된 Cache 서버의 데이터를 삭제

* insert 쿼리를 한 번씩 500번 날리는 것보다 insert 쿼리 500개를 붙여서 한 번에 날리는 것이 더 효율적이라는 원리
* 들어오는 데이터들이 저장되기 전에 메모리 공간에 머무르는데 이때 서버에 장애가 발생하여 다운된다면 데이터가 손실될 수 있다는 단점

### 3. Redis 설치
도커로 설치 후 실행 가능합니다. (https://hub.docker.com/_/redis 참고)

```
# 이미지 다운 (docker images 로 확인 가능)
$ docker pull redis

# 컨테이너로 레디스 실행 (--name: 컨테이너 이름 설정, -p: 포트 포워딩, -d: 백그라운드에서 실행)
$ docker run --name some-redis -p 6379:6379 -d redis

# redis-cli 접속
$ docker exec -it some-redis redis-cli
```

### 4. Redis 명령어
- https://redis.io/commands/
- 다만 Redis 는 Single Thread 기반이기 때문에 keys, flushall, flushdb, getall 등 일반적으로 생각했을 때 O(N) 의 시간복잡도를 가질 것 같은 명령어는 운영 환경에서 사용하면 위험
