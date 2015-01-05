package ca.scottmilliquet.coolcache;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.FileNotFoundException;


public class MainActivity extends ActionBarActivity implements View.OnClickListener{

    BitmapCache imageCache;

    private static final String TAG = "coolcache.MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //create the cache
        imageCache = new BitmapCache();

        setContentView(R.layout.activity_main);

        final Button button = (Button) findViewById(R.id.button);
        button.setOnClickListener(this);

        final Button button2 = (Button) findViewById(R.id.button2);
        button2.setOnClickListener(this);

        final Button button3 = (Button) findViewById(R.id.button3);
        button3.setOnClickListener(this);

        final Button swapButton = (Button) findViewById(R.id.swapButton);
        swapButton.setOnClickListener(this);

        final Button flushCacheButton = (Button) findViewById(R.id.flushCacheButton);
        flushCacheButton.setOnClickListener(this);

        final Button printCacheButton = (Button) findViewById(R.id.printCacheButton);
        printCacheButton.setOnClickListener(this);

    }

    public void onClick(View v){
        switch(v.getId()){
            case R.id.button:
                setImageToImageViewFromTextViewValue(R.id.editText, R.id.imageView);
                break;
            case R.id.button2:
                setImageToImageViewFromTextViewValue(R.id.editText2, R.id.imageView2);
                break;
            case R.id.button3:
                setImageToImageViewFromTextViewValue(R.id.editText3, R.id.imageView3);
                break;
            case R.id.swapButton:
                performSwap1And2FromCache();
                break;
            case R.id.flushCacheButton:
                imageCache.flush();
                break;
            case R.id.printCacheButton:
                imageCache.printCacheState();
                break;

        }
    }

    //toggles images between image view 1 and 2
    private void performSwap1And2FromCache() {

        TextView tv = (TextView)findViewById(R.id.editText);
        TextView tv2 = (TextView)findViewById(R.id.editText2);
        ImageView iv = (ImageView)findViewById(R.id.imageView);
        ImageView iv2 = (ImageView)findViewById(R.id.imageView2);
        if ( tv == null || tv2 == null) {
            Log.d(TAG, "Widget not found");
            return;
        }
        String path1 = tv.getText().toString();
        Bitmap b = null;
        try {
            b = imageCache.getBitmap(path1);
        } catch ( FileNotFoundException fnfe ) {
            Log.d(TAG, "file not found: " + fnfe.getMessage());
        } catch ( IllegalArgumentException iae ) {
            Log.d(TAG, "IAE: " + iae.getMessage());
        } catch (OutOfMemoryError oome){
            Log.d(TAG, "OOME " + oome.getMessage());
        }

        String path2 = tv2.getText().toString();
        Bitmap b2 = null;
        try {
            b2 = imageCache.getBitmap(path2);
        } catch ( FileNotFoundException fnfe ) {
            Log.d(TAG, "file not found: " + fnfe.getMessage());
        } catch ( IllegalArgumentException iae ) {
            Log.d(TAG, "IAE: " + iae.getMessage());
        } catch (OutOfMemoryError oome){
            Log.d(TAG, "OOME " + oome.getMessage());
        }

        if ( b != null && b2 != null) {
            iv.setImageBitmap(b2);
            iv2.setImageBitmap(b);
            tv.setText(path2);
            tv2.setText(path1);
        }

    }

    //helper function
    private void setImageToImageViewFromTextViewValue( int textViewId, int imageViewId) {
        TextView tv = (TextView)findViewById(textViewId);
        if( tv == null ) {
            Log.d(TAG, "Text view not found");
            return;
        }
        Bitmap b = null;
        try {
            b = imageCache.getBitmap(tv.getText().toString());
        } catch ( FileNotFoundException fnfe ) {
            Log.d(TAG, "file not found: " + fnfe.getMessage());
        } catch ( IllegalArgumentException iae ) {
            Log.d(TAG, "IAE: " + iae.getMessage());
        } catch (OutOfMemoryError oome){
            Log.d(TAG, "OOME " + oome.getMessage());
        }
        if ( b != null ){
            ImageView iv = (ImageView)findViewById(imageViewId);
            if (iv == null) {
                Log.d(TAG, "Image view not found");
                return;
            }

            iv.setImageBitmap(b);

        }
    }
}
