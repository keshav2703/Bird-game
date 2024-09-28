package com.ssinfosys.fishgame;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;


public class FlyingFishView extends View
{

    private Bitmap fish[]=new Bitmap[2];
    private Bitmap backgroundImage;
    private Paint paint=new Paint();
    private Bitmap life[]=new Bitmap[2];
    private int fishX=10;
    private int fishY,fishSpeed;
    private int canvasWidth,canvasHeight;
    private  boolean touch = false;
    private int yellowX,yellowY,yellowSpeed=6;
    private Paint yellowPaint=new Paint();
    private int greenX,greenY,greenSpeed=8;
    private Paint greenPaint=new Paint();
    private int blackX,blackY,blackSpeed=12;
    private Paint blackPaint=new Paint();
    private int score,lifeCounter;

    public FlyingFishView(Context context)
    {
        super(context);
        fish[0]= BitmapFactory.decodeResource(getResources(), R.drawable.fish1);
        fish[1]= BitmapFactory.decodeResource(getResources(), R.drawable.fish2);

        backgroundImage=BitmapFactory.decodeResource(getResources(),R.drawable.background);


        yellowPaint.setColor(Color.YELLOW);
        yellowPaint.setAntiAlias(false);

        greenPaint.setColor(Color.GREEN);
        greenPaint.setAntiAlias(false);

        blackPaint.setColor(Color.BLACK);
        blackPaint.setAntiAlias(false);

        paint.setColor(Color.WHITE);
        paint.setTextSize(30);
        paint.setTypeface(Typeface.DEFAULT_BOLD);
        paint.setAntiAlias(true);

        life[0]=BitmapFactory.decodeResource(getResources(),R.drawable.hearts);
        life[1]=BitmapFactory.decodeResource(getResources(),R.drawable.heart_grey);

        fishY = 550;
        score = 0;
        lifeCounter = 3;

    }

    @Override
    protected void onDraw(Canvas canvas)
    {
        super.onDraw(canvas);
        canvas.drawBitmap(backgroundImage,0,0,null);
        canvasWidth=canvas.getWidth();
        canvasHeight=canvas.getHeight();

        /*Fish Method*/
        int minFishY=fish[0].getHeight();
        int maxFishY=canvasHeight - fish[0].getHeight() *2;
        fishY = fishY + fishSpeed;

        if(fishY<minFishY)
        {
            fishY=minFishY;
        }
        if(fishY>maxFishY)
        {
            fishY=maxFishY;
        }

        fishSpeed=fishSpeed + 3;

        if(touch)
        {
            canvas.drawBitmap(fish[1],fishX,fishY,null);
            touch=false;
        }else {
            canvas.drawBitmap(fish[0],fishX,fishY,null);
        }


        /*for Yellow Ball*/
        yellowX = yellowX - yellowSpeed;

        if(hitBallChecker(yellowX,yellowY))
        {
            score = score + 10;
            yellowX = - 100;
        }

        if(yellowX<0)
        {
            yellowX = canvasWidth +21;
            yellowY = (int) Math.floor(Math.random() * (maxFishY - minFishY)) + minFishY;
        }

        canvas.drawCircle(yellowX,yellowY,10,yellowPaint);

        /*for Green Ball*/
        greenX = greenX - greenSpeed;

        if(hitBallChecker(greenX,greenY))
        {
            score = score + 20;
            greenX = - 100;
        }

        if(greenX<0)
        {
            greenX = canvasWidth +21;
            greenY = (int) Math.floor(Math.random() * (maxFishY - minFishY)) + minFishY;
        }

        canvas.drawCircle(greenX,greenY,20,greenPaint);

        /*for Black Ball*/
        blackX = blackX - blackSpeed;

        if(hitBallChecker(blackX,blackY))
        {
            blackX = - 100;
            lifeCounter--;
            if(lifeCounter == 0)
            {
                Toast.makeText(getContext(), "Game Over",Toast.LENGTH_SHORT);
                Intent gameover = new Intent(getContext(),GameOverActivity.class);
                gameover.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                gameover.putExtra("Score",score);
                getContext().startActivity(gameover);
            }
        }

        if(blackX<0)
        {
            blackX = canvasWidth +21;
            blackY = (int) Math.floor(Math.random() * (maxFishY - minFishY)) + minFishY;
        }

        canvas.drawCircle(blackX,blackY,25,blackPaint);

        /*Score & Life*/
        canvas.drawText("Score : " + score, 20,60,paint);

        for(int i=0;i<3;i++)
        {
            int x=(int) (300 + life[0].getWidth() * 1.5 * i);
            int y = 30;

            if(i < lifeCounter)
            {
                canvas.drawBitmap(life[0],x,y,null);
            }
            else
            {
                canvas.drawBitmap(life[1],x,y,null);
            }
        }



    }

    public boolean hitBallChecker(int x,int y)
    {
        if(fishX < x && x < (fishX + fish[0].getWidth()) && fishY < y && y < (fishY +fish[0].getHeight()))
        {
            return true;
        }
        return false;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            touch = true;
            fishSpeed = -22;
        }
        return true;
    }
}
