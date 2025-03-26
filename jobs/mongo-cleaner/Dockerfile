# Etapa de compilación
FROM eclipse-temurin:17-jdk AS builder
WORKDIR /app
COPY . .
RUN javac -d out MongoCleanTransactionJob.java

# Etapa de ejecución
FROM eclipse-temurin:17-jre
WORKDIR /app
COPY --from=builder /app/out /app
ENTRYPOINT ["java", "com.kynsoft.job.MongoCleanTransactionJob"]