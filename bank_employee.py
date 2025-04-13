

import streamlit as st
import pandas as pd
import plotly.express as px
from db import (
    fetch_customer_profiles, update_customer_details,
    fetch_transactions_by_customer, fetch_pending_fraud_transactions,
    update_transaction_status, search_customers, generate_transaction_report,
    export_transactions_to_csv, fetch_customer_by_id,
    fetch_pending_fraud_transactions_pane
)

def bank_employee_page():
    st.title("🏦 ADMIN Panel")

    # Handle customer profile view
    if st.session_state.get("view_customer_profile"):
        customer_id = st.session_state.get("selected_customer_id")
        if customer_id:
            show_customer_profile(customer_id)
        return

    # --- Show Only Pending Customers ---
    st.subheader("Pending Customer Details")
    pending_customers = [c for c in fetch_customer_profiles() if c.get("status") == "pending"]
    if pending_customers:
        df = pd.DataFrame(pending_customers)
        st.dataframe(df[["name", "email", "phone", "velocity_6h", "credit_risk_score", "proposed_credit_limit"]])

        selected_name = st.selectbox("Select Customer to Edit", [c['name'] for c in pending_customers])
        customer = next(c for c in pending_customers if c['name'] == selected_name)

        velocity_6h = st.number_input("Velocity (6h)", value=customer.get("velocity_6h", 0))
        credit_risk_score = st.number_input("Credit Risk Score", value=customer.get("credit_risk_score", 0))
        proposed_credit_limit = st.number_input("Proposed Credit Limit", value=customer.get("proposed_credit_limit", 200))

        if st.button("Update Customer"):
            try:
                update_customer_details(customer['id'], velocity_6h, credit_risk_score, proposed_credit_limit)
                st.success("Customer details updated successfully!")
            except Exception as e:
                st.error(f"Error updating customer: {e}")
    else:
        st.info("No pending customers to update.")

    # --- Fraud Approval Panel ---
    st.subheader("🚨 Fraud Approval Panel")

    # Fetch only transactions with 'pending' fraud status
    pending_transactions = fetch_pending_fraud_transactions_pane()
    print(pending_transactions)

    if pending_transactions:
        for txn in pending_transactions:
            with st.expander(f"Transaction ID: {txn['id']} | Amount: {txn['amount']} | Prediction: {txn['prediction']}"):
                st.write(f"Customer ID: {txn['customer_id']}")
                st.write(f"Payment Type: {txn['payment_type']}")
                st.write(f"Created At: {txn['created_at']}")

                col1, col2, col3 = st.columns([1, 1, 1])
                with col1:
                    if st.button(f"Approve {txn['id']}"):
                        update_transaction_status(txn['id'], 'approved')
                        st.success(f"Transaction {txn['id']} approved!")
                        st.experimental_rerun()  # Refresh the page to show updated transactions

                with col2:
                    if st.button(f"Reject {txn['id']}"):
                        update_transaction_status(txn['id'], 'rejected')
                        st.error(f"Transaction {txn['id']} rejected!")
                        st.experimental_rerun()

                with col3:
                    if st.button(f"View Profile {txn['id']}"):
                        st.session_state["selected_customer_id"] = txn["customer_id"]
                        st.session_state["view_customer_profile"] = True
    else:
        st.info("No pending fraud transactions.")


    # --- Transaction Report ---
    st.subheader("📊 Transaction Report")
    report = generate_transaction_report()

    if report:
        st.metric("Total Transactions", report['total_transactions'])
        st.metric("Fraudulent Transactions", report['fraud_count'])
        st.metric("Total Amount Processed", f"${report['total_amount']:.2f}")
        st.metric("Fraud Percentage", f"{report['fraud_percentage']}%")

        chart_df = pd.DataFrame({
            "Category": ["Fraud", "Not Fraud"],
            "Count": [report['fraud_count'], report['total_transactions'] - report['fraud_count']]
        })
        fig = px.pie(chart_df, names='Category', values='Count', title="Fraud vs Not Fraud")
        st.plotly_chart(fig)

    transactions = fetch_pending_fraud_transactions()  # or use all fetched transactions function
    if transactions:
        st.dataframe(pd.DataFrame(transactions))

    # --- Export ---
    if st.button("Export Transactions to CSV"):
        csv_data = export_transactions_to_csv()
        st.download_button(label="Download CSV", data=csv_data, file_name="transactions.csv", mime="text/csv")

def show_customer_profile(customer_id):
    st.title("👤 Customer Profile")

    try:
        # Fetch the customer and transactions
        customer = fetch_customer_by_id(customer_id)
        print('customer')
        print(customer)
        transactions = fetch_transactions_by_customer(customer_id)
    except Exception as e:
        st.error(f"Error loading customer profile: {e}")
        return

    # Ensure `customer` data is valid
    if not customer:
        st.error("Customer profile not found.")
        return

    # Display customer details
    st.markdown(f"**Name:** {customer['name']}")
    st.markdown(f"**Email:** {customer['email']}")
    st.markdown(f"**Phone:** {customer['phone']}")
    st.markdown(f"**Income:** ${customer['income']}")

    # Display transaction dashboard
    st.subheader("📈 Transaction Dashboard")
    if transactions:
        df = pd.DataFrame(transactions)
        total = len(df)
        frauds = df[df['prediction'] == 'Fraud']
        fraud_count = len(frauds)
        total_amount = df['amount'].sum()

        st.metric("Total Transactions", total)
        st.metric("Fraudulent Transactions", fraud_count)
        st.metric("Total Amount Spent", f"${total_amount:.2f}")
        st.metric("Fraud Percentage", f"{(fraud_count / total * 100):.2f}%")

        pie_df = pd.DataFrame({
            "Category": ["Fraud", "Not Fraud"],
            "Count": [fraud_count, total - fraud_count]
        })
        fig = px.pie(pie_df, names='Category', values='Count', title="Customer Fraud Ratio")
        st.plotly_chart(fig)

        st.subheader("📋 Transaction History")
        st.dataframe(df)
    else:
        st.info("No transactions found for this customer.")

    if st.button("🔙 Back to Bank Panel"):
        st.session_state["view_customer_profile"] = False
        st.session_state["selected_customer_id"] = None
