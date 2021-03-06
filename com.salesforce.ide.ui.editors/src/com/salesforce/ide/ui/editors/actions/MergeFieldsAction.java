/*******************************************************************************
 * Copyright (c) 2014 Salesforce.com, inc..
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Salesforce.com, inc. - initial API and implementation
 ******************************************************************************/
package com.salesforce.ide.ui.editors.actions;

import org.apache.log4j.Logger;
import org.eclipse.core.resources.IFile;
import org.eclipse.jface.action.IAction;
import org.eclipse.ui.IEditorActionDelegate;
import org.eclipse.ui.IEditorPart;

import com.salesforce.ide.core.internal.utils.Utils;
import com.salesforce.ide.core.project.ForceProjectException;
import com.salesforce.ide.ui.ForceIdeUIPlugin;
import com.salesforce.ide.ui.actions.BaseAction;
import com.salesforce.ide.ui.editors.visualforce.SnippetDialog;
import com.salesforce.ide.ui.editors.visualforce.SnippetDialogController;
import com.salesforce.ide.ui.editors.visualforce.VisualForceMultiPageEditor;

public class MergeFieldsAction extends BaseAction implements IEditorActionDelegate {

    private static final Logger logger = Logger.getLogger(MergeFieldsAction.class);

    public MergeFieldsAction() throws ForceProjectException {
        super();
    }

    @Override
    public void init() {}

    @Override
    public void execute(IAction action) {
        VisualForceMultiPageEditor multiPageEditor = getSControlMultiPageEditor();
        SnippetDialog snippetDialog =
                new SnippetDialog(multiPageEditor.getTextEditor().getSite().getShell(), multiPageEditor.getTextEditor());
        try {
            snippetDialog.setSnippetDialogController(new SnippetDialogController());
        } catch (ForceProjectException e) {
            logger.warn("Unable to get snippet controller", e);
            Utils.openError("Action Error", "Unable to open Merge Field: " + e.getMessage());
            return;
        }
        IFile file = multiPageEditor.getEditorInputFile();
        snippetDialog.setProject(file.getProject());
        snippetDialog.open();
    }

    @Override
    public void setActiveEditor(IAction action, IEditorPart targetEditor) {
    // nothing here
    }

    private VisualForceMultiPageEditor getSControlMultiPageEditor() {
        workbenchWindow = ForceIdeUIPlugin.getDefault().getWorkbench().getActiveWorkbenchWindow();
        return (VisualForceMultiPageEditor) workbenchWindow.getActivePage().getActiveEditor();
    }
}
