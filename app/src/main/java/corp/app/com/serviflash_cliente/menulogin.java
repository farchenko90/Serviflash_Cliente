package corp.app.com.serviflash_cliente;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import org.json.JSONException;
import org.json.JSONObject;

import corp.app.com.serviflash_cliente.Modelos.Cliente;
import corp.app.com.serviflash_cliente.Notificaciones.initNotificacion;
import corp.app.com.serviflash_cliente.Services.WebServices;
import corp.app.com.serviflash_cliente.Util.General;

public class menulogin extends AppCompatActivity {

    EditText txtEmail,txtPass;
    General gn;
    LoginButton loginButton;
    CallbackManager callbackManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        /*
        * Facebook
        */
        FacebookSdk.sdkInitialize(getApplicationContext());
        callbackManager = CallbackManager.Factory.create();

        setContentView(R.layout.activity_menulogin);
        initComponent();
        ///
        gn = new General(this);
        new initNotificacion(this).initPush();
        Cliente c = gn.cargarCliente();
        if(c.getEmail() != null){
            new initNotificacion(this).initPush();
            Intent i = new Intent(menulogin.this,Menuinicial.class);
            startActivity(i);
            finish();
        }


        loginButton.setReadPermissions("id");
        loginButton.setReadPermissions("first_name");
        loginButton.setReadPermissions("last_name");
        loginButton.setReadPermissions("email");
        //loginButton.setReadPermissions(Arrays.asList("first_name, last_name, email,gender, birthday"));
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                GraphRequest request = GraphRequest.newMeRequest(
                        loginResult.getAccessToken(),
                        new GraphRequest.GraphJSONObjectCallback() {
                            @Override
                            public void onCompleted(JSONObject object, GraphResponse response) {
                                System.out.println("LoginActivity Response " + response.toString());
                                sesionfacebook(object);
                            }
                        });
                Bundle parameters = new Bundle();
                parameters.putString("fields", "email,first_name,last_name,id"); // Parámetros que pedimos a facebook
                request.setParameters(parameters);
                request.executeAsync();
            }

            @Override
            public void onCancel() {
                System.out.println("Cancelado");
            }

            @Override
            public void onError(FacebookException e) {
                System.out.println("Error: " + e.getMessage());
            }
        });


    }

    private void sesionfacebook(final JSONObject object){
        gn.initCargando("Iniciando...");
        new Thread(new Runnable() {
            @Override
            public void run() {
                final WebServices ws = new WebServices();
                final Cliente c = new Cliente();
                JSONObject j1 = null;
                try{
                    c.setIdface(object.getString("id"));
                    c.setNombreape(object.getString("first_name")+" "+object.getString("last_name"));
                    c.setEmail(object.getString("email"));
                    j1 = ws.loginfacebook(c);
                }catch (JSONException ex){}
                final JSONObject j = j1;
                gn.finishCargando();
                try{
                    if(j == null){
                        Toast.makeText(menulogin.this,"Error: " + ws.mensaje, Toast.LENGTH_SHORT).show();
                    }else{
                        if(j.getInt("message") == 1){
                            JSONObject temp = j.getJSONObject("admin");
                            Toast.makeText(menulogin.this,"Bienvenido "+temp.getString("nombreape"),Toast.LENGTH_SHORT).show();
                            Cliente cl = new Cliente();
                            cl.setIdface(object.getString("id"));
                            gn.guardarCliente(cl,true);
                            Intent i = new Intent(menulogin.this,Menuinicial.class);
                            startActivity(i);
                            finish();
                        }
                    }
                }catch (Exception ex){}
            }
        }).start();
    }

    private void initComponent(){
        txtEmail = (EditText) findViewById(R.id.email);
        txtPass = (EditText) findViewById(R.id.password);
        loginButton = (LoginButton)findViewById(R.id.login_button);
    }

    public void iniciar(View view){
        final Cliente c = new Cliente();
        c.setEmail(txtEmail.getText().toString());
        c.setPass(txtPass.getText().toString());
        gn.initCargando("Iniciando sesión...");
        new Thread(new Runnable() {
            @Override
            public void run() {
                WebServices cs = new WebServices();
                final JSONObject j = cs.login(c);
                gn.finishCargando();
                System.out.println(j);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            if (j == null) {
                                Toast.makeText(menulogin.this, "Error en el servidor", Toast.LENGTH_SHORT).show();
                            } else{
                               if(j.getInt("message") == 3){

                                   JSONObject temp = j.getJSONObject("admin");
                                   Toast.makeText(menulogin.this,"Bienvenido "+temp.getString("nombreape"),Toast.LENGTH_SHORT).show();
                                   Cliente c = new Cliente();
                                   c.setId(temp.getInt("id"));
                                   c.setEmail(temp.getString("email"));
                                   c.setNombreape(temp.getString("nombreape"));
                                   c.setPass(txtPass.getText().toString());
                                   gn.guardarCliente(c,false);
                                   Intent i = new Intent(menulogin.this,Menuinicial.class);
                                   startActivity(i);
                                   finish();
                               }if(j.getInt("message") == 2){
                                   Toast.makeText(menulogin.this, "Clave Incorrecta", Toast.LENGTH_SHORT).show();
                               }if(j.getInt("message") == 1){
                                   Toast.makeText(menulogin.this, "Usuario no existe en el Sistema", Toast.LENGTH_SHORT).show();
                               }
                            }
                        }catch (Exception ex){
                            Toast.makeText(menulogin.this, "Error webSerice login, "+ex.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });

            }
        }).start();

    }

}
