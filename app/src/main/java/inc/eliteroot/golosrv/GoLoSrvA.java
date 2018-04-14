package inc.eliteroot.golosrv;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBarActivity;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.location.LocationServices;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import android.location.Location;

public class GoLoSrvA extends AppCompatActivity implements ConnectionCallbacks {

    private GoogleApiClient mGoogleApiClient;
    protected TextView mLatitudeText;
    protected TextView mLongitudeText;
    private TextView scrn0;
    private TextView scrn1;
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_go_lo_srv);

        scrn0 = (TextView) findViewById(R.id.scrn0);
        scrn0.setText("  unknown 0   ");

        scrn1 = (TextView) findViewById(R.id.scrn0);
        scrn1.setText("  unknown 1  ");

        if (mGoogleApiClient == null) {
            mGoogleApiClient = new GoogleApiClient
                    .Builder(this)
                    .addConnectionCallbacks(this)

                    .addApi(LocationServices.API)
                    .build();
        }
    }

    @Override
    protected void onStart() {
        mGoogleApiClient.connect();
        super.onStart();
        client.connect();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW,
                "GoLoSrvA Page",
                Uri.parse("http://host/path"),
                Uri.parse("android-app://inc.eliteroot.golosrv/http/host/path")
        );
        AppIndex.AppIndexApi.start(client, viewAction);
    }

    @Override
    protected void onStop() {
        mGoogleApiClient.disconnect();
        super.onStop();

        Action viewAction = Action.newAction(
                Action.TYPE_VIEW,
                "GoLoSrvA Page",
                Uri.parse("http://host/path"),
                Uri.parse("android-app://inc.eliteroot.golosrv/http/host/path")
        );
        AppIndex.AppIndexApi.end(client, viewAction);
        client.disconnect();
    }

    Location mLastLocation;

    @Override
    public void onConnected(@Nullable Bundle bundle) {

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    public abstract class MainActivity
            extends ActionBarActivity
            implements GoogleApiClient.ConnectionCallbacks,
            GoogleApiClient.OnConnectionFailedListener {

        @Override
        public void onConnected(Bundle connectionHint) {
            mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
            if (mLastLocation != null) {
                scrn0.setText(String.valueOf(mLastLocation.getLatitude()));
                scrn1.setText(String.valueOf(mLastLocation.getLongitude()));
            }
        }
    }

}
