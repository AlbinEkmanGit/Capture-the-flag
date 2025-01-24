package se.liu.albek052.schack;

/**
 * Used by ChessComponent for receiving update requests from board
 * @see Board#notifyListeners()
 */
public interface BoardListener
{
    public void boardChanged();
}
