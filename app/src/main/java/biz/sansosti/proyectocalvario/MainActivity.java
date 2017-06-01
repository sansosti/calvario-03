package biz.sansosti.proyectocalvario;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.AssetFileDescriptor;
import android.os.Build;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import static biz.sansosti.proyectocalvario.Constants.MAX_CHECKPOINTS_DISPLAY_LIST;
import static biz.sansosti.proyectocalvario.Constants.SHOW_DEBUG_INFO;

public class MainActivity extends AppCompatActivity {

    public static TextView tv_user_shown_status;

    public static TextView tv_latitude;
    public static TextView tv_longitude;
    public static TextView tv_status;
    public static TextView tv_current_checkpoint;
    public static TextView tv_closer_checkpoint;
    public static TextView[] mCheckPoints;

    public static AssetFileDescriptor afdDefaultBackground;
    public static AssetFileDescriptor afdFondoInicio;
    public static AssetFileDescriptor afdFondoConAphex;

    public static AssetFileDescriptor afdPuesto01;
    public static AssetFileDescriptor afdPuesto02;
    public static AssetFileDescriptor afdPuesto03;
    public static AssetFileDescriptor afdPuesto04;
    public static AssetFileDescriptor afdPuesto05;
    public static AssetFileDescriptor afdPuesto06;
    public static AssetFileDescriptor afdPuesto07;
    public static AssetFileDescriptor afdPuesto08;
    public static AssetFileDescriptor afdPuesto09;
    public static AssetFileDescriptor afdPuesto10;
    public static AssetFileDescriptor afdPuesto11;
    public static AssetFileDescriptor afdPuesto12;
    public static AssetFileDescriptor afdPuesto13;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        inicializarAFDs();

        inicializarStaticViews();

        pedirPermisos(this);
    }

    private void inicializarStaticViews() {
        tv_user_shown_status = (TextView) findViewById(R.id.tv_user_shown_status);

        if (SHOW_DEBUG_INFO) {
            tv_latitude = (TextView) findViewById(R.id.tv_latitude);
            tv_longitude = (TextView) findViewById(R.id.tv_longitude);
            tv_status = (TextView) findViewById(R.id.tv_status);
            tv_current_checkpoint = (TextView) findViewById(R.id.tv_current_checkpoint);
            tv_closer_checkpoint = (TextView) findViewById(R.id.tv_closer_checkpoint);

            mCheckPoints = new TextView[MAX_CHECKPOINTS_DISPLAY_LIST];

            for (int i = 0; i < MAX_CHECKPOINTS_DISPLAY_LIST; i++) {
                mCheckPoints[i] = new TextView(this);
                mCheckPoints[i].setId(i);
                mCheckPoints[i].setText(String.valueOf(i));
                int checkPointStyle = R.style.TextAppearance_AppCompat_Inverse;
                if (Build.VERSION.SDK_INT >= 23) {
                    mCheckPoints[i].setTextAppearance(checkPointStyle);
                } else {
                    mCheckPoints[i].setTextAppearance(this, checkPointStyle);
                }

                TableRow parentRow = new TableRow(this);
                TableRow.LayoutParams lp = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT);
                parentRow.setLayoutParams(lp);
                parentRow.addView(mCheckPoints[i]);

                TableLayout parentTable = (TableLayout) findViewById(R.id.tl_debug);
                parentTable.addView(parentRow, i);
            }
        }
    }

    private void inicializarAFDs() {
        afdFondoInicio = getResources().openRawResourceFd(R.raw.fondo_inicio);
        afdFondoConAphex = getResources().openRawResourceFd(R.raw.fondo_con_aphex);

        afdPuesto01 = getResources().openRawResourceFd(R.raw.puesto01);
        afdPuesto02 = getResources().openRawResourceFd(R.raw.puesto02);
        afdPuesto03 = getResources().openRawResourceFd(R.raw.puesto03);
        afdPuesto04 = getResources().openRawResourceFd(R.raw.puesto04);
        afdPuesto05 = getResources().openRawResourceFd(R.raw.puesto05);
        afdPuesto06 = getResources().openRawResourceFd(R.raw.puesto06);
        afdPuesto07 = getResources().openRawResourceFd(R.raw.puesto07);
        afdPuesto08 = getResources().openRawResourceFd(R.raw.puesto08);
        afdPuesto09 = getResources().openRawResourceFd(R.raw.puesto09);
        afdPuesto10 = getResources().openRawResourceFd(R.raw.puesto10);
        afdPuesto11 = getResources().openRawResourceFd(R.raw.puesto11);
        afdPuesto12 = getResources().openRawResourceFd(R.raw.puesto12);
        afdPuesto13 = getResources().openRawResourceFd(R.raw.puesto13);

        afdDefaultBackground = afdFondoInicio;

        final TableLayout tabla = (TableLayout) findViewById(R.id.main_debug_table);
        tabla.setVisibility(View.INVISIBLE);
        if (SHOW_DEBUG_INFO) {
            tabla.setVisibility(View.VISIBLE);
            View.OnLongClickListener listener = new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    switch (tabla.getVisibility()) {
                        case View.VISIBLE:
                            tabla.setVisibility(View.INVISIBLE);
                            break;
                        case View.INVISIBLE:
                            tabla.setVisibility(View.VISIBLE);
                            break;
                    }
                    return false;
                }
            };

            tabla.setOnLongClickListener(listener);
            findViewById(R.id.main_background).setOnLongClickListener(listener);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
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

    private Boolean exit = false;
    @Override
    public void onBackPressed() {
        if (exit) {
            Intent i = new Intent(getApplicationContext(), GPS_Service.class);
            stopService(i);
            finish();
            android.os.Process.killProcess(android.os.Process.myPid());
        } else {
            Toast.makeText(this, R.string.close_confirmation,
                    Toast.LENGTH_SHORT).show();
            exit = true;
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    exit = false;
                }
            }, 3 * 1000);
        }
    }


}
