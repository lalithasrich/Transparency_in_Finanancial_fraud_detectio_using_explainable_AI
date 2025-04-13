
'''
import streamlit as st
import pandas as pd
import datetime
from db import insert_customer_profile, fetch_customer_profiles, log_transaction, connect_to_db, update_transaction_status, fetch_transaction_status
import joblib
from helper import preprocess_and_predict
from bank_employee import bank_employee_page

# Load the saved model
model = joblib.load("best_model.pkl")

# Helper Function to Set Background
import base64

def set_background(image_path):
    # Read and encode the image file
    with open(image_path, "rb") as image_file:
        encoded_string = base64.b64encode(image_file.read()).decode()

    # Use Base64 encoding in CSS
    page_bg_img = f"""
    <style>
    .stApp {{
        background-image: url("data:image/jpeg;base64,{encoded_string}");
        background-size: cover;
        background-repeat: no-repeat;
        background-attachment: fixed;
    }}
    </style>
    """
    st.markdown(page_bg_img, unsafe_allow_html=True)


# Landing Page
def landing_page():
    set_background("C:/Users/kommu/OneDrive/Desktop/Major_Project/final_project/final_project/assets/images/ecommerce.jpg")
    # Title and subtitle  
    st.title("Welcome to ECOMMERCE PORTAL") 

     

    # Separate buttons for Logi
    # n and Signup  
    col1, col2 = st.columns(2)  
    with col1:  
        if st.button("Login"):  
            st.session_state.page = "Login"  

    with col2:  
        if st.button("Signup"):  
            st.session_state.page = "Signup"

# Login Page

def login_page():
    set_background("C:/Users/kommu/OneDrive/Desktop/Major_Project/final_project/final_project/assets/images/background.jpg")
    st.title("Login")

    # Choose login type
    login_type = st.radio("Login as", ["Customer", "Employee"])

    # Common login fields
    email = st.text_input("Email")
    password = st.text_input("Password", type="password")

    if st.button("Login"):
        if login_type == "Customer":
            # Dummy customer auth — replace with real logic
            if email and password:
                st.success(f"Customer {email} logged in successfully!")
                st.session_state.page = "Add Items"
            else:
                st.error("Please enter valid credentials.")
        
        elif login_type == "Employee":
            # Dummy employee auth — replace with real logic
            if email == "employee@bank.com" and password == "admin123":  # Replace with DB check
                st.success("Bank Employee logged in successfully!")
                st.session_state.page = "Employee Panel"
            else:
                st.error("Invalid employee credentials.")


# Customer Signup Page
def customer_signup():
    set_background("C:/Users/kommu/OneDrive/Desktop/Major_Project/final_project/final_project/assets/images/background.jpg")
    st.title("Customer Signup")

    # Input fields for customer profile
    name = st.text_input("Name")
    email = st.text_input("Email")
    phone = st.text_input("Phone Number")
    dob = st.date_input("Date of Birth")
    address = st.text_area("Address")
    income = st.number_input("Income", min_value=0.0, step=1000.0)
    employment_status = st.selectbox("Employment Status", ["Employed", "Unemployed", "Self-Employed", "Student"])
    housing_status = st.selectbox("Housing Status", ["Renting", "Owned", "Living with Parents", "Other"])
    source = st.selectbox("Source", ["Online", "Branch", "Agent"])
    st.session_state['customer_name'] = name
    # Submit button
    if st.button("Sign Up"):
        if name and email and phone and dob and address and income:
            try:
                # Calculate age from DOB
                today = datetime.date.today()
                age = today.year - dob.year - ((today.month, today.day) < (dob.month, dob.day))
                insert_customer_profile(name, email, phone, address, income, age, employment_status, housing_status, source)
                st.success("Profile created successfully!")
            except Exception as e:
                st.error(f"Error: {e}")
        else:
            st.warning("Please fill all fields!")
        st.session_state.page = "Add Items"

def add_to_cart_page():
    """Page to handle product images, product details, and cart."""
    set_background("C:/Users/kommu/OneDrive/Desktop/Major_Project/final_project/final_project/assets/images/background.jpg")

    # Sample product data
    items = {
        "Laptop": {"price": 1000.0, "image": "C:/Users/kommu/OneDrive/Desktop/Major_Project/final_project/final_project/assets/images/laptop.jpg", "description": "HP 15s, 12th Gen Intel Core i3-1215U, 8GB DDR4, 512GB SSD, (Win 11, Office 21, Silver, 1.69kg), Anti-Glare, 15.6-inch (39.6cm) FHD Laptop, Intel UHD Graphics, HD Camera, Dual Speakers, fy5011TU"},
        "Smartphone": {"price": 700.0, "image": "C:/Users/kommu/OneDrive/Desktop/Major_Project/final_project/final_project/assets/images/smartphone.png", "description": "iPhone 16 Pro Max 256 GB: 5G Mobile Phone with Camera Control, 4K 120 fps Dolby Vision and a Huge Leap in Battery Life. Works with AirPods; Natural Titanium"},
        "Tablet": {"price": 500.0, "image": "C:/Users/kommu/OneDrive/Desktop/Major_Project/final_project/final_project/assets/images/tablet.jpg", "description": "Samsung Galaxy Tab A9+ 27.94 cm (11.0 inch) Display, RAM 8 GB, ROM 128 GB Expandable, Wi-Fi Tablet, Silver"},
        "Smartwatch": {"price": 250.0, "image": "C:/Users/kommu/OneDrive/Desktop/Major_Project/final_project/final_project/assets/images/smartwatch.jpg", "description": "TFT Display, SpO2, 100 Sports Mode with Auto Detection, Upto 7 Days Battery (2 Days with Heavy Calling) - Jet Black"},
        "Headphones": {"price": 150.0, "image": "C:/Users/kommu/OneDrive/Desktop/Major_Project/final_project/final_project/assets/images/headphone.jpg", "description": "boAt Rockerz 425 Bluetooth Wireless Over Ear Headphones with Mic Signature Sound, Beast Mode for Gaming, Enx Tech, ASAP Charge, 25H Playtime, Bluetooth V5.2 (Active Black)"},
    }



    # Initialize session state for navigation and cart
    if "add_to_cart_page" not in st.session_state:
        st.session_state.add_to_cart_page = "Product Images"
        st.session_state.selected_item = None
        st.session_state.cart = {}

    # 📌 Product Selection Page
    if st.session_state.add_to_cart_page == "Product Images":
        st.title("Select a Product")
        for item, details in items.items():
            col1, col2, col3 = st.columns([2, 3, 1])
            with col1:
                if st.button(f"View {item}", key=f"view_{item}"):
                    st.session_state.selected_item = item
                    st.session_state.add_to_cart_page = "Product Details"
            with col2:
                st.image(details["image"], caption=item, width=150)
            with col3:
                st.write(f"Price: ${details['price']:.2f}")

    # 📌 Product Details Page
    elif st.session_state.add_to_cart_page == "Product Details":
        selected_item = st.session_state.selected_item
        if not selected_item:
            st.warning("No product selected.")
            st.session_state.add_to_cart_page = "Product Images"
        else:
            details = items[selected_item]
            st.title(selected_item)
            st.image(details["image"], width=300)
            st.write(f"Description: {details['description']}")
            st.write(f"Price: ${details['price']:.2f}")

            if st.button("Add to Cart"):
                if selected_item in st.session_state.cart:
                    st.session_state.cart[selected_item] += 1
                else:
                    st.session_state.cart[selected_item] = 1
                st.success(f"{selected_item} added to cart!")

            if st.button("Remove from Cart"):
                if selected_item in st.session_state.cart:
                    if st.session_state.cart[selected_item] > 0:
                        st.session_state.cart[selected_item] -= 1
                        st.success(f"One {selected_item} removed from cart!")
                    else:
                        st.warning(f"Cannot remove from cart because {selected_item} doesn't remain.")
                        
            if st.button("View cart"):
                st.session_state.add_to_cart_page = "Cart"

            
            if st.button("Back to Products"):
                st.session_state.add_to_cart_page = "Product Images"

    # 📌 Cart Page
    elif st.session_state.add_to_cart_page == "Cart":
        st.title("Your Cart")
        
        if not st.session_state.cart:
            st.write("Cart is empty.")
            if st.button("Back to Products"):
                st.session_state.add_to_cart_page = "Product Images"
        else:
            # Prepare table data
            cart_data = []
            total = 0
            for item, quantity in st.session_state.cart.items():
                price = items[item]["price"]
                subtotal = price * quantity
                cart_data.append([item, f"${price:.2f}", quantity, f"${subtotal:.2f}"])
                total += subtotal

            # Display table
            st.write("### Cart Summary")
            st.table(
                pd.DataFrame(cart_data, columns=["Product", "Price", "Quantity", "Subtotal"])
            )

            # Display total amount
            st.write(f"### Total: ${total:.2f}")
            st.session_state['cart_number'] = total

            # Checkout and navigation buttons
            col1, col2 = st.columns(2)
            with col1:
                if st.button("Checkout"):
                    st.success("Proceeding to checkout...")
                    st.session_state.page = "Enter Details"

            with col2:
                if st.button("Back to Products"):
                    st.session_state.add_to_cart_page = "Product Images"

import streamlit as st
import time


# Transaction Check Page
def transaction_check():
    set_background("C:/Users/kommu/OneDrive/Desktop/Major_Project/final_project/final_project/assets/images/background.jpg")
    
    st.title("Payment Check")
    
    try:
        customers = fetch_customer_profiles()
        customer_options = {customer['name']: customer for customer in customers}
    except Exception as e:
        st.error(f"Error fetching customer profiles: {e}")
        return
    
    customer_name = st.session_state.get("customer_name", None)
    cart_amount = st.session_state.get("cart_number", 0.0)

    st.write(f"*Customer Name:* {customer_name if customer_name else 'Not Selected'}")
    st.write(f"*Cart Amount:* ${cart_amount:.2f}")

    if not customer_name or customer_name not in customer_options:
        st.error("Customer not found. Please select a valid customer.")
        return

    customer = customer_options[customer_name]
    
    payment_type = st.selectbox("Payment Type", ["Credit Card", "Debit Card", "Bank Transfer", "Cash"], key="payment_type_select")

    if st.button("Pay now", key="pay_now_button"):
        with st.spinner("Processing transaction..."):
            time.sleep(2)  # Simulating processing time

            if cart_amount <= 0:
                st.warning("Invalid transaction amount. Please enter a valid amount.")
                return

            transaction_count = 9
            average_transaction_amount = 6

            customer_data = {
                'income': customer['income'],
                'transaction_count': transaction_count,
                'average_transaction_amount': average_transaction_amount,
                'age': customer['age'],
                'employment_status': customer['employment_status'],
                'housing_status': customer['housing_status']
            }

            try:
                prediction, probability = preprocess_and_predict(customer_data, cart_amount, payment_type)

                fraud_status = "Fraud" if prediction == 1 else "Not Fraud"
                transaction_id = log_transaction(customer['id'], cart_amount, fraud_status, payment_type)  # Store transaction ID
                
                st.session_state["transaction_status"] = "pending"
                st.session_state["transaction_id"] = transaction_id

                print(f"Transaction {transaction_id} logged: {fraud_status}. Waiting for bank review.")
                st.success("Transaction sent for bank review!")

            except Exception as model_error:
                st.error(f"Error during prediction: {model_error}")
                print(f"Error during transaction processing: {model_error}")

    check_transaction_status()  # Call to continuously 
import time
import logging
import streamlit as st

# Configure logging
logging.basicConfig(filename="customer_transaction.log", level=logging.INFO, format="%(asctime)s - %(levelname)s - %(message)s")

def check_transaction_status():
    if "transaction_status" in st.session_state and st.session_state["transaction_status"] == "pending":
        st.write("🔄 Waiting for bank approval...")

        for _ in range(10):  # Keep checking every 5 seconds
            time.sleep(5) 
            print(st.session_state["transaction_id"]) 
            transaction_status = fetch_transaction_status(st.session_state["transaction_id"])

            print(f"Fetched status for transaction {st.session_state['transaction_id']}: {transaction_status}")

            if transaction_status == "approved":
                st.success("✅ Transaction successful, proceed to products page.")
                st.session_state["transaction_status"] = "approved"
                print(f"Transaction {st.session_state['transaction_id']} approved: Updating UI.")
                break

            elif transaction_status == "rejected":
                st.error("❌ Transaction rejected, contact support for queries.")
                st.session_state["transaction_status"] = "rejected"
                print(f"Transaction {st.session_state['transaction_id']} rejected: Updating UI.")
                time.sleep(10)
                #st.experimental_rerun()  # Refresh the page
                break  

        if st.session_state["transaction_status"] == "pending":
            st.warning("Transaction is still under review. Please wait and refresh if needed.")
            logging.info(f"Transaction {st.session_state['transaction_id']} still pending after checks.")



# Customer Profile Page
def customer_profile_page():
    set_background("C:/Users/kommu/OneDrive/Desktop/Major_Project/final_project/final_project/assets/images/background.jpg")
    st.title("Customer Profile")
    st.write("This is your profile page. More details to come!")

# Navigation Logic
if "page" not in st.session_state:
    st.session_state.page = "Landing Page"

if st.session_state.page == "Landing Page":
    landing_page()
elif st.session_state.page == "Login":
    login_page()
elif st.session_state.page == "Signup":
    customer_signup()
elif st.session_state.page == "Add Items":
    add_to_cart_page()
elif st.session_state.page == "Enter Details":
    print("transaction check")
    transaction_check()  # Can be renamed to match your flow
elif st.session_state.page == "Customer Profile":
    customer_profile_page()
elif st.session_state.page == "Bank Employee":
    bank_employee_page()
elif st.session_state.page == "Employee Panel":
    bank_employee_page()
      '''
