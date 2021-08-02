package com.example.finaltest.ui.views.list;

import com.example.finaltest.backend.entity.Pipe;
import com.example.finaltest.backend.entity.Point;
import com.example.finaltest.backend.service.PipeService;
import com.example.finaltest.backend.service.PointService;
import com.example.finaltest.ui.layout.MainLayout;
import com.example.finaltest.ui.layout.PointForm;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@Route(value = "points", layout = MainLayout.class)
@PageTitle("Points | Vaadin CRM")
@CssImport("./styles/shared-styles.css")
public class ListView extends VerticalLayout {

    private final PointForm form;
    Grid<Point> grid = new Grid<>(Point.class);
    TextField filterText = new TextField();
    private PointService pointService;

    public ListView(PointService pointService,
                    PipeService pipeService) {
        this.pointService = pointService;
        addClassName("list-view");
        setSizeFull();
        configureGrid();

        form = new PointForm(pipeService.findAll());
        form.addListener(PointForm.SaveEvent.class, this::saveContact);
        form.addListener(PointForm.DeleteEvent.class, this::deleteContact);
        form.addListener(PointForm.CloseEvent.class, e -> closeEditor());

        Div content = new Div(grid, form);
        content.addClassName("content");
        content.setSizeFull();

        add(getToolBar(), content);
        updateList();

        closeEditor();
    }

    private void deleteContact(PointForm.DeleteEvent evt) {
        pointService.delete(evt.getPoint());
        updateList();
        closeEditor();
    }

    private  void saveContact(PointForm.SaveEvent evt) {
        pointService.save(evt.getPoint());
        updateList();
        closeEditor();
    }

    private void closeEditor() {
        form.setPoint(null);
        form.setVisible(false);
        removeClassName("editing");
    }


    private void updateList() {
        grid.setItems(pointService.findAll(filterText.getValue()));
    }

    private HorizontalLayout getToolBar() {
        filterText.setPlaceholder("Filter by Pipe");
        filterText.setClearButtonVisible(true);
        filterText.setValueChangeMode(ValueChangeMode.LAZY);
        filterText.addValueChangeListener(e -> updateList());

        Button addPointButton = new Button("Add point", click -> addPoint());

        HorizontalLayout toolBar = new HorizontalLayout(filterText, addPointButton);
        toolBar.addClassName("toolbar");
        return toolBar;
    }

    private void addPoint() {
        grid.asSingleSelect().clear();
        editPoint(new Point());
    }

    private void configureGrid() {
        grid.addClassName("point-grid");
        grid.setSizeFull();
        grid.removeColumnByKey("pipe");

        grid.setColumns("x", "y", "z");
        grid.addColumn(point -> {
            Pipe pipe = point.getPipe();
            return pipe == null ? "-" : pipe.getName();
        }).setHeader("Pipe");
        grid.getColumns().forEach(col -> col.setAutoWidth(true));

        grid.asSingleSelect().addValueChangeListener(evt -> editPoint(evt.getValue()));
    }

    private void editPoint(Point point) {
        if (point == null) {
            closeEditor();
        } else {
            form.setPoint(point);
            form.setVisible(true);
            addClassName("editing");
        }
    }
}
