package model;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

public class Report {

    private final ObjectProperty<String> name = new SimpleObjectProperty<>();
//    private double rate;
//    private double commission;

    public Report() {
        this.name.set("");
    }

    public Report(String name) {
        this.name.set(name);
    }

    public final String getName() {
        return this.name.get();
    }

    public final void setName(String value) {
        this.name.set(value);
    }

    public final ObjectProperty<String> nameProperty() {
        return this.name;
    }


}