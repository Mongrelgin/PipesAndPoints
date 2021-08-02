package com.example.finaltest.ui.views.plot;

import com.example.finaltest.backend.entity.Pipe;
import com.example.finaltest.backend.entity.Point;
import com.vaadin.flow.component.dependency.JavaScript;
import com.vaadin.flow.component.html.Div;
import elemental.json.Json;
import elemental.json.JsonArray;

import java.util.Set;

@JavaScript("./plotPrinter.js")
public class PlotView extends Div {

    public PlotView() {

    }

    public void cleanPlots() {
        getElement().executeJs("clean()");
    }

    public void setPlots(Set<Pipe> pipes) {
        cleanPlots();
        for (Pipe pps: pipes) {
            JsonArray xArr = Json.createArray();
            JsonArray yArr = Json.createArray();
            JsonArray zArr = Json.createArray();
            int i = 0;
            for (Point pnt: pps.getPoints()) {
                xArr.set(i, pnt.getX());
                yArr.set(i, pnt.getY());
                zArr.set(i, pnt.getZ());
                i++;
            }
            getElement().executeJs("start($0, $1, $2, $3)", pps.getName(), xArr, yArr, zArr);
        }
    }
}
