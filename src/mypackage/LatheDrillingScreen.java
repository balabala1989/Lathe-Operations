package mypackage;

import net.rim.device.api.system.Bitmap;
import net.rim.device.api.system.Display;
import net.rim.device.api.ui.Color;
import net.rim.device.api.ui.Field;
import net.rim.device.api.ui.FieldChangeListener;
import net.rim.device.api.ui.Font;
import net.rim.device.api.ui.FontFamily;
import net.rim.device.api.ui.Graphics;
import net.rim.device.api.ui.Ui;
import net.rim.device.api.ui.UiApplication;
import net.rim.device.api.ui.XYEdges;
import net.rim.device.api.ui.component.ButtonField;
import net.rim.device.api.ui.component.Dialog;
import net.rim.device.api.ui.component.EditField;
import net.rim.device.api.ui.component.LabelField;
import net.rim.device.api.ui.component.RichTextField;
import net.rim.device.api.ui.component.TextField;
import net.rim.device.api.ui.container.HorizontalFieldManager;
import net.rim.device.api.ui.container.MainScreen;
import net.rim.device.api.ui.container.VerticalFieldManager;
import net.rim.device.api.ui.decor.Border;
import net.rim.device.api.ui.decor.BorderFactory;

public class LatheDrillingScreen extends MainScreen implements FieldChangeListener {

	/**
	 * 
	 */
	
