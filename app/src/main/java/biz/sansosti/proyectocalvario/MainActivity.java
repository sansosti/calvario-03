package biz.sansosti.proyectocalvario;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    public static TextView tv_main_text;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        pedirPermisos(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        tv_main_text = (TextView) findViewById(R.id.tv_main_text);
    }

    public void iniciarServicioGPS() {
        Intent i = new Intent(getApplicationContext(), GPS_Service.class);
        startService(i);
    }

    public void pedirPermisos(AppCompatActivity context) {
        solicitarPermiso(context, Manifest.permission.ACCESS_FINE_LOCATION);
        solicitarPermiso(context, Manifest.permission.ACCESS_LOCATION_EXTRA_COMMANDS);
    }

    private void solicitarPermiso(AppCompatActivity context, String permiso) {
        if (ContextCompat.checkSelfPermission(context, permiso) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(context, permiso)) {
            } else {
                if(permiso.equals(Manifest.permission.ACCESS_FINE_LOCATION)) {
                    ActivityCompat.requestPermissions(context, new String[]{permiso}, 1000);
                } else  ActivityCompat.requestPermissions(context, new String[]{permiso}, 999);
            }
        } else {
            if(permiso.equals(Manifest.permission.ACCESS_FINE_LOCATION)) {
                iniciarServicioGPS();
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1000) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                iniciarServicioGPS();
            } else {
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Intent i = new Intent(getApplicationContext(), GPS_Service.class);
        stopService(i);
    }
}
