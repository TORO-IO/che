/*******************************************************************************
 * Copyright (c) 2012-2016 Codenvy, S.A.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Codenvy, S.A. - initial API and implementation
 *******************************************************************************/
package org.eclipse.che.ide.ui.dialogs.input;

import org.eclipse.che.ide.ui.UILocalizationConstant;

import org.eclipse.che.ide.ui.WidgetFocusTracker;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;

import javax.validation.constraints.NotNull;

import static org.eclipse.che.ide.ui.dialogs.input.InputDialogView.ActionDelegate;
import static org.eclipse.che.ide.ui.window.Window.Resources;

/**
 * The footer show on input dialogs.
 *
 * @author Mickaël Leduque
 * @author Artem Zatsarynnyi
 */
public class InputDialogFooter implements IsWidget {

    private static final Resources                   resources = GWT.create(Resources.class);
    /** The UI binder instance. */
    private static       ConfirmWindowFooterUiBinder uiBinder  = GWT.create(ConfirmWindowFooterUiBinder.class);
    /** The i18n messages. */
    @UiField(provided = true)
    UILocalizationConstant messages;
    @UiField
    Button                 okButton;
    @UiField
    Button                 cancelButton;

    HTMLPanel rootPanel;

    /** The action delegate. */
    private       ActionDelegate     actionDelegate;
    private final WidgetFocusTracker widgetFocusTracker;

    @Inject
    public InputDialogFooter(final @NotNull UILocalizationConstant messages, WidgetFocusTracker widgetFocusTracker) {
        this.messages = messages;
        rootPanel = uiBinder.createAndBindUi(this);

        this.widgetFocusTracker = widgetFocusTracker;
        widgetFocusTracker.subscribe(okButton);
        widgetFocusTracker.subscribe(cancelButton);

        okButton.addStyleName(resources.windowCss().primaryButton());
        okButton.getElement().setId("askValue-dialog-ok");

        cancelButton.addStyleName(resources.windowCss().button());
        cancelButton.getElement().setId("askValue-dialog-cancel");
    }

    /**
     * Sets the action delegate.
     *
     * @param delegate
     *         the new value
     */
    public void setDelegate(final ActionDelegate delegate) {
        this.actionDelegate = delegate;
    }

    /**
     * Handler set on the OK button.
     *
     * @param event
     *         the event that triggers the handler call
     */
    @UiHandler("okButton")
    public void handleOkClick(final ClickEvent event) {
        this.actionDelegate.accepted();
    }

    /**
     * Handler set on the cancel button.
     *
     * @param event
     *         the event that triggers the handler call
     */
    @UiHandler("cancelButton")
    public void handleCancelClick(final ClickEvent event) {
        this.actionDelegate.cancelled();
    }

    Button getOkButton() {
        return okButton;
    }

    /** Returns {@code true} if OK button is in the focus and {@code false} - otherwise. */
    boolean isOkButtonInFocus() {
        return widgetFocusTracker.isWidgetFocused(okButton);
    }

    /** Returns {@code true} if Cancel button is in the focus and {@code false} - otherwise. */
    boolean isCancelButtonInFocus() {
        return widgetFocusTracker.isWidgetFocused(cancelButton);
    }

    public void onClose() {
        widgetFocusTracker.unSubscribe(okButton);
        widgetFocusTracker.unSubscribe(cancelButton);
    }

    @Override
    public Widget asWidget() {
        return rootPanel;
    }

    /** The UI binder interface for this component. */
    interface ConfirmWindowFooterUiBinder extends UiBinder<HTMLPanel, InputDialogFooter> {
    }
}