import hashlib
import re
import streamlit as st
import pandas as pd
import datetime
from db import check_user_exists, insert_customer_profile, fetch_customer_profiles, log_transaction, connect_to_db, update_transaction_status, fetch_transaction_status, create_customer_profiles_table, verify_user_credentials
import joblib
from helper import preprocess_and_predict
from bank_employee import bank_employee_page

# Load the saved model
model = joblib.load("best_model.pkl")

# Helper Function to Set Background
import base64
from pathlib import Path

background_path = Path("assets/images/background.jpg").resolve()
assets_path = Path("assets/images").resolve()
def set_background(image_path):
    # Read and encode the image file
    with open(image_path, "rb") as image_file:
        encoded_string = base64.b64encode(image_file.read()).decode()

    # Use Base64 encoding in CSS
    page_bg_img = f"""
    <style>
    .stApp {{
        background-image: url("data:image/jpeg;base64,{encoded_string}");
        background-size: cover;
        background-repeat: no-repeat;
        background-attachment: fixed;
    }}
    </style>
    """
    st.markdown(page_bg_img, unsafe_allow_html=True)


# Landing Page
def landing_page():
    set_background("C:/Users/kommu/OneDrive/Desktop/Major_Project/final_project/final_project/assets/images/background.jpg")
    # Title and subtitle  
    st.title("Welcome to ECOMMERCE PORTAL") 

    # Separate buttons for Logi
    # n and Signup  
    col1, col2 = st.columns(2)  
    with col1:  
        if st.button("Login"):  
            st.session_state.page = "Login"  

    with col2:  
        if st.button("Signup"):  
            st.session_state.page = "Signup"

