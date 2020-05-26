public class MineSweeperMain  implements Runnable {
    GUIZ gui = new GUIZ();
    public static void main(String[] args) {
        new Thread(new MineSweeperMain()).start();
    }

    @Override
    public void run() {
        while (true) {
            gui.repaint();
            if (gui.reset == false) {
                gui.StatusOfVictory();
            }
        }

    }
}

