package Unit_6;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.Timer;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;

public class Culminating extends JPanel implements ActionListener, KeyListener, MouseListener {

    final int WIDTH = 900;
    final int HEIGHT = 650;

    final int FRAMES = 40;
    final int DELTA_TIME = (int) Math.ceil(1000.0 / FRAMES);
    final double PAUSE = 15 * DELTA_TIME / 1000.0;

    final int RESOLUTION = 1;
    final double FOCAL_VIEW = 0.8;

    final int[] LEVEL_TIME = {20, 15, 10, 30}, LEVEL_SIZE = {11, 21, 31, 585};
    final int[] DX = {-2, 2, 0, 0}, DY = {0, 0, -2, 2};
    int[][] MAP;

    int event, forward, back, left, right, turnL, turnR, countdown = -1, mazeTimer, leaderboardIndex;
    double rotation, x, y, dir, dirX, dirY, focalX, focalY, velX, velY, deltaX, deltaY, cellCount;
    double[] camX;

    final String MASTER_FONT = "JetBrains Mono Thin";
    final Color MASTER_COLOUR = new Color(31, 31, 31);
    final String[] BUTTON_NAMES = { "Easy", "Medium", "Hard", "Hard+", "Instructions", "Leaderboard", "Next" };
    JButton[] buttons;
    JLabel titleLabel = new JLabel(), creditsLabel = new JLabel();
    JTextArea textArea = new JTextArea();
    ArrayList<Double> times;

    Timer timer;

