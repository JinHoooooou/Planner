package test.view.timer;

import main.com.kh.controller.TimerController;
import main.com.kh.view.timer.CreateView;
import org.junit.jupiter.api.Test;

import java.util.Scanner;

import static org.assertj.core.api.Assertions.assertThat;
import static test.constant.Constant.*;

public class CreateViewTest extends MainViewTest {

  private CreateView createView;

  public void setInput(String input) {
    super.setUp();
    super.setInput(input);
    createView = new CreateView(new TimerController(), new Scanner(System.in));
  }

  @Test
  public void createViewCase1Test() {
    // Given
    String input = """
            title
            N
            9
            """;
    setInput(input);

    // When
    createView.main();
    String actual = out.toString();

    // Then
    String expected = CREATE_TITLE + LINE +
            CREATE_INPUT_TITLE + LINE +
            CREATE_INPUT_DEFAULT + LINE +
            CREATE_RESULT_SUCCESS + LINE;
    assertThat(actual).isEqualTo(expected);
  }

  @Test
  public void createViewCase2Test() {
    // Given
    String input = """
            title
            Y
            0
            20
            0
            9
            """;
    setInput(input);

    // When
    createView.main();
    String actual = out.toString();

    //Then
    String expected =
            CREATE_TITLE + LINE +
                    CREATE_INPUT_TITLE + LINE +
                    CREATE_INPUT_DEFAULT + LINE +
                    CREATE_INPUT_HOUR + LINE +
                    CREATE_INPUT_MINUTE + LINE +
                    CREATE_INPUT_SECOND + LINE +
                    CREATE_RESULT_SUCCESS + LINE;
    assertThat(actual).isEqualTo(expected);
  }

  @Test
  public void createViewCase3Test() {
    // Given
    String input = """
            title
            INVALID_INPUT
            N
            9
            """;
    setInput(input);

    // When
    createView.main();
    String actual = out.toString();

    // Then
    String expected = CREATE_TITLE + LINE +
            CREATE_INPUT_TITLE + LINE +
            CREATE_INPUT_DEFAULT + LINE +
            CREATE_INPUT_DEFAULT_ERROR + LINE +
            CREATE_INPUT_DEFAULT + LINE +
            CREATE_RESULT_SUCCESS + LINE;
    assertThat(actual).isEqualTo(expected);
  }

  @Test
  public void createViewCase4Test() {
    // Given
    String input = """
            title
            INVALID_INPUT
            Y
            0
            13
            0
            9
            """;
    setInput(input);

    // When
    createView.main();
    String actual = out.toString();

    // Then
    String expected = CREATE_TITLE + LINE +
            CREATE_INPUT_TITLE + LINE +
            CREATE_INPUT_DEFAULT + LINE +
            CREATE_INPUT_DEFAULT_ERROR + LINE +
            CREATE_INPUT_DEFAULT + LINE +
            CREATE_INPUT_HOUR + LINE +
            CREATE_INPUT_MINUTE + LINE +
            CREATE_INPUT_SECOND + LINE +
            CREATE_RESULT_SUCCESS + LINE;
    assertThat(actual).isEqualTo(expected);
  }
}
