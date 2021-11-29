/*
 * JupDemo.java
 *
 * Created on Dec 24, 2011, 3:17:48 PM
 */
package jupdemo;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import javax.swing.JOptionPane;
import jupar.Downloader;
import jupar.Updater;
import jupar.objects.Modes;
import jupar.objects.Release;
import jupar.parsers.ReleaseXMLParser;
import org.xml.sax.SAXException;

/**
 *
 * @author periklis
 */
public class JupDemo extends javax.swing.JFrame {

    /** Creates new form JupDemo */
    public JupDemo() {
        initComponents();
        /**
         * Check for new version
         */
        setLocationRelativeTo(null);
        TrustManager[] trustAllCerts = new TrustManager[]{
            new X509TrustManager() {
                public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                    return null;
                }
                public void checkClientTrusted(
                    java.security.cert.X509Certificate[] certs, String authType) {
                }
                public void checkServerTrusted(
                    java.security.cert.X509Certificate[] certs, String authType) {
                }
            }
        };

        // Activate the new trust manager
        try {
            SSLContext sc = SSLContext.getInstance("SSL");
            sc.init(null, trustAllCerts, new java.security.SecureRandom());
            HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
        } catch (Exception e) {
        }
        int answer = -1;
        Release release = new Release();
        release.setpkgver("1.0");
        release.setPkgrel("1");
        ReleaseXMLParser parser = new ReleaseXMLParser();
        try {
            Release current =
                    parser.parse("https://github.com/hieuprogramer/Qlattt/releases/download/xml/latest.xml", Modes.URL);
            if (current.compareTo(release) > 0) {
                answer =
                        JOptionPane.showConfirmDialog(rootPane, "A new version of this"
                        + " program is available\nWould you like to install it?",
                        "Update", JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE);
                switch (answer) {
                    case 0:
                        /**
                         * Download needed files
                         */
                        Downloader dl = new Downloader();
                        dl.download("https://github.com/hieuprogramer/Qlattt/releases/download/xml/files.xml", "tmp", Modes.URL);
                        System.out.println("oke");
                        break;
                    case 1:
                        break;
                }
            }
        } catch (SAXException ex) {
            JOptionPane.showMessageDialog(rootPane, "The xml wasn't loaded succesfully!\n",
                    "Something went wrong!", JOptionPane.WARNING_MESSAGE);
            Logger.getLogger(JupDemo.class.getName()).log(Level.SEVERE, null, ex);
            answer = -1;
        } catch (FileNotFoundException ex) {
            JOptionPane.showMessageDialog(rootPane, "Files were unable to be read or created successfully!\n"
                    + "Please be sure that you have the right permissions and internet connectivity!",
                    "Something went wrong!", JOptionPane.WARNING_MESSAGE);
            Logger.getLogger(JupDemo.class.getName()).log(Level.SEVERE, null, ex);
            answer = -1;
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(rootPane, "IOEXception!",
                    "Something went wrong!", JOptionPane.WARNING_MESSAGE);
            Logger.getLogger(JupDemo.class.getName()).log(Level.SEVERE, null, ex);
            answer = -1;
        } catch (InterruptedException ex) {
            JOptionPane.showMessageDialog(rootPane, "The connection has been lost!\n"
                    + "Please check your internet connectivity!",
                    "Something went wrong!", JOptionPane.WARNING_MESSAGE);
            Logger.getLogger(JupDemo.class.getName()).log(Level.SEVERE, null, ex);
            answer = -1;
        }

        /**
         * Start the updating procedure
         */
        if (answer == 0) {
            try {
                Updater update = new Updater();
                update.update("update.xml", "tmp", Modes.FILE);
                JOptionPane.showMessageDialog(rootPane, "The update was completed successfuly.\n"
                        + "Please restart the application in order the changes take effect.");
            } catch (SAXException ex) {
                Logger.getLogger(JupDemo.class.getName()).log(Level.SEVERE, null, ex);
            } catch (InterruptedException ex) {
                Logger.getLogger(JupDemo.class.getName()).log(Level.SEVERE, null, ex);
            } catch (FileNotFoundException ex) {
                Logger.getLogger(JupDemo.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(JupDemo.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        /**
         * Delete tmp directory
         */
        File tmp = new File("tmp");
        if (tmp.exists()) {
            for (File file : tmp.listFiles()) {
                file.delete();
            }
            tmp.delete();
        }
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("JUP DEMO");

        jLabel1.setText("This is jup demo!");

        jLabel2.setText("Software Version: 1.0-1");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(199, 199, 199)
                .addComponent(jLabel1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(185, Short.MAX_VALUE)
                .addComponent(jLabel2)
                .addGap(173, 173, 173))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(124, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addComponent(jLabel2)
                .addGap(154, 154, 154))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(JupDemo.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(JupDemo.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(JupDemo.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(JupDemo.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */

        java.awt.EventQueue.invokeLater(new Runnable() {

            public void run() {
                new JupDemo().setVisible(true);
            }
        });
        
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    // End of variables declaration//GEN-END:variables
}
