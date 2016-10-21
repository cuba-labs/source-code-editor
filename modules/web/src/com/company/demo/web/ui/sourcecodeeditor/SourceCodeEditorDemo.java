package com.company.demo.web.ui.sourcecodeeditor;

import com.haulmont.cuba.gui.components.AbstractWindow;
import com.haulmont.cuba.gui.components.CheckBox;
import com.haulmont.cuba.gui.components.LookupField;
import com.haulmont.cuba.gui.components.SourceCodeEditor;
import com.haulmont.cuba.gui.components.SourceCodeEditor.Mode;
import com.haulmont.cuba.gui.components.autocomplete.Suggestion;
import com.haulmont.cuba.gui.data.CollectionDatasource;
import com.haulmont.cuba.security.entity.User;

import javax.inject.Inject;
import java.util.*;

public class SourceCodeEditorDemo extends AbstractWindow {
    @Inject
    private LookupField modeField;
    @Inject
    private SourceCodeEditor simpleCodeEditor;
    @Inject
    private CheckBox handleTabKeyCheck;
    @Inject
    private CheckBox highlightActiveLineCheck;
    @Inject
    private CheckBox printMarginCheck;
    @Inject
    private CheckBox showGutterCheck;
    @Inject
    private CollectionDatasource<User, UUID> usersDs;

    @Inject
    private SourceCodeEditor suggesterCodeEditor;

    @Override
    public void init(Map<String, Object> params) {
        initSimpleTab();
        initSuggesterTab();

    }

    private void initSimpleTab() {
        Map<String, Object> modes = new HashMap<>();
        for (Mode mode : Mode.values()) {
            modes.put(mode.toString(), mode);
        }

        modeField.setOptionsMap(modes);
        modeField.setValue(Mode.Text);
        modeField.addValueChangeListener(e -> {
            if (e.getValue() != null) {
                simpleCodeEditor.setMode((Mode) e.getValue());
            }
        });

        handleTabKeyCheck.setValue(simpleCodeEditor.isHandleTabKey());
        handleTabKeyCheck.addValueChangeListener(e -> {
            if (e.getValue() != null) {
                simpleCodeEditor.setHandleTabKey((Boolean) e.getValue());
            }
        });

        highlightActiveLineCheck.setValue(simpleCodeEditor.isHighlightActiveLine());
        highlightActiveLineCheck.addValueChangeListener(e -> {
            if (e.getValue() != null) {
                simpleCodeEditor.setHighlightActiveLine((Boolean) e.getValue());
            }
        });

        printMarginCheck.setValue(simpleCodeEditor.isShowPrintMargin());
        printMarginCheck.addValueChangeListener(e -> {
            if (e.getValue() != null) {
                simpleCodeEditor.setShowPrintMargin((Boolean) e.getValue());
            }
        });

        showGutterCheck.setValue(simpleCodeEditor.isShowGutter());
        showGutterCheck.addValueChangeListener(e -> {
            if (e.getValue() != null) {
                simpleCodeEditor.setShowGutter((Boolean) e.getValue());
            }
        });
    }

    private void initSuggesterTab() {
        suggesterCodeEditor.setSuggester((source, text, cursorPosition) -> {
            List<Suggestion> suggestions = new ArrayList<>();
            usersDs.refresh();
            for (User user : usersDs.getItems()) {
                suggestions.add(new Suggestion(source, user.getLogin(), user.getName(), null, -1, -1));
            }
            return suggestions;
        });
    }
}