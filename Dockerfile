# 1. 베이스 이미지 설정 (Java 실행 환경)
FROM eclipse-temurin:17-jdk-focal
# 2. 작업 디렉토리 설정
WORKDIR /app

# 3. 빌드된 JAR 파일을 컨테이너 안으로 복사
# (파일명은 실제 생성된 이름으로 수정하세요)
COPY build/libs/oliveold-0.0.1-SNAPSHOT.jar app.jar
# 4. 컨테이너가 실행될 때 앱 실행
ENTRYPOINT ["java","-jar", "app.jar"]