# Login Page

def login_page():
    set_background("C:/Users/kommu/OneDrive/Desktop/Major_Project/final_project/final_project/assets/images/background.jpg")
    st.title("Login")

    # Choose login type
    login_type = st.radio("Login as", ["Customer", "Admin"])

    # Common login fields
    email = st.text_input("Email")
    password = st.text_input("Password", type="password")


    if st.button("Login"):
        if login_type == "Customer":
            # Dummy customer auth — replace with real logic
            if email and password:
                email = email.lower()
                hashed_password = hashlib.sha256(password.encode('utf-8')).hexdigest()
                #if verify_user_credentials(email, hashed_password):
                #    st.success(f"Customer {email} logged in successfully!")
                #    st.session_state.page = "Add Items"
                #else:
                #    st.error("Please enter valid credentials.")
                is_valid, customer_name = verify_user_credentials(email, hashed_password)
                if is_valid:
                    st.success(f"Welcome, {customer_name}👋")
                    st.session_state["customer_name"] = customer_name  # ✅ Store name for checkout page
                    st.session_state["page"] = "Add Items"
                else:
                    st.error("❌Authentication failed. Please check your password and try again.")

        elif login_type == "Admin":
            # Dummy employee auth — replace with real logic
            if email == "admin@axis.com" and password == "admin123":  # Replace with DB check
                st.success("Admin logged in successfully!")
                st.session_state.page = "Admin Panel"
            else:
                st.error("Invalid admin credentials.")


