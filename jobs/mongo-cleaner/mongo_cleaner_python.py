import os
from datetime import datetime
from pymongo import MongoClient

def main():
    mongo_uri = os.getenv("MONGO_CONNECTION_STRING", "mongodb://localhost:27017")
    mongo_db = os.getenv("MONGO_DATABASE_NAME", "paymentsDb")

    client = MongoClient(mongo_uri)
    db = client[mongo_db]
    collection = db["transactions"]

    now = datetime.utcnow()

    result = collection.delete_many({
        "$and": [
            {"Expiration": {"$lt": now}},
            {"$or": [
                {"CurrentStatus": None},
                {"CurrentStatus": ""}
            ]}
        ]
    })

    print(f"🧹 Eliminadas {result.deleted_count} transacciones caducadas/no aprobadas.")

if __name__ == "__main__":
    main()