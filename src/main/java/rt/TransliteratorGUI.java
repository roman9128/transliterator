package rt;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class TransliteratorGUI extends JFrame {

    private Transliterator transliterator;

    private static final int WINDOW_HEIGHT = 500;
    private static final int WINDOW_WIDTH = 1000;
    private static final int WINDOW_POSX = 400;
    private static final int WINDOW_POSY = 400;
    private final JTextPane cyrillicTextArea = new JTextPane();
    private final JTextPane latinTextArea = new JTextPane();
    private final JLabel cyrillicLabel = new JLabel();
    private final JLabel latinLabel = new JLabel();
    private JButton clearBtn = new JButton("Очистить");

    public TransliteratorGUI(Transliterator transliterator) {

        this.transliterator = transliterator;

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocation(WINDOW_POSX, WINDOW_POSY);
        setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        setTitle("Transliterator");
        setResizable(false);
        setVisible(true);
        latinTextArea.setEditable(false);

        cyrillicTextArea.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    e.consume();
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    latinTextArea.setText("");
                    makeTranslit(cyrillicTextArea.getText());
                }
            }
        });

        clearBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clear();
            }
        });

        JPanel labelPanel = new JPanel(new GridLayout(1, 2));
        labelPanel.add(cyrillicLabel);
        labelPanel.add(latinLabel);
        cyrillicLabel.setText("Текст кириллицей");
        latinLabel.setText("Текст латиницей");
        cyrillicLabel.setForeground(Color.LIGHT_GRAY);
        latinLabel.setForeground(Color.LIGHT_GRAY);
        add(labelPanel, BorderLayout.NORTH);

        JPanel textPanel = new JPanel(new GridLayout(1, 2));
        textPanel.add(cyrillicTextArea);
        textPanel.add(latinTextArea);
        cyrillicTextArea.setBorder(BorderFactory.createEtchedBorder());
        latinTextArea.setBorder(BorderFactory.createEtchedBorder());
        add(textPanel, BorderLayout.CENTER);

        JPanel btnPanel = new JPanel(new GridLayout(1, 1));
        btnPanel.add(clearBtn);
        add(btnPanel, BorderLayout.SOUTH);
    }

    private void clear() {
        cyrillicTextArea.setText("");
        latinTextArea.setText("");
    }

    public void makeTranslit(String cyrillicText) {
        latinTextArea.setText(transliterator.translit(cyrillicText));
    }
}