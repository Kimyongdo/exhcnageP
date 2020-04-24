package exchange.example.newopenapiexchangeproject3;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class GlobalTime {

     static String newstime2;
     static Date date2;
     static SimpleDateFormat sdf2;

     static TimeZone[] timeZone2 = new TimeZone[23];
     static DateFormat[] dateFormat2 = new DateFormat[23];

     static String noetime;
     static Date notedate;
     static SimpleDateFormat notesdf;


     static  String[] timedifferent = new String[23];
     static  String[] timeSubstract = new String[23];

    //노트업데이트
    public void Notetime(){
        notedate= new Date();
        notesdf = new SimpleDateFormat("yy/MM/dd");
        noetime = notesdf.format(notedate);
    }

    public void koreantime(){
        date2= new Date();
        sdf2 = new SimpleDateFormat("yy-MM-dd a hh:mm");
        newstime2 = sdf2.format(date2);
    }


    public void globaltime(){
        for(int i=0; i<23; i++){ //json파일 전체 사이즈 의미 20200102현재 23개
            switch(i){
                case 0:
                    dateFormat2[i] = new SimpleDateFormat("yy-MM-dd a hh:mm"); //그 나라 시간.
                    timeZone2[i]  = TimeZone.getTimeZone("Asia/Dubai"); //그 나라 표준시
                    dateFormat2[i].setTimeZone(timeZone2[i]);
                    timedifferent[i]=5+"";
                    timeSubstract[i]="한국은 아랍에미리트보다 5시간 빠릅니다.";
                    break;
                case 1:
                    dateFormat2[i] = new SimpleDateFormat("yy-MM-dd a hh:mm"); //그 나라 시간.
                    timeZone2[i]  = TimeZone.getTimeZone("Australia/Canberra"); //그 나라 표준시
                    dateFormat2[i].setTimeZone(timeZone2[i] );
                    timedifferent[i]=2+"";
                    timeSubstract[i]="오스트레일리아(캔버라)는 한국보다 2시간 빠릅니다.";
                    break;
                case 2:
                    dateFormat2[i] = new SimpleDateFormat("yy-MM-dd a hh:mm"); //그 나라 시간.
                    timeZone2[i]  = TimeZone.getTimeZone("Asia/Bahrain"); //그 나라 표준시
                    dateFormat2[i].setTimeZone(timeZone2[i] );
                    timedifferent[i]=6+"";
                    timeSubstract[i]="한국은 바레인보다 6시간 빠릅니다";
                    break;
                case 3:
                    dateFormat2[i] = new SimpleDateFormat("yy-MM-dd a hh:mm"); //그 나라 시간.
                    timeZone2[i]  = TimeZone.getTimeZone("Asia/Brunei"); //그 나라 표준시
                    dateFormat2[i].setTimeZone(timeZone2[i] );
                    timedifferent[i]=1+"";
                    timeSubstract[i]="한국은 브루네이보다 1시간 빠릅니다.";
                    break;
                case 4:
                    dateFormat2[i] = new SimpleDateFormat("yy-MM-dd a hh:mm"); //그 나라 시간.
                    timeZone2[i]  = TimeZone.getTimeZone("America/Toronto"); //그 나라 표준시
                    dateFormat2[i].setTimeZone(timeZone2[i] );
                    timedifferent[i]=14+"";
                    timeSubstract[i]="한국은 캐나다(토론토)보다 14시간 빠릅니다.";
                    break;
                case 5:
                    dateFormat2[i] = new SimpleDateFormat("yy-MM-dd a hh:mm"); //그 나라 시간.
                    timeZone2[i]  = TimeZone.getTimeZone("Europe/Zurich"); //그 나라 표준시
                    dateFormat2[i].setTimeZone(timeZone2[i] );
                    timedifferent[i]=8+"";
                    timeSubstract[i]="한국은 스위스보다 8시간 빠릅니다.";
                    break;
                case 6:
                    dateFormat2[i] = new SimpleDateFormat("yy-MM-dd a hh:mm"); //그 나라 시간.
                    timeZone2[i]  = TimeZone.getTimeZone("Asia/Shanghai"); //그 나라 표준시
                    dateFormat2[i].setTimeZone(timeZone2[i] );
                    timedifferent[i]=1+"";
                    timeSubstract[i]="한국은 중국보다 1시간 빠릅니다.";
                    break;
                case 7:
                    dateFormat2[i] = new SimpleDateFormat("yy-MM-dd a hh:mm"); //그 나라 시간.
                    timeZone2[i]  = TimeZone.getTimeZone("Europe/Copenhagen"); //그 나라 표준시
                    dateFormat2[i].setTimeZone(timeZone2[i] );
                    timedifferent[i]=8+"";
                    timeSubstract[i]="한국은 덴마크보다 8시간 빠릅니다.";
                    break;
                case 8:
                    dateFormat2[i] = new SimpleDateFormat("yy-MM-dd a hh:mm"); //그 나라 시간.
                    timeZone2[i]  = TimeZone.getTimeZone("Europe/Brussels"); //그 나라 표준시
                    dateFormat2[i].setTimeZone(timeZone2[i] );
                    timedifferent[i]=8+"";
                    timeSubstract[i]="한국은 벨기에(브뤼셀)보다 8시간 빠릅니다.";
                    break;
                case 9:
                    dateFormat2[i] = new SimpleDateFormat("yy-MM-dd a hh:mm"); //그 나라 시간.
                    timeZone2[i]  = TimeZone.getTimeZone("Europe/London"); //그 나라 표준시
                    dateFormat2[i].setTimeZone(timeZone2[i] );
                    timedifferent[i]=9+"";
                    timeSubstract[i]="한국은 영국보다 9시간 빠릅니다.";
                    break;
                case 10:
                    dateFormat2[i] = new SimpleDateFormat("yy-MM-dd a hh:mm"); //그 나라 시간.
                    timeZone2[i]  = TimeZone.getTimeZone("Asia/Hong_Kong"); //그 나라 표준시
                    dateFormat2[i].setTimeZone(timeZone2[i] );
                    timedifferent[i]=1+"";
                    timeSubstract[i]="한국은 홍콩보다 1시간 빠릅니다.";
                    break;
                case 11:
                    dateFormat2[i] = new SimpleDateFormat("yy-MM-dd a hh:mm"); //그 나라 시간.
                    timeZone2[i]  = TimeZone.getTimeZone("Asia/Jakarta"); //그 나라 표준시
                    dateFormat2[i].setTimeZone(timeZone2[i] );
                    timedifferent[i]=2+"";
                    timeSubstract[i]="한국은 인도네시아(자카르타)보다 2시간 빠릅니다.";
                    break;
                case 12:
                    dateFormat2[i] = new SimpleDateFormat("yy-MM-dd a hh:mm"); //그 나라 시간.
                    timeZone2[i]  = TimeZone.getTimeZone("Asia/Tokyo"); //그 나라 표준시
                    dateFormat2[i].setTimeZone(timeZone2[i] );
                    timedifferent[i]=0+"";
                    timeSubstract[i]="한국 및 일본 사이에는 시차가 없습니다.";
                    break;
                case 13:
                    dateFormat2[i] = new SimpleDateFormat("yy-MM-dd a hh:mm"); //그 나라 시간.
                    timeZone2[i]  = TimeZone.getTimeZone("Asia/Seoul"); //그 나라 표준시
                    dateFormat2[i].setTimeZone(timeZone2[i] );
                    timedifferent[i]=0+"";
                    timeSubstract[i]="시차가 없습니다.";
                    break;
                case 14:
                    dateFormat2[i] = new SimpleDateFormat("yy-MM-dd a hh:mm"); //그 나라 시간.
                    timeZone2[i]  = TimeZone.getTimeZone("Asia/Kuwait"); //그 나라 표준시
                    dateFormat2[i].setTimeZone(timeZone2[i] );
                    timedifferent[i]=6+"";
                    timeSubstract[i]="한국은 쿠웨이트보다 6시간 빠릅니다.";
                    break;
                case 15:
                    dateFormat2[i] = new SimpleDateFormat("yy-MM-dd a hh:mm"); //그 나라 시간.
                    timeZone2[i]  = TimeZone.getTimeZone("Asia/Kuala_Lumpur"); //그 나라 표준시
                    dateFormat2[i].setTimeZone(timeZone2[i] );
                    timedifferent[i]=1+"";
                    timeSubstract[i]="한국은 말레이시아보다 1시간 빠릅니다.";
                    break;
                case 16:
                    dateFormat2[i] = new SimpleDateFormat("yy-MM-dd a hh:mm"); //그 나라 시간.
                    timeZone2[i]  = TimeZone.getTimeZone("Europe/Oslo"); //그 나라 표준시
                    dateFormat2[i].setTimeZone(timeZone2[i] );
                    timedifferent[i]=8+"";
                    timeSubstract[i]="한국은 노르웨이보다 8시간 빠릅니다.";
                    break;
                case 17:
                    dateFormat2[i] = new SimpleDateFormat("yy-MM-dd a hh:mm"); //그 나라 시간.
                    timeZone2[i]  = TimeZone.getTimeZone("Pacific/Auckland"); //그 나라 표준시
                    dateFormat2[i].setTimeZone(timeZone2[i] );
                    timedifferent[i]=4+"";
                    timeSubstract[i]="뉴질랜드(오클랜드)시간은 한국보다 4시간 빠릅니다.";
                    break;
                case 18:
                    dateFormat2[i] = new SimpleDateFormat("yy-MM-dd a hh:mm"); //그 나라 시간.
                    timeZone2[i]  = TimeZone.getTimeZone("Asia/Riyadh"); //그 나라 표준시
                    dateFormat2[i].setTimeZone(timeZone2[i] );
                    timedifferent[i]=6+"";
                    timeSubstract[i]="한국은 사우디아라비아보다 6시간 빠릅니다.";
                    break;
                case 19:
                    dateFormat2[i] = new SimpleDateFormat("yy-MM-dd a hh:mm"); //그 나라 시간.
                    timeZone2[i]  = TimeZone.getTimeZone("Europe/Stockholm"); //그 나라 표준시
                    dateFormat2[i].setTimeZone(timeZone2[i] );
                    timedifferent[i]=8+"";
                    timeSubstract[i]="한국은 스웨덴 스톡홀름보다 8시간 빠릅니다.";
                    break;
                case 20:
                    dateFormat2[i] = new SimpleDateFormat("yy-MM-dd a hh:mm"); //그 나라 시간.
                    timeZone2[i]  = TimeZone.getTimeZone("Asia/Singapore"); //그 나라 표준시
                    dateFormat2[i].setTimeZone(timeZone2[i] );
                    timedifferent[i]=1+"";
                    timeSubstract[i]="한국은 싱가포르보다 1시간 빠릅니다.";
                    break;
                case 21:
                    dateFormat2[i] = new SimpleDateFormat("yy-MM-dd a hh:mm"); //그 나라 시간.
                    timeZone2[i]  = TimeZone.getTimeZone("Asia/Bangkok"); //그 나라 표준시
                    dateFormat2[i].setTimeZone(timeZone2[i] );
                    timedifferent[i]=2+"";
                    timeSubstract[i]="한국은 태국방콕 보다 2시간 빠릅니다.";
                    break;
                case 22:
                    dateFormat2[i] = new SimpleDateFormat("yy-MM-dd a hh:mm"); //그 나라 시간.
                    timeZone2[i]  = TimeZone.getTimeZone("America/New_York"); //그 나라 표준시
                    dateFormat2[i].setTimeZone(timeZone2[i] );
                    timedifferent[i]=14+"";
                    timeSubstract[i]="한국시간은 미국(뉴욕)보다 14시간 빠릅니다.";
                    break;
            }


        }

    }


}
