package comp208.mzaber;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    int winners = 0;
    int col = 3;
    int row = 4;
    int ctr = 0;
    int intguess = 0;
    Card previous = null;
    ImageView previous2 = null;
    Card[][] Card =  new Card[row][col];
    TableLayout board;
    int[] images = new int[]{R.drawable.orange, R.drawable.banana, R.drawable.plank,
            R.drawable.rock, R.drawable.strawberry, R.drawable.tree};
    int[] images2 = new int[12];
    int[][] imgArray = new int[row][col];

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        board = (TableLayout)findViewById(R.id.cardBoard);
        uploadImages2();
        setTags();
        shuffle(imgArray);
    }

    // mixes images and uploads them to images2 twice
    public void uploadImages2() {
        for (int i = 0; i < images.length; i++) {
            Random rand = new Random();
            int randomIndexToSwap = rand.nextInt(images.length);
            int temp = images[randomIndexToSwap];
            images[randomIndexToSwap] = images[i];
            images[i] = temp;
        }

        // duple array and move it to images2
        for (int i = 0; i < images.length; i++) {
            images2[i] = images[i];
        }
        for (int j = 0; j < images.length; j++) {
            images2[j+6] = images[j];
        }

        // convert to 2d array in imgArray
        int indexCtr = 0;
        for (int k = 0; k < row; k++) {
            for (int m = 0; m < col; m++) {
                imgArray[k][m] = images2[indexCtr];
                indexCtr++;
            }
        }
    }

    void shuffle(int[][] a) {
        Random random = new Random();

        for (int i = a.length - 1; i > 0; i--) {
            for (int j = a[i].length - 1; j > 0; j--) {
                int m = random.nextInt(i + 1);
                int n = random.nextInt(j + 1);

                int temp = a[i][j];
                a[i][j] = a[m][n];
                a[m][n] = temp;
            }
        }
    }

    public void changeImage(View view)
    {
        ImageView img = (ImageView) view;
        Card current = (Card)img.getTag();

        if (current.resourceId == R.drawable.backface && ctr % 2 == 0)
        {
            img.setImageResource(imgArray[current.col][current.row]);
            current.setResourceId(imgArray[current.col][current.row]);
            previous = current;
            previous2 = img;
            ctr++;
            intguess++;
        }
        else
        {
            img.setImageResource(imgArray[current.col][current.row]);
            ctr++;

            if (imgArray[previous.col][previous.row] == imgArray[current.col][current.row])
            {
                winners++;
                if (winners == 6)
                {
                    // move to next screen
                    setContentView(R.layout.activity_main1);

                    String guesses = Integer.toString(intguess);
                    TextView score = (TextView)findViewById(R.id.scr);
                    score.setText(guesses);
                }
            }
            else
            {
                current.setResourceId(R.drawable.backface);
                previous.setResourceId(R.drawable.backface);
                new android.os.Handler(Looper.getMainLooper()).postDelayed(
                        new Runnable() {
                            public void run() {
                                img.setImageResource(R.drawable.backface);
                                previous2.setImageResource(R.drawable.backface);
                            }
                        },
                        500);
            }
            intguess++;
        }
    }

    public void setTags()
    {
        for(int x = 0; x < row; x++)
        {
            TableRow tr =  (TableRow) board.getChildAt(x);
            for (int y =0; y < col; y++)
            {
                ImageView iv = (ImageView) tr.getChildAt(y);
                Card[x][y] = new Card(x,y);
                Card[x][y].setResourceId(R.drawable.backface);
                iv.setTag(Card[x][y]);
                iv.setImageResource(R.drawable.backface);
            }
        }
    }

    // close the app
    public void close(View view)
    {
        finish();
    }

    // restart the app
    public void restart(View view)
    {
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

}