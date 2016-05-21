package corp.app.com.serviflash_cliente.Util;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;

import corp.app.com.serviflash_cliente.Modelos.Cliente;


/**
 * Created by giocni on 5/12/15.
 */
public class General {

    private Activity context;
    private ProgressDialog dialogCargando;

    public General(Activity context){
        this.context = context;
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
        dialogCargando = new ProgressDialog(context);
        dialogCargando.setMessage(mensaje);
        dialogCargando.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        dialogCargando.setCancelable(true);
        dialogCargando.show();
    }

    public void finishCargando(){
        dialogCargando.dismiss();
    }

    public void guardarCliente(String email,String nombres,String pass){
        SharedPreferences prefs = context.getSharedPreferences("serviflash", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("email", email);
        editor.putString("nombreape", nombres);
        editor.putString("pass", pass);
        editor.commit();
    }

    public Cliente cargarCliente(){
        SharedPreferences prefs = context.getSharedPreferences("serviflash", Context.MODE_PRIVATE);
        Cliente c = new Cliente();
        c.setEmail(prefs.getString("email", null));
        c.setNombreape(prefs.getString("nombreape", null));
        c.setPass(prefs.getString("pass", null));
        return c;
    }





}
