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

    public void setInspections(List<Inspection> inspections) {
        this.inspections = inspections;
    }
}
