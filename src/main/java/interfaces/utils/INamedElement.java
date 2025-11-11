package interfaces.utils;

public interface INamedElement extends IElement {
	// herdado por elementos que podem apresentar um nome
	public String getName(); // no adaptador, pegar das três formas possíveis
	
	public String getDeclaredName();
	
	// String getEffectiveName(); // pode retornar null

	//
	// String getDescription(); // lógica do String describe();
}