# Customer Signup Page
def customer_signup():
    create_customer_profiles_table()
    set_background("C:/Users/kommu/OneDrive/Desktop/Major_Project/final_project/final_project/assets/images/background.jpg")
    st.title("Customer Signup")

    # Input fields for customer profile
    name = st.text_input("Name")
    email = st.text_input("Email")
    phone = st.text_input("Phone Number")
    dob = st.date_input("Date of Birth")
    address = st.text_area("Address")
    income = st.number_input("Income", min_value=0.0, step=1000.0)
    employment_status = st.selectbox("Employment Status", ["Employed", "Unemployed", "Self-Employed", "Student"])
    housing_status = st.selectbox("Housing Status", ["Renting", "Owned", "Living with Parents", "Other"])
    source = st.selectbox("Source", ["Online", "Branch", "Agent"])
    st.session_state['customer_name'] = name
    password = st.text_input("Password", type="password")
    hashed_password = hashlib.sha256(password.encode('utf-8')).hexdigest()
    # Submit button
    if st.button("Sign Up"):
        if name and email and phone and dob and address and income:
            if not re.match(r"[^@]+@[^@]+\.[^@]+", email):
                st.warning("Invalid email format!")
                st.stop()

            if income <= 0:
                st.warning("Income must be a positive number!")
                st.stop()

            if (
                len(password) < 8
                or not re.search(r"[A-Z]", password)
                or not re.search(r"[a-z]", password)
                or not re.search(r"\d", password)
                or not re.search(r"[!@#$%^&*(),.?\":{}|<>]", password)
            ):
                st.warning("Password must be at least 8 characters long, include an uppercase letter, a lowercase letter, a number, and a special character!")
                st.stop()

            if check_user_exists(email):
                st.warning("User already exists! Please log in.")
                st.stop()
            try:
                today = datetime.date.today()
                email = email.lower()
                age = today.year - dob.year - ((today.month, today.day) < (dob.month, dob.day))
                if insert_customer_profile(name, email, phone, address, income, age, employment_status, housing_status, source, hashed_password):
                    st.success("Profile created successfully!")
                else:
                    print("issue while inserting")
            except Exception as e:
                st.error(f"Error: {e}")
        else:
            st.warning("Please fill all fields!")
        st.session_state.page = "Login"

