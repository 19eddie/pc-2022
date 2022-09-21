package smart_room.centralized;

import smart_room.Event;

public class AgentSimulator extends Thread{

    public static void main(String[] args) {
        AgentSimulator thread = new AgentSimulator();
        thread.start();
    }

    public void run() {
        SinglelBoardSimulator board = new SinglelBoardSimulator();
        board.init();
        while (true){
            if(board.getLuminosity() < 0.50 && board.presenceDetected()){
                board.on();
            } else {
                board.off();
            }
        }
    }
}
