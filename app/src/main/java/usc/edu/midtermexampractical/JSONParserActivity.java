package usc.edu.midtermexampractical;

import android.os.Bundle;
import android.widget.LinearLayout;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class JSONParserActivity extends AppCompatActivity {
    ArrayList<String> personName = new ArrayList<>();
    ArrayList<String> personEmail = new ArrayList<>();

    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jsonparser);

        recyclerView = findViewById(R.id.recyclerview);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(linearLayoutManager);

        try {
//            Gets the jsonobject from the json file namely user_list.json
            JSONObject jsonObject = new JSONObject(loadJSONFromAsset());

//            fetch the json array named users
            JSONArray userArray = jsonObject.getJSONArray("users");

//            implement for loop for getting the data from the json file
            for(int i = 0; i < userArray.length(); i++){
                JSONObject userDetail = userArray.getJSONObject(i);
                personName.add(userDetail.getString("name"));
                personEmail.add(userDetail.getString("email"));

            }
        }catch (JSONException e){
            e.printStackTrace();
        }

        CustomAdapter customAdapter = new CustomAdapter(JSONParserActivity.this, personName, personEmail);
        recyclerView.setAdapter(customAdapter);
    }



    private String loadJSONFromAsset() {
        String json = null;
        try{
            InputStream is = getAssets().open("user_list.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        }catch (IOException e){
            e.printStackTrace();
            return null;
        }
        return json;
    }
}