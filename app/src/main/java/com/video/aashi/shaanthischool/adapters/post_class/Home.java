package com.video.aashi.shaanthischool.adapters.post_class;

public class Home {

    String classId;
      String  studentId;
       String locId;
         String  classGeneralId;
          String viewMore;
          String academicYearId;
    String  parentLoginCode;
    String mobSession;

          public  Home(String classId,String studentId,String locId,String classGeneralId,String viewMore,
                       String academicYearId,String studParentCode,String mobSession)
          {

              this.classId = classId;
              this.studentId = studentId;
              this.locId = locId;
              this.classGeneralId = classGeneralId;
              this.viewMore = viewMore;
              this.academicYearId = academicYearId;
              this.parentLoginCode = studParentCode;
              this.mobSession = mobSession;
          }



}
