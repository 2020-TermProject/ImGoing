
import numpy as np
import numpy as np
import matplotlib as plt
import pandas as pd
#importing libraries
import io
from sklearn import datasets, linear_model
from sklearn.metrics import mean_squared_error, r2_score
import urllib
import requests

def stringBack():
    #Getting data from the user
    url="http://khprince.com/restaurantApp/restaurantInfo.csv"
    s=requests.get(url).content
    restaurantInfo=pd.read_csv(io.BytesIO(s), header=None, names=["restaurantName","ownerName", "category", "restaurantLongitude","restaurantLatitude","businessNo","reservedSeat","availableSeat", "Koreanfood","chinesefood","japanesefood", "yangsikfood","bunsikfood","cafefood","reviewpoint" ])
    url="http://khprince.com/restaurantApp/reservationHistory.csv"
    s=requests.get(url).content
    reservationHistory = pd.read_csv(io.BytesIO(s), header=None, names= ["KID","userName", "restaurantName", "ownerName", "reservationTime","review","reservedSeats"])


    url="http://khprince.com/restaurantApp/userID.txt"
    file = urllib.request.urlopen(url)
    for line in file:
    	KID = line.decode("utf-8")



    reservationHistory = reservationHistory[reservationHistory['KID'] == KID]

    #Merging the data
    data = pd.merge(restaurantInfo, reservationHistory,on=['restaurantName', 'ownerName'], how='right')
    data = pd.concat([data['reservationTime'],data['Koreanfood'],data['chinesefood'],data['japanesefood'],data['yangsikfood'], data['bunsikfood'], data['cafefood'], data['review']], axis=1)
    X = data.iloc[:,:-1]
    Y = data.iloc[:, -1:]


    #LinearRegression
    regr = linear_model.LinearRegression(normalize=True)
    regr.fit(X, Y)


    #Prepare Test data
    lenoftime = len(restaurantInfo)
    reservationTime = np.random.randint(1,7,lenoftime)
    reservationTime = pd.DataFrame(reservationTime,columns=['reservationTime'])
    X_test = pd.concat([reservationTime,restaurantInfo['Koreanfood'],restaurantInfo['chinesefood'],restaurantInfo['japanesefood'],restaurantInfo['yangsikfood'], restaurantInfo['bunsikfood'], restaurantInfo['cafefood']], axis=1)

    ypred = regr.predict(X_test)

    finalFrame = pd.concat([pd.DataFrame(data=ypred ,columns=["prediction"]), restaurantInfo['restaurantName'], restaurantInfo['ownerName'], restaurantInfo['category'],restaurantInfo["restaurantLongitude"], restaurantInfo["restaurantLatitude"], restaurantInfo["reservedSeat"], restaurantInfo["availableSeat"]], axis=1)
    return pd.DataFrame.to_json(finalFrame.sort_values(by=['prediction'],axis=0,ascending=False))
