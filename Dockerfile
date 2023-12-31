# Kotlin 빌드를 위한 기본 이미지로 JDK 이미지를 사용합니다.
FROM openjdk:17-jdk-slim

# 호스트 머신의 현재 디렉토리의 모든 파일을 Docker 이미지의 /app 디렉토리에 복사합니다.
COPY . /app
RUN mkdir -p /tomcat/logs

# 작업 디렉토리를 /app으로 설정합니다.
WORKDIR /app

# Kotlin 어플리케이션을 빌드합니다.
RUN ./gradlew bootJar

WORKDIR /app/build/libs

EXPOSE 8080
# 애플리케이션을 실행합니다.
ENTRYPOINT ["java", "-Dspring.profiles.active=prod", "-jar", "nanoogoogae-0.0.1-SNAPSHOT.jar"]
