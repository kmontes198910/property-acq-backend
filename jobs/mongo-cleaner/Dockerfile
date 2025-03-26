# Dockerfile para Mongo Cleaner Python
FROM python:3.11-slim

WORKDIR /app

# Instala dependencias necesarias
COPY requirements.txt .
RUN pip install --no-cache-dir -r requirements.txt

# Copia el script
COPY mongo_cleaner_python.py .

ENTRYPOINT ["python", "mongo_cleaner_python.py"]
