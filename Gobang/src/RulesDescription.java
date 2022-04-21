import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.io.*;

import static java.awt.FileDialog.SAVE;

public class RulesDescription extends JFrame{
    private JMenuBar bar = new JMenuBar();
    private JMenu jm0 = new JMenu("File");
    private JMenuItem jmi0 = new JMenuItem("open");
    private JMenuItem jmi1 = new JMenuItem("save");
    private JTextArea jt0 = new JTextArea();
    private JScrollPane js0 = new JScrollPane(jt0);
    private  JFileChooser jf = new JFileChooser();

    //private FileDialog f = new FileDialog();
    public RulesDescription() {
        jf.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
        jf.setMultiSelectionEnabled(false);
        add(jf);
        jm0.add(jmi0);
        jm0.add(jmi1);//添加File的下属组件
        bar.add(jm0);
        add(bar, BorderLayout.NORTH);
        add(js0, BorderLayout.CENTER);
        setSize(1000, 618);
        setLocation(200, 200);
        ActionListener menulistener = e -> {
            String cmd = e.getActionCommand();
            if (cmd.equals("open")) {
                jf.showOpenDialog(null);
                File file = jf.getSelectedFile();
                try {
                    FileReader read = new FileReader(file);
                    char[] buffer = new char[5000];
                    StringBuffer result = new StringBuffer();
                    int len;
                    while ((len = read.read(buffer)) != -1) {
                        result.append(buffer, 0, len);
                    }
                    read.close();
                    jt0.setText(result.toString());
                } catch (FileNotFoundException ex) {
                    ex.printStackTrace();
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            } else {
                try {
                    FileDialog fd = new FileDialog(this, "save", SAVE);
                    fd.setVisible(true);
                    FileWriter write = new FileWriter(new File(fd.getDirectory() + fd.getFile() + ".txt"));
                    String res = jt0.getText();
                    write.write(res);
                    write.close();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        };
        jmi0.addActionListener(menulistener);
        jmi1.addActionListener(menulistener);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }
}