    public Culminating() throws IOException, FontFormatException {
        times = new ArrayList<>();

        camX = new double[WIDTH + 1];
        for (int i = 0; i <= WIDTH; i++) {
            camX[i] = 2.0 * i / WIDTH - 1;
        }

        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
        setFocusable(true);
        addKeyListener(this);
        addMouseListener(this);

        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, new File("src/Unit_6/JetBrainsMono-Thin.ttf")));

        titleLabel.setText("Maze Runner");
        titleLabel.setFont(new Font(MASTER_FONT, Font.PLAIN, 60));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        buttons = new JButton[7];
        for (int i = 0; i < 7; i++) {
            buttons[i] = new JButton(BUTTON_NAMES[i]);
            buttons[i].setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(Color.WHITE),
                    BorderFactory.createEmptyBorder(5, 10, 5, 10)));
            buttons[i].setFocusable(false);
            buttons[i].setFocusPainted(false);
            buttons[i].setFont(new Font(MASTER_FONT, Font.PLAIN, 35));
            buttons[i].setForeground(Color.WHITE);
            buttons[i].setBackground(MASTER_COLOUR);
            buttons[i].setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            int PRESS_EVENT = i + 2;
            buttons[i].addMouseListener(new MouseAdapter() {
                @Override
                public void mouseReleased(MouseEvent e) {
                    event = PRESS_EVENT;
                }
            });
            buttons[i].setAlignmentX(Component.CENTER_ALIGNMENT);
        }
        buttons[5].addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                leaderboardIndex = 0;

                textArea.setMaximumSize(new Dimension((int) (0.7 * WIDTH), HEIGHT - 230));
                setLeaderboard();

                removeAll();
                add(Box.createRigidArea(new Dimension(0, HEIGHT / 30)));
                add(titleLabel);
                add(Box.createRigidArea(new Dimension(0, HEIGHT / 30)));
                add(textArea);
                add(buttons[6]);
                revalidate();

                event = 7;
            }
        });
        buttons[6].addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                leaderboardIndex = (leaderboardIndex + 1) % LEVEL_SIZE.length;
                setLeaderboard();
            }
        });

        textArea.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
        textArea.setFocusable(false);
        textArea.setFont(new Font(MASTER_FONT, Font.PLAIN, 30));
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        textArea.setForeground(Color.WHITE);
        textArea.setBackground(MASTER_COLOUR);
        textArea.setAlignmentX(Component.CENTER_ALIGNMENT);

        creditsLabel.setText("Made by Henry Bao");
        creditsLabel.setFont(new Font(MASTER_FONT, Font.PLAIN, 12));
        creditsLabel.setForeground(Color.WHITE);
        creditsLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        addAll();

        timer = new Timer(DELTA_TIME, this);
        timer.start();
    }

    void addAll() {
        removeAll();
        add(Box.createRigidArea(new Dimension(0, HEIGHT / 30)));
        add(titleLabel);
        add(Box.createRigidArea(new Dimension(0, HEIGHT / 25)));
        add(buttons[0]);
        for (int i = 1; i < 6; i++) {
            add(Box.createRigidArea(new Dimension(0, HEIGHT / 30)));
            add(buttons[i]);
        }
        add(Box.createRigidArea(new Dimension(0, HEIGHT / 30)));
        add(creditsLabel);
        revalidate();
    }

    void setLeaderboard() {
        titleLabel.setText("Leaderboard: " + BUTTON_NAMES[leaderboardIndex]);
        StringBuilder leaderboard = new StringBuilder();
        times.clear();
        try {
            Scanner sc = new Scanner(new File("src/Unit_6/Leaderboards/" + LEVEL_SIZE[leaderboardIndex]));
            while (sc.hasNextDouble()) {
                times.add(sc.nextDouble());
            }
            sc.close();
        } catch (IOException ex) {
            ex.printStackTrace();
            System.exit(-1);
        }
        Collections.sort(times);
        for (int i = 0; i < 10; i++) {
            leaderboard.append(String.format("%4s%02d.%24.3f\n", "", i + 1, times.get(i)));
        }
        textArea.setText(leaderboard.toString());
    }

    public static void main(String[] args) throws IOException, FontFormatException {
        JFrame frame = new JFrame("Maze Runner");
        frame.setIconImage(ImageIO.read(new File("src/Unit_6/Icon.png")));
        Culminating panel = new Culminating();
        frame.add(panel, BorderLayout.CENTER);
        frame.pack();
        frame.setLocation(450, 125);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setVisible(true);
    }

    @Override
    public void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        super.paintComponent(g2d);
        if (event == 1) {
            g2d.setColor(MASTER_COLOUR);
            g2d.fillRect(0, 0, WIDTH, HEIGHT / 2);
            g2d.setColor(Color.LIGHT_GRAY);
            g2d.fillRect(0, 325, WIDTH, HEIGHT / 2);
            g2d.setStroke(new BasicStroke(RESOLUTION));
            double pointX = dirY * FOCAL_VIEW, pointY = dirX * -FOCAL_VIEW, rayX = x % 1, rayY = y % 1;
            for (int i = 0; i <= WIDTH; i += RESOLUTION) {
                double rotX = dirX + pointX * camX[i], rotY = dirY + pointY * camX[i], dist, sideX, sideY, distX, distY;
                double rayDeltaX = Math.sqrt(1 + (rotY * rotY) / (rotX * rotX)), rayDeltaY = Math.sqrt(1 + (rotX * rotX) / (rotY * rotY));
                int mapPosX = (int) x, mapPosY = (int) y;
                if (rotX < 0) {
                    sideX = -1;
                    distX = rayX * rayDeltaX;
                } else {
                    sideX = 1;
                    distX = (1 - rayX) * rayDeltaX;
                }
                if (rotY < 0) {
                    sideY = -1;
                    distY = rayY * rayDeltaY;
                } else {
                    sideY = 1;
                    distY = (1 - rayY) * rayDeltaY;
                }
                boolean isX = false;
                while (MAP[mapPosX][mapPosY] == 0) {
                    isX = distX < distY;
                    if (distX < distY) {
                        distX += rayDeltaX;
                        mapPosX += sideX;
                    } else {
                        distY += rayDeltaY;
                        mapPosY += sideY;
                    }
                }
                if (isX) {
                    dist = (mapPosX - x + (1 - sideX) / 2) / rotX;
                    if (MAP[mapPosX][mapPosY] == 2) {
                        g2d.setColor(new Color(74, 160, 74));
                    } else if (MAP[mapPosX][mapPosY] == 3) {
                        g2d.setColor(new Color(170, 58, 58));
                    } else {
                        g2d.setColor(Color.WHITE);
                    }
                } else {
                    dist = (mapPosY - y + (1 - sideY) / 2) / rotY;
                    if (MAP[mapPosX][mapPosY] == 2) {
                        g2d.setColor(new Color(67, 145, 67));
                    } else if (MAP[mapPosX][mapPosY] == 3) {
                        g2d.setColor(new Color(141, 49, 49));
                    } else {
                        g2d.setColor(new Color(239, 239, 239));
                    }
                }
                g2d.drawLine(i, HEIGHT / 2 - (int) ((HEIGHT / 2) / dist), i, HEIGHT / 2 + (int) ((HEIGHT / 2) / dist));
            }
        } else {
            g2d.setColor(new Color(31, 31, 31));
            g2d.fillRect(0, 0, WIDTH, HEIGHT);
            if (event >= 2 && event <= 5) {
                int BLOCK_SIZE = (int) (0.9 * HEIGHT / MAP.length);
                for (int i = 0; i < MAP.length; i++) {
                    for (int j = 0; j < MAP.length; j++) {
                        if ((int) x == i && (int) y == j) {
                            g2d.setColor(new Color(170, 58, 58));
                            g2d.fillRect((WIDTH - (MAP.length - 2 * j) * BLOCK_SIZE) / 2, (HEIGHT - (MAP.length - 2 * i) * BLOCK_SIZE) / 2, BLOCK_SIZE, BLOCK_SIZE);
                        } else if (MAP[i][j] == 0) {
                            g2d.setColor(Color.WHITE);
                            g2d.fillRect((WIDTH - (MAP.length - 2 * j) * BLOCK_SIZE) / 2, (HEIGHT - (MAP.length - 2 * i) * BLOCK_SIZE) / 2, BLOCK_SIZE, BLOCK_SIZE);
                        }
                    }
                }
                g2d.setColor(new Color(74, 160, 74));
                g2d.fillRect((WIDTH - (4 - MAP.length) * BLOCK_SIZE) / 2, (HEIGHT - (4 - MAP.length) * BLOCK_SIZE) / 2, BLOCK_SIZE, BLOCK_SIZE);
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (event == 1) {
            if ((int) x == MAP.length - 2 && (int) y == MAP.length - 2) {
                try {
                    boolean fileExists = new File("src/Unit_6/Leaderboards/" + MAP.length).isFile();
                    PrintWriter pw = new PrintWriter(new FileWriter("src/Unit_6/Leaderboards/" + MAP.length, true));
                    if (!fileExists) {
                        for (int i = 0; i < 10; i++) {
                            pw.println(9999999.0);
                        }
                    }
                    pw.println((double) mazeTimer / FRAMES);
                    pw.close();
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }

                titleLabel.setFont(new Font(MASTER_FONT, Font.PLAIN, 35));
                titleLabel.setText(String.format("You found the exit in %.3f seconds!", (double) mazeTimer / FRAMES));

                creditsLabel.setText("Press ESC to return home.");
                creditsLabel.setFont(new Font(MASTER_FONT, Font.PLAIN, 25));

                removeAll();
                add(Box.createVerticalGlue());
                add(titleLabel);
                add(Box.createRigidArea(new Dimension(0, HEIGHT / 30)));
                add(creditsLabel);
                add(Box.createVerticalGlue());
                revalidate();

                event = 8;
            } else {
                mazeTimer++;
                handleInput();
            }
        } else if (event == 6) {
            titleLabel.setText("Instructions");

            textArea.setText("Your task is to get from the top-left corner of the maze to the bottom-right corner in the shortest time possible.\n\nYou can move by WASD and turn using <- and ->, and receive a hint by pressing H for a 20 second penalty.\n\nPress ESC to return home from any page.");
            textArea.setMaximumSize(new Dimension((int) (0.7 * WIDTH), HEIGHT));

            removeAll();
            add(Box.createRigidArea(new Dimension(0, HEIGHT / 30)));
            add(titleLabel);
            add(Box.createRigidArea(new Dimension(0, HEIGHT / 25)));
            add(textArea);
            revalidate();
        } else if (event >= 2 && event <= 5) {
            if (countdown == -1) {
                removeAll();
                x = 1.1;
                y = 1.1;
                dir = Math.toRadians(45);
                MAP = new int[LEVEL_SIZE[event - 2]][LEVEL_SIZE[event - 2]];
                for (int i = 0; i < LEVEL_SIZE[event - 2]; i++) {
                    Arrays.fill(MAP[i], 1);
                }
                MAP[1][1] = 0;
                cellCount = Math.pow(LEVEL_SIZE[event - 2] / 2, 2) - 1;
                gen();
                MAP[0][1] = 3;
                MAP[1][0] = 3;
                MAP[LEVEL_SIZE[event - 2] - 1][LEVEL_SIZE[event - 2] - 2] = 2;
                MAP[LEVEL_SIZE[event - 2] - 2][LEVEL_SIZE[event - 2] - 1] = 2;
                mazeTimer = 0;
                countdown = LEVEL_TIME[event - 2] * FRAMES;
            } else if (countdown == 0) {
                forward = 0;
                back = 0;
                turnL = 0;
                turnR = 0;

                event = 1;
            }
            countdown--;
        }
        repaint();
    }

    void gen() {
        Random rand = new Random();
        int xx = 1, yy = 1;
        while (cellCount > 0) {
            int dir = rand.nextInt(4);
            if (xx + DX[dir] < 0 || xx + DX[dir] >= LEVEL_SIZE[event - 2] || yy + DY[dir] < 0 || yy + DY[dir] >= LEVEL_SIZE[event - 2]) {
                continue;
            }
            if (MAP[xx + DX[dir]][yy + DY[dir]] == 1) {
                MAP[xx + DX[dir] / 2][yy + DY[dir] / 2] = 0;
                cellCount--;
            }
            xx += DX[dir];
            yy += DY[dir];
            MAP[xx][yy] = 0;
        }
    }

    void handleInput() {
        rotation = (rotation + (turnL - turnR) * Math.toRadians(15)) * 0.48;
        dir += rotation * PAUSE;
        dirX = Math.cos(dir);
        dirY = Math.sin(dir);
        focalX = dirX * 0.25;
        focalY = dirY * 0.25;
        velX = (velX + focalX * (forward - back) + focalY * (right - left)) * 0.42;
        velY = (velY + focalY * (forward - back) - focalX * (right - left)) * 0.42;
        deltaX = velX * PAUSE;
        deltaY = velY * PAUSE;
        move(x + deltaX, y + deltaY);
    }

    void move(double x, double y) {
        if (MAP[(int) x][(int) this.y] == 0) {
            this.x = x;
        } else {
            velX = 0;
        }
        if (MAP[(int) this.x][(int) y] == 0) {
            this.y = y;
        } else {
            velY = 0;
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyPressed(KeyEvent e) {
        if (event == 1) {
            if (e.getKeyChar() == 'w') {
                forward = 1;
            }
            if (e.getKeyChar() == 's') {
                back = 1;
            }
            if (e.getKeyChar() == 'a') {
                left = 1;
            }
            if (e.getKeyChar() == 'd') {
                right = 1;
            }
            if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
                turnR = 1;
            }
            if (e.getKeyCode() == KeyEvent.VK_LEFT) {
                turnL = 1;
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            titleLabel.setText("Maze Runner");
            titleLabel.setFont(new Font(MASTER_FONT, Font.PLAIN, 60));

            creditsLabel.setText("Made by Henry Bao");
            creditsLabel.setFont(new Font(MASTER_FONT, Font.PLAIN, 12));

            addAll();

            countdown = -1;
            event = 0;
        }
        if (event == 1) {
            if (e.getKeyChar() == 'h') {
                mazeTimer += 20 * FRAMES;
                countdown = 3 * FRAMES;
                event = 2;
            } else {
                if (e.getKeyChar() == 'w') {
                    forward = 0;
                }
                if (e.getKeyChar() == 's') {
                    back = 0;
                }
                if (e.getKeyChar() == 'a') {
                    left = 0;
                }
                if (e.getKeyChar() == 'd') {
                    right = 0;
                }
                if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
                    turnR = 0;
                }
                if (e.getKeyCode() == KeyEvent.VK_LEFT) {
                    turnL = 0;
                }
            }
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {}

    @Override
    public void mousePressed(MouseEvent e) {}

    @Override
    public void mouseReleased(MouseEvent e) {
        if (event >= 2 && event <= 5) {
            countdown = 0;
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {}

    @Override
    public void mouseExited(MouseEvent e) {}
}