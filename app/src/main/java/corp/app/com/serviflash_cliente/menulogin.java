package corp.app.com.serviflash_cliente;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONObject;

import corp.app.com.serviflash_cliente.Modelos.Cliente;
import corp.app.com.serviflash_cliente.Services.WebServices;
import corp.app.com.serviflash_cliente.Util.General;

public class menulogin extends AppCompatActivity {

    EditText txtEmail,txtPass;
    General gn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menulogin);
        initComponent();
        gn = new General(this);

        Cliente c = gn.cargarCliente();
        if(c.getEmail() != null){
            Intent i = new Intent(menulogin.this,Menuinicial.class);
            startActivity(i);
            finish();
        }

    }

    private void initComponent(){
        txtEmail = (EditText) findViewById(R.id.email);
        txtPass = (EditText) findViewById(R.id.password);
    }

    public void iniciar(View view){
        final Cliente c = new Cliente();
        c.setEmail(txtEmail.getText().toString());
        c.setPass(txtPass.getText().toString());
        new Thread(new Runnable() {
            @Override
            public void run() {
                WebServices cs = new WebServices();
                final JSONObject j = cs.login(c);
                System.out.println(j);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            if (j == null) {
                                Toast.makeText(menulogin.this, "Error en el servidor", Toast.LENGTH_SHORT).show();
                            } else{
                               if(j.getInt("message") == 3){
                                   gn.initCargando("Iniciando sesión...");
                                   JSONObject temp = j.getJSONObject("admin");
                                   Toast.makeText(menulogin.this,"Bienvenido "+temp.getString("nombreape"),Toast.LENGTH_SHORT).show();
                                   gn.guardarCliente(temp.getString("email"),temp.getString("nombreape"),
                                           txtPass.getText().toString());
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
