
import psycopg2
from psycopg2.extras import RealDictCursor
import csv
from io import StringIO

# Database connection settings
DB_CONFIG = {
    "host": "localhost",
    "database": "postgres",
    "user": "postgres",
    "password": "admin",
    "port": "5432",
}

def connect_to_db():
    """Establish a connection to the database."""
    return psycopg2.connect(**DB_CONFIG)

# Fetch Pending Fraud Transactions
def fetch_pending_fraud_transactions_pane():
    try:
        conn = connect_to_db()
        cursor = conn.cursor()
        query = """
        SELECT id, customer_id, amount, prediction, payment_type, created_at
        FROM transactions
        WHERE fraud_status = 'pending';
        """  # Ensures only pending transactions are retrieved
        cursor.execute(query)
        rows = cursor.fetchall()
        conn.close()
        
        return [dict(zip([desc[0] for desc in cursor.description], row)) for row in rows]
    except Exception as e:
        print(f"Error fetching pending fraud transactions: {e}")
        return []

# Search Customers
def search_customers(query):
    try:
        conn = connect_to_db()
        cursor = conn.cursor()
        sql_query = """
        SELECT id, name, email, phone, address FROM customer_profiles
        WHERE name ILIKE %s OR email ILIKE %s OR phone ILIKE %s;
        """
        search_param = f"%{query}%"
        cursor.execute(sql_query, (search_param, search_param, search_param))
        rows = cursor.fetchall()
        conn.close()
        
        return [{
            "id": row[0], "name": row[1], "email": row[2], "phone": row[3], "address": row[4]
        } for row in rows]
    except Exception as e:
        print(f"Error searching customers: {e}")
        return []

# Generate Transaction Report
def generate_transaction_report():
    try:
        conn = connect_to_db()
        cursor = conn.cursor()
        query = """
        SELECT 
            COUNT(*) AS total_transactions,
            SUM(CASE WHEN fraud_status = 'rejected' THEN 1 ELSE 0 END) AS fraud_count,
            SUM(amount) AS total_amount
        FROM transactions;
        """
        cursor.execute(query)
        row = cursor.fetchone()
        conn.close()
        
        total_transactions = row[0]
        fraud_count = row[1]
        total_amount = row[2]
        fraud_percentage = (fraud_count / total_transactions * 100) if total_transactions else 0
        
        return {
            "total_transactions": total_transactions,
            "fraud_count": fraud_count,
            "total_amount": total_amount,
            "fraud_percentage": round(fraud_percentage, 2)
        }
    except Exception as e:
        print(f"Error generating transaction report: {e}")
        return {}


def create_customer_profiles_table():
    conn = connect_to_db()
    if not conn:
        return
    try:
        with conn.cursor() as cur:
            cur.execute("""
            SELECT EXISTS (
                SELECT FROM information_schema.tables 
                WHERE table_schema = 'public' AND table_name = 'customer_profiles'
            );
            """)
            table_exists = cur.fetchone()[0]

            if table_exists:
                pass
            else:
                cur.execute("""
                        CREATE TABLE customer_profiles (
                            id SERIAL PRIMARY KEY,
                            name TEXT,
                            email TEXT,
                            phone TEXT,
                            address TEXT,
                            income FLOAT,
                            age INT,
                            employment_status TEXT,
                            housing_status TEXT,
                            source TEXT,
                            proposed_credit_limit FLOAT,
                            password TEXT
                        );
                    """)
                conn.commit()
                print("Table 'customer_profiles' is ready.")
    except Exception as e:
        print("Error creating table:", e)
    finally:
        conn.close()

# Export Transactions to CSV
def export_transactions_to_csv():
    try:
        conn = connect_to_db()
        cursor = conn.cursor()
        query = "SELECT * FROM transactions;"
        cursor.execute(query)
        rows = cursor.fetchall()
        
        output = StringIO()
        csv_writer = csv.writer(output)
        csv_writer.writerow([desc[0] for desc in cursor.description])  # Headers
        csv_writer.writerows(rows)
        
        conn.close()
        return output.getvalue()
    except Exception as e:
        print(f"Error exporting transactions to CSV: {e}")
        return ""
