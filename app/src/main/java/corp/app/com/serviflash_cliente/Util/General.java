package corp.app.com.serviflash_cliente.Util;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.view.View;
import android.widget.TextView;

import corp.app.com.serviflash_cliente.Modelos.Cliente;
import corp.app.com.serviflash_cliente.R;


/**
 * Created by giocni on 5/12/15.
 */
public class General {

    public static Context contexto;
    private Activity context;
    private ProgressDialog dialogCargando;

    public General(Activity context){
        this.contexto = context;
    }

    public General(Context context,View v){
        this.contexto = context;
        if(v != null){
            Cliente cl = cargarCliente();
            ((TextView)v.findViewById(R.id.username)).setText(cl.getNombreape());
            ((TextView)v.findViewById(R.id.email)).setText(cl.getEmail());
        }
    }

    public void mostrarDialog(String titulo,String mensaje,boolean cancelable){
        AlertDialog.Builder b = new AlertDialog.Builder(context);
        b.setTitle(titulo);
        b.setMessage(mensaje);
        b.setCancelable(cancelable);
        b.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        AlertDialog al = b.create();
        al.show();
    }

    public void initCargando(String mensaje){
        dialogCargando = new ProgressDialog(contexto);
        dialogCargando.setMessage(mensaje);
        dialogCargando.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        dialogCargando.setCancelable(true);
        dialogCargando.show();
    }

    public void finishCargando(){
        dialogCargando.dismiss();
    }

    public void guardarCliente(Cliente c,boolean face){
        SharedPreferences prefs = contexto.getSharedPreferences("serviflash", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt("id", c.getId());
        editor.putString("email", c.getEmail());
        editor.putString("nombreape", c.getNombreape());
        editor.putString("pass", c.getPass());
        editor.putString("idface",c.getIdface());
        editor.putBoolean("face",face);
        editor.commit();
    }

    public int getIdCliente(){
        SharedPreferences prefs = contexto.getSharedPreferences("serviflash", Context.MODE_PRIVATE);
        return prefs.getInt("id", 0);
    }

    public Cliente cargarCliente(){
        SharedPreferences prefs = contexto.getSharedPreferences("serviflash", Context.MODE_PRIVATE);
        Cliente c = new Cliente();
        c.setId(prefs.getInt("id",0));
        c.setEmail(prefs.getString("email", null));
        c.setNombreape(prefs.getString("nombreape", null));
        c.setPass(prefs.getString("pass", null));
        return c;
    }


    public void itemmenu(int id){
        if(id == R.id.nav_inicio ){

        }if(id == R.id.nav_pedido){

        }if(id == R.id.configuration_section){
            if(id == R.id.nav_perfil){

            }if(id == R.id.nav_log_out){

            }
        }
    }


}
