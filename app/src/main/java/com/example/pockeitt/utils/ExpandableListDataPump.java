package com.example.pockeitt.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ExpandableListDataPump {
    public static HashMap<String, List<String>> getData() {
        HashMap<String, List<String>> expandableListDetail = new HashMap<String, List<String>>();

        List<String> bills = new ArrayList<String>();
        bills.add("Add Bills here");

        List<String> needs = new ArrayList<String>();

        List<String> wants = new ArrayList<String>();


        List<String> savings = new ArrayList<String>();

        expandableListDetail.put("Bills", bills);
        expandableListDetail.put("Needs", needs);
        expandableListDetail.put("Wants", wants);
        expandableListDetail.put("Savings", savings);
        return expandableListDetail;
    }
}