def insert_customer_profile(name, email, phone, address, income, age, employment_status, housing_status, source, password):
    try:
        conn = connect_to_db()
        cursor = conn.cursor()
    except Exception as e:
        raise Exception(f"Error inserting customer profile: {e}")
    proposed_credit_limit = 55.0
    query = """
        INSERT INTO customer_profiles 
        (name, email, phone, address, income, age, employment_status, housing_status, source, proposed_credit_limit, password)
        VALUES (%s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s)
        RETURNING *;
    """
    try:
        cursor.execute(query, (name, email, phone, address, income, age, employment_status, housing_status, source, proposed_credit_limit, password))
        inserted_row = cursor.fetchone()
        conn.commit()
    except:
        return False

    print("Inserted Details:")
    columns = [desc[0] for desc in cursor.description]
    for col, val in zip(columns, inserted_row):
        print(f"{col}: {val}")

    cursor.close()
    conn.close()
    return True
   

def fetch_customer_profiles():
    try:
        conn = connect_to_db()
        cursor = conn.cursor()
        query = """
            SELECT id, name, email, phone, address, income, age, employment_status, housing_status, proposed_credit_limit 
            FROM customer_profiles;
        """  # ✅ Added 'age', 'employment_status', and 'housing_status'
        cursor.execute(query)
        rows = cursor.fetchall()
        conn.close()
        
        return [{
            "id": row[0], 
            "name": row[1], 
            "email": row[2], 
            "phone": row[3], 
            "address": row[4], 
            "income": row[5], 
            "age": row[6],  
            "employment_status": row[7], 
            "housing_status": row[8],  
            "proposed_credit_limit": row[9]
        } for row in rows]
    except Exception as e:
        print(f"Error fetching customer profiles: {e}")
        return []

def log_transaction(customer_id, amount, prediction_label, payment_type):
    try:
        conn = connect_to_db()
        cursor = conn.cursor()
        query = """
            INSERT INTO transactions (customer_id, amount, prediction, payment_type, fraud_status)
            VALUES (%s, %s, %s, %s, 'pending');
        """
        cursor.execute(query, (customer_id, amount, prediction_label, payment_type))
        conn.commit()
        cursor.close()
        conn.close()
    except Exception as e:
        raise Exception(f"Error logging transaction: {e}")
def update_customer_details(customer_id, velocity_6h, credit_risk_score, proposed_credit_limit):
    try:
        conn = connect_to_db()
        cursor = conn.cursor()
        query = """
            UPDATE customer_profiles
            SET velocity_6h = %s, credit_risk_score = %s, proposed_credit_limit = %s
            WHERE id = %s;
        """
        cursor.execute(query, (velocity_6h, credit_risk_score, proposed_credit_limit, customer_id))
        conn.commit()
        cursor.close()
        conn.close()
    except Exception as e:
        raise Exception(f"Error updating customer details: {e}")
def fetch_transactions_by_customer(customer_id):
    try:
        conn = connect_to_db()
        cursor = conn.cursor()
        query = """
            SELECT id, amount, prediction, payment_type, created_at
            FROM transactions
            WHERE customer_id = %s;
        """
        cursor.execute(query, (customer_id,))
        rows = cursor.fetchall()
        conn.close()
        
        return [{
            "id": row[0],
            "amount": row[1],
            "prediction": row[2],
            "payment_type": row[3],
            "created_at": row[4]
        } for row in rows]
    except Exception as e:
        print(f"Error fetching transactions for customer {customer_id}: {e}")
        return []

def update_transaction_status(transaction_id, status):
    try:
        conn = connect_to_db()
        cursor = conn.cursor()
        query = """
            UPDATE transactions
            SET fraud_status = %s
            WHERE id = %s;
        """
        cursor.execute(query, (status, transaction_id))
        conn.commit()
        cursor.close()
        conn.close()
    except Exception as e:
        raise Exception(f"Error updating transaction status: {e}")