def add_to_cart_page():
    """Page to handle product images, product details, and cart."""
    set_background("C:/Users/kommu/OneDrive/Desktop/Major_Project/final_project/final_project/assets/images/background.jpg")

    #items =  Sample product data
    items = {
        "Laptop": {"price": 1000.0, "image": "C:/Users/kommu/OneDrive/Desktop/Major_Project/final_project/final_project/assets/images/laptop.jpg", "description": "HP 15s, 12th Gen Intel Core i3-1215U, 8GB DDR4, 512GB SSD, (Win 11, Office 21, Silver, 1.69kg), Anti-Glare, 15.6-inch (39.6cm) FHD Laptop, Intel UHD Graphics, HD Camera, Dual Speakers, fy5011TU"},
        "Smartphone": {"price": 700.0, "image": "C:/Users/kommu/OneDrive/Desktop/Major_Project/final_project/final_project/assets/images/smartphone.png", "description": "iPhone 16 Pro Max 256 GB: 5G Mobile Phone with Camera Control, 4K 120 fps Dolby Vision and a Huge Leap in Battery Life. Works with AirPods; Natural Titanium"},
        "Tablet": {"price": 500.0, "image": "C:/Users/kommu/OneDrive/Desktop/Major_Project/final_project/final_project/assets/images/tablet.jpg", "description": "Samsung Galaxy Tab A9+ 27.94 cm (11.0 inch) Display, RAM 8 GB, ROM 128 GB Expandable, Wi-Fi Tablet, Silver"},
        "Smartwatch": {"price": 250.0, "image": "C:/Users/kommu/OneDrive/Desktop/Major_Project/final_project/final_project/assets/images/smartwatch.jpg", "description": "TFT Display, SpO2, 100 Sports Mode with Auto Detection, Upto 7 Days Battery (2 Days with Heavy Calling) - Jet Black"},
        "Headphones": {"price": 150.0, "image": "C:/Users/kommu/OneDrive/Desktop/Major_Project/final_project/final_project/assets/images/headphone.jpg", "description": "boAt Rockerz 425 Bluetooth Wireless Over Ear Headphones with Mic Signature Sound, Beast Mode for Gaming, Enx Tech, ASAP Charge, 25H Playtime, Bluetooth V5.2 (Active Black)"},
    }

    # Initialize session state for navigation and cart
    if "add_to_cart_page" not in st.session_state:
        st.session_state.add_to_cart_page = "Product Images"
        st.session_state.selected_item = None
        st.session_state.cart = {}

    # 📌 *Product Selection Page*
    if st.session_state.add_to_cart_page == "Product Images":
        st.title("Select a Product")
        for item, details in items.items():
            col1, col2, col3 = st.columns([2,4,1])  # Spacer columns included

            with col1:
                st.image(details["image"], caption=item, width=150)

            with col2:
                st.write(f"*Price: ${details['price']:.2f}*")
                st.write(f"{details['description']}")

            with col3:
                if st.button("View", key=f"view_{item}"):
                    st.session_state.selected_item = item
                    st.session_state.add_to_cart_page = "Product Details"



    # 📌 *Product Details Page*
    elif st.session_state.add_to_cart_page == "Product Details":
        selected_item = st.session_state.selected_item
        if not selected_item:
            st.warning("No product selected.")
            st.session_state.add_to_cart_page = "Product Images"
        else:
            details = items[selected_item]
            st.title(selected_item)
            st.image(details["image"], width=300)
            st.write(f"*Description:* {details['description']}")
            st.write(f"*Price: ${details['price']:.2f}*")

            if st.button("Add to Cart"):
                if selected_item in st.session_state.cart:
                    st.session_state.cart[selected_item] += 1
                else:
                    st.session_state.cart[selected_item] = 1
                st.success(f"{selected_item} added to cart!")

                        
            if st.button("View cart"):
                st.session_state.add_to_cart_page = "Cart"

            
            if st.button("Back to Products"):
                st.session_state.add_to_cart_page = "Product Images"

    # 📌 *Cart Page*
    elif st.session_state.add_to_cart_page == "Cart":
        st.title("Your Cart")

        if not st.session_state.cart:
            st.write("Cart is empty.")
            if st.button("Back to Products"):
                st.session_state.add_to_cart_page = "Product Images"
        else:
            total = 0

            # Table Headers
            st.write("### Cart Summary")
            col1, col2, col3, col4, col5, col6 = st.columns([3, 2, 1, 2, 1, 2])
            with col1:
                st.markdown("*Product*")
            with col2:
                st.markdown("*Price*")
            with col3:
                st.markdown("*Qty*")
            with col4:
                st.markdown("*Subtotal*")
            with col5:
                st.markdown("*Add*")
            with col6:
                st.markdown("*Remove*")
                

            # Table Rows
            for item, quantity in list(st.session_state.cart.items()):
                price = items[item]["price"]
                subtotal = price * quantity
                total += subtotal

                col1, col2, col3, col4, col5, col6 = st.columns([3, 2, 1, 2, 1, 2])
                with col1:
                    st.write(f"*{item}*")  # Product name
                with col2:
                    st.write(f"${price:.2f}")  # Price
                with col3:
                    st.write(f"{quantity}")  # Quantity display
                with col4:
                    st.write(f"*${subtotal:.2f}*")  # Subtotal
                with col5:
                    if st.button("➕", key=f"increase_{item}"):
                        st.session_state.cart[item] += 1
                        st.rerun()
                with col6:
                    if st.button("➖", key=f"decrease_{item}"):
                        if quantity > 1:
                            st.session_state.cart[item] -= 1
                        else:
                            del st.session_state.cart[item]  # Remove item if quantity is 0
                        st.rerun()
                

            # Display total amount
            st.write(f"### Total: ${total:.2f}")
            st.session_state['cart_number'] = total

            # Checkout and navigation buttons
            col1, col2 = st.columns(2)
            with col1:
                if st.button("Checkout"):
                    st.success("Proceeding to checkout...")
                    st.session_state.page = "Enter Details"

            with col2:
                if st.button("Back to Products"):
                    st.session_state.add_to_cart_page = "Product Images"

