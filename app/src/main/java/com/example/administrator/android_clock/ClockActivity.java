package com.example.administrator.android_clock;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import java.util.Calendar;


public class ClockActivity extends AppCompatActivity {
    public static final int CURRENT_TIME = 0;

    private TextView mClockTime;


    /*声明关键Handler和Thread变量*/
    public Handler mHandler;
    private Thread mClockThread;

    private Calendar mCalendar;
    private int mYear;
    private int mMonth;
    private int mDay;
    private int mHour;
    private int mMinutes;
    private int mSecond;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clock);

        findViewById();
        mHandler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what){
                    case CURRENT_TIME:
                        mClockTime.setText(mYear+"/"+mMonth+"/"+mDay+" "+mHour+":"+mMinutes+":"+mSecond);
                        break;
                }
                super.handleMessage(msg);
            }
        };

        mClockThread = new LopperThread();
        mClockThread.start();
    }
    /*通过一个线程持续获取系统时间*/
    class LopperThread extends Thread{
        @Override
        public void run() {
            super.run();
            try{
                do {
                   long time = System.currentTimeMillis();
                    mCalendar = Calendar.getInstance();
                    mCalendar.setTimeInMillis(time);
                    mYear = mCalendar.get(Calendar.YEAR);
                    mMonth=mCalendar.get(Calendar.MONTH);
                    mDay=mCalendar.get(Calendar.DAY_OF_MONTH);
                    mHour = mCalendar.get(Calendar.HOUR_OF_DAY);
                    mMinutes = mCalendar.get(Calendar.MINUTE);
                    mSecond = mCalendar.get(Calendar.SECOND);

                    Thread.sleep(1000);

                    Message msg = new Message();
                    msg.what = CURRENT_TIME;
                    mHandler.sendMessage(msg);
                }
                while(LopperThread.interrupted()==false);
                /*当系统发出中断消息时停止本循环*/
            }
            catch (Exception e){
                e.printStackTrace();
            }


        }
    }
    private void findViewById(){
        mClockTime = (TextView)findViewById(R.id.mClockTime);

    }
}
