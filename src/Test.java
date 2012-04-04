/**
 * Created with IntelliJ IDEA.
 * User: farmisen
 * Date: 3/30/12
 * Time: 4:36 PM
 * To change this template use File | Settings | File Templates.
 */

import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;
import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.keyboard.NativeKeyListener;
import org.jnativehook.mouse.NativeMouseEvent;
import org.jnativehook.mouse.NativeMouseInputListener;
import org.sikuli.script.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

public class Test extends JFrame implements NativeMouseInputListener {

    static final int H_SIZE = 300;
    static final int V_SIZE = 200;
    private LootPicker lootPicker;
    public int mouseX;
    public int mouseY;

    public Test() {

        GlobalScreen.getInstance().addNativeMouseListener(this);
        GlobalScreen.getInstance().addNativeMouseMotionListener(this);
        //super("My First Application");

       // pack();                                                                                                                c
       // resize(H_SIZE, V_SIZE);
       // show();

        Settings.MoveMouseDelay = 0;

        App app = App.open("/Users/farmisen/Library/Application Support/Steam/SteamApps/common/realm of the mad god/Realm of the Mad God.app");
        Region window = null;

        while (null == window ) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }
            window = app.window();
        }
        window.highlight(1);

        lootPicker = new LootPicker(window);
        System.out.println("picker:" + lootPicker);

    }

    public static void main(String[] args) throws InterruptedException {
        Test test = new Test();

        while(true) {
            Thread.sleep(1000);
        }
    }


    @Override
    public void mousePressed(NativeMouseEvent nativeMouseEvent) {
    }

    @Override
    public void mouseReleased(NativeMouseEvent nativeMouseEvent) {
        if ( nativeMouseEvent.getButton() == 2 && null != lootPicker) {
            lootPicker.pick();
        }
    }

    @Override
    public void mouseMoved(NativeMouseEvent nativeMouseEvent) {
        mouseX = nativeMouseEvent.getX();
        mouseY = nativeMouseEvent.getY();
    }


}
