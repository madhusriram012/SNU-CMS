package com.example.snucms.gymslot;

import com.google.firebase.firestore.DocumentReference;

import java.util.ArrayList;

public class SlotClass {
    public DocumentReference documentReference;
    public String slotName, timing;
    public int totalSlots, remainingSlots;
    public ArrayList<String> names, rollno;

    SlotClass() {
    }

    public SlotClass(DocumentReference documentReference, String slotName, String timing, int totalSlots, int remainingSlots, ArrayList<String> names, ArrayList<String> rollno) {
        this.documentReference = documentReference;
        this.slotName = slotName;
        this.timing = timing;
        this.totalSlots = totalSlots;
        this.remainingSlots = remainingSlots;
        this.names = names;
        this.rollno = rollno;
    }

    public SlotClass setSlot(String slotName) {
        this.slotName = slotName;
        return this;
    }

    /*public SlotClass fromSnapshot(QueryDocumentSnapshot doc) {
        Map<String, Object> slotMap = doc.getData();
        ArrayList<String> namesList =
        SlotClass slot = new SlotClass(
                doc.getId(),
                slotMap.get("timing").toString(),
                Integer.parseInt(slotMap.get("totalSlots").toString()),
                Integer.parseInt(slotMap.get("remainingSlots").toString()),
                slotMap.get("names"),
                slotMap.get("rollno")
        );
        slot.name = doc.getId();
        slot.timing = timing;
        slot.totalSlots = totalSlots;
        slot.remainingSlots = remainingSlots;
        slot.names = names;
        slot.rollno = rollno;
        
    }*/



}
