package interfaces.control;

import java.util.List;


public interface IJoinNode {

    String getName();

    List<String> getInputNames();

    List<String> getOutputNames();

    String describe();
}