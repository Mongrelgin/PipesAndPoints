package com.example.finaltest.ui.layout;

import com.example.finaltest.backend.entity.Pipe;
import com.example.finaltest.backend.entity.Point;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.converter.StringToDoubleConverter;
import com.vaadin.flow.shared.Registration;

import java.util.List;


public class PointForm extends FormLayout {

    TextField x = new TextField("X");
    TextField y = new TextField("Y");
    TextField z = new TextField("Z");
    ComboBox<Pipe> pipe = new ComboBox<>("Pipe");

    Button save = new Button("Save");
    Button delete = new Button("Delete");
    Button close = new Button("Close");

    Binder<Point> binder = new Binder<>(Point.class);

    public PointForm(List<Pipe> pipes) {
        addClassName("point-form");

        binder.forField(x).withConverter(new StringToDoubleConverter("Error")).bind(Point::getX, Point::setX);
        binder.forField(y).withConverter(new StringToDoubleConverter("Error")).bind(Point::getY, Point::setY);
        binder.forField(z).withConverter(new StringToDoubleConverter("Error")).bind(Point::getZ, Point::setZ);
        binder.forField(pipe).bind(Point::getPipe, Point::setPipe);

        pipe.setItems(pipes);
        pipe.setItemLabelGenerator(Pipe::getName);

        add(
                x,
                y,
                z,
                pipe,
                createButtonsLayout()
        );
    }

    public void setPoint(Point point) {
        binder.setBean(point);
    }

    private Component createButtonsLayout() {
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        delete.addThemeVariants(ButtonVariant.LUMO_ERROR);
        close.addThemeVariants(ButtonVariant.LUMO_TERTIARY);

        save.addClickShortcut(Key.ENTER);
        close.addClickShortcut(Key.ESCAPE);

        save.addClickListener(click -> validateAndSave());
        delete.addClickListener(click -> fireEvent(new DeleteEvent(this, binder.getBean())));
        close.addClickListener(click -> fireEvent(new CloseEvent(this)));

        binder.addStatusChangeListener(evt -> save.setEnabled(binder.isValid()));

        return new HorizontalLayout(save, delete, close);
    }

    private void validateAndSave() {
        if (binder.isValid()) {
            fireEvent(new SaveEvent(this, binder.getBean()));
        }
    }

    public static abstract class PointFormEvent extends ComponentEvent<PointForm> {
        private Point point;

        protected PointFormEvent(PointForm source, Point point) {
            super(source, false);
            this.point = point;
        }

        public Point getPoint() {
            return point;
        }
    }

    public static class SaveEvent extends PointFormEvent {

        SaveEvent(PointForm source, Point point) {
            super(source, point);
        }
    }

    public static class DeleteEvent extends PointFormEvent {

        DeleteEvent(PointForm source, Point point) {
            super(source, point);
        }
    }

    public static class CloseEvent extends PointFormEvent {

        CloseEvent(PointForm source) {
            super(source, null);
        }
    }

    public <T extends ComponentEvent<?>> Registration addListener(Class<T> eventType,
                                                                  ComponentEventListener<T> listener) {
        return getEventBus().addListener(eventType, listener);
    }

}
