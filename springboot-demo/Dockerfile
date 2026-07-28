#FROM          基础镜像（Java 21）
#WORKDIR       容器内工作目录
#COPY          把 jar 拷进镜像
#EXPOSE 8080   声明端口（文档作用）
#ENTRYPOINT    容器启动命令

# 第一阶段：构建（可选，也可本地 mvn 打好 jar 再 COPY）
FROM eclipse-temurin:21-jdk AS builder
WORKDIR /app
COPY pom.xml mvnw ./
COPY .mvn .mvn
COPY src ./src
RUN chmod +x mvnw && ./mvnw clean package -DskipTests

# 第二阶段：运行（镜像更小）
FROM eclipse-temurin:21-jre
WORKDIR /app
COPY --from=builder /app/target/springboot-demo-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]