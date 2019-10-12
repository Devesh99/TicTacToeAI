package com.devesh.android.tictactoe;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private ImageView[][] buttons = new ImageView[3][3];
    //in array: 0 for empty 1 for AI,2 for Player
    private int[][] array = new int[3][3];
    private int roundCount = 0;
    private int playerpoints;
    private int aipoints;
    private TextView textViewPlayer;
    private TextView textViewai;
    private TextView textTurn;
    private int reset = 1;
    boolean playerFirst=true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textViewPlayer = findViewById(R.id.text_player);
        textViewai = findViewById(R.id.text_ai);
        textTurn = findViewById(R.id.text_turn);
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                String buttonID = "button_" + i + j;
                int resID = getResources().getIdentifier(buttonID, "id", getPackageName());
                buttons[i][j] = findViewById(resID);
                buttons[i][j].setOnClickListener(this);
            }
        }
        Button buttonNew = findViewById(R.id.button_New_Game);
        Button start=findViewById(R.id.button_start);
        buttonNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (int i = 0; i < 3; i++) {
                    for (int j = 0; j < 3; j++) {
                        buttons[i][j].animate().alpha(0).scaleY(0).scaleX(0).setDuration(1000);
                    }
                }
                textTurn.setText("Select X to play first");
  
                for (int i = 0; i < 3; i++)
                    for (int j = 0; j < 3; j++)
                        array[i][j] = 0;
                    roundCount=0;
            }
        });
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(roundCount!=0)
                    return;
                reset=0;
                if (!playerFirst)
                    play();

                else {
                        textTurn.setText("Your Turn");
                }

            }

    });
}

    @Override
    public void onClick(View v) {
        int i=-1,j=-1;

        if (reset != 0)
            return;
        roundCount++;
        switch (returnID(v)) {
            case 1: {
                i=0;
                j=0;
                break;
            }
            case 2: {
                i=0;
                j=1;
                break;
            }
            case 3: {
                i=0;
                j=2;
                break;
            }
            case 4: {
                i=1;
                j=0;
                break;
            }
            case 5: {
                i=1;
                j=1;
                break;
            }
            case 6: {
                i=1;
                j=2;
                break;
            }
            case 7: {
                i=2;
                j=0;
                break;
            }
            case 8: {
                i=2;
                j=1;
                break;
            }
            case 9: {
                i=2;
                j=2;
                break;
            }
        }
        if(array[i][j]!=0)
            return;
        array[i][j]=2;
        if(playerFirst){

            buttons[i][j].setImageResource(R.drawable.x);
            buttons[i][j].animate().alpha(1).scaleX(1).scaleY(1).setDuration(1000);
        }
        else{

            buttons[i][j].setImageResource(R.drawable.o);
            buttons[i][j].animate().alpha(1).scaleX(1).scaleY(1).setDuration(1000);
        }


        if (checkForWin()) {
            playerwins();
            reset = 1;
        } else if (!checkForWin() && roundCount == 9) {
            draw();
            reset = 1;
        } else
            play();
    }
    public void rbcross(View v)
    {
        playerFirst=true;
    }
    public void rbnaught(View v)
    {
        playerFirst=false;
    }
    private boolean checkForWin() {
        String[][] field = new String[3][3];
        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 3; j++) {
                field[i][j] = array[i][j]+"";
            }
        for (int i = 0; i < 3; i++)
            if (field[i][0].equals(field[i][1]) && field[i][0].equals(field[i][2]) && !field[i][0].equals("0"))
                return true;
        for (int i = 0; i < 3; i++)
            if (field[0][i].equals(field[1][i]) && field[0][i].equals(field[2][i]) && !field[0][i].equals("0"))
                return true;
        if (field[0][0].equals(field[1][1]) && field[0][0].equals(field[2][2]) && !field[0][0].equals("0"))
            return true;
        if (field[0][2].equals(field[1][1]) && field[0][2].equals(field[2][0]) && !field[0][2].equals("0"))
            return true;
        return false;
    }

    private void aiwins() {
        reset = 1;
        aipoints++;
        Toast.makeText(this, "AI Wins!", Toast.LENGTH_SHORT).show();
        updatePointsText();
    }

    private void playerwins() {
        reset = 1;
        playerpoints++;
        Toast.makeText(this, "Player Wins!", Toast.LENGTH_SHORT).show();
        updatePointsText();
    }

    private void draw() {
        reset = 1;
        Toast.makeText(this, "Draw!", Toast.LENGTH_SHORT).show();
    }

    private void updatePointsText() {
        textViewai.setText("AI: " + aipoints);
        textViewPlayer.setText("Player: " + playerpoints);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putInt("roundCount", roundCount);
        outState.putInt("playerpoints", playerpoints);
        outState.putInt("aipoints", aipoints);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        roundCount = savedInstanceState.getInt("roundcount");
        playerpoints = savedInstanceState.getInt("playerpoints");
        aipoints = savedInstanceState.getInt("aipoints");
    }

    private int returnID(View v) {
        if (v.getId() == R.id.button_00)
            return 1;
        else if (v.getId() == R.id.button_01)
            return 2;
        else if (v.getId() == R.id.button_02)
            return 3;
        else if (v.getId() == R.id.button_10)
            return 4;
        else if (v.getId() == R.id.button_11)
            return 5;
        else if (v.getId() == R.id.button_12)
            return 6;
        else if (v.getId() == R.id.button_20)
            return 7;
        else if (v.getId() == R.id.button_21)
            return 8;
        else
            return 9;
    }

    private void play() {
        if (reset != 0)
            return;
        textTurn.setText("Computer's Turn");
        int i, j;

        i = (int) (Math.random() * 3);
        j = (int) (Math.random() * 3);
        if(roundCount==0)
        {
        while ((i == 0 &&(j==0||j==2))|| (i == 1 && j == 1) || (i == 2 && (j==0||j == 2))) {
            if (array[i][j] == 0) {
                buttons[i][j].setImageResource(R.drawable.x);

                buttons[i][j].animate().alpha(1).scaleX(1).scaleY(1).setDuration(1000);
                array[i][j] = 1;
                roundCount++;
                textTurn.setText("Your Turn");
                return;
            }
            i = (int) (Math.random() * 3);
            j = (int) (Math.random() * 3);
        }
    }
        int board[][] = new int[3][3];
        for (i = 0; i < 3; i++)
            for (j = 0; j < 3; j++)
                board[i][j] = array[i][j];
        findBestMove(board);
        if (checkForWin()) {
            aiwins();
            reset = 1;
        }
        if (!checkForWin() && roundCount == 9) {
            draw();
            reset = 1;
        }
        textTurn.setText("Your Turn");
    }

    private void findBestMove(int[][] board) {
        int bestVal = -999;
        int bestI = -1;
        int bestJ = -1;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[i][j] == 0) {
                    board[i][j] = 1;
                    int moveVal = minimax(board, 0, false);
                    board[i][j] = 0;
                    if (moveVal >= bestVal) {
                        bestI = i;
                        bestJ = j;
                        bestVal = moveVal;
                    }
                }
            }
        }
        array[bestI][bestJ] = 1;
        if(playerFirst) {
            buttons[bestI][bestJ].setImageResource(R.drawable.o);
            buttons[bestI][bestJ].animate().alpha(1).scaleX(1).scaleY(1).setDuration(1000);
        }
        else
        {
            buttons[bestI][bestJ].setImageResource(R.drawable.x);
            buttons[bestI][bestJ].animate().alpha(1).scaleX(1).scaleY(1).setDuration(1000);
        }
        roundCount++;
    }

    int minimax(int[][] board, int depth, boolean isMax) {
        int score = evaluate(board);
        if (score == 10 || score == -10)
            return score;
        if (!isMovesLeft(board))
            return 0;
        if (isMax) {
            int best = -1000;
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    if (board[i][j] == 0) {
                        board[i][j] = 1;
                        best = max(best, minimax(board, depth + 1, !isMax));
                        board[i][j] = 0;
                    }
                }
            }
            return best;
        } else {
            int best = 1000;
            for (int i = 0; i < 3; i++)
                for (int j = 0; j < 3; j++) {
                    if (board[i][j] == 0) {
                        board[i][j] = 2;
                        best = min(best, minimax(board, depth + 1, !isMax));
                        board[i][j] = 0;
                    }
                }

            return best;
        }
    }

    int evaluate(int[][] b) {
        for (int row = 0; row < 3; row++) {
            if (b[row][0] == b[row][1] && b[row][1] == b[row][2]) {
                if (b[row][0] == 1)
                    return 10;
                else if (b[row][0] == 2)
                    return -10;
            }
        }
        for (int columns = 0; columns < 3; columns++) {
            if (b[0][columns] == b[1][columns] && b[1][columns] == b[2][columns]) {
                if (b[0][columns] == 1)
                    return 10;
                else if (b[0][columns] == 2)
                    return -10;
            }
        }
        if (b[0][0] == b[1][1] && b[1][1] == b[2][2]) {
            if (b[0][0] == 1)
                return 10;
            else if (b[0][0] == 2)
                return -10;
        }
        if (b[2][0] == b[1][1] && b[1][1] == b[0][2]) {
            if (b[2][0] == 1)
                return 10;
            else if (b[2][0] == 2)
                return -10;
        }
        return 0;
    }

    boolean isMovesLeft(int[][] board) {
        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 3; j++) {
                if (board[i][j] == 0)
                    return true;
            }
        return false;
    }

    private int max(int a, int b) {
        if (a >=b)
            return a;
        return b;
    }

    private int min(int a, int b) {
        if (a > b)
            return b;
        return a;
    }
}