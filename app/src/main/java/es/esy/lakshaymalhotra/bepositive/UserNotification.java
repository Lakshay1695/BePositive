package es.esy.lakshaymalhotra.bepositive;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class UserNotification extends AppCompatActivity {

    Button b1,b2;
    TextView text_name,text_phone;
    RequestQueue requestQueue;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    int seeker,donor,request;
    ProgressDialog pd;

    String date,time;



    Intent i;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_notification);
         i=getIntent();
         seeker=i.getIntExtra("Seeker",0);
        request=i.getIntExtra("Request",0);

        text_name = (TextView)findViewById(R.id.text_name);
        text_phone=(TextView)findViewById(R.id.text_phone);
        text_name.setText("Name : " + i.getStringExtra("NAME"));
        text_phone.setText("Mobile no. : "+i.getStringExtra("PHONE"));

        requestQueue = Volley.newRequestQueue(this);
        preferences=getSharedPreferences(Util.pref_name1,MODE_PRIVATE);
        editor=preferences.edit();
        donor=preferences.getInt(Util.key_id,0);
        pd=new ProgressDialog(this);
        pd.setMessage("Accepting.....");
        pd.setCancelable(false);
    }

    public void onClick(View view) {
        Toast.makeText(this, "Accept", Toast.LENGTH_SHORT).show();
        putDonor();

    }

    public void onClick1(View view) {
        Toast.makeText(this, "Reject", Toast.LENGTH_SHORT).show();


    }
    void putDonor(){

        date = DateFormat.getDateInstance().format(new Date());
        time = DateFormat.getTimeInstance().format(new Date());
        pd.show();
        StringRequest stringRequest=new StringRequest(Request.Method.POST, Util.update, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject object=new JSONObject(response);
                    int succ=object.getInt("success");
                    String mess=object.getString("message");

                    if(succ>0){
                        pd.dismiss();
                        Toast.makeText(UserNotification.this, ""+mess, Toast.LENGTH_SHORT).show();
                    }else{
                        pd.dismiss();
                        Toast.makeText(UserNotification.this, ""+mess, Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(UserNotification.this, "Some Exception", Toast.LENGTH_SHORT).show();
                    pd.dismiss();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(UserNotification.this, ""+error.getMessage(), Toast.LENGTH_SHORT).show();
                pd.dismiss();

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> map=new HashMap<>();
                map.put("seeker", String.valueOf(seeker));
                map.put("donor", String.valueOf(donor));
                map.put("request", String.valueOf(request));
                map.put("date",date);
                map.put("time",time);
                return map;
            }
        };
        requestQueue.add(stringRequest);
        }
    }
