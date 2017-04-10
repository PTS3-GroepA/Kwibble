import Data.Repos.AnswerRepository;
import Models.Answer.*;
import org.junit.Test;
import Data.Context.*;

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