package Data.Repos;

import Data.Answer;
import Data.Context.Interfaces.Answerable;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by dennisvermeulen on 27-03-17.
 */
public class AnswerRepositoryTest{

    @Test
    public void Test(){

        AnswerRepository ansrep = new AnswerRepository(new MySQLMusicContext());

        List<Answer> answers = new ArrayList<>();

        for(Answer ans:ansrep.getAllAnswers()){
            answers.add(ans);
        }

        assertEquals(answers, answers);
    }

}