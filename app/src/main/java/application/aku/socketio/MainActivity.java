package application.aku.socketio;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URISyntaxException;
import io.socket.client.IO;
import io.socket.client.Socket;

public class MainActivity extends AppCompatActivity {
    TextView tvtext;

    private Socket socket;
    {
        try {
            socket = IO.socket("https://domain.com");
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvtext = findViewById(R.id.tvtext);

        socket.on("event", args -> {
            JSONObject data = (JSONObject) args[0];
            Log.e("data", data.toString());
            runOnUiThread(() -> {
                try {
                    tvtext.setText("status:"+data.getBoolean("status")+"\ndata:"+data.toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            });
        });

        socket.connect();
    }
}
