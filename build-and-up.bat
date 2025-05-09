@echo off

REM 서비스 디렉토리 목록
set services=discovery-server member-service book-service book-query-service api-gateway-service

REM 각 서비스 빌드
for %%s in (%services%) do (
    echo ==== Building %%s ====
    if exist %%s (
        pushd %%s
        call gradlew.bat build -x test
        popd
    ) else (
        echo !!!! Directory %%s not found
    )
)

REM 도커 이미지 빌드
echo ==== Building Docker images ====
docker-compose build --no-cache

REM 도커 컨테이너 실행
echo ==== Starting containers ====
docker-compose up -d
