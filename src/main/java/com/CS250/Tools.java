/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.CS250;

import javafx.event.ActionEvent;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;

/**
 *
 * @author Blazi
 */
public class Tools{
   
    String tool;
    GraphicsContext gc;
    LayoutController lc;
    
    private Line line = new Line();
    private double circleStartX, circleStartY, circleEndX, circleEndY;
    private double squareStartX, squareStartY, squareEndX, squareEndY;
    
    
    //**************************Setter Functions******************************//
    public void setToolstring(String toolstring){
        tool = toolstring;
    }
    
    public void setGraphicsContext(GraphicsContext context){
        gc = context;
    }

    public void setLayoutController(LayoutController controller){
        lc = controller;
    }
    
    
    //***************************Tool Functions*******************************//
    public void freeDrawStart(MouseEvent mouse){
        System.out.println("FreeDraw found");
        gc.beginPath();
        gc.moveTo(mouse.getX(), mouse.getY());
        gc.stroke();
    }

    public void freeDrawDrag(MouseEvent mouse){
        gc.lineTo(mouse.getX(), mouse.getY());
        gc.stroke();
    }
    
    public void lineStart(MouseEvent mouse){
        System.out.println("Line tool used");
        line.setStartX(mouse.getX());
        line.setStartY(mouse.getY());
    }
    
    public void lineDrag(MouseEvent mouse){
        if( (line.getEndX() != mouse.getX()) || (line.getEndY() != mouse.getY())){
            line.setEndX(mouse.getX());
            line.setEndY(mouse.getY());
            lc.canvasReload();
            gc.strokeLine(line.getStartX(), line.getStartY(), line.getEndX(), line.getEndY());
        }
    }
    
    public void ellipseStart(MouseEvent mouse){
        System.out.println("Circle or Ellipse tool used");
        circleStartX = mouse.getX();
        circleStartY = mouse.getY();
    }
    
    public void ellipseDrag(MouseEvent mouse){
        double diameterX, diameterY;
        if( (circleEndX != mouse.getX()) || (circleEndY != mouse.getY())){
            lc.canvasReload();
            circleEndX = mouse.getX();
            circleEndY = mouse.getY();

            diameterX = Math.abs( Math.abs(circleEndX) - Math.abs(circleStartX) );
            diameterY = Math.abs( Math.abs(circleEndY) - Math.abs(circleStartY) );

            if(circleEndX < circleStartX && circleEndY < circleStartY){
                gc.fillOval(circleStartX-diameterX, circleStartY-diameterY,
                        diameterX, diameterY);
                gc.strokeOval(circleStartX-diameterX, circleStartY-diameterY,
                        diameterX, diameterY);
            }else if(circleEndY < circleStartY){
                gc.fillOval(circleStartX, circleStartY-diameterY,
                        diameterX, diameterY);
                gc.strokeOval(circleStartX, circleStartY-diameterY,
                        diameterX, diameterY);
            }else if(circleEndX < circleStartX){
                gc.fillOval(circleStartX-diameterX, circleStartY,
                        diameterX, diameterY);
                gc.strokeOval(circleStartX-diameterX, circleStartY,
                        diameterX, diameterY);
            }else{
                gc.fillOval(circleStartX, circleStartY,
                        diameterX, diameterY);
                gc.strokeOval(circleStartX, circleStartY,
                        diameterX, diameterY);
            }
        }
    }
    
    public void circleDrag(MouseEvent mouse){
        double diameter;
        if( (circleEndX != mouse.getX()) || (circleEndY != mouse.getY())){
            lc.canvasReload();
            circleEndX = mouse.getX();
            circleEndY = mouse.getY();

            diameter = Math.abs( Math.abs(circleEndX) - Math.abs(circleStartX) );

            if(circleEndX < circleStartX && circleEndY < circleStartY){
                gc.fillOval(circleStartX-diameter, circleStartY-diameter,
                        diameter, diameter);
                gc.strokeOval(circleStartX-diameter, circleStartY-diameter,
                        diameter, diameter);
            }else if(circleEndY < circleStartY){
                gc.fillOval(circleStartX, circleStartY-diameter,
                        diameter, diameter);
                gc.strokeOval(circleStartX, circleStartY-diameter,
                        diameter, diameter);
            }else if(circleEndX < circleStartX){
                gc.fillOval(circleStartX-diameter, circleStartY,
                        diameter, diameter);
                gc.strokeOval(circleStartX-diameter, circleStartY,
                        diameter, diameter);
            }else{
                gc.fillOval(circleStartX, circleStartY,
                        diameter, diameter);
                gc.strokeOval(circleStartX, circleStartY,
                        diameter, diameter);
            }

        }
    }
    
    public void rectangleStart(MouseEvent mouse){
        System.out.println("Square or Rectangle tool used");
        squareStartX = mouse.getX();
        squareStartY = mouse.getY();
    }
    
    public void rectangleDrag(MouseEvent mouse){
        double widthX, widthY;
        if( (squareEndX != mouse.getX()) || (squareEndY != mouse.getY())){
            lc.canvasReload();
            squareEndX = mouse.getX();
            squareEndY = mouse.getY();

            widthX = Math.abs(squareEndX - squareStartX);
            widthY = Math.abs(squareEndY - squareStartY);

            if(squareEndX < squareStartX && squareEndY < squareStartY){
                gc.fillRect(squareStartX-widthX, squareStartY-widthY,
                        widthX, widthY);
                gc.strokeRect(squareStartX-widthX, squareStartY-widthY,
                        widthX, widthY);
            }else if(squareEndY < squareStartY){
                gc.fillRect(squareStartX, squareStartY-widthY,
                        widthX, widthY);
                gc.strokeRect(squareStartX, squareStartY-widthY,
                        widthX, widthY);
            }else if(squareEndX < squareStartX){
                gc.fillRect(squareStartX-widthX, squareStartY,
                        widthX, widthY);
                gc.strokeRect(squareStartX-widthX, squareStartY,
                        widthX, widthY);
            }else{
                gc.fillRect(squareStartX, squareStartY,
                        widthX, widthY);
                gc.strokeRect(squareStartX, squareStartY,
                        widthX, widthY);
            }
        }
    }
    
    public void squareDrag(MouseEvent mouse){
        double width;
        if( (squareEndX != mouse.getX()) || (squareEndY != mouse.getY())){
            lc.canvasReload();
            squareEndX = mouse.getX();
            squareEndY = mouse.getY();

            width = Math.abs(squareEndX - squareStartX);

            if(squareEndX < squareStartX && squareEndY < squareStartY){
                gc.fillRect(squareStartX-width, squareStartY-width,
                        width, width);
                gc.strokeRect(squareStartX-width, squareStartY-width,
                        width, width);
            }else if(squareEndY < squareStartY){
                gc.fillRect(squareStartX, squareStartY-width,
                        width, width);
                gc.strokeRect(squareStartX, squareStartY-width,
                        width, width);
            }else if(squareEndX < squareStartX){
                gc.fillRect(squareStartX-width, squareStartY,
                        width, width);
                gc.strokeRect(squareStartX-width, squareStartY,
                        width, width);
            }else{
                gc.fillRect(squareStartX, squareStartY,
                        width, width);
                gc.strokeRect(squareStartX, squareStartY,
                        width, width);
            }
        }
    }
    
    public void textStamp(MouseEvent mouse, String text, double fontSize){
        System.out.println("Text tool used");
        gc.setFont(new Font(text, fontSize));
        gc.fillText(text, mouse.getX(), mouse.getY());
    }
    
    
}