import streamlit as st
import time


# Transaction Check Page
def transaction_check():
    set_background("C:/Users/kommu/OneDrive/Desktop/Major_Project/final_project/final_project/assets/images/background.jpg")
    
    st.title("Payment Check")
    
    try:
        customers = fetch_customer_profiles()
        customer_options = {customer['name']: customer for customer in customers}
    except Exception as e:
        st.error(f"Error fetching customer profiles: {e}")
        return
    customer_name = st.session_state.get("customer_name", "Unknown")
    cart_amount = st.session_state.get("cart_number", 0.0)

    st.write(f"Customer Name: {customer_name}")
    st.write(f"Cart Amount: ${cart_amount:.2f}")

    if not customer_name or customer_name not in customer_options:
        st.error("Customer not found. Please select a valid customer.")
        return

    customer = customer_options[customer_name]
    
    payment_type = st.selectbox("Payment Type", ["Credit Card", "Debit Card", "Bank Transfer"], key="payment_type_select")
    pay_credit = pay_debit = pay_bank = False
    errors = []
    if payment_type == "Credit Card":
        st.subheader("Credit Card Details")
        card_number = st.text_input("Card Number", max_chars=16)
        cvv = st.text_input("CVV", type="password", max_chars=3)
        expiry_date = st.date_input("Expiry Date")
        card_holder = st.text_input("Card Holder Name")

        pay_credit = st.button("Pay now", key="pay_now_credit")

        if pay_credit:
            if not card_number:
                errors.append("Credit card number is required.")
            elif not card_number.isdigit():
                errors.append("Credit card number must contain only digits.")
            elif len(card_number) != 16:
                errors.append("Credit card number must be exactly 16 digits.")

            if not cvv:
                errors.append("CVV is required.")
            elif not cvv.isdigit():
                errors.append("CVV must contain only digits.")
            elif len(cvv) != 3:
                errors.append("CVV must be exactly 3 digits.")

            if expiry_date < datetime.date.today():
                errors.append("Credit card has expired. Please use a valid card.")

            if not card_holder.strip():
                errors.append("Card holder name is required.")

    elif payment_type == "Debit Card":
        st.subheader("Debit Card Details")
        card_number = st.text_input("Card Number", max_chars=16, key="debit_card_number")
        cvv = st.text_input("CVV", type="password", max_chars=3, key="debit_cvv")
        expiry_date = st.date_input("Expiry Date", key="debit_expiry")
        card_holder = st.text_input("Card Holder Name", key="debit_holder")

        pay_debit = st.button("Pay now", key="pay_now_debit")

        if pay_debit:
            if not card_number:
                errors.append("Debit card number is required.")
            elif not card_number.isdigit():
                errors.append("Debit card number must contain only digits.")
            elif len(card_number) != 16:
                errors.append("Debit card number must be exactly 16 digits.")

            if not cvv:
                errors.append("CVV is required.")
            elif not cvv.isdigit():
                errors.append("CVV must contain only digits.")
            elif len(cvv) != 3:
                errors.append("CVV must be exactly 3 digits.")

            if expiry_date < datetime.date.today():
                errors.append("Debit card has expired. Please use a valid card.")

            if not card_holder.strip():
                errors.append("Card holder name is required.")


    elif payment_type == "Bank Transfer":
        st.subheader("Bank Transfer Details")
        # transfer_type = st.radio("Transfer Type", ["NEFT", "RTGS", "IMPS"])
        account_holder_name = st.text_input("Account Holder Name")
        account_number = st.text_input("Account Number")
        ifsc_code = st.text_input("IFSC Code")
        bank_name = st.text_input("Bank Name")
        branch = st.text_input("Branch")

        pay_bank = st.button("Pay now", key="pay_now_bank")

        if pay_bank:
            if not account_holder_name:
                errors.append("Account holder name is required.")
            if not account_number or not account_number.isdigit() or not (8 <= len(account_number) <= 18):
                errors.append("Account number must be 8 to 18 digits long and numeric.")
            if not ifsc_code or len(ifsc_code) != 11:
                errors.append("Valid IFSC code is required (11 characters).")
            if not bank_name:
                errors.append("Bank name is required.")
            if not branch:
                errors.append("Branch name is required.")

    # Final processing block for all payment types
    if pay_credit or pay_debit or pay_bank:
        with st.spinner("Processing transaction..."):
            time.sleep(2)  # Simulating processing time

            if cart_amount <= 0:
                st.warning("Invalid transaction amount. Please enter a valid amount.")
                return

            transaction_count = 9
            average_transaction_amount = 6

            customer_data = {
                'income': customer['income'],
                'transaction_count': transaction_count,
                'average_transaction_amount': average_transaction_amount,
                'age': customer['age'],
                'employment_status': customer['employment_status'],
                'housing_status': customer['housing_status']
            }

            try:
                prediction, probability = preprocess_and_predict(customer_data, cart_amount, payment_type)

                fraud_status = "Fraud" if prediction == 1 else "Not Fraud"
                transaction_id = log_transaction(customer['id'], cart_amount, fraud_status, payment_type)  # Store transaction ID
                
                st.session_state["transaction_status"] = "pending"
                st.session_state["transaction_id"] = transaction_id

                print(f"Transaction {transaction_id} logged: {fraud_status}. Waiting for bank review.")
                st.success("Transaction sent for bank review!")

            except Exception as model_error:
                st.error(f"Error during prediction: {model_error}")
                print(f"Error during transaction processing: {model_error}")

    check_transaction_status()  # Call to continuously 

