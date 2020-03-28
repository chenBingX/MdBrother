import com.intellij.codeInsight.completion.*;
import com.intellij.codeInsight.lookup.LookupElementBuilder;
import com.intellij.patterns.PlatformPatterns;
import com.intellij.util.ProcessingContext;
import sun.net.www.http.HttpClient;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class MdCodeCompletion extends CompletionContributor {
    public MdCodeCompletion() {
        extend(CompletionType.BASIC, PlatformPatterns.psiElement(), new CompletionProvider<CompletionParameters>() {
            @Override
            protected void addCompletions(CompletionParameters parameters, ProcessingContext context, CompletionResultSet resultSet) {
                List<String> values = new ArrayList<>();
                if (parameters.getPosition().getLanguage().getID().equals("TEXT")) {
                    String document = parameters.getEditor().getDocument().getText();
                    String[] _nSplite = document.split("\n");
                    for (String s0 : _nSplite) {
                        String[] spaceSplite = s0.split(" ");
                        for (String s1 : spaceSplite){
                                String value = s1;
                                if (!RegexUtils.isMatch(RegexUtils.REGEX_SYMBOL, value) && !"".equals(value) && !isContainer(values, value)) {
                                    values.add(value);
                                }
                        }
                    }
                }
                System.out.println("values = " + values.toString());
                for (String s : values) {
                    // 构建LookupElement，并且加入到 CompletionResultSet 中
                    resultSet.addElement(LookupElementBuilder.create(s)
                            // 使用简单的InsertHandler就好
                            .withInsertHandler(new DefaultInsertHandler())
                            // 让它高亮
                            .withBoldness(true)
                    );
                }
            }
        });
    }

    private boolean isContainer(List<String> list, String s) {
        for (String temp : list) {
            if (temp.equals(s)) return true;
        }
        return false;
    }
}
