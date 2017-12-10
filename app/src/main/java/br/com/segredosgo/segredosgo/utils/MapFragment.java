package br.com.segredosgo.segredosgo.utils;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.util.AttributeSet;
import android.util.Log;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;

import br.com.segredosgo.segredosgo.R;
import br.com.segredosgo.segredosgo.models.Segredo;
import br.com.segredosgo.segredosgo.models.SegredoDAO;


public class MapFragment extends Fragment implements OnMapReadyCallback, LocationListener {

    private GoogleMap mMap;
    private View view;
    MapView mMapView;
    private LatLng latLng;
    private List<Segredo> segredos;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        try {
            view = inflater.inflate(R.layout.fragment_map, container, false);
            MapsInitializer.initialize(this.getActivity());
            mMapView = (MapView) view.findViewById(R.id.map_view);
            mMapView.onCreate(savedInstanceState);
            mMapView.getMapAsync(this);
        } catch (InflateException e) {
            Log.e("Mapa", "Inflate exception");
        }

        SegredoDAO dao = new SegredoDAO(getContext());
        segredos = dao.buscaSegredos();
        dao.close();

        return view;
    }

    @SuppressLint("MissingPermission")
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        mMap = googleMap;
        googleMap.setMyLocationEnabled(true);

        if (latLng!=null)
            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15));

        if (!segredos.isEmpty()) {
            Log.v("Log","n vazio");
            for (int i = 0; i < segredos.size(); i++) {
                Log.v("Log","id= "+segredos.get(i).getId());
                Log.v("Log",""+segredos.get(i).getLongitude());
                Log.v("Log",""+segredos.get(i).getLatitude());
                LatLng latLng = new LatLng(segredos.get(i).getLatitude(), segredos.get(i).getLongitude());
                googleMap.addMarker(new MarkerOptions().position(latLng).title(segredos.get(i).getTitulo()));
            }
        } else{
            Log.v("Log","vazio");
        }

        mMapView.onResume();
    }

    @Override
    public void onLocationChanged(Location location) {
        latLng = new LatLng(location.getLatitude(), location.getLongitude());
        Log.v("Log", "changed");
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        Log.v("Log", "statuschanged");
    }

    @Override
    public void onProviderEnabled(String provider) {
        Log.v("Log", "enable");
    }

    @Override
    public void onProviderDisabled(String provider) {
        Log.v("Log", "disable");
    }
}
