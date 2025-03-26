FROM eclipse-temurin:17-jdk AS builder
WORKDIR /app
COPY . .
RUN javac -d out com/kynsoft/job/MongoCleanTransactionJob.java

FROM eclipse-temurin:17-jre
WORKDIR /app
COPY --from=builder /app/out /app
ENTRYPOINT ["java", "com.kynsoft.job.MongoCleanTransactionJob"]