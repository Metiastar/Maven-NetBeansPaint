/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.CS250;

import javafx.application.Application;
import javafx.scene.Parent;
import org.junit.Test;
import org.loadui.testfx.GuiTest;
import static org.testfx.api.FxAssert.verifyThat;
import org.testfx.matcher.base.NodeMatchers;
import org.testfx.util.WaitForAsyncUtils;

/**
 *
 * @author Blazi
 */
public class MainTest extends GuiTest{
    final String[] tools = new String[]{"#toolText", "#toolFreeDraw", "#toolLine",
        "#toolCircle", "#toolOval", "#toolSquare", "#toolRectangle",
        "#toolDropper", "#toolSelectMove", "#toolSelectRect"};

    
    @Override
    protected Parent getRootNode() {
        return null;
    }

    @Override
    public void setupStage() throws Throwable {
        new Thread(() -> Application.launch(Main.class))
            .start();
    }
    
    /*
    *
    */
    @Test
    public void testToolbarToggle(){
        for ( String tool: tools){
            click(tool);
        }
    }

    
    
    
}
