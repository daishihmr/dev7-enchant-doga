package jp.dev7.enchant.doga.converter.exception;

import java.io.File;
import java.io.IOException;

@SuppressWarnings("serial")
public class UnsupportedImageTypeException extends IOException {
    public UnsupportedImageTypeException(String message, File file) {
        super(message + " (" + file.getAbsolutePath() + ")");
    }
}
