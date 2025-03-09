
from django.contrib import admin
from django.urls import path
from . import views
urlpatterns = [
    path('viewPoint/', view=views.index,name="viewPoint")
]
