package javaapplicationproject;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;

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

public class Test extends JFrame {

    private JPanel[] boxes = new JPanel[5];
    // เก็บ label ของ index เพื่อแก้ข้อความได้ทีหลัง
    private JLabel[] indexLabels = new JLabel[5];

    private ImageIcon smallIcon;

    // circular queue index
    private int front = -1;
    private int rear = -1;
    private final int SIZE = 5;

    // ====== pointer labels ======
    private JLabel frontLabel = new JLabel("front");
    private JLabel rearLabel = new JLabel("rear");

    private void setupPointerLabels(JLayeredPane layeredPane) {
        frontLabel.setFont(new Font("Comic Sans MS", Font.BOLD, 20));
        frontLabel.setForeground(new Color(255, 140, 0));
        rearLabel.setFont(new Font("Comic Sans MS", Font.BOLD, 20));
        rearLabel.setForeground(Color.BLUE);

        frontLabel.setVisible(false);
        rearLabel.setVisible(false);

        layeredPane.add(frontLabel, JLayeredPane.POPUP_LAYER);
        layeredPane.add(rearLabel, JLayeredPane.POPUP_LAYER);
    }

    private void updatePointers() {
        frontLabel.setVisible(false);
        rearLabel.setVisible(false);

        if (front != -1) {
            Point p = boxes[front].getLocationOnScreen();
            Point frameP = this.getLocationOnScreen();

            int x = p.x - frameP.x + boxes[front].getWidth() / 2 - 50;
            int y = p.y - frameP.y + boxes[front].getHeight() - 30;

            frontLabel.setBounds(x, y, 80, 30);
            frontLabel.setVisible(true);
        }

        if (rear != -1) {
            Point p = boxes[rear].getLocationOnScreen();
            Point frameP = this.getLocationOnScreen();

            int x = p.x - frameP.x + boxes[rear].getWidth() / 2 - 50;
            int y = p.y - frameP.y + boxes[rear].getHeight() - 30;

            if (front == rear) {
                rearLabel.setText("front/rear");
                rearLabel.setForeground(new Color(255, 140, 0));
            } else {
                rearLabel.setText("rear");
                rearLabel.setForeground(Color.BLUE);
            }
            rearLabel.setBounds(x, y, 200, 30);
            rearLabel.setVisible(true);
        }
    }

    private void resetQueue(JPanel queuebox) {
        front = -1;
        rear = -1;
        for (int i = 0; i < SIZE; i++) {
            boxes[i].removeAll();
        }
        frontLabel.setVisible(false);
        rearLabel.setVisible(false);

        queuebox.revalidate();
        queuebox.repaint();
    }

    public Test() {
        setTitle("House Lizard & Gecko");
        setSize(960, 540);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Set Program to Center Sceen
        BackgroundPanel bgPanel = new BackgroundPanel("/images/background.png");
        setContentPane(bgPanel);

        JLabel title = new JLabel("House Lizard & Gecko", SwingConstants.CENTER);
        title.setFont(new Font("Comic Sans MS", Font.BOLD, 54));
        bgPanel.setLayout(new BorderLayout());
        bgPanel.add(title, BorderLayout.NORTH);

        // ====== Radio Buttons ======
        JRadioButton singleBtn = new JRadioButton("Singly circular queue", true);
        JRadioButton doubleBtn = new JRadioButton("Double circular queue");
        ButtonGroup queueType = new ButtonGroup();
        queueType.add(singleBtn);
        queueType.add(doubleBtn);
        singleBtn.setOpaque(false);
        doubleBtn.setOpaque(false);

        JRadioButton enqBtn = new JRadioButton("Enqueue", true);
        JRadioButton deqBtn = new JRadioButton("Dequeue");
        ButtonGroup operation = new ButtonGroup();
        operation.add(enqBtn);
        operation.add(deqBtn);
        enqBtn.setOpaque(false);
        deqBtn.setOpaque(false);

        JPanel leftPanel = new JPanel(new GridLayout(0, 1, 5, 5));
        leftPanel.add(singleBtn);
        leftPanel.add(doubleBtn);
        leftPanel.setOpaque(false);

        JPanel rightPanel = new JPanel(new GridLayout(0, 1, 5, 5));
        rightPanel.add(enqBtn);
        rightPanel.add(deqBtn);
        rightPanel.setOpaque(false);

        JPanel radioPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 20));
        radioPanel.add(leftPanel);
        radioPanel.add(rightPanel);
        radioPanel.setOpaque(false);

        JPanel container = new JPanel();
        container.setOpaque(false);
        container.add(radioPanel);

        // ====== Queue Box ======
        JPanel queuebox = new JPanel(new FlowLayout(FlowLayout.CENTER, 30, 20));
        for (int i = 0; i < boxes.length; i++) {
            boxes[i] = new JPanel(null);
            boxes[i].setPreferredSize(new Dimension(120, 120));
            boxes[i].setBorder(BorderFactory.createLineBorder(Color.BLACK, 3));
            boxes[i].setOpaque(false);

            // สร้าง label ของ index แล้วเก็บไว้
            indexLabels[i] = new JLabel(String.valueOf(i), SwingConstants.CENTER);
            indexLabels[i].setFont(new Font("Comic Sans MS", Font.BOLD, 18));
            indexLabels[i].setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));

            JPanel boxWithLabel = new JPanel();
            boxWithLabel.setLayout(new BoxLayout(boxWithLabel, BoxLayout.Y_AXIS));
            boxWithLabel.setOpaque(false);

            boxWithLabel.add(indexLabels[i]); // ใช้ตัวที่เก็บใน array
            boxWithLabel.add(boxes[i]);

            queuebox.add(boxWithLabel);
        }

        queuebox.setOpaque(false);

        // ====== Add Pointer Labels Once ======
        JLayeredPane layeredPane = getLayeredPane();
        setupPointerLabels(layeredPane);

        // ====== Event เวลาเปลี่ยน Singly/Double ======
        singleBtn.addActionListener(e -> resetQueue(queuebox));
        doubleBtn.addActionListener(e -> resetQueue(queuebox));

        container.add(queuebox);
        bgPanel.add(container, BorderLayout.CENTER);

        // ====== Input + Button ======
        JTextField textfield = new JTextField("Enter a number", 10);
        JButton sentbutton = new JButton("Sent");
        textfield.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (textfield.getText().equals("Enter a number")) {
                    textfield.setText("");
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (textfield.getText().isEmpty()) {
                    textfield.setText("Enter a number");
                }
            }
        });
        JPanel answerPanel = new JPanel(new GridLayout(0, 1, 5, 5));
        answerPanel.add(textfield);
        answerPanel.add(sentbutton);
        JPanel bottom = new JPanel();
        bottom.add(answerPanel);
        bottom.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));
        bottom.setOpaque(false);
        bgPanel.add(bottom, BorderLayout.SOUTH);

