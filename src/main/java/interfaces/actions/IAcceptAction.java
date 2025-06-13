package interfaces.actions;

public interface IAcceptAction {
    // nome da action usage (ex: “TurnOn”)
    String getName();

    // nome do payload (ex: “TurnOn”)
    String getPayloadName();

    // nome do receptor, se houver (ex: “battery”)
    String getReceiverName();
}