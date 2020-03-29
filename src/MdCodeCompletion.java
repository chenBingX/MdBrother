import com.intellij.codeInsight.completion.*;
import com.intellij.codeInsight.lookup.LookupElementBuilder;
import com.intellij.patterns.PlatformPatterns;
import com.intellij.util.ProcessingContext;

import java.util.ArrayList;
import java.util.List;

public class MdCodeCompletion extends CompletionContributor {
    public MdCodeCompletion() {
        extend(CompletionType.BASIC, PlatformPatterns.psiElement(), new CompletionProvider<CompletionParameters>() {
            @Override
            protected void addCompletions(CompletionParameters parameters, ProcessingContext context, CompletionResultSet resultSet) {
                try {
                    List<String> values = new ArrayList<>();
                    if (parameters.getOriginalFile().getVirtualFile().getName().endsWith(".md")) {
                        String document = parameters.getEditor().getDocument().getText();
                        String[] _nSplite = document.split("\n");
                        for (String s0 : _nSplite) {
                            String[] spaceSplite = RegexUtils.getReplaceAll(s0, RegexUtils.REGEX_SYMBOL, " ").split(" ");
                            for (String s1 : spaceSplite) {
                                String value = s1;
                                if (value.length() > 1
                                        && !RegexUtils.isMatch(RegexUtils.REGEX_SYMBOL, value)
                                        && !isContainer(values, value)) {
                                    values.add(value);
                                }
                            }
                        }
                    }
//                    System.out.println("values = " + values.toString());
                    for (String s : values) {
                        // 构建LookupElement，并且加入到 CompletionResultSet 中
                        resultSet.addElement(LookupElementBuilder.create(s)
                                // 使用简单的InsertHandler就好
                                .withInsertHandler(new DefaultInsertHandler())
                                // 让它高亮
                                .withBoldness(true)
                        );
                    }
                } catch (Exception e) {
                    e.printStackTrace();
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
