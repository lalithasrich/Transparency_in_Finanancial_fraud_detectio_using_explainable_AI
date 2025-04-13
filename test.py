from sqlalchemy.orm import sessionmaker
from db import engine

# Test connection
try:
    # Create a session to test the connection
    Session = sessionmaker(bind=engine)
    session = Session()

    # Run a simple query to verify connection
    result = session.execute("SELECT 1").scalar()
    print("Connection successful! Test query result:", result)

    # Close the session
    session.close()
except Exception as e:
    print("Error connecting to the database:", e)