	private Bitmap backgroundBitmap;
	VerticalFieldManager verticalManager;
	RichTextField rtfHeading;
	RichTextField rtfResult;
	Font fontHeading = null;
	private EditField depthEdit;
	private EditField feedEdit;
	private EditField revolutionEdit;
	private LabelField depthLabel;
	private LabelField feedLabel;
	private LabelField revolutionLabel;
	ButtonField calculateButton;
	ButtonField backButton;
	Border roundedBorder;
	
	
	public LatheDrillingScreen() {
		

		

		super(MainScreen.NO_HORIZONTAL_SCROLL | MainScreen.USE_ALL_WIDTH | MainScreen.USE_ALL_HEIGHT | MainScreen.NO_VERTICAL_SCROLL);
        setTitle( "Lathe Operations Calculator" );
        roundedBorder = BorderFactory.createRoundedBorder(new XYEdges(10,10,10,10), Color.WHITE, Border.STYLE_FILLED);
        
        backgroundBitmap = Bitmap.getBitmapResource("background.jpg");
        verticalManager  = new VerticalFieldManager(VerticalFieldManager.USE_ALL_WIDTH | VerticalFieldManager.USE_ALL_HEIGHT){
        	 public void paint(Graphics graphics)
             {
                 //Draw the background image and then call paint.
                 graphics.drawBitmap(0, 0, Display.getWidth(),Display.getHeight(), backgroundBitmap, 0, 0);
                 super.paint(graphics);
             }
        };
        
        rtfHeading = new RichTextField("Calculate Time for Drilling", RichTextField.TEXT_ALIGN_HCENTER){
        	protected void paint(Graphics g){ 
                g.setColor(0xffffffff);
                super.paint(g);
            }
        };
        rtfHeading.setMargin(50, 50, 40, 50);
        fontHeading = getFontToDisplay("Times New Roman", 55);
        rtfHeading.setFont(fontHeading);
        verticalManager.add(rtfHeading);
        
        depthEdit = new EditField("","",10, TextField.NO_LEARNING | TextField.NO_NEWLINE | TextField.NON_SPELLCHECKABLE ){
            protected void paint(Graphics graphics) {
                graphics.clear();
                graphics.setBackgroundColor( Color.WHITE );
         
                super.paint(graphics);
              }
            };
            depthEdit.setBorder(roundedBorder);
            depthEdit.setMargin(10,10,20,10);
	    depthLabel = new LabelField( "Depth of Hole (m)" ){
            public void paint(Graphics g ){
                g.setColor(Color.WHITE);
                super.paint(g);
              }
            };
            depthLabel.setFont( Font.getDefault().derive(Font.EMBOSSED_EFFECT,25,Ui.UNITS_px) );   
		verticalManager.add(depthLabel);
		verticalManager.add(depthEdit);
		
		feedEdit = new EditField("","",10, TextField.NO_LEARNING | TextField.NO_NEWLINE | TextField.NON_SPELLCHECKABLE ){
            protected void paint(Graphics graphics) {
                graphics.clear();
                graphics.setBackgroundColor( Color.WHITE );
         
                super.paint(graphics);
              }
            };
            feedEdit.setBorder(roundedBorder);
            feedEdit.setMargin(10,10,20,10);
	    feedLabel = new LabelField( "Feed/rev (N)" ){
            public void paint(Graphics g ){
                g.setColor(Color.WHITE);
                super.paint(g);
              }
            };
            feedLabel.setFont( Font.getDefault().derive(Font.EMBOSSED_EFFECT,25,Ui.UNITS_px) );   
		verticalManager.add(feedLabel);
		verticalManager.add(feedEdit);
		
		revolutionEdit = new EditField("","",10, TextField.NO_LEARNING | TextField.NO_NEWLINE | TextField.NON_SPELLCHECKABLE ){
            protected void paint(Graphics graphics) {
                graphics.clear();
                graphics.setBackgroundColor( Color.WHITE );
         
                super.paint(graphics);
              }
            };
            revolutionEdit.setBorder(roundedBorder);
            revolutionEdit.setMargin(10,10,20,10);
	    revolutionLabel = new LabelField( "Revolution of job/min" ){
            public void paint(Graphics g ){
                g.setColor(Color.WHITE);
                super.paint(g);
              }
            };
            revolutionLabel.setFont( Font.getDefault().derive(Font.EMBOSSED_EFFECT,25,Ui.UNITS_px) );   
		verticalManager.add(revolutionLabel);
		verticalManager.add(revolutionEdit);
		
		
		
		rtfResult = new RichTextField("", RichTextField.TEXT_ALIGN_HCENTER){
        	protected void paint(Graphics g){ 
                g.setColor(0xffffffff);
                super.paint(g);
            }
        };
        rtfResult.setMargin(20, 50, 10, 50);
        fontHeading = getFontToDisplay("Georgia", 30);
        rtfResult.setFont(fontHeading);
        verticalManager.add(rtfResult);
        
        fontHeading = getFontToDisplay("Comic Sans MS", 30);
        calculateButton = new ButtonField( "Calculate", ButtonField.CONSUME_CLICK | FIELD_HCENTER );
        calculateButton.setChangeListener(this);
        calculateButton.setFont(fontHeading);
        calculateButton.setMargin(65, 40, 0, 80);
        
        backButton = new ButtonField( "Back", ButtonField.CONSUME_CLICK | FIELD_HCENTER );
        backButton.setChangeListener(this);
        backButton.setFont(fontHeading);
        backButton.setMargin(65, 40, 0, 80);
	        
	        
	        
	        HorizontalFieldManager horizontalManager = new HorizontalFieldManager(USE_ALL_WIDTH);
	        horizontalManager.add(calculateButton);
	        horizontalManager.add(backButton);
		verticalManager.add(horizontalManager);
		add(verticalManager);
	
		
		
	
		
	}
	
	
	public void fieldChanged(Field field, int context) {

		double depthRod;
		double feed = 0;
		double rev = 0;
		double boringTime;
		if(field == calculateButton)
		{
			if(calculateButton.getLabel().equals("Calculate"))
			{
				calculateButton.setLabel("Reset");
				depthEdit.setEditable(false);
				feedEdit.setEditable(false);
				revolutionEdit.setEditable(false);
				try {
					depthRod = Double.parseDouble(depthEdit.getText());
					feed = Double.parseDouble(feedEdit.getText());
					rev = Double.parseDouble(revolutionEdit.getText());
					if(feed == 0 || rev == 0)
					{
						Dialog.inform("Feed/rev (N) and Revolution of job/min cannot be zero");
					}
					else
					{
						boringTime = depthRod / (feed * rev);
						rtfResult.setText("Required Time for boring : \n" + String.valueOf(boringTime) + " (min)");
					}
				} catch (NumberFormatException e) {
					
					Dialog.inform("Please Enter Number!!!!");
					
				}
				catch (ArithmeticException e) {
					if(feed == 0 || rev == 0)
					{
						Dialog.inform("Feed/rev (N) and Revolution of job/min cannot be zero");
					}
					else
					{
						Dialog.inform("Error!!!! Please try after some time");
						UiApplication.getUiApplication().popScreen(UiApplication.getUiApplication().getActiveScreen());

					}
				}
				catch (Exception e) {

					Dialog.inform("Error!!!! Please try after some time");
					UiApplication.getUiApplication().popScreen(UiApplication.getUiApplication().getActiveScreen());

				}
				
			}
			else
			{
				calculateButton.setLabel("Calculate");
				depthEdit.setText("");
				feedEdit.setText("");
				revolutionEdit.setText("");
				rtfResult.setText("");
				depthEdit.setEditable(true);
				feedEdit.setEditable(true);
				revolutionEdit.setEditable(true);
			}
		}
		else if(field == backButton)
		{
			UiApplication.getUiApplication().popScreen(UiApplication.getUiApplication().getActiveScreen());
		}
		
	}
	
	private Font getFontToDisplay(String stFontName, int fontSize)
    {
    	try
        {
            FontFamily ff1 = FontFamily.forName(stFontName);
            fontHeading = ff1.getFont(Font.ITALIC | Font.EXTRA_BOLD , fontSize);
            return fontHeading;
        }
        catch (Exception e) {
			e.printStackTrace();
			Dialog.inform("Error Occurred. Please try after some time");
			return null;
		}
    }
    
    protected boolean onSavePrompt() {
        return true;
    }

}
