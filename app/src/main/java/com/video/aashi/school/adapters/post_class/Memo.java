package com.video.aashi.school.adapters.post_class;

public class Memo {

    String classId;
    String studentId;
    String locId;
    String classGeneralId;
    String parentLoginCode;
    String mobSession;

    public  Memo(String classId,String studentId,String locId,String classGeneralId,String parentLoginCode,String mobSession)
    {
        this.classId = classId;
        this.studentId = studentId;
        this.locId = locId;
        this.classGeneralId = classGeneralId;
        this .parentLoginCode = parentLoginCode;
        this.mobSession = mobSession;

    }

}
