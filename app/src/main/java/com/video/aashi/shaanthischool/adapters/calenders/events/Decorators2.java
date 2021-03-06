package com.video.aashi.shaanthischool.adapters.calenders.events;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.DayViewDecorator;
import com.prolificinteractive.materialcalendarview.DayViewFacade;

import java.util.Collection;
import java.util.HashSet;

public class Decorators2 implements DayViewDecorator {
    private HashSet<CalendarDay> dates;
    private int color;

    private Context context;
    private  String absent;
    public Decorators2(int color, Collection<CalendarDay> dates, Context context, String absent) {
        this.context = context;
        this.color = color;
        this.dates = new HashSet<>(dates);
        this.absent = absent;
    }
    @Override
    public boolean shouldDecorate(CalendarDay day) {
        return dates.contains(day);
    }

    @Override
    public void decorate(DayViewFacade view) {
        view.addSpan(new StyleSpan(Typeface.BOLD));
       // view.addSpan(new RelativeSizeSpan(1.4f));
        view.addSpan(new ForegroundColorSpan(Color.parseColor("#FF4CD7C9")));
    }
}
