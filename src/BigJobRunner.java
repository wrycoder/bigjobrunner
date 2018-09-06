/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author toulipp
 */
public class BigJobRunner extends javax.swing.JFrame {
    public static final int DELAY_MS = 250;
    
    public BigJobRunner() {
        javax.swing.JMenuBar menuBar = new javax.swing.JMenuBar();
        javax.swing.JMenu menu = new javax.swing.JMenu("Menu");
        javax.swing.JMenuItem wait1 = new javax.swing.JMenuItem("Wait 5000 ms");
        menu.add(wait1);
        menuBar.add(menu);
        setJMenuBar(menuBar);
        wait1.addActionListener(getActionListener(this, delayActionListener(5000)));
        setSize(400, 400);
        setTitle("Big Job to Do");
        setVisible(true);
    }
    
    private java.awt.event.ActionListener delayActionListener(final int delay) {
        java.awt.event.ActionListener listener = new java.awt.event.ActionListener() {

            public void actionPerformed(java.awt.event.ActionEvent ae) {
                try {
                    Thread.sleep(delay);
                } catch (InterruptedException e) {
                }
            }

        };
        return listener;
    }
    
    public static void main(String[] args) {
        new BigJobRunner();
    }
    
    public static java.awt.event.ActionListener getActionListener(final javax.swing.JFrame frame,
        final java.awt.event.ActionListener originalActionListener) {
        java.awt.event.ActionListener actionListener = new java.awt.event.ActionListener() {
            public void actionPerformed(final java.awt.event.ActionEvent e) {
                java.util.TimerTask timerTask = new java.util.TimerTask() {
                    public void run() {
                        originalCursor = frame.getCursor();
                        startWaitCursor(frame);
                    }

                };
                java.util.Timer timer = new java.util.Timer();
                try {
                    timer.schedule(timerTask, DELAY_MS);
                    originalActionListener.actionPerformed(e);
                } finally {
                    timer.cancel();
                    stopWaitCursor(frame);
                }
            }
        };
        return actionListener;
    }
    private static void startWaitCursor(javax.swing.JFrame frame) {
        frame.getGlassPane().setCursor(java.awt.Cursor.getPredefinedCursor(java.awt.Cursor.WAIT_CURSOR));
        frame.getGlassPane().addMouseListener(mouseAdapter);
        frame.getGlassPane().setVisible(true);
    }

    private static void stopWaitCursor(javax.swing.JFrame frame) {
        frame.getGlassPane().setCursor(originalCursor);
        frame.getGlassPane().removeMouseListener(mouseAdapter);
        frame.getGlassPane().setVisible(false);
    }
    private static java.awt.Cursor originalCursor = null;
    private static final java.awt.event.MouseAdapter mouseAdapter = new java.awt.event.MouseAdapter() {
    };
}