import time
import logging
import streamlit as st

# Configure logging
logging.basicConfig(filename="customer_transaction.log", level=logging.INFO, format="%(asctime)s - %(levelname)s - %(message)s")

def check_transaction_status():
    if "transaction_status" in st.session_state and st.session_state["transaction_status"] == "pending":
        st.write("🔄 Waiting for bank approval...")
        for _ in range(10):  # Keep checking every 5 seconds
            time.sleep(5) 
            print(st.session_state["transaction_id"]) 
            transaction_status = fetch_transaction_status(st.session_state["transaction_id"])

            print(f"Fetched status for transaction {st.session_state['transaction_id']}: {transaction_status}")

            if transaction_status == "approved":
                st.success("✅ Transaction successful, proceed to products page.")
                st.session_state["transaction_status"] = "approved"
                print(f"Transaction {st.session_state['transaction_id']} approved: Updating UI.")
                break

            elif transaction_status == "rejected":
                st.error("❌ Transaction rejected, contact support for queries.")
                st.session_state["transaction_status"] = "rejected"
                print(f"Transaction {st.session_state['transaction_id']} rejected: Updating UI.")
                time.sleep(10)
                #st.experimental_rerun()  # Refresh the page
                break  

        if st.session_state["transaction_status"] == "pending":
            st.warning("Transaction is still under review. Please wait and refresh if needed.")
            logging.info(f"Transaction {st.session_state['transaction_id']} still pending after checks.")



# Customer Profile Page
def customer_profile_page():
    set_background(str(background_path))
    st.title("Customer Profile")
    st.write("This is your profile page. More details to come!")

# Navigation Logic
if "page" not in st.session_state:
    st.session_state.page = "Landing Page"

if st.session_state.page == "Landing Page":
    landing_page()
elif st.session_state.page == "Login":
    login_page()
elif st.session_state.page == "Signup":
    customer_signup()
elif st.session_state.page == "Add Items":
    add_to_cart_page()
elif st.session_state.page == "Enter Details":
    print("transaction check")
    transaction_check()  # Can be renamed to match your flow
elif st.session_state.page == "Customer Profile":
    customer_profile_page()
elif st.session_state.page == "Bank Employee":
    bank_employee_page()
elif st.session_state.page == "Admin Panel":
    bank_employee_page()