from django.shortcuts import render
from django.http.response import JsonResponse
import joblib
from sklearn.ensemble import RandomForestClassifier





def index(request):
    if request.method=="GET":
        income=request.GET.get('income',0)
        customer_age=request.GET.get('customer_age',0)
        housing_status=request.GET.get('housing_status',0)
        phone_mobile_valid=request.GET.get('phone_mobile_valid',0)
        bank_months_count=request.GET.get('bank_months_count',0)
        keep_alive_session=request.GET.get('keep_alive_session',0)
        
        model=joblib.load('app/static/models/randomforest.joblib')
        scale=joblib.load('app/static/models/scaler.joblib')
        ## ['income', 'customer_age', 'housing_status', 'phone_mobile_valid','bank_months_count', 'keep_alive_session']
        l1=[int(income),int(customer_age),int(housing_status),int(phone_mobile_valid),int(bank_months_count),int(keep_alive_session)]
        
        input=scale.transform([l1])
        pred=model.predict(input)[0]   

        return JsonResponse({'Predicted':str(pred)})
