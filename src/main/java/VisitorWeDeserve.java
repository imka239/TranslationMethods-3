import java.util.ArrayList;
import java.util.List;

public class VisitorWeDeserve extends LexerBaseVisitor<String> {
    private List<String> modDel = new ArrayList<>();
    private List<String> modAdd = new ArrayList<>();

    private String toStr(List<String> lst) {
        StringBuilder s = new StringBuilder();
        lst.forEach(s::append);
        return s.toString();
    }

    @Override
    public String visitText(LexerParser.TextContext ctx) {
        StringBuilder result = new StringBuilder();
        if (ctx.C() != null) {
            //C UNDERDASH WORD POWER WORD
            // <msubsup>
            //    <mi>C</mi>
            //    <mi>n</mi>
            //    <mi>k</mi>
            //  </msubsup>
            result.append("<msubsup>");
            result.append("<mi>");
            result.append(ctx.C().getText());
            result.append("</mi>");
            for (int i = 0; i < 2; i++) {
                result.append("<mi>");
                result.append(ctx.WORD().get(i).getText());
                result.append("</mi>");
            }
            result.append("</msubsup>");
            result.append(visitText(ctx.text()));
        } else if (ctx.DOLLAR() != null) {
            result.append("<i>");
            modDel.add(0, "</i>");
            modAdd.add(0, "<i>");
            result.append(visitTexttodollar(ctx.texttodollar()));
            result.append(visitText(ctx.text()));
        } else if (ctx.normal_operation() != null) {
            result.append(toStr(modDel));
            result.append(ctx.normal_operation().getText());
            result.append(toStr(modAdd));
            result.append(visitText(ctx.text()));
        } else if (ctx.strange_operation() != null) {
            if (ctx.FIGUREOPEN().isEmpty()) {
                if (ctx.strange_operation().POWER() != null) {
                    result.append("<sup>");
                    result.append(ctx.WORD().get(0).getText());
                    result.append("</sup>");
                    result.append(visitText(ctx.text()));
                } else if (ctx.strange_operation().UNDERDASH() != null) {
                    result.append("<sub>");
                    result.append(ctx.WORD().get(0).getText());
                    result.append("</sub>");
                    result.append(visitText(ctx.text()));
                }
            } else {
                if (ctx.strange_operation().POWER() != null) {
                    result.append("<sup>");
                    result.append(visitTexttofigureclose(ctx.texttofigureclose().get(0)));
                    result.append("</sup>");
                    result.append(visitText(ctx.text()));
                } else if (ctx.strange_operation().UNDERDASH() != null) {
                    result.append("<sub>");
                    result.append(visitTexttofigureclose(ctx.texttofigureclose().get(0)));
                    result.append("</sub>");
                    result.append(visitText(ctx.text()));
                }
            }
        } else if (!ctx.WORD().isEmpty()) {
            result.append(ctx.WORD().get(0).getText());
            String next = (visitText(ctx.text()));
            addSpace(result, next);
        } else {
            //todo
        }
        return result.toString();
    }

    private void addSpace(StringBuilder result, String next) {
        if (next.length() > 0 && ((next.charAt(0) >= '0' && next.charAt(0) <= '9') ||
                (next.charAt(0) >= 'a' && next.charAt(0) <= 'z') ||
                (next.charAt(0) >= 'A' && next.charAt(0) <= 'Z'))) {
            result.append(" ");
        }
        result.append(next);
    }

    @Override
    public String visitTexttodollar(LexerParser.TexttodollarContext ctx) {
        StringBuilder result = new StringBuilder();
        if (ctx.DOLLAR() != null) {
            modDel.remove("</i>");
            modAdd.remove("<i>");
            result.append("</i>");
            return result.toString();
        } else if (ctx.normal_operation() != null) {
            result.append(toStr(modDel));
            result.append(ctx.normal_operation().getText());
            result.append(toStr(modAdd));
            result.append(visitTexttodollar(ctx.texttodollar()));
        } else if (ctx.strange_operation() != null) {
            if (ctx.FIGUREOPEN().isEmpty()) {
                if (ctx.strange_operation().POWER() != null) {
                    result.append("<sup>");
                    result.append(ctx.WORD().getText());
                    result.append("</sup>");
                    result.append(visitTexttodollar(ctx.texttodollar()));
                } else if (ctx.strange_operation().UNDERDASH() != null) {
                    result.append("<sub>");
                    result.append(ctx.WORD().getText());
                    result.append("</sub>");
                    result.append(visitTexttodollar(ctx.texttodollar()));
                }
            } else {
                if (ctx.strange_operation().POWER() != null) {
                    result.append("<sup>");
                    result.append(visitTexttofigureclose(ctx.texttofigureclose().get(0)));
                    result.append("</sup>");
                    result.append(visitTexttodollar(ctx.texttodollar()));
                } else if (ctx.strange_operation().UNDERDASH() != null) {
                    result.append("<sub>");
                    result.append(visitTexttofigureclose(ctx.texttofigureclose().get(0)));
                    result.append("</sub>");
                    result.append(visitTexttodollar(ctx.texttodollar()));
                }
            }
        } else if (ctx.WORD() != null) {
            result.append(ctx.WORD().getText());
            String next = (visitTexttodollar(ctx.texttodollar()));
            addSpace(result, next);
        } else {
            //todo
        }
        return result.toString();
    }

    @Override
    public String visitTexttofigureclose(LexerParser.TexttofigurecloseContext ctx) {
        StringBuilder result = new StringBuilder();
        if (ctx.FIGURECLOSE() != null) {
            return result.toString();
        } else if (ctx.normal_operation() != null) {
            result.append(toStr(modDel));
            result.append(ctx.normal_operation().getText());
            result.append(toStr(modAdd));
            result.append(visitTexttofigureclose(ctx.texttofigureclose().get(0)));
        } else if (ctx.strange_operation() != null) {
            if (ctx.FIGUREOPEN().isEmpty()) {
                if (ctx.strange_operation().POWER() != null) {
                    result.append("<sup>");
                    result.append(ctx.WORD().getText());
                    result.append("</sup>");
                    result.append(visitTexttofigureclose(ctx.texttofigureclose().get(0)));
                } else if (ctx.strange_operation().UNDERDASH() != null) {
                    result.append("<sub>");
                    result.append(ctx.WORD().getText());
                    result.append("</sub>");
                    result.append(visitTexttofigureclose(ctx.texttofigureclose().get(0)));
                }
            } else {
                if (ctx.strange_operation().POWER() != null) {
                    result.append("<sup>");
                    result.append(visitTexttofigureclose(ctx.texttofigureclose().get(0)));
                    result.append("</sup>");
                    result.append(visitTexttofigureclose(ctx.texttofigureclose().get(1)));
                } else if (ctx.strange_operation().UNDERDASH() != null) {
                    result.append("<sub>");
                    result.append(visitTexttofigureclose(ctx.texttofigureclose().get(0)));
                    result.append("</sub>");
                    result.append(visitTexttofigureclose(ctx.texttofigureclose().get(1)));
                }
            }
        } else if (ctx.WORD() != null) {
            result.append(ctx.WORD().getText());
            String next = visitTexttofigureclose(ctx.texttofigureclose().get(0));
            addSpace(result, next);
        } else {
            //todo
        }
        return result.toString();
    }
}
