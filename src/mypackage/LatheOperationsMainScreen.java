package mypackage;

import net.rim.device.api.system.Bitmap;
import net.rim.device.api.system.Display;
import net.rim.device.api.ui.FieldChangeListener;
import net.rim.device.api.ui.Font;
import net.rim.device.api.ui.FontFamily;
import net.rim.device.api.ui.Graphics;
import net.rim.device.api.ui.MenuItem;
import net.rim.device.api.ui.UiApplication;
import net.rim.device.api.ui.container.MainScreen;
import net.rim.device.api.ui.container.VerticalFieldManager;
import net.rim.device.api.ui.Field;
import net.rim.device.api.ui.component.BasicEditField;
import net.rim.device.api.ui.component.ButtonField;
import net.rim.device.api.ui.component.Dialog;
import net.rim.device.api.ui.component.Menu;
import net.rim.device.api.ui.component.RichTextField;
import net.rim.device.api.util.StringProvider;

public class LatheOperationsMainScreen extends MainScreen implements FieldChangeListener {

	ButtonField latheBoringButton;
	ButtonField latheDrillingButton;
	// ButtonField latheTappingButton;
	ButtonField latheTurningButton;
	ButtonField helpButton;
	private Bitmap backgroundBitmap;
	VerticalFieldManager verticalManager;
	RichTextField rtfHeading;
	Font fontHeading = null;
	 
    public LatheOperationsMainScreen() {
        super(MainScreen.NO_HORIZONTAL_SCROLL | MainScreen.USE_ALL_WIDTH | MainScreen.USE_ALL_HEIGHT | MainScreen.NO_VERTICAL_SCROLL);
        setTitle( "Lathe Operations Calculator" );

        backgroundBitmap = Bitmap.getBitmapResource("background.jpg");
        verticalManager  = new VerticalFieldManager(VerticalFieldManager.USE_ALL_WIDTH | VerticalFieldManager.USE_ALL_HEIGHT){
        	 public void paint(Graphics graphics)
             {
                 //Draw the background image and then call paint.
                 graphics.drawBitmap(0, 0, Display.getWidth(),Display.getHeight(), backgroundBitmap, 0, 0);
                 super.paint(graphics);
             }
        };
        
        rtfHeading = new RichTextField("Lathe Operations", RichTextField.TEXT_ALIGN_HCENTER){
        	protected void paint(Graphics g){ 
                g.setColor(0xffffffff);
                super.paint(g);
            }
        };
        rtfHeading.setMargin(50, 50, 40, 50);
        fontHeading = getFontToDisplay("Times New Roman", 55);
        rtfHeading.setFont(fontHeading);
        verticalManager.add(rtfHeading);
       
        fontHeading = getFontToDisplay("Comic Sans MS", 30);
        latheBoringButton = new ButtonField( "   Boring   ", ButtonField.CONSUME_CLICK | FIELD_HCENTER );
        latheBoringButton.setChangeListener(this);
        latheBoringButton.setMargin(35, 35, 35, 45);
        latheBoringButton.setFont(fontHeading);
        verticalManager.add( latheBoringButton );
        
        latheDrillingButton = new ButtonField( "  Drilling  ", ButtonField.CONSUME_CLICK | FIELD_HCENTER );
        latheDrillingButton.setChangeListener(this);
        latheDrillingButton.setMargin(35, 35, 35, 45);
        latheDrillingButton.setFont(fontHeading);
        verticalManager.add( latheDrillingButton );
        
        /*latheTappingButton = new ButtonField( "  Tapping   ", ButtonField.CONSUME_CLICK | FIELD_HCENTER );
        latheTappingButton.setChangeListener(this);
        latheTappingButton.setMargin(35, 35, 25, 45);
        latheTappingButton.setFont(fontHeading);
        verticalManager.add( latheTappingButton );*/
        
        latheTurningButton = new ButtonField( "  Turning   ", ButtonField.CONSUME_CLICK | FIELD_HCENTER );
        latheTurningButton.setChangeListener(this);
        latheTurningButton.setMargin(35, 35, 35, 45);
        latheTurningButton.setFont(fontHeading);
        verticalManager.add( latheTurningButton );
        
       
        
        helpButton = new ButtonField( "    Help    ", ButtonField.CONSUME_CLICK | FIELD_HCENTER );
        helpButton.setChangeListener(this);
        helpButton.setMargin(35, 35, 35, 45);
        helpButton.setFont(fontHeading);
        verticalManager.add( helpButton );
        
        add(verticalManager);
        
        
       
    }
    
    public void fieldChanged(Field field, int context) {
        if(field == latheBoringButton)
        {
        	UiApplication.getUiApplication().pushScreen(new LatheBorningScreen());
        }
        else if(field == latheDrillingButton)
        {
        	UiApplication.getUiApplication().pushScreen(new LatheDrillingScreen());
        }
       /* else if(field == latheTappingButton)
        {
        	UiApplication.getUiApplication().pushScreen(new LatheTappingScreen());
        }*/
        else if(field == latheTurningButton)
        {
        	UiApplication.getUiApplication().pushScreen(new LatheTurningScreen());
        }
        else 
        {
        	UiApplication.getUiApplication().pushScreen(new HelpScreen());
        }
    }

    protected void makeMenu( Menu menu, int instance ) {
    	super.makeMenu(menu, instance);
        MenuItem mntm = new NewMenuItem();
        menu.add( mntm );
    }

    private class NewMenuItem extends MenuItem {
        public NewMenuItem() {
            super( new StringProvider( "Choose" ), 0, 0 );
        }

        public void run() {
        	chooseOption();
        }
    }

    private void chooseOption() {
    	
    	Dialog.inform("Please Select a Option and Continue!!!!");
       
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
