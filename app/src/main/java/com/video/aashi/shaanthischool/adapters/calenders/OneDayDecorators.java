package com.video.aashi.shaanthischool.adapters.calenders;

import android.graphics.Typeface;
import android.text.style.RelativeSizeSpan;
import android.text.style.StyleSpan;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.DayViewDecorator;
import com.prolificinteractive.materialcalendarview.DayViewFacade;

import java.util.Date;

public class OneDayDecorators implements DayViewDecorator {

  private CalendarDay date;

  public OneDayDecorators() {
    date = CalendarDay.today();
  }
  @Override
  public boolean shouldDecorate(CalendarDay day) {
    return date != null && day.equals(date);
  }
  @Override
  public void decorate(DayViewFacade view) {
    view.addSpan(new StyleSpan(Typeface.BOLD));
    view.addSpan(new RelativeSizeSpan(1.4f));
  }
  public void setDate(Date datess)
  {
    this.date = CalendarDay.from(datess);

  }
}
