package mypackage;

import net.rim.device.api.system.Bitmap;
import net.rim.device.api.system.Display;
import net.rim.device.api.ui.Field;
import net.rim.device.api.ui.FieldChangeListener;
import net.rim.device.api.ui.Font;
import net.rim.device.api.ui.FontFamily;
import net.rim.device.api.ui.Graphics;
import net.rim.device.api.ui.UiApplication;
import net.rim.device.api.ui.component.ButtonField;
import net.rim.device.api.ui.component.Dialog;
import net.rim.device.api.ui.component.RichTextField;
import net.rim.device.api.ui.container.MainScreen;
import net.rim.device.api.ui.container.VerticalFieldManager;

public class HelpScreen extends MainScreen implements FieldChangeListener {

	/**
	 * 
	 */
	
	private Bitmap backgroundBitmap;
	VerticalFieldManager verticalManager;
	RichTextField rtfHeading;
	RichTextField rtfResult;
	Font fontHeading = null;
	ButtonField backButton;
	StringBuffer stHelpContents;
	
	public HelpScreen() {
		

		

		super(MainScreen.NO_HORIZONTAL_SCROLL | MainScreen.USE_ALL_WIDTH | MainScreen.USE_ALL_HEIGHT | MainScreen.NO_VERTICAL_SCROLL);
        setTitle( "Baseball Calculator" );
        
        backgroundBitmap = Bitmap.getBitmapResource("background.jpg");
        verticalManager  = new VerticalFieldManager(VerticalFieldManager.USE_ALL_WIDTH | VerticalFieldManager.USE_ALL_HEIGHT){
        	 public void paint(Graphics graphics)
             {
                 //Draw the background image and then call paint.
                 graphics.drawBitmap(0, 0, Display.getWidth(),Display.getHeight(), backgroundBitmap, 0, 0);
                 super.paint(graphics);
             }
        };
        
        rtfHeading = new RichTextField("About Lathe Operations", RichTextField.TEXT_ALIGN_HCENTER){
        	protected void paint(Graphics g){ 
                g.setColor(0xffffffff);
                super.paint(g);
            }
        };
        rtfHeading.setMargin(50, 50, 40, 50);
        fontHeading = getFontToDisplay("Times New Roman", 55);
        rtfHeading.setFont(fontHeading);
        verticalManager.add(rtfHeading);
        
       stHelpContents = new StringBuffer();
		stHelpContents.append("\t A simple app that helps you to calculate following Technical Parameters in Lathe Operation. \n");
		stHelpContents.append("1.  Boring Time Calculation\n" +
							  "2.  Drilling Time Calculation\n" +
							  "3.  Turning Time Calculation\n" + "\nUsage :-\n1.  Provide necessary input\n2.  \"Calculate\" will calculate and give you the necessary paramter\n");
		stHelpContents.append("3.  \"Reset\" will reset the input to original state\n4.  \"Back\" will take yoou to Main Screen");
		
		 rtfResult = new RichTextField(stHelpContents.toString(), RichTextField.TEXT_ALIGN_LEFT){
	        	protected void paint(Graphics g){ 
	                g.setColor(0xffffffff);
	                super.paint(g);
	            }
	        };
	        rtfResult.setMargin(50, 50, 40, 50);
	        fontHeading = getFontToDisplay("Georgia", 20);
	        rtfResult.setFont(fontHeading);
	        
	       
	        
	        verticalManager.add(rtfResult);
	        

        fontHeading = getFontToDisplay("Comic Sans MS", 30);
        backButton = new ButtonField( "Back", ButtonField.CONSUME_CLICK | FIELD_HCENTER );
        backButton.setChangeListener(this);
        backButton.setFont(fontHeading);
        backButton.setMargin(65, 40, 40, 80);
        
        verticalManager.add(backButton);
        
		add(verticalManager);
	
		
		
	
		
	}
	
	public void fieldChanged(Field field, int context) {

		if(field == backButton)
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
