package group.tonight.yue;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.socks.library.KLog;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        startActivity(new Intent(this, HomeActivity.class));
        finish();

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        boolean isFirstLaunch = preferences.getBoolean("first_launch", true);

        if (isFirstLaunch) {

            preferences.edit()
                    .putBoolean("first_launch", false)
                    .apply();

            new Thread(new Runnable() {
                @Override
                public void run() {

                    try {
                        Workbook workbook = Workbook.getWorkbook(getAssets().open("深圳.xls"));
                        int numberOfSheets = workbook.getNumberOfSheets();
                        if (numberOfSheets != 0) {
                            Sheet sheet = workbook.getSheet(0);
                            int rows = sheet.getRows();//获取总共多少行
                            int columns = sheet.getColumns();//获取总共多少列
                            List<String> keyList = new ArrayList<>();
//                            keyList.add("girlId");
                            keyList.add("title");
                            keyList.add("type");
                            keyList.add("area");
                            keyList.add("address");
                            keyList.add("from");
                            keyList.add("count");
                            keyList.add("ageRange");
                            keyList.add("suZhi");
                            keyList.add("score");
                            keyList.add("serviceItems");
                            keyList.add("price");
                            keyList.add("openTime");
                            keyList.add("surroundings");
                            keyList.add("safety");
                            keyList.add("contactInfo");
                            keyList.add("evaluate");
                            keyList.add("description");


                            JSONArray jsonArray = new JSONArray();
                            for (int m = 0; m < rows; m++) {
                                if (m == 0) {
                                    continue;
                                }
                                if (m == 1) {
                                    continue;
                                }
                                //循环读取第i行的所有列的数据
                                JSONObject jsonObject = new JSONObject();
                                for (int n = 0; n < columns; n++) {
                                    //sheet.getCell(列，行);
                                    Cell cell = sheet.getCell(n, m);
                                    String contents = cell.getContents();
                                    jsonObject.put(keyList.get(n), contents);
                                }
                                jsonArray.put(jsonObject);
                            }

                            Type type = new TypeToken<List<Girl>>() {
                            }.getType();
                            String json = jsonArray.toString();
                            List<Girl> beanList = new Gson().fromJson(json, type);

                            UserDatabase.get().getGirlDao().insert(beanList);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (BiffException e) {
                        e.printStackTrace();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        } else {

        }
    }
}
