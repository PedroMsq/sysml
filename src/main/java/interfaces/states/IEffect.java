package interfaces.states;

import org.omg.sysml.lang.sysml.ActionUsage;

public interface IEffect {
    String getEffectType();
    ActionUsage getAction(); //alterar para adaptador de ActionUsage
}
