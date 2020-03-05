package com.example.cadastroapp;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class BasicActivity extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    private LocationManager locationManager;
    private LocationListener locationListener;
    EditText editTextLocalizacao;
    Button buttonEnviarLocalizacao;
    TextView textViewStatus;

    LatLng coordenadas;
    Double latitude, longitude;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_basic);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        editTextLocalizacao = findViewById(R.id.editTextLocalizacao);
        buttonEnviarLocalizacao = findViewById(R.id.buttonLocalizacao);
        textViewStatus = findViewById(R.id.textViewRequisicoesStatus);

        textViewStatus.setVisibility(View.GONE);

        buttonEnviarLocalizacao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                salvaLocalizacaoUsuario();
            }
        });
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        recuperaLocalizacaoUsuario();

        /*Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(-34,151);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sidney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));*/
    }

    private void recuperaLocalizacaoUsuario(){

        locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                latitude = location.getLatitude();
                longitude = location.getLongitude();
                coordenadas = new LatLng(latitude, longitude);
                String posicao = "Lat: " + String.valueOf(latitude) + " - Long: " + String.valueOf(longitude);
                editTextLocalizacao.setText(posicao);
                mMap.clear();
                mMap.addMarker(
                        new MarkerOptions()
                            .position(coordenadas)
                            .title("Minha Localização")
                            .icon(BitmapDescriptorFactory.fromResource(R.drawable.man_sick))
                );
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(coordenadas,18));
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {

            }
        };
        if(ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)== PackageManager.PERMISSION_GRANTED){
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 10000,10,locationListener);
        }
    }

    private void salvaLocalizacaoUsuario(){
        try{
            LocalizacaoDoUsuario localizacaoDoUsuario= new LocalizacaoDoUsuario();
            localizacaoDoUsuario.setLatitude(String.valueOf(coordenadas.latitude));
            localizacaoDoUsuario.setLongitude(String.valueOf(coordenadas.longitude));
            localizacaoDoUsuario.setUsuario("Renan");
            localizacaoDoUsuario.setStatus(StatusRequisicoes.STATUS_AGUARDANDO);
            localizacaoDoUsuario.salvaLocalizacao();

            buttonEnviarLocalizacao.setVisibility(View.GONE);
            editTextLocalizacao.setVisibility(View.GONE);
            textViewStatus.setSystemUiVisibility(View.VISIBLE);



        }catch (Exception ex){
            ex.printStackTrace();
            String mens = "Aguarde o carregamento da localização GPS";
            Toast.makeText(this, mens, Toast.LENGTH_SHORT).show();

        }
    }

}


/*@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_basic);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }
*/