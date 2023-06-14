package mx.hugo4295.ponenciatescha;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.res.AssetManager;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;

public class PrincipalActivity extends AppCompatActivity {
    JSONObject encontrado = new JSONObject();
    ConstraintLayout principal;
    EditText entrada;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);

        principal = findViewById(R.id.principal);
        principal.setBackgroundColor(Color.parseColor("#11FF22"));
        entrada = findViewById(R.id.txtentrada);

        encontrado=null;
        verificaUsr();
        cambiarfondo();
    }

    private void verificaUsr() {
        InputStream flujo;
        byte[] buffer;
        AssetManager assetManager = this.getAssets();
        int size = 0;
        try {
            flujo = assetManager.open("configuracion/personaliza.json");
            size = flujo.available();
            buffer = new byte[size];
            flujo.read(buffer);
            String JsonCadena = new String(buffer, "UTF-8");
            JSONObject jsonObject;
            jsonObject = new JSONObject(JsonCadena);
            JSONArray jsonArray = jsonObject.getJSONArray("usuario");
            for (int i=0;i<jsonArray.length();i++){
                JSONObject dato = jsonArray.getJSONObject(i);
                if (dato.getString("nombre").equals("Fernando")){
                    encontrado = dato;
                    break;
                }
            }
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
    }

    private void cambiarfondo(){
        if (encontrado != null) {
            try {
                String fondo = encontrado.getString("fondo");
                principal.setBackgroundColor(Color.parseColor(fondo));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}