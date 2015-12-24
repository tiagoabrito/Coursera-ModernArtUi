package pt.tibrito.modernartui;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.SeekBar;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity {

    private static final String MOMA_WEB_PAGE = "http://www.moma.org/";
    int mc1,mc2,mc3,mc4,mc5;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.more_info,menu);
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        final TextView surface1 = (TextView) findViewById(R.id.block1);
        final TextView surface2 = (TextView) findViewById(R.id.block2);
        final TextView surface3 = (TextView) findViewById(R.id.block3);
        final TextView surface4 = (TextView) findViewById(R.id.block4);
        final TextView surface5 = (TextView) findViewById(R.id.block5);

        //TODO : add suport for color change opening a new window for color pick :)

        SeekBar slider = (SeekBar) findViewById(R.id.seekBar);

        mc1 = Color.MAGENTA;
        mc2 = Color.CYAN;
        mc3 = Color.YELLOW;
        mc4 = Color.GRAY;
        mc5 = Color.GREEN;

        surface1.setBackgroundColor(mc1);
        surface2.setBackgroundColor(mc2);
        surface3.setBackgroundColor(mc3);
        surface4.setBackgroundColor(mc4);
        surface5.setBackgroundColor(mc5);

        slider.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                surface1.setBackgroundColor(updateColor(mc1,progress));
                surface2.setBackgroundColor(updateColor(mc2,progress));
                surface3.setBackgroundColor(updateColor(mc3,progress));
                surface4.setBackgroundColor(updateColor(mc4,progress));
                surface5.setBackgroundColor(updateColor(mc5,progress));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });
    }

    int updateColor(int originalColor, int progress) {

        int red = Color.red(originalColor);
        int green = Color.green(originalColor);
        int blue = Color.blue(originalColor);

        if (red==green&&green==blue){
            // it is grey or white
            return originalColor;
        }

        float[] hsv = new float[3];
        Color.colorToHSV(originalColor, hsv);
        hsv[0] += progress * 2;
        hsv[0] %= 360;
        return Color.HSVToColor(hsv);
    }

    public void onMoreInfoClick(MenuItem item) {

        // Create a new AlertDialogFragment
        MoreInfoFragment mMoreInfo = MoreInfoFragment.newInstance();

        // Show AlertDialogFragment
        mMoreInfo.show(getFragmentManager(),"moreInfo");

    }

    public static class MoreInfoFragment extends DialogFragment {

        public static MoreInfoFragment newInstance() {
            return new MoreInfoFragment();
        }

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the Builder class for convenient dialog construction
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setMessage(R.string.inspired)

                    .setNegativeButton(R.string.notNow, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    // User cancelled the dialog
                                    dismiss();
                                }
                            }
                    )                    .setPositiveButton(R.string.visitMOMA, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    // go to MOMA website
                    Intent baseIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(MOMA_WEB_PAGE));
                    startActivity(baseIntent);
                }
            });
            // Create the AlertDialog object and return it
            return builder.create();
        }
    }
}

