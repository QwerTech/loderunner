package org.qwertech.loderunner;

import org.qwertech.loderunner.api.GameBoard;
import org.qwertech.loderunner.api.LoderunnerAction;
import org.qwertech.loderunner.api.LoderunnerBase;

import java.net.URISyntaxException;
import java.util.function.Function;

public class LodeRunnerClient extends LoderunnerBase {

    private Function<GameBoard, LoderunnerAction> callback;

    public LodeRunnerClient(String url) throws URISyntaxException {
        super(url);
    }

    public void run(Function<GameBoard, LoderunnerAction> callback) {
        connect();
        this.callback = callback;
    }

    @Override
    protected String doMove(GameBoard gameBoard) {
        LoderunnerAction action = callback.apply(gameBoard);
        return loderunnerActionToString(action);
    }


    public void initiateExit() {
        setShouldExit(true);
    }
}
