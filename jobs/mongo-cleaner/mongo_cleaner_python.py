import os
from datetime import datetime
from pymongo import MongoClient

def main():
    mongo_uri = "mongodb://mongoadmin:mongopassword@mongodb-service.mongo-db.svc.cluster.local:27017"
    mongo_db = "Payments"

    client = MongoClient(mongo_uri)
    db = client[mongo_db]
    collection = db["Transactions"]
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