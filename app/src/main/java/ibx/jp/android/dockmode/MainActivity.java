package ibx.jp.android.dockmode;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.os.Bundle;
import android.content.Intent;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.support.v7.app.AppCompatActivity;

import android.os.Process;

import processing.android.PFragment;
import processing.android.CompatUtils;
import processing.core.PApplet;

public class MainActivity extends AppCompatActivity {
    //スケッチのインスタンス変数
    private PApplet sketch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FrameLayout frame = new FrameLayout(this);
        frame.setId(CompatUtils.getUniqueViewId());
        setContentView(frame, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT));
        //ここでスケッチを生成する
        sketch = new Sketch();
        PFragment fragment = new PFragment(sketch);
        fragment.setView(frame, this);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        if (sketch != null) {
            sketch.onRequestPermissionsResult(
                    requestCode, permissions, grantResults);
        }
    }

    @Override
    public void onNewIntent(Intent intent) {
        if (sketch != null) {
            sketch.onNewIntent(intent);
        }
    }

    /**
     * 電源接続時にインテントを自動起動するクラス。
     *
     * 編集中。
     */
    public class PowerReceiver extends BroadcastReceiver {
        public void onReceive(Context context, Intent intent) {
            String TAG = "BroadcastReceiver";
            if (intent != null) {
                if ( Intent.ACTION_POWER_CONNECTED.equals(intent.getAction()) ) {
                    // 電源接続時に行う処理。
                    Log.d(TAG, "Power connected.");
                    Intent i = new Intent(context, MainActivity.class);
                    i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(i);

                } else if ( Intent.ACTION_POWER_DISCONNECTED.equals(intent.getAction()) ) {
                    // 電源切断時に行う処理。
                    Log.d(TAG, "Power disconnected.");
                    finish();
                }
            }
        }
    }
}