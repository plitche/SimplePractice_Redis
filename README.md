# SimplePractice_Redis

### 1. Redis란?
Redis 는 Key-Value 형태로 데이터를 관리하는 오픈 소스
Redis 는 빠른 속도와 간편한 사용법으로 인해 캐시, 인증 토큰, 세션 관리 등등 여러 용도로 사용

 - In-Memory Data Strucutre Store
 - Key - Value 형태로 데이터 저장
 - 여러 가지 Value 타입 저장 가능 (String, Set, Hash, List 등등..)
 - Single Thread
 - 데이터 만료 시간 지정 가능

### 2. Redis 설치
도커로 설치 후 실행 가능합니다. (https://hub.docker.com/_/redis 참고)

```
# 이미지 다운 (docker images 로 확인 가능)
$ docker pull redis

# 컨테이너로 레디스 실행 (--name: 컨테이너 이름 설정, -p: 포트 포워딩, -d: 백그라운드에서 실행)
$ docker run --name some-redis -p 6379:6379 -d redis

# redis-cli 접속
$ docker exec -it some-redis redis-cli
```

### 3. Redis 명령어
- https://redis.io/commands/
- 다만 Redis 는 Single Thread 기반이기 때문에 keys, flushall, flushdb, getall 등 일반적으로 생각했을 때 O(N) 의 시간복잡도를 가질 것 같은 명령어는 운영 환경에서 사용하면 위험
