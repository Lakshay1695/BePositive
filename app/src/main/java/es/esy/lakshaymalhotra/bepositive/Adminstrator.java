package es.esy.lakshaymalhotra.bepositive;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

import java.util.ArrayList;

public class Adminstrator extends AppCompatActivity implements AdapterView.OnItemLongClickListener {
    ListView listView;
    UserBean userBean;
    ArrayList<UserBean> arrayList;
    UserAdapter userAdapter;
    RequestQueue requestQueue;
    ProgressDialog pd;
    void views(){
        listView=(ListView)findViewById(R.id.list);
        userBean =new UserBean();
        arrayList=new ArrayList<>();
        requestQueue= Volley.newRequestQueue(this);
        pd=new ProgressDialog(this);
        pd.setMessage("Reteriving......");
        pd.setCancelable(false);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adminstrator);
        views();
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
        userBean=arrayList.get(position);
        AlertDialog.Builder ab=new AlertDialog.Builder(this);
        ab.setCancelable(true);
        String[] items={"View","Send Notification","Delete"};
        ab.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which){
                    case 0:
                        showUser();
                        break;
                    case 1:
                            deleteUser();
                        break;

                    case 2:
                        deleteUser();
                        break;
                }
            }
        });
        ab.create().show();
        return false;
    }

    void showUser(){
        AlertDialog.Builder ab=new AlertDialog.Builder(this);
        ab.setCancelable(false);
        ab.setTitle("Detials of "+userBean.getName());
        ab.setMessage(userBean.toString());
        ab.setPositiveButton("Ok",null);
        ab.create().show();
    }

    void deleteUser(){
        Intent intent=new Intent(Adminstrator.this,Notification.class);
        startActivity(intent);
    }
}

