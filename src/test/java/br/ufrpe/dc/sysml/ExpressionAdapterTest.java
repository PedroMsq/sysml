package br.ufrpe.dc.sysml;

import static org.junit.jupiter.api.Assertions.*;

import java.lang.Class;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.omg.sysml.lang.sysml.*;

import adapters.attributes.AttributeUsageAdapter;
import adapters.expressions.*;
import interfaces.expressions.IExpression;
import interfaces.expressions.ILiteralExpression;

class ExpressionAdapterTest {

    private static SysMLV2Spec sysmlSpec;
    private static Namespace rootNamespace;

    @BeforeAll
    static void setUp() {
        sysmlSpec = new SysMLV2Spec();
        sysmlSpec.parseFile("other/LiteralExpressionsExample.sysml");
        rootNamespace = sysmlSpec.getRootNamespace();
        assertNotNull(rootNamespace, "O namespace raiz não deve ser nulo.");
    }

    // -------------------------------------------------------------
    // MÉTODOS AUXILIARES
    // -------------------------------------------------------------

//	private FeatureValue getFeatureValue(AttributeUsage au) {
//        return au.getOwnedFeature().stream()
//                .filter(f -> f instanceof FeatureValue)
//                .map(f -> (FeatureValue) f)
//                .findFirst()
//                .orElseThrow(() -> new AssertionError("FeatureValue não encontrado para " + au.getDeclaredName()));
//    }

    private AttributeUsage findAttribute(String name) {
        return findAttributeRecursive(rootNamespace, name)
            .orElseThrow(() -> new AssertionError("Attribute '" + name + "' não encontrado."));
    }

    private Optional<AttributeUsage> findAttributeRecursive(Element element, String name) {

        // 1. Se o elemento atual for um AttributeUsage, verifica o nome
        if (element instanceof AttributeUsage au &&
            name.equals(au.getDeclaredName())) {
            return Optional.of(au);
        }

        // 2. Se for Namespace, desce na hierarquia
        if (element instanceof Namespace ns) {
            for (Element member : ns.getOwnedMember()) {
                Optional<AttributeUsage> found = findAttributeRecursive(member, name);
                if (found.isPresent()) return found;
            }
        }

        return Optional.empty();
    }


    private Expression getLiteralFrom(AttributeUsage au) {

        // 1 — Procura FeatureValue diretamente (caso mais comum)
        for (Feature f : au.getOwnedFeature()) {
            if (f instanceof FeatureValue fv) {
                if (fv.getOwnedMemberElement() instanceof Expression expr) {
                    return expr;
                }
            }
        }

        // 2 — Procura valores em OwnedMember (caminho comum em PartDefinition)
        for (Element member : au.getOwnedMember()) {
            if (member instanceof FeatureValue fv) {
                if (fv.getOwnedMemberElement() instanceof Expression expr) {
                    return expr;
                }
            }
        }

        // 3 — Procura valores via OwnedRelationship (SysML às vezes guarda aqui)
        for (Element rel : au.getOwnedRelationship()) {
            if (rel instanceof FeatureValue fv) {
                if (fv.getOwnedMemberElement() instanceof Expression expr) {
                    return expr;
                }
            }
        }

        throw new AssertionError("FeatureValue não encontrado para: " + au.getDeclaredName());
    }


    // TESTES


	@Test
	@DisplayName("LiteralExpression – speed = 120.0")
	void testLiteralRational() {
	    AttributeUsage speed = findAttribute("speed");
	    Expression literal = getLiteralFrom(speed);

	    ILiteralExpression adapter = (ILiteralExpression) ExpressionAdapter.of(literal);

	    assertEquals("LiteralRationalImpl", adapter.getLiteralType());
	    assertEquals(120.0, adapter.getValue());
	    System.out.println("speed = " + adapter.asText());
	}

	@Test
	@DisplayName("LiteralExpression – serialNumber = 123456")
	void testLiteralInteger() {
	    AttributeUsage serial = findAttribute("serialNumber");
	    Expression literal = getLiteralFrom(serial);

	    ILiteralExpression adapter = (ILiteralExpression) ExpressionAdapter.of(literal);

	    assertEquals("LiteralIntegerImpl", adapter.getLiteralType());
	    assertEquals(123456, adapter.getValue());
	    System.out.println("serialNumber = " + adapter.asText());
	}

	@Test
	@DisplayName("LiteralExpression – isElectric = true")
	void testLiteralBoolean() {
	    AttributeUsage electric = findAttribute("isElectric");
	    Expression literal = getLiteralFrom(electric);

	    ILiteralExpression adapter = (ILiteralExpression) ExpressionAdapter.of(literal);

	    assertEquals("LiteralBooleanImpl", adapter.getLiteralType());
	    assertEquals(true, adapter.getValue());
	    System.out.println("isElectric = " + adapter.asText());
	}


	@Test
	void testSerialNumberViaAdapter() {
	    AttributeUsage attr = findAttribute("serialNumber");
	    AttributeUsageAdapter adapter = new AttributeUsageAdapter(attr);

	    IExpression expr = adapter.getDefaultValue()
	                              .orElseThrow();

	    assertTrue(expr instanceof ILiteralExpression);
	    ILiteralExpression lit = (ILiteralExpression) expr;

	    assertEquals("LiteralIntegerImpl", lit.getLiteralType());
	    assertEquals(123456, lit.getValue());
	}
}
