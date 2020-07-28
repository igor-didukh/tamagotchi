package main;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;

public class TextFieldFilter extends DocumentFilter {
    private final String bannedSymbols = "[^0123456789]";
    
    @Override
    public void insertString(DocumentFilter.FilterBypass fb, int offset, String string, AttributeSet attr) throws BadLocationException {
        string = string.replaceAll(bannedSymbols, "");
        super.insertString(fb, offset, string, attr);
    }

    @Override
    public void replace(DocumentFilter.FilterBypass fb, int offset, int length, String text, AttributeSet attrs) throws BadLocationException {
        text = text.replaceAll(bannedSymbols, "");
        super.replace(fb, offset, length, text, attrs);
    }
}