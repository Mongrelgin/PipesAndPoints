package com.example.finaltest.ui.views.graph;

import com.example.finaltest.backend.entity.Pipe;
import com.example.finaltest.backend.service.PipeService;
import com.example.finaltest.ui.layout.MainLayout;
import com.example.finaltest.ui.views.plot.PlotView;
import com.vaadin.flow.component.AttachEvent;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import java.util.LinkedList;
import java.util.List;

@Route(value = "graph", layout = MainLayout.class)
@PageTitle("Graphs | Vaadin CRM")
@CssImport("./styles/shared-styles.css")
public class GraphView extends VerticalLayout {

    private List<Pipe> pipesForGraph = new LinkedList<>();
    private PipeService pipeService;
    private Grid<Pipe> grid = new Grid<>(Pipe.class);
    private TextField newName = new TextField("Enter new Pipe");
    private Button addButton = new Button("Add");
    private Button deletePipes = new Button("Delete");

    private PlotView plotView = new PlotView();

    public GraphView(PipeService pipeService) {
        this.pipeService = pipeService;
        addClassName("graph-view");
        setSizeFull();
        configureGrid();

        grid.addDropListener(c -> plotView.cleanPlots());
        grid.addItemClickListener(c -> {
            if (grid.getSelectedItems().size() > 0) {
                plotView.setPlots(grid.getSelectedItems());
            } else {
                //plotView.cleanPlots();
                plotView.setPlots(grid.getSelectedItems());
                //remove(plotView);
            }
            //System.out.println("AAAAAAAa");
        });

        addButton.addClickListener(c -> {
            if (!newName.isEmpty()) {
                pipeService.save(new Pipe(newName.getValue()));
            }
            updateList();
            newName.clear();
        });

        deletePipes.addClickListener(c -> {
           showDialog();
        });

        HorizontalLayout addHor = new HorizontalLayout(newName, addButton, deletePipes);
        addHor.setDefaultVerticalComponentAlignment(Alignment.BASELINE);

        plotView.setId("pipesPlot");

        VerticalLayout gridVL = new VerticalLayout(grid);
        VerticalLayout plotVL = new VerticalLayout(plotView);
        //gridVL.setHeight("50%");
        plotVL.setHeight("50%");
        //gridVL.setWidth("50%");
        plotVL.setWidth("50%");

        gridVL.setSizeFull();
        //plotVL.setSizeFull();

        add(addHor, gridVL, plotVL);
        setDefaultHorizontalComponentAlignment(Alignment.CENTER);
        updateList();
    }

    private void showDialog() {
        Button delete = new Button("Delete");
        Button cancel = new Button("Cancel");
        HorizontalLayout buttons = new HorizontalLayout(delete, cancel);

        Dialog dialog = new Dialog(new Span("Delete pipes?"), buttons);

        delete.addThemeVariants(ButtonVariant.LUMO_ERROR);
        cancel.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
        dialog.setCloseOnEsc(true);
        delete.addClickShortcut(Key.ENTER);

        cancel.addClickListener(buttonClickEvent -> dialog.close());
        delete.addClickListener(buttonClickEvent -> {
            pipeService.delete(grid.getSelectedItems());
            updateList();
            dialog.close();
        });

        dialog.open();
    }

    private void updateList() {
        grid.setItems(pipeService.findAll());
    }

    private void configureGrid() {
        grid.addClassName("pipe-grid");
        grid.setSizeFull();
        grid.getColumns().forEach(col -> col.setAutoWidth(true));

        grid.removeColumnByKey("id");
        grid.removeColumnByKey("persisted");
        grid.removeColumnByKey("points");

        grid.setSelectionMode(Grid.SelectionMode.MULTI);

        grid.addItemClickListener(e -> {
            Pipe item = e.getItem();
            if (grid.getSelectedItems().contains(item)) {
                grid.deselect(item);
                pipesForGraph.remove(item);
            } else {
                grid.select(e.getItem());
                pipesForGraph.add(item);
            }
        });
    }

    public void onAttach(AttachEvent event) {
        grid.getElement().executeJs("this.getElementsByTagName(\"vaadin-grid-flow-selection-column\")[0].hidden = true;");
    }
}