// ====== โหลดรูปและย่อให้เล็ก ======
        ImageIcon iconGecko = new ImageIcon(getClass().getResource("/images/geckoShow.png"));
        ImageIcon iconLizard = new ImageIcon(getClass().getResource("/images/lizardShow.png"));

        Image scaledGecko = iconGecko.getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH);
        Image scaledLizard = iconLizard.getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH);

        ImageIcon geckoIcon = new ImageIcon(scaledGecko);
        ImageIcon lizardIcon = new ImageIcon(scaledLizard);

        Random rand = new Random();

// ====== Event ปุ่ม Sent ======
        sentbutton.addActionListener((ActionEvent e) -> {
            try {
                int count = Integer.parseInt(textfield.getText().trim());
                if (enqBtn.isSelected()) { // ---- ENQUEUE ----
                    if ((front == 0 && rear == SIZE - 1) || (rear + 1) % SIZE == front) {
                        JOptionPane.showMessageDialog(this, "Queue is full!");
                        return;
                    }
                    if (front == -1) {
                        front = 0;
                        rear = 0;
                    } else {
                        rear = (rear + 1) % SIZE;
                        // อัปเดตข้อความ index เช่น 0(50)
                        

                    }

                    // เลือกรูปตาม Singly / Double
                    ImageIcon currentIcon = singleBtn.isSelected() ? geckoIcon : lizardIcon;

                    boxes[rear].removeAll();
                    for (int i = 0; i < count; i++) {
                        int maxX = boxes[rear].getPreferredSize().width - currentIcon.getIconWidth();
                        int maxY = boxes[rear].getPreferredSize().height - currentIcon.getIconHeight();
                        int x = rand.nextInt(Math.max(1, maxX));
                        int y = rand.nextInt(Math.max(1, maxY));
                        JLabel label = new JLabel(currentIcon);
                        label.setBounds(x, y, currentIcon.getIconWidth(), currentIcon.getIconHeight());
                        boxes[rear].add(label);
                    }

                } else { // ---- DEQUEUE ----
                    if (front == -1) {
                        JOptionPane.showMessageDialog(this, "Queue is empty!");
                        return;
                    }

                    boxes[front].removeAll();

                    if (front == rear) {
                        front = -1;
                        rear = -1;
                    } else {
                        front = (front + 1) % SIZE;
                        boxes[front].removeAll();

                    }
                    indexLabels[front].setText(String.valueOf(front)); // รีเซ็ต label กลับ
                }

                queuebox.revalidate();
                queuebox.repaint();
                updatePointers();
indexLabels[rear].setText(rear + "(" + count + ")");
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this,
                        "กรุณากรอกตัวเลขที่ถูกต้อง",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        });

        // ====== โหลดรูปสัตว์ ======
        ImageIcon frogIconshow = new ImageIcon(getClass().getResource("/images/gecko.png"));
        ImageIcon lizardIconshow = new ImageIcon(getClass().getResource("/images/lizard.png"));

        Image imgFrog = frogIconshow.getImage().getScaledInstance(150, 150, Image.SCALE_SMOOTH);
        Image imgLizard = lizardIconshow.getImage().getScaledInstance(180, 180, Image.SCALE_SMOOTH);

        JLabel frogLabel = new JLabel(new ImageIcon(imgFrog));
        JLabel lizardLabel = new JLabel(new ImageIcon(imgLizard));

        frogLabel.setBounds(20, getHeight() - 180, 150, 150);
        lizardLabel.setBounds(getWidth() - 200, getHeight() - 195, 200, 200);

        layeredPane.add(frogLabel, JLayeredPane.POPUP_LAYER);
        layeredPane.add(lizardLabel, JLayeredPane.POPUP_LAYER);

        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Test::new);
    }
}
