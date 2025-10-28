package interfaces.utils;

public interface INamedElement {
	// herdado por elementos que podem apresentar um nome
	String getName(); // no adaptador, pegar das três formas possíveis
	
	// String getEffectiveName(); // pode retornar null
	
	String getDefinition(); // pode retornar null
	//
	// String getDescription(); // lógica do String describe();
	
	// getDeclaredName no adaptador, ou getEffectiveName
}
