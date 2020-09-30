/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.CS250;

import java.util.concurrent.TimeoutException;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.scene.robot.Robot;
import javafx.stage.Stage;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.testfx.api.FxToolkit;
import org.testfx.framework.junit.ApplicationTest;
import org.testfx.service.query.PointQuery;

/**
 *
 * @author Blazi
 */
public abstract class TestFXBase extends ApplicationTest {
    
    
    @Before
    public void setUpClass() throws Exception{
        ApplicationTest.launch(Main.class);
    }
    
    @Override
    public void start(Stage stage) throws Exception{
        stage.show();
    }
    
    @After
    public void afterEachTest() throws TimeoutException{
        FxToolkit.hideStage();
        release(new KeyCode[]{});
        release(new MouseButton[]{});
    }
    
    public <T extends Node> T find(final String query){
        return (T) lookup(query).queryAll().iterator().next();
    }
    
    
}
