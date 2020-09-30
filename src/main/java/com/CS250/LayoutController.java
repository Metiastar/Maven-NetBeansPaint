/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.CS250;


import java.awt.image.RenderedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.Stack;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Alert;
import static javafx.scene.control.Alert.AlertType.*;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Slider;
import javafx.scene.control.Tab;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelReader;
import javafx.scene.image.WritableImage;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import static javafx.scene.paint.Color.*;
import javafx.scene.shape.Circle;
import javafx.scene.shape.StrokeLineCap;
import javafx.scene.shape.StrokeLineJoin;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Popup;
import javafx.stage.Stage;
import javafx.util.converter.IntegerStringConverter;
import javax.imageio.ImageIO;

/**
 * FXML Controller class
 *
 * @author Blazi
 */
public class LayoutController implements Initializable {
   
    
    public final ToggleGroup toolsDrawing = new ToggleGroup();
    public final ToggleGroup toolsBrushes = new ToggleGroup();
    private ActionEvent trigger = null;
    
    @FXML
    public Canvas canvasTable;
    @FXML
    private MenuItem Import;
    @FXML
    private MenuItem SaveAs;
    @FXML
    public MenuItem Save;
    @FXML
    public MenuItem Redo;
    @FXML
    public MenuItem Undo;
    @FXML
    private TextField lineWidth;
    @FXML
    private Slider sliderLineWidth;
    @FXML
    private TextField lineOpacity;
    @FXML
    private Slider sliderOpacity;
    @FXML
    private ToggleButton brushPen;
    @FXML
    private ToggleButton brushEraser;
    @FXML
    private ColorPicker colorPicker;
    @FXML
    private TextField colorHex;
    @FXML
    private Circle colorCircle;
    @FXML
    private Circle lineCircle;
    @FXML
    private ToggleButton toolText;
    @FXML
    private ToggleButton toolFreeDraw;
    @FXML
    private ToggleButton toolLine;
    @FXML
    private ToggleButton toolSquare;
    @FXML
    private ToggleButton toolCircle;
    @FXML
    private ToggleButton toolOval;
    @FXML
    private ToggleButton toolRectangle;
    @FXML
    private ToggleButton toolDropper;
    @FXML
    private ToggleButton toolSelectRect;
    @FXML
    private ToggleButton toolSelectMove;
    @FXML
    private BorderPane parent;
    @FXML
    private Tab initialTab;
    @FXML
    private Label lineText;
    @FXML
    private TextArea lineTextInput;
    @FXML
    private HBox lineTextOptions;
    @FXML
    private ScrollPane canvasScroll;
    @FXML
    private MenuItem Zoom_In;
    @FXML
    private MenuItem Zoom_Out;
    @FXML
    private MenuItem Zoom_Reset;
    
    
    
    
    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        GraphicsContext c = canvasTable.getGraphicsContext2D();
        c.setFill(WHITE);                                              
        c.fillRect(0,0,canvasTable.getWidth(),canvasTable.getHeight());
        c.setFill(BLACK);
        c.setStroke(BLACK);
        c.setLineWidth(2);
        c.setLineCap(StrokeLineCap.ROUND);
        colorPicker.setValue(BLACK);
        
        
        lineWidth.setTextFormatter(new TextFormatter<>(new IntegerStringConverter())); 
        lineOpacity.setTextFormatter(new TextFormatter<>(new IntegerStringConverter()));
        lineWidth.setText("2");
        lineCircle.setRadius(1);
        lineTextInput.setText("");
        
        /*Set drawing tool toggle group*/
        toolText.setToggleGroup(toolsDrawing);
        toolFreeDraw.setToggleGroup(toolsDrawing);
        toolLine.setToggleGroup(toolsDrawing);
        toolSquare.setToggleGroup(toolsDrawing);
        toolRectangle.setToggleGroup(toolsDrawing);
        toolCircle.setToggleGroup(toolsDrawing);
        toolOval.setToggleGroup(toolsDrawing);
        toolDropper.setToggleGroup(toolsDrawing);
        toolSelectRect.setToggleGroup(toolsDrawing);
        toolSelectMove.setToggleGroup(toolsDrawing);
        toolFreeDraw.setSelected(true);
        
