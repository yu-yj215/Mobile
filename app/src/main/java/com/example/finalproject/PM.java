package com.example.finalproject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class PM {

   public String pm10Value;
   public String so2Value;
   public String no2Value;
   public String coValue;
   public String o3Value;
   public String pm25Value;
   public String dataTime;

   public void parseJson(String jsonData) {
      try {
         JSONObject jsonObject = new JSONObject(jsonData);

         // response 안의 body 안의 items 배열 가져오기
         JSONObject responseBody = jsonObject.getJSONObject("response").getJSONObject("body");
         JSONArray itemsArray = responseBody.getJSONArray("items");

         // items 배열에서 첫 번째 데이터 가져오기
         JSONObject firstItem = itemsArray.getJSONObject(0);

         // 필요한 값 추출
         this.pm10Value = firstItem.getString("pm10Value");
         this.so2Value = firstItem.getString("so2Value");
         this.no2Value = firstItem.getString("no2Value");
         this.coValue = firstItem.getString("coValue");
         this.o3Value = firstItem.getString("o3Value");
         this.pm25Value = firstItem.getString("pm25Value");
         this.dataTime = firstItem.getString("dataTime");

         // 추출한 값 출력 또는 필요한 곳에 전달
         System.out.println("pm10Value: " + this.pm10Value);
         System.out.println("so2Value: " + this.so2Value);
         System.out.println("no2Value: " + this.no2Value);
         System.out.println("coValue: " + this.coValue);
         System.out.println("o3Value: " + this.o3Value);
         System.out.println("pm25Value: " + this.pm25Value);
         System.out.println("dataTime: " + this.dataTime);

      } catch (JSONException e) {
         e.printStackTrace();
      }
   }
}

