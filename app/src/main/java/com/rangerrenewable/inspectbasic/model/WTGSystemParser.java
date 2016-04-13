package com.rangerrenewable.inspectbasic.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Stubbed class that parses a WTG system and it's inspections from a spreadsheet
 */
public class WTGSystemParser {

    // Ok this doesn't actually parse a spreadsheet quite yet.. assume the data exists
    // and was imported from a spreadsheet.

    public static WTGSystem importNacelleSystem() {
        WTGSystem nacelle = new WTGSystem("Nacelle");

        // create the inspection list
        List<Inspection> inspections = createNacelleInspectionList();

        // set the list of inspections to the nacelle system
        nacelle.setInspections(inspections);

        return nacelle;
    }

    // begin mass typing...
    private static List<Inspection> createNacelleInspectionList() {
        ArrayList<Inspection> inspections = new ArrayList<>();

        Inspection inspection = new Inspection("Nac-Cl1",
                "Walkways/Steps - Uniform in appearance",
                "Walkways and steps should be clean, free of dirt/mud",
                null,
                false,
                "No Result",
                null,
                null);

        inspections.add(inspection);

        inspection = new Inspection("Nac-Cl2",
                "Walkways/Steps - Clean and dry",
                "Walkways and steps should not have any water puddles",
                null,
                false,
                "No Result",
                null,
                null);

        inspections.add(inspection);

        inspection = new Inspection("Nac-Cl3",
                "Walkways/Steps - No Rust",
                "Walkways and steps should have no signs of rust",
                null,
                false,
                "No Result",
                null,
                null);

        inspections.add(inspection);

        inspection = new Inspection("Nac-Cl4",
                "Walkways/Steps - No FOD",
                "Walkways and steps should have no debris, shavings or apparent trash",
                null,
                false,
                "No Result",
                null,
                null);

        inspections.add(inspection);

        inspection = new Inspection("Jun-Cab1",
                "Junction box - Circuit labels",
                "All wires shall be clearly marked/labelled",
                null,
                false,
                "No Result",
                null,
                null);

        inspections.add(inspection);

        inspection = new Inspection("Jun-Cab2",
                "Junction box - Tight connection",
                "All wire-ferrules firmly seated into terminal block",
                null,
                false,
                "No Result",
                null,
                null);

        inspections.add(inspection);

        inspection = new Inspection("Jun-Cab3",
                "Junction box - No FOD",
                "Junction box should have no debris, shavings, loose screws/nuts/washers or any other Foreign Object Debris (FOD)",
                null,
                false,
                "No Result",
                null,
                null);

        inspections.add(inspection);

        inspection = new Inspection("Jun-Cab4",
                "Junction box - RTD Sensor verification",
                "If properly connected, resistance of RTD circuit should be 109-115 Ohms",
                null,
                false,
                "No Result",
                null,
                null);

        inspections.add(inspection);

        inspection = new Inspection("Psr-1",
                "HPU and Brake Pressure",
                "Record pressure reading for HPU and Brakes",
                null,
                false,
                "No Result",
                null,
                null);

        inspections.add(inspection);
        return inspections;
    }
}
