# ---- Giai đoạn 1: Build với context là thư mục gốc của project ----
FROM maven:3.9-eclipse-temurin-21 AS build
WORKDIR /app

COPY mrcBpmCommon/pom.xml ./mrcBpmCommon/pom.xml
COPY mrcBpmCommon/src ./mrcBpmCommon/src
RUN cd mrcBpmCommon && mvn clean install -DskipTests

COPY mrcEProcess/pom.xml ./mrcEProcess/pom.xml
COPY mrcEProcess/src ./mrcEProcess/src
RUN cd mrcEProcess && mvn package -DskipTests


# ---- Giai đoạn 2: Tạo image chạy ứng dụng cuối cùng ----
FROM eclipse-temurin:21-jre-jammy
RUN groupadd --system appgroup && useradd --system --gid appgroup appuser
WORKDIR /app

# Copy file JAR đã được build ở giai đoạn 1.
# Đường dẫn phải tính từ thư mục làm việc của build stage (/app)
COPY --from=build /app/mrcEProcess/target/mrcEProcess-*.jar app.jar

RUN chown appuser:appgroup app.jar
USER appuser
ENV _JAVA_OPTIONS="-XX:+UseZGC -Xms256m -Xmx512m"

# [FIXED] Sửa lại Healthcheck để dùng đúng port 8080 mà ứng dụng đang chạy
HEALTHCHECK --interval=30s --timeout=10s --start-period=15s --retries=3 \
  CMD wget -q -O /dev/null http://localhost:8080/actuator/health || exit 1

# EXPOSE port mà ứng dụng chạy bên trong container (mặc định của Spring Boot là 8080)
EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]