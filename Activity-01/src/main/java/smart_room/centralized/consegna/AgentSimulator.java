package smart_room.centralized.consegna;

import smart_room.centralized.SinglelBoardSimulator;

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
