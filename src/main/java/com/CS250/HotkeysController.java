/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.CS250;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

/**
 * FXML Controller class
 *
 * @author Blazi
 */
public class HotkeysController implements Initializable {
    
    public String[] hotkeys = new String[3];
    public String[] changedHotkeys = new String[3];
    LayoutController lc;
    
    @FXML
    private TextField keySave;
    @FXML
    private TextField keyUndo;
    @FXML
    private TextField keyRedo;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        keySave.setText("CTRL+S");
        keyUndo.setText("CTRL+Z");
        keyRedo.setText("CTRL+Shift+Z");
        
        hotkeys[0] = "CTRL+S";
        hotkeys[1] = "CTRL+Z";
        hotkeys[2] = "CTRL+Shift+Z";
        changedHotkeys[0] = "CTRL+S";
        changedHotkeys[1] = "CTRL+Z";
        changedHotkeys[2] = "CTRL+Shift+Z";
        
    }
    
    void setLayoutController(LayoutController layout){
        lc = layout;
    }
    
    /**
     *  Hotkey order goes by: Save, Save As, Undo, Redo
     */
    void setHotkeys(String[] prevHotkeys){
        System.arraycopy(prevHotkeys, 0, hotkeys, 0, prevHotkeys.length);
    }
    
    @FXML
    private void hotkeysSaveChanges(ActionEvent event) {
        
    }

    @FXML
    private void hotkeysApplyChanges(ActionEvent event) {
    }

    @FXML
    private void hotkeysCancelChanges(ActionEvent event) {
        
    }

    @FXML
    private void keyRedoChange(ActionEvent event) {
       /* 
        if (keyRedo.isFocused()){
            keyRedo.setText("");
            keyRedo.setOnKeyPressed(new EventHandler<KeyEvent>(){
                @Override
                public void handle(KeyEvent t) {
                    keyRedo.setText(t.getCode().toString());
                }
            
            });
        }*/
    }

    @FXML
    private void keySaveChange(KeyEvent event) {
    }

    @FXML
    private void keySaveChange(MouseEvent event) {
    }
    
    @FXML
    private void keyUndoChange(MouseEvent event) {
        keyUndo.setText("");
        
        keyUndo.setOnKeyPressed(new EventHandler<KeyEvent>(){
            @Override
            public void handle(KeyEvent t) {
                if (t.getCode() == KeyCode.ENTER){
                    keyUndo.commitValue();
                    keyUndo.setText(keyUndo.getText());
                    changedHotkeys[1] = keyUndo.getText();
                }else if(keyUndo.getText().isEmpty()){
                    keyUndo.setText(t.getCode().toString());
                }else{
                    keyUndo.setText(keyUndo.getText() + "+" + t.getCode().toString());
                }

            }
        });

        if (keyUndo.equals("")){
            keyUndo.setText(hotkeys[1]);
        }
        
    }
    
    
}