# Log transaction when customer clicks 'Pay Now'
def log_transaction(customer_id, amount, fraud_status, payment_type):
    try:
        conn = connect_to_db()
        cursor = conn.cursor()
        query = """
            INSERT INTO transactions (customer_id, amount, prediction, payment_type, fraud_status)
            VALUES (%s, %s, %s, %s, 'pending') RETURNING id;
        """
        cursor.execute(query, (customer_id, amount, fraud_status, payment_type))
        transaction_id = cursor.fetchone()[0]  # Fetch the newly inserted ID
        conn.commit()
        conn.close()

        print(f"Logged transaction {transaction_id}: {fraud_status}")
        return transaction_id
    except Exception as e:
        print(f"Error logging transaction: {e}")
        return None  # Ensure None is returned if there's an error


# Fetch transactions pending review by the bank employee
def fetch_pending_fraud_transactions():
    try:
        conn = connect_to_db()
        cursor = conn.cursor()
        query = """
        SELECT id, customer_id, amount, prediction, payment_type, created_at
        FROM transactions
        WHERE prediction = 'Fraud';
        """  # ✅ Changed from fraud_status to prediction
        cursor.execute(query)
        rows = cursor.fetchall()
        conn.close()
        
        return [{
            "id": row[0],
            "customer_id": row[1],
            "amount": row[2],
            "prediction": row[3],  # No need to convert since it's already 'Fraud' or 'Not Fraud'
            "payment_type": row[4],
            "created_at": row[5]
        } for row in rows]
    except Exception as e:
        print(f"Error fetching pending fraud transactions: {e}")
        return []


# Approve or reject transaction based on bank employee input
def update_transaction_status(transaction_id, status):
    try:
        conn = connect_to_db()
        cursor = conn.cursor()
        query = """
            UPDATE transactions
            SET fraud_status = %s
            WHERE id = %s;
        """
        cursor.execute(query, (status, transaction_id))
        conn.commit()

        # ✅ Notify customer if transaction is rejected
        if status.lower() == "rejected":
            notify_customer(transaction_id)  # Function to send update

        cursor.close()
        conn.close()
    except Exception as e:
        raise Exception(f"Error updating transaction status: {e}")

def notify_customer(transaction_id):
    # Simulate sending a notification to the customer
    print(f"Transaction {transaction_id} rejected: Notify customer page.")
def fetch_transaction_status(transaction_id):
    try:
        conn = connect_to_db()
        cursor = conn.cursor()
        query = "SELECT fraud_status FROM transactions WHERE id = %s;"
        cursor.execute(query, (transaction_id,))
        status = cursor.fetchone()[0]  
        conn.close()
        print(f"Fetched transaction status: {status}")  # Debugging print
        return status
    except Exception as e:
        print(f"Error fetching transaction status: {e}")
        return "error"
import psycopg2
from psycopg2.extras import RealDictCursor

def fetch_customer_by_id(customer_id):
    conn = None
    cursor = None
    try:
        # Ensure `connect_to_db()` returns a valid connection
        conn = connect_to_db()
        cursor = conn.cursor(cursor_factory=RealDictCursor)  # Use RealDictCursor

        query = "SELECT * FROM customer_profiles WHERE id = %s"
        cursor.execute(query, (customer_id,))
        customer = cursor.fetchone()  # Fetch as a dictionary
        print('customer form db')
        print(customer)

        return customer

    except Exception as e:
        print(f"Error fetching customer by ID: {e}")
        return None

    finally:
        if cursor:
            cursor.close()
        if conn:
            conn.close()



def verify_user_credentials(email, password):
    conn = connect_to_db()
    if not conn:
        return False
    cur = conn.cursor()
    #cur.execute("SELECT * FROM customer_profiles;")
    #existing_customers = cur.fetchall()
    #print("Existing Customers:", existing_customers)
    cur.execute("SELECT name FROM public.customer_profiles WHERE email = %s AND password = %s", (email, password))
    #cur.execute("SELECT * FROM public.customer_profiles WHERE email = %s AND password = %s", (email, password))
    user = cur.fetchone()
    cur.close()
    conn.close()
    if user is None:
        return False
    else: 
        return True,user[0]
    
def check_user_exists(email):
    conn = connect_to_db()
    if not conn:
        return False
    cur = conn.cursor()
    cur.execute("SELECT email FROM public.customer_profiles WHERE email = %s", (email,))
    user = cur.fetchone()
    cur.close()
    conn.close()
    return user is not None