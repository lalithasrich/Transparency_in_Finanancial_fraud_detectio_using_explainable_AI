from db import connect_to_db

try:
    conn = connect_to_db()
    print("✅ Connected successfully")
    conn.close()
except Exception as e:
    print("❌ Connection failed", e)
