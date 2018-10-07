package ibx.jp.android.dockmode;


import java.util.Calendar;

import processing.core.*;
import processing.data.*;

public class Sketch extends PApplet {
    @Override
    public void settings() {
        //Androidではフルスクリーンが推奨
        //スクリーンサイズの指定は、settings内で行う事
        fullScreen();
    }


    String baseURL = "http://weather.livedoor.com/forecast/webservice/json/v1?city=";
    String city = "260010";  //都市コード
    String title;
    String we;
    String temax = "NO DATA";
    String temin = "";
    String week = "";
    JSONArray forecasts;

    PImage back;
    PImage ring;
    PImage line;
//    PImage whim;
    PImage hari1;
    PImage hari2;
    PImage hari2b;

    int MARGIN = 245;
    String mt2;
    float s;
    float m;
    float h;
    int s2;
    int sl;
    int m2;
    int h2;
    int y;
    int mt;
    int d;
    int i;
    int t;
    int t2;
    int upk ;
    int dwk ;
    int enk ;
    int ath = 18;
    int aon;
    int tth;
    int tthh;
    int ton;
    int tp;

    int mode;

    @Override
    public void setup() {
        strokeWeight(2);
        stroke(255);
        smooth();
        frameRate(30);
        back = loadImage("back.png");
        ring = loadImage("ring.png");
        line = loadImage("line.png");
        hari1 = loadImage("hari1.png");
        hari2 = loadImage("hari2.png");
        hari2b = loadImage("hari2b.png");
        background(0);
        textAlign(CENTER);

        PFont font = createFont("YuGothic-Regular", 64, true);
        textFont(font);

        JSONObject w = loadJSONObject(baseURL + city);
        title = w.getString("title");
        forecasts = w.getJSONArray("forecasts");


        JSONObject f = forecasts.getJSONObject(0);     //本日  0;今日　1;明日　2:明後日
        we = f.getString("telop");
        JSONObject t = f.getJSONObject("temperature");
        if (!t.isNull("max")) {
            temax = t.getJSONObject("max").getString("celsius");
        }
        if (!t.isNull("min")) {
            temin = t.getJSONObject("min").getString("celsius");
        }
        JSONObject f2 = forecasts.getJSONObject(1);
        JSONObject t2 = f2.getJSONObject("temperature");
        if (!t2.isNull("max")) {
            temax = t2.getJSONObject("max").getString("celsius");
        }
        if (!t2.isNull("min")) {
            temin = t2.getJSONObject("min").getString("celsius");
        }

        y = year();
        mt = month();
        d = day();

        week = weekFromDate(y, mt, d);
    }
    //------------------------------------------------------------------------------------
//------------------------------------------------------------------------------------all
    @Override
    public void draw() {
        background(0);
        image(back, 0, 0);
        s = second();
        m = minute()+ (s/(float)60.0);
        h = hour()%12 + (m/(float)60.0);
        s2 = second();
        m2 = minute();
        h2 = hour();
        y = year();
        mt = month();
        d = day();

        if (mt == 1) {
            mt2 = "Jan.";
        } else if (mt == 2) {
            mt2 = "Feb.";
        } else if (mt == 3) {
            mt2 = "Mar.";
        } else if (mt == 4) {
            mt2 = "Apr.";
        } else if (mt == 5) {
            mt2 = "May ";
        } else if (mt == 6) {
            mt2 = "Jun.";
        } else if (mt == 7) {
            mt2 = "Jul.";
        } else if (mt == 8) {
            mt2 = "Aug.";
        } else if (mt == 9) {
            mt2 = "Sep.";
        } else if (mt == 10) {
            mt2 = "Oct.";
        } else if (mt == 11) {
            mt2 = "Nov.";
        } else if (mt == 12) {
            mt2 = "Dec.";
        } else {
            mt2 = "Mab.";
        }

        noFill();
        stroke(255);
        translate(width/2+1, height/2+2);

  /*

  // 文字盤の表示
  pushMatrix();
  noStroke();
  fill(128);
  for (int i2=0; i2<7; i2++) {
    for (int i=0; i<360; i++) {
      rotate(radians(1));
      ellipse(width/2-MARGIN-i2*4, 0, 2, 2);
    }
  }
  popMatrix();
  */

        translate(-width/2-1, -height/2-2);
        image(ring, 0, 0);
        translate(width/2+1, height/2+2);
        tint(255);

  /*
  noFill();
  translate(-width/2, -height/2);
  image(line, 0, 0);

  strokeWeight(2);
  translate(width/2+1, height/2+2);

  for (int i = 0; i<30; i++) {
    stroke(100+5*i);
    ellipse(0, 0, height+i, height+i);
    stroke(180-5*i);
    ellipse(0, 0, height+i+30, height+i+30);
  }
  noFill();
  */

        if (mode == 1) {
            stroke(230);
            for (int i = 0; i<120; i++) {
                if (tth > 10 || ton == 0) {
                    stroke(230);
                } else if (tthh <1) {
                    stroke(230-i/2, 30, 30);
                }

                ellipse(0, 0, height-i, height-i);
            }
            filter(INVERT);
        }

        for (int i=0; i<60; i++) {
            if (m2 <= i && mode != 1) {
                stroke(220, 180);
            } else if (mode != 1) {
                stroke(220, 100);
            } else if (tth <= i+1) {
                if (tthh >0) {
                    stroke(220);
                } else {
                    stroke(80);
                }
            } else if (tth >10 || ton==0) {
                stroke(240);
            } else {
                stroke(80);
            }
            rotate(radians(6));
            line(0, -390, 0, -380);
        }

        for (int i2=0; i2<30; i2++) {
            for (int i=0; i<12; i++) {
                stroke(80+i2*6);
                rotate(radians(30));
                if (i2>20) {
                    line(360+i2, -5+i2/2-10, 360+i2, 5-i2/2+10);
                } else {
                    line(360+i2, -5, 360+i2, 5);
                }
            }
        }
        if (mode ==1) {
            filter(INVERT);
        }
        resetMatrix();

        fill(200);
        textSize(32);
        text(y+"  "+mt2+d+" ("+week+")", 645, 415);
        text("sec", 1200, 690);
        text("\u25b2", 225, 135);
        text("\u25bc", 225, 190);

        textSize(128);
        if (m2<10) {
            text(h2+":0"+m2, 645, 340);
        } else {
            text(h2+":"+m2, 645, 340);
        }

        textSize(72);
        if (s2<10) {
            text("0"+s2, 1120, 690);
        } else {
            text(s2, 1120, 690);
        }
        text(temax+"\u2103", 120, 130);
        text(temin+"\u2103", 120, 225);

        textSize(24);
        text(title, 1100, 80);
        textSize(42);
        text(we, 1140, 160);

        //-----------------------------------------------------------------------------------------1
        if (mode == 1) {
            textSize(24);
            text("Timer Mode", width/2, 540);
            if (ton == 0) {
                if (tthh == 0 ) {
                    if (upk == 1) {
                        tth = tth +5;
                    }
                    if (dwk == 1) {
                        tth = tth -5;
                    }
                } else {
                    if (upk == 1) {
                        tth = tth +10;
                    }
                    if (dwk == 1) {
                        tth = tth -10;
                    }
                }
                textSize(24);
                text("Timer Setting", 130, 745);
            } else if (tp == 1 && ton == 1) {
                if (s2!=sl) {
                    tth = tth+1;
                }

                text("Stopwatch Running", 130, 745);
            } else if (ton == 1) {
                if (s2!=sl) {
                    tth = tth-1;
                }
                if (tthh == 0 && tth ==0) {
                    textSize(48);
                    text("TIME OVER", width/2, 600);
                    textSize(24);
                }
                text("Timer Running", 130, 745);
            } else if (ton == 2) {
                text("Stopped", 130, 745);
            }
            pushMatrix();
            translate(width/2+1, height/2+2);
            if (tthh*60+tth >=0) {
                rotate(radians((tthh*60+tth)*6));
            }
            noFill();
            image(hari2b, -width/2-1, -height/2-2);
            popMatrix();
            if (tth >=60) {
                tth = 0;
                tthh++;
            }
            if (tth <0) {
                if (tthh == 0) {
                    tth = 0;
                } else {
                    if (ton == 0) {
                        tth = 50;
                    } else {
                        tth = 59;
                    }
                    tthh--;
                }
            }

            if (ton == 0 && enk == 1 && t2>10) {
                ton = 1;
                t2 = 0;
                if (tthh == 0 && tth == 0) {
                    tp = 1;
                } else {
                    tp = 0;
                }
            } else if (ton == 1 && enk == 1 && t2>10) {
                ton = 2;
                t2 = 0;
            } else if (ton == 2 && enk == 1 && t2>10) {
                ton = 0;
                tth = tth/10;
                tth = tth*10;
                t2 = 0;
            }
            textSize(48);
            if (tthh>0) {
                if (tth >=10) {
                    text(tthh+":"+tth, 130, 700);
                } else {
                    text(tthh+":0"+tth, 130, 700);
                }
            } else if (tth == 0) {
                text(tth+"++", 130, 700);
            } else {
                text(tth+"sec", 130, 700);
            }
        }

        //-----------------------------------------------------------------------------------2
        if (mode == 2) {
            textSize(24);
            text("Alarm Setting", width/2, 540);
            if (upk == 1) {
                ath = ath +1;
            }
            if (dwk == 1) {
                ath = ath -1;
            }
            if (ath >46) {
                ath = 0;
            }
            if (ath <0) {
                ath = 46;
            }
            textSize(48);
            text(ath/2+":00", 130, 700);
            if (aon == 0 && enk == 1 && t2>10) {
                aon = 1;
                t2 = 0;
            } else if (aon == 1 && enk == 1 && t2>10) {
                aon = 0;
                t2 = 0;
            }
        }
        //-----------------------------------------------------------------------------------0.2
        if (mode == 0 || mode == 2) {
            textSize(24);
            if (aon == 0) {
                text("Alarm OFF", 130, 745);
            } else {
                text("Alarm ON", 130, 745);
                textSize(48);
                text(ath/2+":00", 130, 700);
                if (h2 == ath/2 && m2 == 0) {
                    text("ALARM TIME", width/2, 600);
                }
            }
        }
        //------------------------------------------------------------------------------------all
        if (h2==0 && m2 ==0 && s2 ==1) {
            setup();
        }

        translate(width/2+1, height/2+2);

        //----------------------------------------------------------------------------------0.2
        if (mode == 0 || mode == 2) {

            //秒針
            //pushMatrix();
            //rotate(radians(s*(360/60)));
            //strokeWeight(1);
            //line(0,0,0,-height/2);
            //popMatrix();

            //分針
            pushMatrix();
            rotate(radians(m*(360/60)));
            image(hari2, -width/2-1, -height/2-2);
            popMatrix();

            //時針
            pushMatrix();
            rotate(radians(h*(360/12)));
            image(hari1, -width/2-1, -height/2-2);
            popMatrix();
        }

        t++;
        t2++;
        sl = s2;
    }
    //-------------------------------------------------------------------------------------
//------------------------------------------------------------------------------------

