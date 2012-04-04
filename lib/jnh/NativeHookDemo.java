// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

package org.jnativehook.example;

import java.awt.*;
import java.awt.event.*;
import java.io.PrintStream;
import javax.swing.*;
import javax.swing.text.Document;
import org.jnativehook.GlobalScreen;
import org.jnativehook.keyboard.*;
import org.jnativehook.mouse.NativeMouseEvent;
import org.jnativehook.mouse.NativeMouseInputListener;

public class NativeHookDemo extends JFrame
    implements NativeKeyListener, NativeMouseInputListener, ActionListener, WindowListener, ItemListener
{

    public NativeHookDemo()
    {
        setTitle("JNativeHook Demo");
        setLayout(new BorderLayout());
        setDefaultCloseOperation(3);
        setSize(600, 300);
        addWindowListener(this);
        JPanel jpanel = new JPanel();
        jpanel.setBorder(BorderFactory.createEtchedBorder(1));
        jpanel.setLayout(new FlowLayout(3));
        add(jpanel, "North");
        chkKeyboard = new JCheckBox("Keyboard Events");
        chkKeyboard.setMnemonic(75);
        chkKeyboard.addItemListener(this);
        chkKeyboard.setSelected(true);
        jpanel.add(chkKeyboard);
        chkButton = new JCheckBox("Button Events");
        chkButton.setMnemonic(66);
        chkButton.addItemListener(this);
        chkButton.setSelected(true);
        jpanel.add(chkButton);
        chkMotion = new JCheckBox("Motion Events");
        chkMotion.setMnemonic(77);
        chkMotion.addItemListener(this);
        chkMotion.setSelected(true);
        jpanel.add(chkMotion);
        txtEventInfo = new JTextArea();
        txtEventInfo.setEditable(false);
        txtEventInfo.setBackground(new Color(255, 255, 255));
        txtEventInfo.setForeground(new Color(0, 0, 0));
        txtEventInfo.setText("");
        JScrollPane jscrollpane = new JScrollPane(txtEventInfo);
        jscrollpane.setPreferredSize(new Dimension(375, 125));
        add(jscrollpane, "Center");
        setVisible(true);
    }

    public void itemStateChanged(ItemEvent itemevent)
    {
        java.awt.ItemSelectable itemselectable = itemevent.getItemSelectable();
        if(itemselectable == chkKeyboard)
        {
            if(itemevent.getStateChange() == 1)
                GlobalScreen.getInstance().addNativeKeyListener(this);
            else
                GlobalScreen.getInstance().removeNativeKeyListener(this);
        } else
        if(itemselectable == chkButton)
        {
            if(itemevent.getStateChange() == 1)
                GlobalScreen.getInstance().addNativeMouseListener(this);
            else
                GlobalScreen.getInstance().removeNativeMouseListener(this);
        } else
        if(itemselectable == chkMotion)
        {
            System.out.println("Check check box clicl]k");
            if(itemevent.getStateChange() == 1)
                GlobalScreen.getInstance().addNativeMouseMotionListener(this);
            else
                GlobalScreen.getInstance().removeNativeMouseMotionListener(this);
        }
    }

    public void actionPerformed(ActionEvent actionevent)
    {
        txtEventInfo.setText("");
        requestFocusInWindow();
    }

    public void keyPressed(NativeKeyEvent nativekeyevent)
    {
        displayEventInfo(nativekeyevent);
    }

    public void keyReleased(NativeKeyEvent nativekeyevent)
    {
        displayEventInfo(nativekeyevent);
    }

    public void mousePressed(NativeMouseEvent nativemouseevent)
    {
        displayEventInfo(nativemouseevent);
    }

    public void mouseReleased(NativeMouseEvent nativemouseevent)
    {
        displayEventInfo(nativemouseevent);
    }

    public void mouseMoved(NativeMouseEvent nativemouseevent)
    {
        displayEventInfo(nativemouseevent);
    }

    private void displayEventInfo(NativeKeyEvent nativekeyevent)
    {
        txtEventInfo.append((new StringBuilder()).append("\n").append(nativekeyevent.paramString()).toString());
        txtEventInfo.setCaretPosition(txtEventInfo.getDocument().getLength());
    }

    private void displayEventInfo(NativeMouseEvent nativemouseevent)
    {
        txtEventInfo.append((new StringBuilder()).append("\n").append(nativemouseevent.paramString()).toString());
        txtEventInfo.setCaretPosition(txtEventInfo.getDocument().getLength());
    }

    public void windowActivated(WindowEvent windowevent)
    {
    }

    public void windowClosing(WindowEvent windowevent)
    {
    }

    public void windowDeactivated(WindowEvent windowevent)
    {
    }

    public void windowDeiconified(WindowEvent windowevent)
    {
    }

    public void windowIconified(WindowEvent windowevent)
    {
    }

    public void windowOpened(WindowEvent windowevent)
    {
        GlobalScreen.getInstance();
        try
        {
            txtEventInfo.setText((new StringBuilder()).append("Auto Repate Rate: ").append(GlobalScreen.getInstance().getAutoRepeatRate()).toString());
            txtEventInfo.append((new StringBuilder()).append("\nAuto Repate Delay: ").append(GlobalScreen.getInstance().getAutoRepeatDelay()).toString());
        }
        catch(NativeKeyException nativekeyexception)
        {
            txtEventInfo.setText((new StringBuilder()).append("Error: ").append(nativekeyexception.toString()).append("\n").toString());
        }
        txtEventInfo.setCaretPosition(txtEventInfo.getDocument().getLength());
    }

    public void windowClosed(WindowEvent windowevent)
    {
        System.runFinalization();
        System.exit(0);
    }

    public static void main(String args[])
    {
        new NativeHookDemo();
    }

    private static final long serialVersionUID = 0x23844a2dL;
    private JCheckBox chkKeyboard;
    private JCheckBox chkButton;
    private JCheckBox chkMotion;
    private JTextArea txtEventInfo;
}
