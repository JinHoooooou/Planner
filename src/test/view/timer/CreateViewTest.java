package test.view.timer;

import main.com.kh.view.timer.CreateView;
import org.junit.jupiter.api.Test;

import java.util.Scanner;

import static org.assertj.core.api.Assertions.assertThat;
import static test.constant.Constant.*;

public class CreateViewTest extends MainViewTest {

  private CreateView createView;

  @Override
  public void setInput(String input) {
    super.setInput(input);
    createView = new CreateView(timerController, new Scanner(System.in));
  }

  @Test
  public void createViewCase1Test() {
    // Case: Timer title은 입력하고
    // And: hour/minute/second는 입력하지 않는 상황
    // Given
    String input = """
            title
            N
            9
            """;
    setInput(input);

    // When
    createView.execute();
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
    // Case: Timer title을 입력하고
    // And: hour/minute/second를 모두 입력하는 상황
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
    createView.execute();
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
    // Case: Timer title을 입력하고
    // And: hour/minute/second는 입력하지 않는데, (Y/N)입력을 한번 잘못한 상황
    // Given
    String input = """
            title
            INVALID_INPUT
            N
            9
            """;
    setInput(input);

    // When
    createView.execute();
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
    // Case: Timer의 title을 입력하고
    // And: hour/minute/second도 입력하는데 (Y/N)입력을 한번 잘못한 상황
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
    createView.execute();
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
