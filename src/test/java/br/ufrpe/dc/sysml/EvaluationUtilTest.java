package br.ufrpe.dc.sysml;

import static org.junit.jupiter.api.Assertions.*;

import org.eclipse.emf.common.util.EList;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.omg.sysml.expressions.util.EvaluationUtil;
import org.omg.sysml.lang.sysml.*;

import org.omg.sysml.lang.sysml.SysMLFactory;

class EvaluationUtilTest {

    private static SysMLV2Spec spec;
    private static Namespace root;

    @BeforeAll
    static void loadModel() {
        spec = new SysMLV2Spec();
        spec.parseFile("other/LiteralExpressionsExample.sysml");
        root = (Namespace) spec.getRootNamespace();
        assertNotNull(root);
    }


    // pega um atributo por nome dentro do part Vehicle
    
    private AttributeUsage getAttribute(String name) {
        PartDefinition vehicle = findPartDefinition(root, "Vehicle");
        if (vehicle == null) {
            throw new AssertionError("PartDefinition 'Vehicle' não encontrado no modelo.");
        }

        for (Element e : vehicle.getOwnedMember()) {
            if (e instanceof AttributeUsage au && name.equals(au.getDeclaredName())) {
                return au;
            }
        }

        throw new AssertionError("Attribute '" + name + "' não encontrado no Vehicle.");
    }

    
    private PartDefinition findPartDefinition(Namespace root, String name) {
        if (root instanceof PartDefinition pd && name.equals(pd.getDeclaredName())) {
            return pd;
        }
        for (Element e : root.getOwnedMember()) {
            if (e instanceof Namespace ns) {
                PartDefinition found = findPartDefinition(ns, name);
                if (found != null) return found;
            }
        }
        return null;
    }


    private Expression getAttributeExpression(String attrName) {

        AttributeUsage attr = getAttribute(attrName);

        // Caso do Literal direto no OwnedElement
        for (Element e : attr.getOwnedElement()) {
            if (e instanceof Expression expr) {
                return expr;
            }
        }

        // Caso do FeatureValue
        for (Feature f : attr.getOwnedFeature()) {
            if (f instanceof FeatureValue fv && fv.getOwnedMemberElement() instanceof Expression expr) {
                return expr;
            }
        }

        throw new AssertionError("Nenhuma Expression encontrada para: " + attrName);
    }


    private Expression unwrap(Expression expr) {
        if (expr instanceof OperatorExpression op &&
            "[]".equals(op.getOperator()) &&
            !op.getArgument().isEmpty()) {

            return op.getArgument().get(0);
        }
        return expr;
    }
    
    private void debugElementStructure(Element e, String indent) {
        System.out.println(indent + e.getClass().getSimpleName() + 
                           " — name=" + e.getDeclaredName());
        Namespace nm = e.getOwningNamespace();
        
        // Owned Elements
        for (Element m : e.getOwnedElement()) {
            System.out.println(indent + "  [Element] " + m.getClass().getSimpleName() +
                               " — name=" + m.getDeclaredName());
            debugElementStructure(m, indent + "    ");
        }

//        // Owned Members
//        for (Element m : nm.getOwnedMember()) {
//            System.out.println(indent + "  [Member] " + m.getClass().getSimpleName() +
//                               " — name=" + m.getDeclaredName());
//            debugElementStructure(m, indent + "    ");
//        }
//        
//        // Owned Relationship
//        for (Element m : nm.getOwnedRelationship()) {
//            System.out.println(indent + "  [Member] " + m.getClass().getSimpleName() +
//                               " — name=" + m.getDeclaredName());
//            debugElementStructure(m, indent + "    ");
//        }
        
    }



    @Test
    @DisplayName("valueOf() retorna valores corretos para Literais")
    void testValueOf() {
        LiteralInteger li = SysMLFactory.eINSTANCE.createLiteralInteger();
        li.setValue(42);

        LiteralBoolean lb = SysMLFactory.eINSTANCE.createLiteralBoolean();
        lb.setValue(true);

        LiteralRational lr = SysMLFactory.eINSTANCE.createLiteralRational();
        lr.setValue(3.14);

        LiteralString ls = SysMLFactory.eINSTANCE.createLiteralString();
        ls.setValue("abc");

        assertEquals(42, EvaluationUtil.valueOf(li));
        assertEquals(true, EvaluationUtil.valueOf(lb));
        assertEquals(3.14, EvaluationUtil.valueOf(lr));
        assertEquals("abc", EvaluationUtil.valueOf(ls));
    }


    // ---------------------------------------------------------
    @Test
    @DisplayName("elementFor() converte Java primitives → Literal Expressions")
    void testElementFor() {
        Element e1 = EvaluationUtil.elementFor(10);
        Element e2 = EvaluationUtil.elementFor(true);
        Element e3 = EvaluationUtil.elementFor("car");

        assertTrue(e1 instanceof LiteralInteger);
        assertTrue(e2 instanceof LiteralBoolean);
        assertTrue(e3 instanceof LiteralString);

        assertEquals(10, ((LiteralInteger) e1).getValue());
        assertEquals(true, ((LiteralBoolean) e2).isValue());
        assertEquals("car", ((LiteralString) e3).getValue());
    }


    @Test
    @DisplayName("numberOfArgs() em OperatorExpression do maxLoad")
    void testNumberOfArgs() {
        Expression expr = getAttributeExpression("maxLoad");

        assertTrue(expr instanceof OperatorExpression);

        OperatorExpression op = (OperatorExpression) expr;

        int args = EvaluationUtil.numberOfArgs(op);

        // operador "[]" deve ter dois argumentos: valor e unidade
        assertEquals(2, args); 
    }


    @Test
    @DisplayName("evaluate() retorna literal correto (speed = 120.0)")
    void testEvaluateLiteral() {
        AttributeUsage speed = getAttribute("speed");

        System.out.println("\n=== DEBUG speed structure ===");
        debugElementStructure(speed, "");

        Expression expr = getAttributeExpression("speed");

        var result = EvaluationUtil.evaluate(expr, speed);
        assertEquals(1, result.size());
        expr = unwrap(expr);

        //EList<Element> result = EvaluationUtil.evaluate(expr, root);

        assertEquals(1, result.size());
        Element r = result.get(0);

        assertTrue(r instanceof LiteralRational);
        assertEquals(120.0, ((LiteralRational) r).getValue());
    }


    @Test
    @DisplayName("expressionFor() reconstrói expressão a partir de valores")
    void testExpressionFor() {
        // cria lista de elementos
        EList<Element> list = EvaluationUtil.integerResult(7);

        Expression expr = EvaluationUtil.expressionFor(list, root);

        assertNotNull(expr);
        assertTrue(expr instanceof LiteralInteger);
        assertEquals(7, ((LiteralInteger) expr).getValue());
    }
}
