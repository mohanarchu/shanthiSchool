package com.video.aashi.school.fragments;


import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.telecom.Call;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.CalendarMode;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;
import com.prolificinteractive.materialcalendarview.OnMonthChangedListener;
import com.video.aashi.school.APIUrl;
import com.video.aashi.school.MainActivity;
import com.video.aashi.school.Navigation;
import com.video.aashi.school.PinLogin;
import com.video.aashi.school.R;
import com.video.aashi.school.adapters.ApiClient;
import com.video.aashi.school.adapters.Expired;
import com.video.aashi.school.adapters.Interfaces.DatabaseHelper;
import com.video.aashi.school.adapters.Interfaces.MyInterface;
import com.video.aashi.school.adapters.arrar_adapterd.Name;
import com.video.aashi.school.adapters.arrar_adapterd.NoticeBoards;
import com.video.aashi.school.adapters.calenders.EventDecorator;
import com.video.aashi.school.adapters.calenders.HighlightWeekendsDecorator;
import com.video.aashi.school.adapters.calenders.OneDayDecorator;
import com.video.aashi.school.adapters.post_class.Attend;
import com.video.aashi.school.adapters.viewAdspters.Notification_designs;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.threeten.bp.Month;
import org.w3c.dom.Text;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.concurrent.Executors;

import okhttp3.Cache;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.ResponseBody;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * A simple {@link Fragment} subclass.
 */
public class Attendace extends Fragment implements OnDateSelectedListener {




