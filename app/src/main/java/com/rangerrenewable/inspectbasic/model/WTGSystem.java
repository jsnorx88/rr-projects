package com.rangerrenewable.inspectbasic.model;

import java.util.ArrayList;
import java.util.List;

/**
 * WTGSystem
 *  - Contains Inspection List and other fields for the system type
 */
public class WTGSystem {

    /**
     * Current Known System Types
     *
     *  Blade
     *  Rotor
     *  Nacelle
     *  Gearbox
     *  Transformer
     *  Tower
     */

    // fields
    private String name;
    private List<Inspection> inspections;

    public WTGSystem(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setInspections(List<Inspection> inspections) {
        this.inspections = inspections;
    }

    public List<Inspection> getInspections() { return this.inspections; };

    public int getIndexForInspection(Inspection inspection) {
        // hacky .. tweak.. this..
        for (int i = 0; i < this.inspections.size() - 1; i++) {
            Inspection thisInspection = this.inspections.get(i);
            if (inspection.getDefectCode().equals(thisInspection.getDefectCode())) {
                return i;
            }
        }
        // indicate it is done
        return -1;
    }

    public Inspection getInspection(int nextInspectionIndex) {
        return inspections.get(nextInspectionIndex);
    }
}
