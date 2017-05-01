package Models.Answer;

import java.io.File;

/**
 * Created by dennisvermeulen on 20-03-17.
 *
 * Used for questions where the answer will be an image of some description.
 */
public class ImageAnswer extends Answer<File> {
    public ImageAnswer(String name, boolean isCorrectAnswer) {
        super(name, isCorrectAnswer);
    }

    @Override
    public File ShowAnswer() {
        return null;
    }

}
