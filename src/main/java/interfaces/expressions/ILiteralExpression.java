package interfaces.expressions;

public interface ILiteralExpression extends IExpression {
	
	public String getLiteralType();
	
	public Object getValue();

	public String asText();
}
