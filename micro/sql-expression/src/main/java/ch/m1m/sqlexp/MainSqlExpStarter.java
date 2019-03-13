package ch.m1m.sqlexp;

import net.sf.jsqlparser.JSQLParserException;
import net.sf.jsqlparser.expression.*;
import net.sf.jsqlparser.expression.operators.arithmetic.Addition;
import net.sf.jsqlparser.expression.operators.arithmetic.Multiplication;
import net.sf.jsqlparser.expression.operators.conditional.AndExpression;
import net.sf.jsqlparser.expression.operators.conditional.OrExpression;
import net.sf.jsqlparser.expression.operators.relational.EqualsTo;
import net.sf.jsqlparser.parser.CCJSqlParserUtil;
import net.sf.jsqlparser.schema.Column;
import net.sf.jsqlparser.util.deparser.ExpressionDeParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Stack;

public class MainSqlExpStarter {

    private static Logger log = LoggerFactory.getLogger(MainSqlExpStarter.class);

    public static void main(String... args) {
        int exitCode = 0;
        int nIterations = 1;

        log.info("starting");

        for (int ii = 0; ii < nIterations; ii++) {

            try {
                log.info("calling...");

                parse_1();

                // OK
                //evaluateAdd("1+3*5");

                log.info("calling returned");

            } catch (Exception e) {
                log.info("ERROR: ", e);
                exitCode = 1;
            }
        }

        System.exit(exitCode);
    }

    private static void parse_1() throws JSQLParserException {
        String inExpr = "Cust_A = 'toto' and Cust_B = 10004000 or (Cust_X = 'true' and Cust_Y = 'false')";
        inExpr = "D = 5.5 and (A = NULL or b = 'b') and c = 'c'";
        evaluateCond(inExpr);
    }

    static void evaluateCond(String expr) throws JSQLParserException {
        final Stack<String> stack = new Stack();
        Expression parseExpression = CCJSqlParserUtil.parseCondExpression(expr, false);
        ExpressionDeParser deparser = new ExpressionDeParser() {

            public void visit(AndExpression andExpression) {
                String left = andExpression.getLeftExpression().toString();
                String right = andExpression.getRightExpression().toString();
                log.info("AndExpression: left={} right={}", left, right);
                super.visit(andExpression);
                log.info("return AndExpression: left={} right={}", left, right);
            }

            public void visit(OrExpression orExpression) {
                log.info("OrExpression: {}", orExpression.toString());
                super.visit(orExpression);
                log.info("return OrExpression: {}", orExpression.toString());
            }

            @Override
            public void visit(Parenthesis parenthesis) {
                log.info("Parenthesis: {}", parenthesis.toString());
                super.visit(parenthesis);
                log.info("return Parenthesis: {}", parenthesis.toString());
            }

            // handle 'data' items
            //
            @Override
            public void visit(Column inItem) {
                super.visit(inItem);
                String value = inItem.getColumnName();
                log.info("item Column: {}", value);
                stack.push(value);
                //log.info("return item Column: {}", value);
            }

            @Override
            public void visit(LongValue inItem) {
                String value = inItem.getStringValue();
                log.info("item LongValue: {}", value);
                super.visit(inItem);
                stack.push(value);
            }

            @Override
            public void visit(StringValue inItem) {
                String value = inItem.getValue();
                log.info("item StringValue: {}", value);
                super.visit(inItem);
                stack.push(value);
            }

            @Override
            public void visit(DoubleValue inItem) {
                String value = inItem.toString();
                log.info("item DoubleValue: {}", value);
                super.visit(inItem);
                stack.push(value);
            }

            @Override
            public void visit(NullValue inItem) {
                String value = inItem.toString();
                log.info("item NullValue: {}", value);
                super.visit(inItem);
                stack.push(value);
                //log.info("return item NullValue: {}", value);
            }

            // handle operators
            //
            @Override
            public void visit(EqualsTo equalsTo) {
                log.info("operator EqualsTo called: {}", equalsTo.toString());
                super.visit(equalsTo);
                String right = stack.pop();
                String left = stack.pop();
                log.info("return operator EqualsTo called: {}", equalsTo.toString());
            }

        };
        StringBuilder b = new StringBuilder();
        deparser.setBuffer(b);

        log.info(expr);
        parseExpression.accept(deparser);
        log.info("after parsing: {}", b.toString());

    }

    static void evaluateAdd(String expr) throws JSQLParserException {
        final Stack<Long> stack = new Stack<Long>();
        System.out.println("expr=" + expr);
        Expression parseExpression = CCJSqlParserUtil.parseExpression(expr);
        ExpressionDeParser deparser = new ExpressionDeParser() {
            @Override
            public void visit(Addition addition) {
                log.info("visit Addition");
                super.visit(addition);

                long sum1 = stack.pop();
                long sum2 = stack.pop();

                stack.push(sum1 + sum2);
            }

            @Override
            public void visit(Multiplication multiplication) {
                log.info("visit Multiplication");
                super.visit(multiplication);

                long fac1 = stack.pop();
                long fac2 = stack.pop();

                stack.push(fac1 * fac2);
            }

            @Override
            public void visit(LongValue longValue) {
                super.visit(longValue);
                stack.push(longValue.getValue());
            }
        };
        StringBuilder b = new StringBuilder();
        deparser.setBuffer(b);
        parseExpression.accept(deparser);

        System.out.println(expr + " = " + stack.pop() );
    }

}
