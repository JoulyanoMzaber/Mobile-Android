package comp208.mzaber;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    int col = 3;
    int row = 4;
    int ctr = 0;
    Card previous = null;
    ImageView previous2 = null;
    Card[][] Card =  new Card[row][col];
    TableLayout board;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        board = (TableLayout)findViewById(R.id.cardBoard);
        setTags();
    }

    public void changeImage(View view)
    {
        ImageView img = (ImageView) view;
        Card current = (Card)img.getTag();

        if(current.resourceId == R.drawable.backface && ctr % 2 == 0)
        {
            int[] images = new int[]{R.drawable.orange, R.drawable.banana, R.drawable.plank,
                    R.drawable.rock, R.drawable.strawberry, R.drawable.tree};
            Random random = new Random();
            int randomIndex = random.nextInt(images.length);

            img.setImageResource(images[randomIndex]);
            current.setResourceId(images[randomIndex]);
            previous = current;
            previous2 = img;
            ctr++;
        }
        else if (previous.resourceId != current.resourceId)
        {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            img.setImageResource(R.drawable.backface);
            current.setResourceId(R.drawable.backface);

            previous.setResourceId(R.drawable.backface);
            previous2.setImageResource(R.drawable.backface);
            ctr++;
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
}