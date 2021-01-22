import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.TokenStream;
import org.junit.Test;

public class Tests {
    @Test
    public void testPowerSub1() {
        LexerLexer lexer = new LexerLexer( CharStreams.fromString(
                "\\C_n^k - 1000 + 10^10"));

        //<i>a</i><sub><i>i</i></sub> = <i>b</i><sub><i>i</i></sub> + <i>x</i><sup>2</sup>
        TokenStream tokens = new CommonTokenStream( lexer );
        LexerParser parser = new LexerParser( tokens );
        LexerVisitor<String> v = new VisitorWeDeserve();
        System.out.println(v.visitText(parser.text()));
        System.out.println("<i>a<sub>i</sub></i>=<i>b<sub>i</sub></i>+<i>x<sup>2</sup></i>");
        assert((v.visitText( parser.text() )).equals("<i>a<sub>i</sub></i>=<i>b<sub>i</sub></i>+<i>x<sup>2</sup></i>"));
    }

    @Test
    public void testPowerSub2() {
        LexerLexer lexer = new LexerLexer( CharStreams.fromString(
                "$a_{i sdfds sdfsdf} = b_i + x^{212312 123 sfsdfs}$"));

        //<i>a</i><sub><i>i</i></sub> = <i>b</i><sub><i>i</i></sub> + <i>x</i><sup>2</sup>
        TokenStream tokens = new CommonTokenStream( lexer );
        LexerParser parser = new LexerParser( tokens );
        LexerVisitor<String> v = new VisitorWeDeserve();
        System.out.println(v.visitText(parser.text()));
        System.out.println("<i>a<sub>i sdfds sdfsdf</sub></i>=<i>b<sub>i</sub></i>+<i>x<sup>212312 123 sfsdfs</sup></i>");
        assert((v.visitText(parser.text())).equals("<i>a<sub>i sdfds sdfsdf</sub></i>=<i>b<sub>i</sub></i>+<i>x<sup>212312 123 sfsdfs</sup></i>"));
    }

    @Test
    public void testPowerSub3() {
        LexerLexer lexer = new LexerLexer( CharStreams.fromString(
                "$a_{123 546}^{654 234}$ = $123$"));

        //<i>a</i><sub><i>i</i></sub> = <i>b</i><sub><i>i</i></sub> + <i>x</i><sup>2</sup>
        TokenStream tokens = new CommonTokenStream( lexer );
        LexerParser parser = new LexerParser( tokens );
        LexerVisitor<String> v = new VisitorWeDeserve();
        System.out.println(v.visitText(parser.text()));
        System.out.println("<i>a<sub>i sdfds sdfsdf</sub></i>=<i>b<sub>i</sub></i>+<i>x<sup>212312 123 sfsdfs</sup></i>");
        assert((v.visitText(parser.text())).equals("<i>a<sub>i sdfds sdfsdf</sub></i>=<i>b<sub>i</sub></i>+<i>x<sup>212312 123 sfsdfs</sup></i>"));
    }
}
