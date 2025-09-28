package javaapplicationproject;

import javax.swing.*;
import java.awt.*;


class BackgroundPanel extends JPanel {

    private Image bg;

    public BackgroundPanel(String path) {
        bg = new ImageIcon(getClass().getResource(path)).getImage();
        setLayout(new GridBagLayout());
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(bg, 0, 0, getWidth(), getHeight(), this);
    }
}

public class JavaApplicationProject extends JFrame {

    public JavaApplicationProject() {
        setTitle("House Lizard & Gecko");
        setSize(960, 540);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        BackgroundPanel bgPanel = new BackgroundPanel("/images/background.png");
        setContentPane(bgPanel);

        // Title
        JLabel title = new JLabel("House Lizard & Gecko", SwingConstants.CENTER);
        title.setFont(new Font("Comic Sans MS", Font.BOLD, 54));
        bgPanel.setLayout(new BorderLayout());
        bgPanel.add(title, BorderLayout.NORTH);

        // RadioButton Left Side
        JRadioButton singleBtn = new JRadioButton("Singly circular queue");
        JRadioButton doubleBtn = new JRadioButton("Double circular queue");
        ButtonGroup queueType = new ButtonGroup();
        singleBtn.setFont(new Font("Comic Sans MS", Font.BOLD, 16));
        doubleBtn.setFont(new Font("Comic Sans MS", Font.BOLD, 16));
        queueType.add(singleBtn);
        queueType.add(doubleBtn);
        singleBtn.setOpaque(false);
        doubleBtn.setOpaque(false);
        // RadioButton Right Side
        JRadioButton enqBtn = new JRadioButton("Enqueue");
        JRadioButton deqBtn = new JRadioButton("Dequeue");
        enqBtn.setFont(new Font("Comic Sans MS", Font.BOLD, 16));
        deqBtn.setFont(new Font("Comic Sans MS", Font.BOLD, 16));
        enqBtn.setOpaque(false);
        deqBtn.setOpaque(false);
        ButtonGroup operation = new ButtonGroup();
        operation.add(enqBtn);
        operation.add(deqBtn);
        JPanel leftPanel = new JPanel(new GridLayout(0, 1, 5, 5));
        leftPanel.add(singleBtn);
        leftPanel.add(doubleBtn);
        leftPanel.setOpaque(false);
// Panel ขวา (Operation)
        JPanel rightPanel = new JPanel(new GridLayout(0, 1, 5, 5));
        rightPanel.add(enqBtn);
        rightPanel.add(deqBtn);

        rightPanel.setOpaque(false);

// Panel ใหญ่รวมซ้าย-ขวา
        JPanel radioPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 20));
        radioPanel.add(leftPanel);
        radioPanel.add(rightPanel);
        radioPanel.setOpaque(false);
        JPanel container = new JPanel();
        container.setOpaque(false); // ให้เห็น background
        container.add(radioPanel);
        JPanel queuebox = new JPanel(new FlowLayout(FlowLayout.CENTER, 30, 20));
        JPanel[] boxes = new JPanel[5];
        for (int i = 0; i < 5; i++) {
            boxes[i] = new JPanel(new FlowLayout());
            boxes[i].setPreferredSize(new Dimension(120, 120));
            boxes[i].setBorder(BorderFactory.createLineBorder(Color.BLACK, 3));
            boxes[i].setOpaque(false);

            queuebox.add(boxes[i]);
        }
        queuebox.setOpaque(false);
        container.add(queuebox);
        bgPanel.add(container, BorderLayout.CENTER);

        JTextField textfield = new JTextField("Enter a number", 10);
        JButton sentbutton = new JButton("Sent");

        JPanel answerPanel = new JPanel(new GridLayout(0, 1, 5, 5));
        answerPanel.add(textfield);
        answerPanel.add(sentbutton);
        JPanel bottom = new JPanel();
        bottom.add(answerPanel);
        bottom.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));
        bottom.setOpaque(false);
        bgPanel.add(bottom, BorderLayout.SOUTH);

        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(JavaApplicationProject::new);
    }
}
