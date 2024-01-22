package test.view.timer;

import main.com.kh.view.timer.MainView;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.assertj.core.api.Assertions.assertThat;
import static test.constant.Constant.*;

class MainViewTest {

  protected ByteArrayInputStream in;
  protected ByteArrayOutputStream out;
  private MainView mainView;

  @BeforeEach
  public void setUp() {
    out = new ByteArrayOutputStream();
    System.setOut(new PrintStream(out));
    mainView = new MainView();
  }

  public void setInput(String input) {
    in = new ByteArrayInputStream(input.getBytes());
    System.setIn(in);

  }

  @AfterEach
  public void tearDown() {
    System.setOut(System.out);
    System.setIn(System.in);
  }

  public String mainView() {
    return MAIN_TITLE + LINE +
            MAIN_CREATE + LINE +
            MAIN_START + LINE +
            MAIN_READ_ALL + LINE +
            MAIN_UPDATE + LINE +
            MAIN_DELETE + LINE +
            MAIN_EXIT + LINE +
            MAIN_INPUT_MENU + LINE;
  }

  @Test
  public void mainViewTest() {
    // Given
    String input = """
            9
            """;
    setInput(input);

    // When
    mainView.main();
    String actual = out.toString();

    // Then
    String expected = mainView();
    assertThat(actual).isEqualTo(expected);
  }
}