package com.example.snucms.callbob;

import com.google.firebase.firestore.DocumentReference;

import com.google.firebase.Timestamp;

import java.util.Map;

public class IssueClass {

    public DocumentReference documentReference;
    public String id, title, description, location;
    public Timestamp genTime, fixTime;
    public boolean ack, studentVerify, callBobVerify;

    public IssueClass() {
        fixTime = null;
        ack = false;
        studentVerify = false;
        callBobVerify = false;
    }

    public IssueClass(DocumentReference documentReference, String id, String title, String description, String location, Timestamp genTime, Timestamp fixTime, boolean ack, boolean studentVerify, boolean callBobVerify) {
        this.documentReference = documentReference;
        this.id = id;
        this.title = title;
        this.description = description;
        this.location = location;
        this.genTime = genTime;
        this.fixTime = fixTime;
        this.ack = ack;
        this.studentVerify = studentVerify;
        this.callBobVerify = callBobVerify;
    }

    public static IssueClass fromMap(
            //DocumentReference documentReference, String id, String title, String description, String location, Timestamp genTime, Timestamp fixTime, boolean ack, boolean studentVerify, boolean callBobVerify
            Map<String, Object> data
    ) {
        IssueClass temp = new IssueClass();
        temp.documentReference = (DocumentReference) data.get("documentReference");
        temp.id = (String) data.get("id");
        temp.title = (String) data.get("title");
        temp.description = (String) data.get("description");
        temp.location = (String) data.get("location");
        temp.genTime = (Timestamp) data.get("genTime");
        temp.fixTime = (Timestamp) data.get("fixTime");
        temp.ack = (boolean) data.get("ack");
        temp.studentVerify = (boolean) data.get("studentVerify");
        temp.callBobVerify = (boolean) data.get("callBobVerify");

        return temp;
    }

    public IssueClass setId(String id) {
        this.id = id;
        return this;
    }

    public Timestamp getGenTime() {
        return genTime;
    }

    public Timestamp getFixTime() {
        return fixTime;
    }

    public boolean isAck() {
        return ack;
    }

    public boolean isStudentVerify() {
        return studentVerify;
    }

    public boolean isCallBobVerify() {
        return callBobVerify;
    }

    public void setGenTime(com.google.firebase.Timestamp genTime) {
        this.genTime = (Timestamp) genTime;
    }

    public void setFixTime(com.google.firebase.Timestamp fixTime) {
        this.fixTime = (Timestamp) fixTime;
    }

    public void setAck(boolean ack) {
        this.ack = ack;
    }

    public void setStudentVerify(boolean studentVerify) {
        this.studentVerify = studentVerify;
    }

    public void setCallBobVerify(boolean callBobVerify) {
        this.callBobVerify = callBobVerify;
    }

}