    public Attendace() {
        // Required empty public constructor
    }
    private List<CalendarDay> present = new ArrayList<>();
    private List<CalendarDay> absent = new ArrayList<>();
    private List<CalendarDay> holiday = new ArrayList<>();
    private List<CalendarDay> weekoff = new ArrayList<>();
    private  List<CalendarDay> afternoon= new ArrayList<>();
    TextView  presents,absents,holidays,weekoffs,afternoons,forenoons;
    public static final String RESULT = "result";
    public static final String EVENT = "event";
    private static final int ADD_NOTE = 44;
    LinearLayout click;
    MaterialCalendarView materialCalendarView;
    Toolbar toolbar;
    MyInterface myInterface;
    String aca_year;
    Switch myswitch;
    TextView mytext;
    String day01;
    String day01DateDisp;
    String day02;
    String day02DateDisp;
    String day03;
    String day03DateDisp;
    String day04;
    String day04DateDisp;
    String day05;
    String day05DateDisp;
    String day06;
    String day06DateDisp;
    String day07;
    String day07DateDisp;
    String day08;
    String day08DateDisp;
    String day09;
    String day09DateDisp;
    String day10;
    String day10DateDisp;
    String day11;
    String day11DateDisp;
    String day12;
    String day12DateDisp;
    String day13;
    String day13DateDisp;
    String day14;
    String day14DateDisp;
    String day15;
    String day15DateDisp;
    String day16;
    String day16DateDisp;
    String day17;
    String day17DateDisp;
    String day18;
    String day18DateDisp;
    String day19;
    String day19DateDisp;
    String day20;
    String day20DateDisp;
    String day21;
    String day21DateDisp;
    String day22;
    String day22DateDisp;
    String day23;
    String day23DateDisp;
    String day24;
    String day24DateDisp;
    String day25;
    String day25DateDisp;
    String day26;
    String day26DateDisp;
    String day27;
    String day27DateDisp;
    String day28;
    String day28DateDisp;
    String day29;
    String day29DateDisp;
    String day30;
    String day30DateDisp;
    String day31;
    String day31DateDisp;
    String s_yesr;
    String loc_id;
    Retrofit retrofit;
    String monthName;
    ProgressDialog progressDialog;
    Month month;
    Handler handler = new Handler();
    OneDayDecorator oneDayDecorator;
    HighlightWeekendsDecorator highlightWeekendsDecorator;
    private DatabaseHelper db;
    private List<Name> names;
    NameAdapter nameAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_attendance, container, false);
        toolbar =(android.support.v7.widget.Toolbar) getActivity().findViewById(R.id.toolbar);
        toolbar.setTitle("Attendance");
        materialCalendarView = (MaterialCalendarView) view.findViewById(R.id.mycalender);
        materialCalendarView.state().edit().setCalendarDisplayMode(CalendarMode.MONTHS).commit();
        materialCalendarView.setTileWidth(LinearLayout.LayoutParams.MATCH_PARENT);

        progressDialog = new ProgressDialog(getActivity());
        materialCalendarView.addDecorator(new HighlightWeekendsDecorator(getActivity()));

        materialCalendarView.setSelectionColor(Color.TRANSPARENT);


        myswitch =(Switch)view.findViewById(R.id.simpleSwitch);
        mytext =(TextView)view.findViewById(R.id.switches);
        db = new DatabaseHelper(getActivity());
        names = new ArrayList<>();
        progressDialog.show();
        progressDialog.setMessage("Please wait...");
        Calendar c = Calendar.getInstance();
        Handler handler = new Handler();
        String months;
        months = String.valueOf(c.get(Calendar.MONTH));
        handler.postDelayed(new Runnable() {
            public void run() {
                progressDialog.dismiss();
            }
        }, 2000);
        handler.postDelayed(new Runnable() {
            public void run() {
                final Calendar currentCal=Calendar.getInstance();
                month  = Month.of(currentCal.get(Calendar.MONTH)+ 1);
                loadDatas(month.toString());
                getExtras(month.toString());
                Log.i("Tag","getAttandances" + month);
                progressDialog.show();
            }
        }, 1000);
        //materialCalendarView.addDecorator(highlightWeekendsDecorator);
        //  materialCalendarView.addDecorator(oneDayDecorator);
        presents =(TextView)view.findViewById(R.id.presents);
        absents =(TextView)view.findViewById(R.id.absents);
        holidays =(TextView)view.findViewById(R.id.holidays);
        weekoffs =(TextView)view.findViewById(R.id.weekoffs);
        afternoons=(TextView)view.findViewById(R.id.afternoons);
        forenoons=(TextView)view.findViewById(R.id.forenoons);
        click = (LinearLayout) view.findViewById(R.id.click);
        setHasOptionsMenu(true);
        getActivity().invalidateOptionsMenu();
        myInterface= ApiClient.getApiCLient().create(MyInterface.class);
        File httpCacheDirectory = new File(getActivity().getCacheDir(), "httpCache");
        Cache cache = new Cache(httpCacheDirectory, 10 * 1024 * 1024);
        OkHttpClient httpClient = new OkHttpClient.Builder()
                .cache(cache)
                .addInterceptor(chain -> {
                    try {
                        return chain.proceed(chain.request());
                    } catch (Exception e) {
                        Request offlineRequest = chain.request().newBuilder()
                                .header("Cache-Control", "public, only-if-cached," +
                                        "max-stale=" + 60 * 60 * 24)
                                .build();
                        return chain.proceed(offlineRequest);
                    }
                })
                .build();
        retrofit =   new Retrofit.Builder().baseUrl(HomePage.url).addConverterFactory
                (GsonConverterFactory.create())
                .client(httpClient)
                .build();
        final LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        myswitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked)
                {
                    materialCalendarView.state().edit().setCalendarDisplayMode(CalendarMode.WEEKS).commit();
                    mytext.setText("Switch to Month View");
                }
                else
                {
                    materialCalendarView.state().edit().setCalendarDisplayMode(CalendarMode.MONTHS).commit();
                    mytext.setText("Switch to Week View");
                }
            }
        });
        //  layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        //  noticeboard.setLayoutManager(layoutManager);
        myInterface = retrofit.create(MyInterface.class);
        s_yesr = Navigation.student_id;
        loc_id = Navigation.location_id;
        aca_year = Navigation.academicyear;

        click.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        materialCalendarView.setOnMonthChangedListener(new OnMonthChangedListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onMonthChanged(MaterialCalendarView widget, CalendarDay date)
            {
                month = Month.of(date.getMonth() + 1);
                isNetworkConnected();
                getExtras(month.toString());
                loadDatas(month.toString());
            //    Log.i("Tag", "MyAttendance" + month);

            }
        });
        final Calendar currentCal=Calendar.getInstance();
        month  = Month.of(currentCal.get(Calendar.MONTH)+ 1);
        isNetworkConnected();
      //  new LoadAttendance().execute(month.toString());
        return view;
    }
    @Override
    public void onDateSelected(@NonNull MaterialCalendarView widget, @NonNull CalendarDay date, boolean selected) {
        //oneDayDecorator.setDate(date.getDate());
        widget.invalidateDecorators();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater)
    {
        menu.clear();
        inflater.inflate(R.menu.dashboard ,menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId())
        {

        }
        return super.onOptionsItemSelected(item);
    }
    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager)getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        //if there is a network
        if (activeNetwork != null) {
            //if connected to wifi or mobile data plan
            if (activeNetwork.getType() == ConnectivityManager.TYPE_WIFI ||
                    activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE) {
            }
        }
        else
        {
          //  progressDialog.dismiss();
           // loadNames();
            Toast.makeText(getActivity(),"Connection Error...",Toast.LENGTH_LONG).show();
        }
        return true;
    }
    void getExtras(String months)
    {
        retrofit2.Call<ResponseBody> responseBodyCall = myInterface.getAttendance(new Attend(aca_year,s_yesr,loc_id,Navigation.loginId,Navigation.session));
        responseBodyCall.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(retrofit2.Call<ResponseBody> call, Response<ResponseBody> response) {
                String bodyString = null;
                try {
                    bodyString = response.body().string();
                    try {
                        JSONObject object = new JSONObject(bodyString);
                        String   failure = object.getString("status");
                        String  errorMessage = object.getString("errorMessage");
                        if  (failure.contains("failure")) {
                            String finalErrorMessage = errorMessage;
                            Expired expired = new Expired(getActivity(), finalErrorMessage);
                            expired.setTitle(finalErrorMessage);
                            expired.setCancelable(false);

                            expired.setPositiveButton("OK", (dialog1, which) -> {
                                expired.getSharedPreferences();
                                Intent i = new Intent(getActivity(), PinLogin.class);
                                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(i);
                            });
                            expired.show();
                        }
                        JSONArray list = object.getJSONArray("Student Attendance Details");
                        if (list.length() == 0) {
                            Toast.makeText(getActivity(), "No attendance found", Toast.LENGTH_LONG).show();
                        } else {
                            for (int i = 0; i < list.length(); i++) {

                                JSONObject data = list.getJSONObject(i);
                                String attendanceyear;
                                JSONObject jsonObject = data.getJSONObject("day01Date");
                                attendanceyear = jsonObject.getString("time");
                                @SuppressLint("SimpleDateFormat") SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
                                monthName = data.getString("monthName");
                                String string = monthName.toUpperCase();
                                if (string.equals(months)) {
                                    Log.i("Tag", "MyAttendances" + month+string);
                                    String workings = data.getString("totalWorkingDays");
                                    String forenoonss = data.getString("totalForeNoonDays");
                                    String afternoondays = data.getString("totalAfterNoonDays");
                                    String presnts1 = data.getString("totalDaysPresent");
                                    String absents1 = data.getString("totalDaysAbsent");
                                    String holidays1 = data.getString("totalDaysLeave");
                                    presents.setText(presnts1);
                                    absents.setText(absents1);
                                    holidays.setText(holidays1);
                                    afternoons.setText(afternoondays);
                                    forenoons.setText(forenoonss);
                                    weekoffs.setText(workings);
                                }
                            }
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                catch(IOException e){
                    e.printStackTrace();
                }
            }
            @Override
            public void onFailure(retrofit2.Call<ResponseBody> call, Throwable t) {
            }
        });
    }


    void loadDatas(String months)
    {
        progressDialog.show();
        retrofit2.Call<ResponseBody> responseBodyCall = myInterface.getAttendance(new Attend(aca_year,s_yesr,loc_id,Navigation.loginId,Navigation.session));
        responseBodyCall.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(retrofit2.Call<ResponseBody> call, Response<ResponseBody> response)
            {
                present = new ArrayList<>();
                absent = new ArrayList<>();
                holiday = new ArrayList<>();
                weekoff = new ArrayList<>();
                String bodyString = null;
                if (response.isSuccessful())
                {
                    try {
                        bodyString = response.body().string();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                   // Log.i("Tag", "MyAttendance" + call.request().url() + bodyString);
                    try {

                        JSONObject object = new JSONObject(bodyString);
                        JSONArray list = object.getJSONArray("Student Attendance Details");

                        if (list.length() ==0 )
                        {
                            Toast.makeText(getActivity(),"No attendance found",Toast.LENGTH_LONG).show();
                        }

                        else
                        {
                            for (int i = 0; i < list.length(); i++)
                            {

                              //  Log.i("Tag", "MyAttendances" + list.length());
                                JSONObject data = list.getJSONObject(i);
                                String attendanceyear;
                                //  attendanceyear = data.getString("attendanceYear");
                                JSONObject jsonObject = data.getJSONObject("day01Date");
                                attendanceyear = jsonObject.getString("time");
                                @SuppressLint("SimpleDateFormat") SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
                                //  String dateString = formatter.format(new Date(Long.parseLong(attendanceyear)));
                                //String normaltime = dateString.replace("/","-");
                                monthName = data.getString("monthName");
                                String string= monthName.toUpperCase();


                                    String workings = data.getString("totalWorkingDays");
                                    String forenoonss = data.getString("totalForeNoonDays");
                                    String afternoondays = data.getString("totalAfterNoonDays");
                                    String presnts1 = data.getString("totalDaysPresent");
                                    String absents1 = data.getString("totalDaysAbsent");
                                    String holidays1 = data.getString("totalDaysLeave");
                                    presents.setText(presnts1);
                                    absents.setText(absents1);
                                    holidays.setText(holidays1);
                                    afternoons.setText(afternoondays);
                                    forenoons.setText(forenoonss);
                                    weekoffs.setText(workings);
                               if (string.equals(months))
                               {
                          //      Log.i("Tag", "getAttendance" + month+string);
                                day01 = data.getString("day01");
                                day01DateDisp = data.getString("day01DateDisp");
                                DateDecoration(day01DateDisp,day01);
                                day02 = data.getString("day02");
                                day02DateDisp = data.getString("day02DateDisp");
                                DateDecoration(day02DateDisp,day02);
                                day03 = data.getString("day03");
                                day03DateDisp = data.getString("day03DateDisp");
                                DateDecoration(day03DateDisp,day03);
                                day04 = data.getString("day04");
                                day04DateDisp = data.getString("day04DateDisp");
                                DateDecoration(day04DateDisp,day04);
                                day05 = data.getString("day05");
                                day05DateDisp = data.getString("day05DateDisp");
                                DateDecoration(day05DateDisp,day05);
                                day06 = data.getString("day06");
                                day06DateDisp = data.getString("day06DateDisp");
                                DateDecoration(day06DateDisp,day06);
                                day07 = data.getString("day07");
                                day07DateDisp = data.getString("day07DateDisp");
                                DateDecoration(day07DateDisp,day07);
                                day08 = data.getString("day08");
                                day08DateDisp = data.getString("day08DateDisp");
                                DateDecoration(day08DateDisp,day08);
                                day09 = data.getString("day09");
                                day09DateDisp = data.getString("day09DateDisp");
                                DateDecoration(day09DateDisp,day09);
                                day10 = data.getString("day10");
                                day10DateDisp = data.getString("day10DateDisp");
                                DateDecoration(day10DateDisp,day10);
                                day11 = data.getString("day11");
                                day11DateDisp = data.getString("day11DateDisp");
                                DateDecoration(day11DateDisp,day11);
                                day12 = data.getString("day12");
                                day12DateDisp = data.getString("day12DateDisp");
                                DateDecoration(day12DateDisp,day12);
                                day13 = data.getString("day13");
                                day13DateDisp = data.getString("day13DateDisp");
                                DateDecoration(day13DateDisp,day13);
                                day14 = data.getString("day14");
                                day14DateDisp = data.getString("day14DateDisp");
                                DateDecoration(day14DateDisp,day14);
                                day15 = data.getString("day15");
                                day15DateDisp = data.getString("day15DateDisp");
                                DateDecoration(day15DateDisp,day15);
                                day16 = data.getString("day16");
                                day16DateDisp = data.getString("day16DateDisp");
                                DateDecoration(day16DateDisp,day16);
                                day17 = data.getString("day17");
                                day17DateDisp = data.getString("day17DateDisp");
                                DateDecoration(day17DateDisp,day17);
                                day18 = data.getString("day18");
                                day18DateDisp = data.getString("day18DateDisp");
                                DateDecoration(day18DateDisp,day18);
                                day19 = data.getString("day19");
                                day19DateDisp = data.getString("day19DateDisp");
                                DateDecoration(day19DateDisp,day19);
                                day20 = data.getString("day20");
                                day20DateDisp = data.getString("day20DateDisp");
                                DateDecoration(day20DateDisp,day20);
                                day21 = data.getString("day21");
                                day21DateDisp = data.getString("day21DateDisp");
                                DateDecoration(day21DateDisp,day21);
                                day22 = data.getString("day22");
                                day22DateDisp = data.getString("day22DateDisp");
                                DateDecoration(day22DateDisp,day22);
                                day23 = data.getString("day23");
                                day23DateDisp = data.getString("day23DateDisp");
                                DateDecoration(day23DateDisp,day23);
                                day24 = data.getString("day24");
                                day24DateDisp = data.getString("day24DateDisp");
                                DateDecoration(day24DateDisp,day24);
                                day25 = data.getString("day25");
                                day25DateDisp = data.getString("day25DateDisp");
                                DateDecoration(day25DateDisp,day25);
                                day26 = data.getString("day26");
                                day26DateDisp = data.getString("day26DateDisp");
                                DateDecoration(day26DateDisp,day26);
                                day27 = data.getString("day27");
                                day27DateDisp = data.getString("day27DateDisp");
                                DateDecoration(day27DateDisp,day27);
                                day28 = data.getString("day28");
                                day28DateDisp = data.getString("day28DateDisp");
                                DateDecoration(day28DateDisp,day28);
                                day29 = data.getString("day29");
                                day29DateDisp = data.getString("day29DateDisp");
                                DateDecoration(day29DateDisp,day29);
                                day30 = data.getString("day30");
                                day30DateDisp = data.getString("day30DateDisp");
                                DateDecoration(day30DateDisp,day30);
                                day31 = data.getString("day31");
                                day31DateDisp = data.getString("day31DateDisp");
                              //  saveNameToLocalStorage(day01DateDisp,day01);
                                if (day31.contains("NA"))
                                {
                                }
                                else
                                {
                                    DateDecoration(day31DateDisp,day31);
                                }
                                //   Log.e("Tag","NewDa" + presnts1 + absents1+holidays1 ) ;
                                //  handler.postDelayed(new Runnable() {
                                //      public void run() {
                                //         progressDialog.dismiss();
                                //        }
                                //   }, 10000);
                                 }

                                 disProgress();
                             //   Log.e("Tag","NewDate"+monthName + month);
                            }
                        }
                    }
                    catch (Exception e)

                    {
                        e.printStackTrace();
                    }
                }
                else
                {
                    Toast.makeText(getActivity(),"Something went wrong..!!",Toast.LENGTH_SHORT).show();
                    //progressDialog.dismiss();
                }



            }
            @Override
            public void onFailure(retrofit2.Call<ResponseBody> call, Throwable t)
            {

                //  Toast.makeText(getActivity(),"Something went wrong",Toast.LENGTH_LONG).show();

            }
        });
    }

    void DateDecoration(String dates,String date2)
       {
            String mydate = dates.replace("-","/");
            @SuppressLint("SimpleDateFormat") SimpleDateFormat dateFormat= new SimpleDateFormat("dd/MM/yyyy");
            if (date2.contains("PR"))
            {
                Date date = null;
                try
                {
                    date = dateFormat.parse(mydate);
                } catch (ParseException e)
                {
                    e.printStackTrace();
                }
                CalendarDay day = CalendarDay.from(date);
                oneDayDecorator = new OneDayDecorator(CalendarDay.from(date),"present");
                //   absent.add(day);
            }
            else if(date2 .contains("AB"))
            {
                Date date = null;
                try {
                    date = dateFormat.parse(mydate);
                } catch (ParseException e)
                {
                    e.printStackTrace();
                }
                CalendarDay day = CalendarDay.from(date);
                oneDayDecorator = new OneDayDecorator(CalendarDay.from(date),"absent");
             //   absent.add(day);
            }
            else if ((date2.contains("HL")))
            {

                Date date = null;
                try {
                date = dateFormat.parse(mydate);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                CalendarDay day = CalendarDay.from(date);
                oneDayDecorator = new OneDayDecorator(CalendarDay.from(date),"holiday");
              //  holiday.add(day);

            }
            else if ((date2.contains("WO")))
            {
                Date date = null;
                try {
                    date = dateFormat.parse(mydate);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                CalendarDay day = CalendarDay.from(date);
                oneDayDecorator = new OneDayDecorator(CalendarDay.from(date),"weekoff");
            //    weekoff.add(day);
            }
            else if (date2.contains("AN"))
            {
                Date date = null;
                try {
                    date = dateFormat.parse(mydate);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                CalendarDay day = CalendarDay.from(date);
                oneDayDecorator = new OneDayDecorator(CalendarDay.from(date),"afternoon");
             //   afternoon.add(day);
            }
            else if (date2.contains("FN"))
            {
                Date date = null;
                try {
                    date = dateFormat.parse(mydate);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                CalendarDay day = CalendarDay.from(date);
                oneDayDecorator = new OneDayDecorator(CalendarDay.from(date),"forenoon");

                //   afternoon.add(day);

            }

            materialCalendarView.addDecorator(oneDayDecorator);
         //   EventDecorator eventDecorator = new EventDecorator(Color.parseColor("#FF4CD7C9"), present,getActivity(),"present");
         //   EventDecorator absents = new EventDecorator(Color.parseColor("#ea383c"),absent,getActivity(),"absent");
         //   EventDecorator hols = new EventDecorator(Color.parseColor("#ea383c"),holiday,getActivity(),"holiday");
         //   EventDecorator weekoffs = new EventDecorator(Color.parseColor("#ea383c"),weekoff,getActivity(),"weekoff");
         //   EventDecorator afternoons = new EventDecorator(Color.parseColor("#ea383c"),afternoon,getActivity(),"afternoon");
         //   materialCalendarView.addDecorator(eventDecorator);
        //    materialCalendarView.addDecorator(absents);
        //    materialCalendarView.addDecorator(hols);
        //    materialCalendarView.addDecorator(weekoffs);
        //    materialCalendarView.addDecorator(afternoons);
      }

    private void loadNames()
    {

        Cursor cursor = db.getNames();
        if (cursor.moveToFirst())
        {
            do {
                Name name = new Name(cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_NAME)),
                        cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_STATUS)));
                names.add(name);
            }
            while (cursor.moveToNext());
        }
        nameAdapter = new NameAdapter(getActivity(),0, names);
        // DateDecoration(names.get());
    }
    private void saveNameToLocalStorage(String name,String status) {
       // editTextName.setText("");
        db.addName(name, status);
        Name n = new Name(name, status);
        names.add(n);
      //  refreshList();
    }
    public class NameAdapter extends ArrayAdapter<Name> {

        //storing all the names in the list
        List<Name> names;
        //context object
        private Context context;

        //constructor
        public NameAdapter(Context context ,int resource,List<Name> names) {
            super(context,resource,names  );
            this.context = context;
            this.names = names;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            //getting the layoutinflater
          //  LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            Name name = names.get(position);
            DateDecoration(name.getName(),name.getStatus());
            //getting listview itmes
            //getting the current name
            //setting the name to textview
         return null;
        }
    }
    void  disProgress()
    {
        Handler handler = new Handler();
        String months;

        handler.postDelayed(new Runnable() {
            public void run() {
                progressDialog.dismiss();
            }
        }, 100);
    }
}


