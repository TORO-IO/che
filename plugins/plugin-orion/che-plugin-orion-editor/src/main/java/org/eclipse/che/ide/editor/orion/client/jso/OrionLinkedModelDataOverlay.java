/*******************************************************************************
 * Copyright (c) 2014-2015 Codenvy, S.A.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Codenvy, S.A. - initial API and implementation
 *******************************************************************************/

package org.eclipse.che.ide.editor.orion.client.jso;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.JsArrayString;

import org.eclipse.che.ide.jseditor.client.link.LinkedModelData;

import java.util.List;

/**
 * @author Evgen Vidolob
 */
public class OrionLinkedModelDataOverlay extends JavaScriptObject  implements LinkedModelData{
    protected OrionLinkedModelDataOverlay() {
    }

    @Override
    public final native void setType(String type) /*-{
        this.type = type;
    }-*/;

    @Override
    public final void setValues(List<String> values) {
        JsArrayString arr = JavaScriptObject.createArray().cast();
        for (String value : values) {
            arr.push(value);
        }
        setValues(arr);
    }


    public final native void setValues(JsArrayString val) /*-{
        this.values = val;
    }-*/;

    public static native OrionLinkedModelDataOverlay create() /*-{
        return {};
    }-*/;
}