    @Override
    public void keyPressed() {
        if (keyCode == UP) {
            upk = 1;
        } else if (keyCode == DOWN) {
            dwk = 1;
        } else if (keyCode == ENTER) {
            enk = 1;
        } else if (key == 't') {    // timer mode
            mode = 1;
        } else if (key == 'a') {     // wake up
            mode = 2;
        } else if (key == 'r') {     // recover
            mode = 0;
        }
    }

    @Override
    public void keyReleased() {
        upk = dwk = enk = 0;
    }

    @Override
    public void  mousePressed() {
        if (mouseY < 200) {
            upk = 1;
        } else if (mouseY > 600) {
            dwk = 1;
        } else {
            enk = 1;
        }
    }

    @Override
    public void  mouseReleased() {
        upk = dwk = enk = 0;
    }

    /**
     * 日付から曜日を取得するメソッド。
     *
     * @param year 年。（int型。）
     * @param month 月。（int型。）
     * @param date 日。（int型。）
     * @return 曜日。（String型。）
     */
    private String weekFromDate(int year, int month, int date) {
        String result = "";
        Calendar cal = Calendar.getInstance();

        cal.set(year, month - 1, date);

        switch (cal.get(Calendar.DAY_OF_WEEK)) {
            case Calendar.SUNDAY:
                result = "Sun";
                break;
            case Calendar.MONDAY:
                result = "Mon";
                break;
            case Calendar.TUESDAY:
                result = "Tue";
                break;
            case Calendar.WEDNESDAY:
                result = "Wed";
                break;
            case Calendar.THURSDAY:
                result = "Thu";
                break;
            case Calendar.FRIDAY:
                result = "Fri";
                break;
            case Calendar.SATURDAY:
                result = "Sat";
                break;
        }

        return result;
    }

}
