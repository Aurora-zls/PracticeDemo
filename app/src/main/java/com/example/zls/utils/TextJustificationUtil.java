package com.example.zls.utils;

import android.graphics.Paint;
import android.text.TextUtils;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;

public class TextJustificationUtil {

    public static void justify(TextView textView, float contentWidth, String textContent) {
        String tempText;
        StringBuilder resultText = new StringBuilder();
        Paint paint = textView.getPaint();

        ArrayList<String> paraList;
        paraList = paraBreak(textContent);
        for (int i = 0; i < paraList.size(); i++) {
            ArrayList<String> lineList = lineBreak(paraList.get(i).trim(), paint, contentWidth);
            tempText = TextUtils.join(" ", lineList).trim();
            resultText.append(tempText).append("\n");
        }
        textView.setText(resultText.toString());
    }

    //分开每个段落
    private static ArrayList<String> paraBreak(String text) {
        String[] paraArray = text.split("\\n+");
        return new ArrayList<>(Arrays.asList(paraArray));
    }

    //分开每一行，使每一行填入最多的单词数
    private static ArrayList<String> lineBreak(String text, Paint paint, float contentWidth) {
        String[] wordArray = text.split("\\s");
        ArrayList<String> lineList = new ArrayList<>();
        String myText = "";

        for (String word : wordArray) {
            if (paint.measureText(myText + " " + word) <= contentWidth) {
                myText = myText + " " + word;
            } else {
                int totalSpacesToInsert = (int) ((contentWidth - paint.measureText(myText)) / paint
                        .measureText(" "));
                lineList.add(justifyLine(myText, totalSpacesToInsert));
                myText = word;
            }
        }
        lineList.add(myText);
        return lineList;
    }

    //已填入最多单词数的一行，插入对应的空格数直到该行满
    private static String justifyLine(String text, int totalSpacesToInsert) {
        String[] wordArray = text.split("\\s");
        if (wordArray.length == 1) {
            return text;
        }
        String toAppend = " ";

        while ((totalSpacesToInsert) > (wordArray.length - 1)) {
            toAppend = toAppend + " ";
            totalSpacesToInsert = totalSpacesToInsert - (wordArray.length - 1);
        }
        int i = 0;
        String justifiedText = "";
        for (String word : wordArray) {
            if (i < totalSpacesToInsert) {
                justifiedText = justifiedText + word + " " + toAppend;
            } else if (i < (wordArray.length - 1)) {
                justifiedText = justifiedText + word + toAppend;
            } else {
                justifiedText = justifiedText + word;
            }

            i++;
        }

        return justifiedText;
    }

}