        /*Set brush tool toggle group*/
        brushPen.setToggleGroup(toolsBrushes);
        brushEraser.setToggleGroup(toolsBrushes);
        brushPen.setSelected(true);
        
        
        /*default hotkeys*/
        Save.setAccelerator(KeyCombination.keyCombination("CTRL+S"));
        SaveAs.setAccelerator(KeyCombination.keyCombination("CTRL+Shift+S"));
        Undo.setAccelerator(KeyCombination.keyCombination("CTRL+Z"));
        Redo.setAccelerator(KeyCombination.keyCombination("CTRL+Shift+Z"));
        Zoom_In.setAccelerator(KeyCombination.keyCombination("CTRL+N"));
        Zoom_Out.setAccelerator(KeyCombination.keyCombination("CTRL+M"));
        Zoom_Reset.setAccelerator(KeyCombination.keyCombination("CTRL+O"));
        
        canvasDataStackAdd();
    }    
    
    /*################## Window Functions ##################*/
    @FXML
    private void windowAbout(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(Main.class.getResource("About.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
       
        Stage About = new Stage();
        
        About.setTitle("About NBPaint");
        About.setScene(scene);
        About.show();
    }

    @FXML
    private void windowHotkeys(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(Main.class.getResource("Hotkeys.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        Stage About = new Stage();
        About.setTitle("About NBPaint");
        About.setScene(scene);
        About.show();
    }
    
    @FXML
    private void windowResize(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(Main.class.getResource("CanvasResize.fxml"));
        Parent root = loader.load();
        Popup resize = new Popup();
        
        
        resize.getContent().addAll(root);
        
        CanvasResizeController CR = loader.getController();
        
        int[] values = new int[2];
        values[0] = (int) canvasTable.getWidth();
        values[1] = (int) canvasTable.getHeight();
        CR.setInitialValues(values);
        CR.setPopup(resize);
        CR.setLayoutController(this);
        
        
        resize.show(canvasTable.getScene().getWindow());
    }
    
    
    /*################## File Functions ##################*/
    private FileChooser fc = new FileChooser();
    private FileChooser current_file = new FileChooser();
    
    
    @FXML
    private void handleImport(ActionEvent event) {       
        fc.setTitle("Import Image");                                            
        fc.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Image File (*.jpg) (*.png) (*.bmp)", "*.jpg","*.png","*.bmp"));
        File selected = fc.showOpenDialog(null);
        
        if(selected != null){
            try{
                Image image = new Image(selected.toURI().toURL().toString());   
                //System.out.println(image.getUrl());

                if(image.getWidth() > canvasTable.getWidth()){                  //This is just to resize the window incase the image file is too big, might change later on once a possible resize tool is added
                    canvasTable.setWidth(image.getWidth());    
                }
                if(image.getHeight() > canvasTable.getHeight()){
                    canvasTable.setHeight(image.getHeight());
                }

                canvasTable.getGraphicsContext2D().drawImage(image, 0, 0, image.getWidth(), image.getHeight());             
            }catch(Exception e){
                System.out.println(e);
            }

            fc.getExtensionFilters().removeAll(fc.getExtensionFilters());
        }
        canvasDataStackAdd();
    }

    @FXML
    private void handleSaveAs(ActionEvent event) {
        fc.setTitle("Save Image As");
        if (current_file.getInitialFileName() != null){
            fc.setInitialFileName(current_file.getInitialFileName());
        }
        
        fc.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("(*.png)", "*.png"),
                new FileChooser.ExtensionFilter("(*.jpg)", "*.jpg"),
                new FileChooser.ExtensionFilter("(*.bmp)", "*.bmp"));                               
        File outputSA = fc.showSaveDialog(canvasTable.getScene().getWindow());
        
        if (outputSA != null){
            try {
                WritableImage writableImage = new WritableImage((int)canvasTable.getWidth(), (int)canvasTable.getHeight()); 
                canvasTable.snapshot(null, writableImage);                                                                  
                RenderedImage ri = SwingFXUtils.fromFXImage(writableImage, null);     
                
                String filter = fc.getSelectedExtensionFilter().getExtensions().get(0);                                     //puts correct extension on image file
                filter = filter.substring(2);
                
                if (fc.getInitialFileName() != null && !fc.getInitialFileName().contains(filter)){
                    Alert alert = handleFileExtensionAlert(filter);
                    Optional<ButtonType> result = alert.showAndWait();
                    if (result.isPresent() && result.get() == ButtonType.OK) {
                        fc.setInitialFileName(outputSA.getName() + filter);
                        ImageIO.write(ri, "png", outputSA);                         
                    }
                    
                }else if (fc.getInitialFileName() != null && fc.getInitialFileName().contains(filter)){
                    ImageIO.write(ri, "png", outputSA); 
                }else{
                    fc.setInitialFileName(outputSA.getName() + filter);
                    ImageIO.write(ri, "png", outputSA); 
                }
                
                current_file.setInitialDirectory(outputSA);
                current_file.setInitialFileName(outputSA.getName());
            } catch (IOException ex) {
                System.out.println(ex.getMessage());
            }
        }
        
        fc.getExtensionFilters().removeAll(fc.getExtensionFilters());
        
    }

    @FXML
    public void handleSave(ActionEvent event) {
        if(current_file.getInitialDirectory() != null){
            File outputS = current_file.getInitialDirectory().getAbsoluteFile();
            try{
                WritableImage writableImage = new WritableImage((int)canvasTable.getWidth(), (int)canvasTable.getHeight()); 
                canvasTable.snapshot(null, writableImage);                                                                  
                RenderedImage ri = SwingFXUtils.fromFXImage(writableImage, null);                                           

                ImageIO.write(ri, "png", outputS);                              

            } catch (IOException ex) {
                System.out.println(ex.getMessage());
            }
            
        } else {
            handleSaveAs(trigger);
        }
    }
    
    public Alert handleFileExtensionAlert(String filter){
        Alert alertbox = new Alert(CONFIRMATION);
        alertbox.setContentText("Warning! There may be lossy conversion from " +
                                fc.getInitialFileName().substring(fc.getInitialFileName().length()-3) +
                                " to " + filter + ".");
        return alertbox;
    }
    
    /*################## Line Style Functions ##################*/
    
    
    @FXML
    private void changeLineWidth(ActionEvent event) {
        GraphicsContext c = canvasTable.getGraphicsContext2D();
        int value = Integer.parseInt(lineWidth.getText());
        c.setLineWidth(value);
        lineCircle.setRadius(value/2);
        lineText.setFont(new Font("Text", value));
        lineText.setTranslateX(-value/2);
        lineText.setTranslateY(-value/2);
        if(value != sliderLineWidth.getValue()){
            sliderLineWidth.adjustValue(value);
        }
        
    }
    
    @FXML
    private void changeLineWidthSlider(MouseEvent event) {
        String value = Integer.toString((int)sliderLineWidth.getValue());
        lineWidth.setText(value);
        changeLineWidth(trigger);
    }
    
    @FXML
    private void colorChooser(ActionEvent event){
        GraphicsContext c = canvasTable.getGraphicsContext2D();
        c.setFill(colorPicker.getValue());
        c.setStroke(colorPicker.getValue());
        colorHex.setText("#"+ colorPicker.getValue().toString().substring(2, 8).toUpperCase());
        colorCircle.setFill(colorPicker.getValue());
        lineCircle.setFill(colorPicker.getValue());
        lineText.setTextFill(colorPicker.getValue());
        
        
    }
    
    @FXML
    private void changeColorHex(ActionEvent event) {
        
        String shortenColorCode = colorHex.getText().substring(1).toLowerCase();
        String shortencolorChooser = colorPicker.getValue().toString().substring(2,8);
        
        if( !shortencolorChooser.equals(shortenColorCode) ){
            Color h = Color.web(colorHex.getText());
            colorPicker.setValue(h);
            colorChooser(trigger);
        }
        
    }
    
    private void colorDrop(MouseEvent mouse){
        GraphicsContext c = canvasTable.getGraphicsContext2D();
        String curCanvas = URLStack.peek();
        Image image = new Image(curCanvas);
        PixelReader pixels = image.getPixelReader();
        Color color = pixels.getColor((int)mouse.getX(), (int)mouse.getY());
        colorPicker.setValue(color);
        colorHex.setText("#"+ colorPicker.getValue().toString().substring(2, 8).toUpperCase());
        c.setFill(colorPicker.getValue());
        c.setStroke(colorPicker.getValue());
        colorCircle.setFill(colorPicker.getValue());
        lineCircle.setFill(colorPicker.getValue());
    }
    
    @FXML
    private void changeLineOpacity(ActionEvent event) {
    }

    private void switchBrush(GraphicsContext c) {
        String brush = toolsBrushes.getSelectedToggle().toString();
        
        if(brush.contains("Pen")){
            c.setLineCap(StrokeLineCap.ROUND);
            c.setLineJoin(StrokeLineJoin.ROUND);
        }
        /*Colors using background color; Once transparency is figured out, eraser will be changed to make canvas transparent*/
        if(brush.contains("Eraser")){
            c.setFill(WHITE);
            c.setStroke(WHITE);
        }
        
    }

    /**
     * Changes graphics context color and width to 
     * indicate the use of functions that require unique appearances.
     * 
     */
    @FXML
    private void specToolSwitch(ActionEvent event) {
        GraphicsContext c = canvasTable.getGraphicsContext2D();
        if ( toolSelectRect.isSelected()){
            c.setFill(GREY);
            c.setStroke(GREY);
            c.setLineDashes(15.0);
            c.setLineWidth(1.0);
        }
        if ( toolText.isSelected()){
            lineCircle.setVisible(false);
            lineText.setVisible(true);
            lineTextOptions.setVisible(true);
        }
    }

    @FXML
    private void drawToolSwitch(ActionEvent event) {
        GraphicsContext c = canvasTable.getGraphicsContext2D();
        if (lineCircle.visibleProperty().get() == false){
            lineCircle.setVisible(true);
            lineText.setVisible(false);
            lineTextOptions.setVisible(false);
        }
        
        c.setFill(colorPicker.getValue());
        c.setStroke(colorPicker.getValue());
        c.setLineWidth(Integer.parseInt(lineWidth.getText()));
        
    }
    
    
    /*################## Canvas Functions ##################*/
    
    private Canvas tempCanvas = new Canvas();
    private boolean selectOn = false;
    private double[] selectDimensions = new double[4];
    private double selectStartX, selectStartY, selectEndX, selectEndY;
    private double startMoveX, startMoveY, offsetMoveX, offsetMoveY;
    private WritableImage selection;
    
    Tools t = new Tools();
    
   /**
    * Registers the selected ToggleButton and
    * prepares the GraphicsContext in accordance to the ToggleButton's name.
    * Does nothing if no ToggleButton is selected.
    * 
    *@param mouse
    */
    @FXML
    private void drawInit(MouseEvent mouse) {
        
        GraphicsContext c = canvasTable.getGraphicsContext2D();
        GraphicsContext tempC = tempCanvas.getGraphicsContext2D();
        switchBrush(c);
        switchBrush(tempC);
        
        if(toolsDrawing.getSelectedToggle() != null){
            canvasDataStackAdd();
            
            String toolstring = toolsDrawing.getSelectedToggle().toString();
            t.setGraphicsContext(c);
            t.setToolstring(toolstring);
            
            
            /**************Drawing Tools**************/
            if ( toolstring.contains("FreeDraw") ){
                t.freeDrawStart(mouse);
            }
            if( toolstring.contains("Line")){
                t.lineStart(mouse);
            }
            if( toolstring.contains("Circle") || toolstring.contains("Oval")){
                t.ellipseStart(mouse);
            }
            if( toolstring.contains("Square") || toolstring.contains("Rectangle")){
                t.rectangleStart(mouse);
            }
            if( toolstring.contains("Text")){
                Text text = new Text();
                if(!"".equals(lineTextInput.getText())){
                    text.setText(lineTextInput.getText());
                    t.textStamp(mouse, text.getText(), text.getFont().getSize());
                }
                
            }
            
            /**************Fill Tools**************/
            if( toolstring.contains("Dropper")){
                colorDrop(mouse);
            }
            
            /************Selection Tools************/
            if( toolstring.contains("SelectRect")){
                System.out.println("Rectangle Select used");
                selectStartX = mouse.getX();
                selectStartY = mouse.getY();
                selectEndX = selectStartX;
                selectEndY = selectStartY;
                
            }
            if( toolstring.contains("SelectMove")){
                System.out.println("Selection Move used");
                Image canvas = canvasTable.snapshot(null, null);
                selection = new WritableImage(canvas.getPixelReader(), (int) selectDimensions[0], (int) selectDimensions[1], (int) selectDimensions[2], (int) selectDimensions[3]);
                startMoveX = selectDimensions[0];
                startMoveY = selectDimensions[1];
                offsetMoveX = (mouse.getX() - startMoveX );
                offsetMoveY = (mouse.getY() - startMoveY );
            }
        }
        
    }
    
    /**
     * Uses the currently selected ToggleButton to
     * draw a graphic onto the GraphicsContext.
     * Does nothing if no ToggleButton is selected.
     * 
     * @param mouse
     */
    @FXML
    private void drawDrag(MouseEvent mouse) {
        GraphicsContext c = canvasTable.getGraphicsContext2D();
        GraphicsContext tempC = tempCanvas.getGraphicsContext2D();
        
        if(toolsDrawing.getSelectedToggle() != null){
            String toolstring = toolsDrawing.getSelectedToggle().toString();
            t.setGraphicsContext(c);
            t.setLayoutController(this);
            
            /**************Shape Tools**************/
            if (toolstring.contains("FreeDraw")){
                t.freeDrawDrag(mouse);
            }
            if( toolstring.contains("Line")){
                t.lineDrag(mouse);
            }
            if( toolstring.contains("Circle")){
               t.circleDrag(mouse);
            }
            if( toolstring.contains("Oval")){
                t.ellipseDrag(mouse);
            }
            if( toolstring.contains("Square")){
                t.squareDrag(mouse);
            }
            if( toolstring.contains("Rectangle")){
                t.rectangleDrag(mouse);
            }
            
            /************Selection Tools************/
            if( toolstring.contains("SelectRect")){
                double widthX, widthY;
                if( (selectEndX != mouse.getX()) || (selectEndY != mouse.getY())){
                    canvasReload();
                    //tempC.clearRect(0, 0, tempCanvas.getWidth(), tempCanvas.getHeight());
                    selectEndX = mouse.getX();
                    selectEndY = mouse.getY();
                    
                    widthX = Math.abs(selectEndX - selectStartX);
                    widthY = Math.abs(selectEndY - selectStartY);
                    selectDimensions[2] = widthX;
                    selectDimensions[3] = widthY;
                    
                    if(selectEndX < selectStartX && selectEndY < selectStartY){
                        c.strokeRect(selectStartX-widthX, selectStartY-widthY,
                                widthX, widthY);
                        selectDimensions[0] = selectStartX-widthX;
                        selectDimensions[1] = selectStartY-widthY;
                    }else if(selectEndY < selectStartY){
                        c.strokeRect(selectStartX, selectStartY-widthY,
                                widthX, widthY);
                        selectDimensions[0] = selectStartX;
                        selectDimensions[1] = selectStartY-widthY;
                    }else if(selectEndX < selectStartX){
                        c.strokeRect(selectStartX-widthX, selectStartY,
                                widthX, widthY);
                        selectDimensions[0] = selectStartX-widthX;
                        selectDimensions[1] = selectStartY;
                    }else{
                        c.strokeRect(selectStartX, selectStartY,
                                widthX, widthY);
                        selectDimensions[0] = selectStartX;
                        selectDimensions[1] = selectStartY;
                    }
                }
                selectOn = true;
            }
            if(toolstring.contains("SelectMove")){
                if (selectOn == true){
                    if(startMoveX != mouse.getX() || startMoveY != mouse.getY()){
                        canvasReload();
                        
                        c.clearRect(selectDimensions[0], selectDimensions[1], selectDimensions[2], selectDimensions[3]);
                        c.drawImage(selection, mouse.getX() - offsetMoveX , mouse.getY() - offsetMoveY);
                        
                    }
                }
            }
            
            
        }
        
    }
    
    @FXML
    private void drawReleased(MouseEvent mouse) {
        if(toolsDrawing.getSelectedToggle() != null){
            String toolstring = toolsDrawing.getSelectedToggle().toString();
            if(toolstring.contains("SelectMove")){
                selectDimensions[0] = mouse.getX()- offsetMoveX;
                selectDimensions[1] = mouse.getY()- offsetMoveY;
            }
        }
    }
    
    
    
    /*################## Stack Functions ##################*/
    private Stack<Double[]> dimensionStack = new Stack<Double[]>();
    private Stack<Double[]> redoDimensionStack = new Stack<Double[]>();
    private Stack<String> URLStack = new Stack<String>();
    private Stack<String> redoURLStack = new Stack<String>();
    
    /**
     * Redraws the canvas in accordance to the newest entries of the redoDimension and redoURL stacks.
     * After which, those entries are placed into the dimension and URL stacks.
     * 
     * @param event 'Redo' option in 'Edit' menu. Alternatively,
     *              use keyboard shortcut, Default: 'CTRL + Shift + Z'
     */
    @FXML
    public void handleRedo(ActionEvent event) {
        Double[] redoD = redoDimensionStack.peek();
        Double[] addUndo = new Double[2];
        
        addUndo[0] = canvasTable.getWidth();
        addUndo[1] = canvasTable.getHeight();
        dimensionStack.push(addUndo);
        URLStack.push(canvasURL(canvasTable));
        
        canvasTable.setWidth(redoD[0]);
        canvasTable.setHeight(redoD[1]);
        
        String redoURL = redoURLStack.peek();
        String URL = redoURL;
        Image undoImage = new Image(URL);
        canvasTable.getGraphicsContext2D().drawImage(undoImage, 0,0, redoD[0], redoD[1]);
        
        redoDimensionStack.pop();
        redoURLStack.pop();
    }
    
    /**
     * Redraws the canvas in accordance to the newest entries of the dimension and URL stacks.
     * After which, those entries are placed into the redoDimension and redoURL stacks.
     * 
     * @param event 'Redo' option in 'Edit' menu. Alternatively,
     *              use keyboard shortcut, Default: 'CTRL + Z'
     */
    @FXML
    public void handleUndo(ActionEvent event) {
        Double[] undoD = dimensionStack.peek();
        Double[] addRedo = new Double[2];
        
        addRedo[0] = canvasTable.getWidth();
        addRedo[1] = canvasTable.getHeight();
        redoDimensionStack.push(addRedo);
        redoURLStack.push(canvasURL(canvasTable));
        
        canvasTable.setWidth(undoD[0]);
        canvasTable.setHeight(undoD[1]);
        
        String undoURL = URLStack.peek();
        Image undoImage = new Image(undoURL);
        canvasTable.getGraphicsContext2D().drawImage(undoImage, 0,0, undoD[0], undoD[1]);
        
        dimensionStack.pop();
        URLStack.pop();
    }
    
    /**
     * Redraws the canvas when updating drawing functions in accordance to the newest entries of the Dimension and URL stacks.
     * 
     */
    public void canvasReload(){
        Double[] undoD = dimensionStack.peek();
        String undoURL = URLStack.peek();
        Image undoImage = new Image(undoURL);
        canvasTable.getGraphicsContext2D().clearRect(0,0, undoD[0], undoD[1]);
        canvasTable.getGraphicsContext2D().drawImage(undoImage, 0,0, undoD[0], undoD[1]);
    }
    
    public void canvasDataStackAdd(){
        Double[] dimensions = new Double[2];
        dimensions[0] = canvasTable.getWidth();
        dimensions[1] = canvasTable.getHeight();
        dimensionStack.push(dimensions);
        URLStackAdd();
    }
    
    private void URLStackAdd(){
        String URL = canvasURL(canvasTable);
        URLStack.push(URL);
    }

    private String canvasURL(Canvas canvas){
        
        WritableImage add = new WritableImage((int) canvas.getWidth(),(int) canvas.getHeight());
        canvas.snapshot(null, add);
        RenderedImage ri = SwingFXUtils.fromFXImage(add, null);
        File temp;
        String url = "";
        try {
            temp = File.createTempFile("imagetmp", ".tmp");
            ImageIO.write(ri, "png", temp);
            url = temp.toURI().toURL().toString();
        } catch (IOException ex) {
            Logger.getLogger(LayoutController.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("url failed");
        }
        
        return url;
    }

    
    
    /*################## Canvas View Functions ##################*/
    
    /**
     * Recreates the canvas to match the specified width and height.
     * 
     * @param width The width of the new canvas.
     * @param height The height of the new canvas.
     * 
     * @param keep Determines whether the current image will be resized to fit the new dimensions.
     *              If 0 and new canvas dimensions are smaller than previous dimensions, image will be cut off by canvas.
     *              If 1, the current image will be resized to fit the new dimensions.
     */
    public void resizeCanvas(int width, int height, int keep){
        GraphicsContext gc = canvasTable.getGraphicsContext2D();
        
        Image curImg = canvasTable.snapshot(null, null);
        Image resampled = resampler(curImg, width, height);
            
        if(width != canvasTable.getWidth()){                  
            canvasTable.setWidth(width);
            System.out.println("Width set to: " + width);
        }
        if(height != canvasTable.getHeight()){
            canvasTable.setHeight(height);
            System.out.println("Height set to: " + height);
        }
        
        gc.setFill(WHITE);
        gc.fillRect(0, 0, canvasTable.getWidth(), canvasTable.getHeight());
        gc.setFill(colorPicker.getValue());
        
        if(keep == 1){
            gc.drawImage(resampled, 0,0, width, height);
            System.out.println("image proportion changed");
        }else{
            canvasReload();
        }
    }
    
    public Image resampler(Image input, int newW, int newH){
        ImageView imageView = new ImageView(input);
        imageView.setFitWidth(newW);
        imageView.setFitHeight(newH);
        imageView.setPreserveRatio(false);
        return imageView.snapshot(null, null);
    }

    @FXML
    private void zoomIn(ActionEvent event) {
        canvasScroll.setScaleX(canvasScroll.getScaleX() + .25);
        canvasScroll.setScaleY(canvasScroll.getScaleY() + .25);
    }

    @FXML
    private void zoomOut(ActionEvent event) {
        canvasScroll.setScaleX(canvasScroll.getScaleX() - .25);
        canvasScroll.setScaleY(canvasScroll.getScaleY() - .25);
    }

    @FXML
    private void zoomReset(ActionEvent event) {
        canvasScroll.setScaleX(1);
        canvasScroll.setScaleY(1);
    }

    @FXML
    private void windowTabClose(Event event) {
    }


    
    
    
    
}
